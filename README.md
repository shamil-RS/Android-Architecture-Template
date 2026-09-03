# Android Architecture Template

Production-ready, modular Android architecture template inspired by Google's *Now in Android* guidelines. Designed for modern teams who want to build scalable applications without spending days on boilerplate configuration.

## Key Architectural Features

**Strict Multi-Modularity:** Isolated layers divided into `:core`, `:feature`, and custom `:build-logic` convention plugins.
**Reactive State Management:** Unidirectional Data Flow (UDF) powered by Kotlin Coroutines, Flows, and a standardized `Result<T>` wrapper.
**Custom Design System (`AppTheme`):** Decoupled from rigid Material 3 defaults to give you full control over custom typography, colors, and sizes with preview scaling support.
**Automated Package Renaming:** Built-in Python script (`init_project.py`) to instantly rebrand the template to your own namespace.
**CI/CD Integration:** Pre-configured GitHub Actions workflow for automated linting, testing, and debugging builds.

## 🚀 Quick Start (Automated Setup)

This repository is configured as a **GitHub Template**. Click the **"Use this template"** button above to create a copy of this architecture in your own profile[cite: 2].

After cloning the project, to avoid manually changing package structures across all modules, run the built-in automation script directly in the Android Studio **Terminal**[cite: 2]:

```bash
py init_project.py com.yourbrand.app

```

### What the Script Does:

1. Automatically replaces all references to the base package across `build.gradle.kts`, `AndroidManifest.xml`, and Kotlin source files.
2. Restructures physical folder paths on disk (supporting both `java` and `kotlin` directories).
3. Protects your custom Gradle Convention Plugins and design system (`ui.theme`) from broken imports.

💡 *If you make a typo in the package name, don't worry: the script is fault-tolerant. You can re-run the command with the correct name, and it will automatically rewrite the project over the previous typo. Once project configuration succeeds and you click **Sync Now** in Android Studio, the `init_project.py` file can be safely deleted from the root directory.*

---

## 🛠️ CI/CD & Automation

Out of the box, the project includes a pre-configured **GitHub Actions** pipeline (`.github/workflows/ci.yml`) that automatically performs the following actions on every push or Pull Request:

* Builds the project (`assembleDebug`).
* Runs static code analysis (`lintDebug`).
* Executes unit tests (`testDebugUnitTest`)