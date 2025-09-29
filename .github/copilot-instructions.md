# Copilot Instructions for CustomIntentApp

> **CustomIntentApp** is an Android application that handles custom intents and returns structured responses. It processes text input from other applications and returns various data types (String, Integer, Float, Boolean, Object, List) based on the requested action.

## 🛠️ Technology Stack
- **Primary Language**: Kotlin (version 1.9.10+)
- **UI Framework**: Jetpack Compose
- **Build System**: Gradle with Kotlin DSL
- **Android SDK**: 
  - Minimum SDK: 28 (Android 9.0)
  - Target SDK: 36 (Android 15)
  - Compile SDK: 36

## 🏗️ Architecture Patterns
- **Primary Architecture**: MVVM (Model-View-ViewModel)
- **State Management**: 
  - Use sealed classes for UI states (see `IntentUiState`)
  - Use sealed classes for UI events (see `IntentUiEvent`)
  - Use StateFlow for reactive state management
- **Asynchronous Programming**: Kotlin Coroutines with Flow
- **UI Architecture**: 
  - Single Activity with Jetpack Compose
  - ViewModels for business logic
  - Compose UI for declarative UI

## 📱 Android Development Guidelines

### Intent Handling
- This app specializes in custom intent processing
- Primary intent actions supported:
  - `Intent.ACTION_SEND` for text sharing
  - Custom actions like `PROCESS_RETURN_VALUE_ACTION`
- Return different data types: String, Integer, Float, Boolean, Object, List of Objects

### UI Development
- Use Material Design 3 components
- Follow Jetpack Compose best practices:
  - Stateless composables when possible
  - Lift state up to appropriate level
  - Use `remember` and `rememberSaveable` appropriately
- Theme system: Custom theme with Color, Type, and Theme files

### ViewModel Patterns
- Extend Android's ViewModel class
- Use StateFlow for exposing UI state
- Handle events through sealed event classes
- Implement proper lifecycle management

## 🔧 Build Configuration

### Gradle Setup
- Use Gradle version catalog (`gradle/libs.versions.toml`)
- Kotlin DSL for build scripts
- Manual Compose compiler configuration (avoiding kotlin-compose plugin for stability)

### Dependencies Management
- AndroidX Core KTX: Latest stable
- Lifecycle Runtime KTX: For ViewModel support
- Activity Compose: For Compose integration
- Compose BOM: For version alignment
- Material3: For design components

### Troubleshooting
- Refer to `BUILD_TROUBLESHOOTING.md` for common build issues
- Android Gradle Plugin resolution may require specific repository configuration
- Use manual Compose compiler setup if kotlin-compose plugin fails

## 🧪 Testing Strategy

### Unit Testing
- Location: `app/src/test/java/`
- Use JUnit 4 for unit tests
- Test ViewModels and business logic
- Mock external dependencies

### Instrumented Testing  
- Location: `app/src/androidTest/java/`
- Use AndroidJUnit for instrumented tests
- Test UI interactions with Compose testing framework
- Use Espresso for legacy view interactions

### Testing Guidelines
- Write tests for all public methods in ViewModels
- Test state transitions in sealed classes
- Verify intent handling logic
- Test Compose UI components

## 🚀 CI/CD Integration

### Automated Workflows
The repository includes comprehensive GitHub Actions workflows:
- **ci.yml** - Continuous Integration (build, test, lint)
- **instrumented-tests.yml** - Android instrumented testing
- **pr-validation.yml** - Pull request validation
- **codeql.yml** - Security code scanning
- **issue-triage.yml** - Automatic issue labeling
- **release.yml** - Release automation

### Quality Gates
- All unit tests must pass
- Build validation required  
- Lint checks with no critical issues
- Security scans must pass
- Code review required for changes
- Instrumented tests (optional but recommended)

### Workflow Triggers
- **Push to main**: Full CI pipeline
- **Pull requests**: Validation and testing
- **Releases**: Automated APK building
- **Issues**: Auto-labeling and triage

## 📋 Code Style Guidelines

### Kotlin Conventions
- Follow official Kotlin coding conventions
- Use descriptive variable and function names
- Prefer sealed classes over enums for state representation
- Use data classes for simple data containers

### Compose Conventions
- Group related composables in the same file
- Use descriptive @Composable function names
- Follow single responsibility principle
- Use proper state hoisting patterns

### File Organization
- Package by feature when appropriate
- Group related classes in logical packages:
  - `ui/screen/` - Compose screens
  - `ui/viewmodel/` - ViewModels
  - `ui/state/` - State and event classes
  - `ui/theme/` - Theme-related components

## 🔍 Common Patterns

### State Management Pattern
```kotlin
// State definition
sealed class UiState {
    object Loading : UiState()
    data class Success(val data: Data) : UiState()
    data class Error(val message: String) : UiState()
}

// Event definition  
sealed class UiEvent {
    data class Action(val param: String) : UiEvent()
}
```

### ViewModel Pattern
```kotlin
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Action -> handleAction(event.param)
        }
    }
}
```

## 📦 Project Structure
```
app/src/main/java/org/dhis2/customintent/
├── MainActivity.kt                 # Entry point, handles intents
├── ui/
│   ├── screen/
│   │   └── IntentScreen.kt        # Main Compose screen
│   ├── viewmodel/
│   │   └── MainViewModel.kt       # Main business logic
│   ├── state/
│   │   └── IntentUiState.kt       # State and event definitions
│   └── theme/                     # Theme components
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
```

## 🎯 Intent Handling Specifics

### Supported Intent Actions
- `Intent.ACTION_SEND` - Standard Android sharing
- `PROCESS_RETURN_VALUE_ACTION` - Custom processing
- `ACTION_RETURN_*` - Various return type actions

### Return Types Supported
```kotlin
enum class ExtraReturnType {
    STRING,      // Simple text response
    INTEGER,     // Numeric integer
    FLOAT,       // Floating point number
    BOOLEAN,     // True/false value
    OBJECT,      // JSON object
    LIST_OF_OBJECTS // JSON array
}
```

### Intent Processing Flow
1. MainActivity receives intent
2. MainViewModel processes intent data
3. UI state updates based on processing
4. User can modify response
5. Result returned to calling app

## 🔒 Security Considerations
- Validate all intent extras before processing
- Sanitize text input to prevent injection attacks
- Use proper intent filters in AndroidManifest.xml
- Implement proper permission checks if needed
- Log security-relevant events for debugging

## 🧰 Development Tools & Commands

### Build Commands
```bash
./gradlew build                 # Full build
./gradlew assembleDebug        # Debug APK
./gradlew test                 # Unit tests
./gradlew connectedAndroidTest # Instrumented tests
./gradlew lint                 # Lint checks
```

### Common Development Tasks
- Testing intent handling: Use ADB to send test intents
- UI testing: Use Compose UI testing framework
- Performance: Monitor with Android Studio Profiler
- Debugging: Enable detailed logging in debug builds

## 📖 Documentation References
- [Android Intents Documentation](https://developer.android.com/guide/components/intents-filters)
- [Jetpack Compose Guidelines](https://developer.android.com/jetpack/compose/documentation)
- [MVVM Architecture Guide](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)

## 🚨 Important Notes
- This app handles custom Android intents - maintain compatibility with existing intent contracts
- Follow Android security best practices for intent handling  
- Ensure proper data validation for all intent extras
- Maintain backward compatibility for supported Android versions
- Always test intent handling on physical devices when possible
- The app uses manual Compose compiler configuration for build stability
- Refer to CI_IMPLEMENTATION.md for CI/CD workflow details