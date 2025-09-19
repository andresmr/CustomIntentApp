# Contributing to Custom Intent App

Thank you for your interest in contributing to the Custom Intent App! 🎉

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Making Changes](#making-changes)
- [Pull Request Process](#pull-request-process)
- [Coding Standards](#coding-standards)
- [Testing Guidelines](#testing-guidelines)
- [Reporting Issues](#reporting-issues)

## Code of Conduct

This project adheres to a code of conduct. By participating, you are expected to uphold this code. Please be respectful and professional in all interactions.

## Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/CustomIntentApp.git
   cd CustomIntentApp
   ```
3. **Add the upstream remote**:
   ```bash
   git remote add upstream https://github.com/andresmr/CustomIntentApp.git
   ```

## Development Setup

### Prerequisites

- **Android Studio Hedgehog or later**
- **JDK 17 or higher**
- **Android SDK with API level 28+**
- **Git**

### Setup Steps

1. **Open the project** in Android Studio
2. **Sync Gradle** files
3. **Run the app** to ensure everything works:
   ```bash
   ./gradlew installDebug
   ```

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest

# Lint checks
./gradlew lint
```

## Making Changes

### Branch Naming Convention

- `feature/description` - for new features
- `bugfix/description` - for bug fixes
- `docs/description` - for documentation updates
- `refactor/description` - for code refactoring

### Commit Message Format

```
type(scope): description

[optional body]

[optional footer]
```

**Types:**
- `feat`: new feature
- `fix`: bug fix
- `docs`: documentation changes
- `style`: code style changes (formatting, etc.)
- `refactor`: code refactoring
- `test`: adding or updating tests
- `chore`: maintenance tasks

**Examples:**
```
feat(ui): add dark mode support
fix(intent): resolve crash when processing large text
docs(readme): update installation instructions
```

## Pull Request Process

1. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes** following the coding standards

3. **Test your changes**:
   ```bash
   ./gradlew test
   ./gradlew lint
   ./gradlew build
   ```

4. **Commit your changes**:
   ```bash
   git add .
   git commit -m "feat(scope): your change description"
   ```

5. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request** on GitHub

### PR Requirements

- [ ] All tests pass
- [ ] Code follows Kotlin conventions
- [ ] Changes are documented
- [ ] PR description is clear and complete
- [ ] Related issues are referenced

## Coding Standards

### Kotlin Style Guide

Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html):

- Use **4 spaces** for indentation
- **PascalCase** for class names
- **camelCase** for function and variable names
- **UPPER_SNAKE_CASE** for constants

### Architecture Guidelines

- Follow **MVVM architecture**
- Use **Jetpack Compose** for UI
- Use **Kotlin Coroutines** for async operations
- Use **sealed classes** for state management

### Code Organization

```
app/src/main/java/org/dhis2/customintent/
├── ui/
│   ├── screen/          # Compose screens
│   ├── viewmodel/       # ViewModels
│   ├── state/           # UI state classes
│   └── theme/           # Compose theme
├── data/                # Data layer
├── domain/              # Domain layer (if needed)
└── utils/               # Utility classes
```

## Testing Guidelines

### Unit Tests

- Test all business logic
- Use **JUnit 4** and **Mockk** for mocking
- Aim for **80%+ code coverage**
- Place tests in `src/test/kotlin/`

### Instrumented Tests

- Test UI interactions
- Use **Espresso** for UI testing
- Test on **API 28+**
- Place tests in `src/androidTest/kotlin/`

### Test Naming Convention

```kotlin
fun `should return success when input is valid`() {
    // Test implementation
}
```

## Reporting Issues

### Before Creating an Issue

1. **Search existing issues** to avoid duplicates
2. **Test with the latest version**
3. **Provide detailed information**

### Issue Types

- **🐛 Bug Report** - Something isn't working
- **✨ Feature Request** - New functionality
- **📚 Documentation** - Documentation improvements

## Code Review Process

1. **Automated checks** must pass (CI/CD)
2. **Manual review** by maintainers
3. **Address feedback** and update PR
4. **Approval** required before merge

## Recognition

Contributors will be:
- **Listed in CONTRIBUTORS.md**
- **Credited in release notes**
- **Acknowledged in commit messages**

## Getting Help

- **Discussions**: Use GitHub Discussions for questions
- **Issues**: Create an issue for bugs or feature requests
- **Email**: Contact maintainers for private matters

## License

By contributing, you agree that your contributions will be licensed under the same license as the project (MIT License).

---

Thank you for contributing! 🚀