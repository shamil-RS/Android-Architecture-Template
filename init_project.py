import os
import re
import shutil
import sys

def find_current_project_package(root_dir):
    """Dynamically finds the current base package specified in the app module."""
    app_gradle_path = os.path.join(root_dir, "app", "build.gradle.kts")
    if not os.path.exists(app_gradle_path):
        app_gradle_path = os.path.join(root_dir, "settings.gradle.kts")

    if os.path.exists(app_gradle_path):
        try:
            with open(app_gradle_path, 'r', encoding='utf-8') as f:
                content = f.read()
            match = re.search(r'namespace\s*=\s*"([a-zA-Z0-9_.]+)"', content)
            if match:
                detected = match.group(1)
                # FIX: If namespace contains sub-packages like .app, strip it to get the base package
                if detected.endswith(".app"):
                    return detected[:-4]
                return detected
        except Exception:
            pass
    return "com.architecturetemplate"

def fix_gradle_content(content, old_package, new_package):
    """Isolates the plugins block and convention imports from auto-replacement."""
    plugins_block_match = re.search(r'(plugins\s*\{.*?\})', content, re.DOTALL)
    if plugins_block_match:
        original_plugins_block = plugins_block_match.group(1)
        content = content.replace(original_plugins_block, "<!--GRADLE_PLUGINS_BLOCK_HOLDER-->")
    else:
        original_plugins_block = None

    convention_imports = re.findall(r'(import\s+[\w.]+\.convention\..+)', content)
    for i, imp in enumerate(convention_imports):
        placeholder = f"<!--CONVENTION_IMPORT_HOLDER_{i}-->"
        content = content.replace(imp, placeholder)

    # Safely replace the dynamically found old package
    content = content.replace(old_package, new_package)
    content = content.replace("com.architecturetemplate", new_package)

    for i, imp in enumerate(convention_imports):
        placeholder = f"<!--CONVENTION_IMPORT_HOLDER_{i}-->"
        content = content.replace(placeholder, imp)

    if original_plugins_block:
        content = content.replace("<!--GRADLE_PLUGINS_BLOCK_HOLDER-->", original_plugins_block)

    return content

def main():
    if len(sys.argv) < 2:
        print("❌ Please specify the new package name. Example: py init_project.py com.test.successapp")
        sys.exit(1)

    new_package = sys.argv[1].strip()
    root_dir = os.path.dirname(os.path.abspath(__file__))
    script_name = "init_project.py"
    marker_file = os.path.join(root_dir, ".template_configured")

    old_base_package = find_current_project_package(root_dir)

    if old_base_package == new_package:
        print(f"ℹ️ The project is already configured for the package {new_package}. No further action is required.")
        sys.exit(0)

    print(f"🚀 Detected current project package: {old_base_package}")
    print(f"🔄 Reconfiguring the project for the new package: {new_package}...")

    # STAGE 1: Physical directory restructuring on disk (Moved to first place)
    source_folders = ["java", "kotlin"]
    source_types = ["main", "test", "androidTest"]
    target_suffixes = [os.path.join("src", s_type, s_folder) for s_folder in source_folders for s_type in source_types]

    for dirpath, dirnames, _ in os.walk(root_dir):
        if any(p in dirpath for p in ['.gradle', '.git', 'build', '.idea']): continue

        if any(dirpath.endswith(suffix) for suffix in target_suffixes):
            old_rel_path = os.path.join(*old_base_package.split('.'))
            full_old_path = os.path.join(dirpath, old_rel_path)
            alternative_old_path = os.path.join(dirpath, *"com.architecturetemplate".split('.'))

            paths_to_check = [full_old_path, alternative_old_path]

            for path_to_move in paths_to_check:
                if os.path.exists(path_to_move) and os.path.isdir(path_to_move):
                    new_rel_path = os.path.join(*new_package.split('.'))
                    full_new_path = os.path.join(dirpath, new_rel_path)

                    os.makedirs(full_new_path, exist_ok=True)
                    for item in os.listdir(path_to_move):
                        shutil.move(os.path.join(path_to_move, item), os.path.join(full_new_path, item))

    # STAGE 2: Text replacement across all files (Runs after folders are moved)
    extensions_to_update = ('.kt', '.java', '.kts', '.xml', '.pro', '.properties', '.md', '.txt')

    for dirpath, _, filenames in os.walk(root_dir):
        if any(p in dirpath for p in ['.gradle', '.git', 'build', '.idea']): continue
        for fname in filenames:
            if fname.endswith(extensions_to_update) and fname != script_name:
                fpath = os.path.join(dirpath, fname)
                try:
                    with open(fpath, 'r', encoding='utf-8') as f:
                        content = f.read()

                    if fname.endswith('.gradle.kts'):
                        content = fix_gradle_content(content, old_base_package, new_package)
                    else:
                        content = content.replace(old_base_package, new_package)
                        content = content.replace("com.architecturetemplate", new_package)

                    with open(fpath, 'w', encoding='utf-8') as f:
                        f.write(content)
                except Exception:
                    pass

    # STAGE 3: Cleaning up empty directories
    for dirpath, dirnames, _ in os.walk(root_dir, topdown=False):
        if any(p in dirpath for p in ['.gradle', '.git', 'build', '.idea']): continue
        if not os.listdir(dirpath):
            if not any(dirpath.endswith(sub) for sub in ["src", "main", "java", "kotlin", "res", "test", "androidTest"]):
                try: os.rmdir(dirpath)
                except Exception: pass

    # STAGE 4: Control marker
    with open(marker_file, 'w', encoding='utf-8') as f:
        f.write("configured")

    # STAGE 5: Automatic Build & Cache Cleanup (Prevents "outside the root directory" build errors)
    print("🧹 Cleaning up old build artifacts and Gradle caches...")
    for dirpath, dirnames, _ in os.walk(root_dir, topdown=False):
        # Delete local module 'build' directories
        if 'build' in dirnames:
            build_path = os.path.join(dirpath, 'build')
            try:
                shutil.rmtree(build_path)
            except Exception:
                pass
        # Delete root '.gradle' cache folder where transforms are kept
        if '.gradle' in dirnames and dirpath == root_dir:
            gradle_cache_path = os.path.join(dirpath, '.gradle')
            try:
                shutil.rmtree(gradle_cache_path)
            except Exception:
                pass

    print("\n💡 Hint: If you made a typo in the package name, you can run the command")
    print(f"again right now, for example: py {script_name} com.test.correct_name")
    print("The script will automatically detect your previous typo and rename the project correctly!")
    print("The script file will be deleted automatically during the next successful Gradle sync or manually.\n")
    print("🎉 Success! The project has been successfully reconfigured.")

if __name__ == "__main__":
    main()