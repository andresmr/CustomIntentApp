# Contributing to CustomIntentApp

Thank you for your interest in contributing to CustomIntentApp! This document provides guidelines and instructions for contributing to the project.

## 🚀 Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/your-username/CustomIntentApp.git
   cd CustomIntentApp
   ```
3. **Create a new branch** for your feature or bug fix:
   ```bash
   git checkout -b feature/your-feature-name
   ```

## 🛠️ Development Guidelines

### Code Style and Standards

- **Language**: Use Kotlin following official Kotlin coding conventions
- **Architecture**: Follow MVVM pattern with clear separation of concerns
- **UI**: Use Jetpack Compose for all UI components
- **State Management**: Use sealed classes and StateFlow for reactive state management
- **Async Operations**: Use Kotlin Coroutines for asynchronous programming

### Code Organization

```
app/src/main/java/org/dhis2/customintent/
├── MainActivity.kt                    # Entry point
├── ui/
│   ├── screen/                       # Compose screens
│   ├── state/                        # State management
│   ├── viewmodel/                    # ViewModels
│   └── theme/                        # UI theming
```

### Naming Conventions

- **Classes**: PascalCase (e.g., `MainActivity`, `IntentScreen`)
- **Functions**: camelCase (e.g., `processIntent`, `onEvent`)
- **Variables**: camelCase (e.g., `uiState`, `responseText`)
- **Constants**: SCREAMING_SNAKE_CASE (e.g., `REQUEST_CODE`)

## 🧪 Testing

### Unit Tests
- Write unit tests for ViewModels and business logic
- Place tests in `app/src/test/java/`
- Use JUnit 4 for testing framework
- Run tests with: `./gradlew test`

### UI Tests
- Write UI tests for Compose screens
- Place tests in `app/src/androidTest/java/`
- Use Compose testing libraries
- Run tests with: `./gradlew connectedAndroidTest`

### Example Test Structure
```kotlin
class MainViewModelTest {
    @Test
    fun `processIntent should update state with received text`() {
        // Arrange
        val viewModel = MainViewModel()
        val intent = Intent().apply {
            action = "org.dhis2.customintent.ACTION_PROCESS_TEXT"
            putExtra(Intent.EXTRA_TEXT, "test text")
        }
        
        // Act
        viewModel.processIntent(intent)
        
        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is IntentUiState.Success)
        assertEquals("test text", (state as IntentUiState.Success).receivedText)
    }
}
```

## 📝 Commit Guidelines

### Commit Message Format
Use conventional commit format:
```
type(scope): description

[optional body]

[optional footer]
```

### Types
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code formatting changes
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Build process or tool changes

### Examples
```
feat(ui): add dark theme support
fix(intent): handle null text in intent processing
docs(readme): update installation instructions
test(viewmodel): add tests for intent processing
```

## 🔄 Pull Request Process

1. **Update your branch** with the latest main:
   ```bash
   git checkout main
   git pull upstream main
   git checkout your-feature-branch
   git rebase main
   ```

2. **Run tests** and ensure they pass:
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest
   ```

3. **Create a pull request** with:
   - Clear, descriptive title
   - Detailed description of changes
   - Reference to any related issues
   - Screenshots for UI changes

4. **Address review feedback** promptly

## 🐛 Bug Reports

When reporting bugs, please include:

- **Android version** and device information
- **App version** or commit hash
- **Steps to reproduce** the issue
- **Expected behavior**
- **Actual behavior**
- **Logs or screenshots** if applicable

## 💡 Feature Requests

For feature requests, please provide:

- **Clear description** of the feature
- **Use case** or problem it solves
- **Proposed implementation** (if any)
- **Alternative solutions** considered

## 📋 Code Review Checklist

Before submitting a PR, ensure:

- [ ] Code follows Kotlin conventions
- [ ] MVVM architecture is maintained
- [ ] All tests pass
- [ ] New features have tests
- [ ] Documentation is updated
- [ ] No unused imports or variables
- [ ] Proper error handling
- [ ] Compose best practices followed

## 🤝 Community Guidelines

- Be respectful and inclusive
- Provide constructive feedback
- Help others learn and grow
- Focus on the code, not the person
- Welcome newcomers and answer questions

## 📞 Getting Help

- **Issues**: Create an issue for bugs or feature requests
- **Discussions**: Use GitHub Discussions for questions
- **Code Review**: Request reviews from maintainers

Thank you for contributing to CustomIntentApp! 🎉