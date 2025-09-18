# Custom Intent App

An Android application built with Kotlin and Jetpack Compose that demonstrates custom intent handling and inter-app communication. This app can receive text data from other applications, process it, and return responses through Android's intent system.

## 🚀 Features

- **Custom Intent Handling**: Supports both standard Android intents (`ACTION_SEND`) and custom intents (`org.dhis2.customintent.ACTION_PROCESS_TEXT`)
- **Text Processing**: Receives and displays text data from external applications
- **Interactive Response**: Allows users to craft custom responses that are returned to the calling app
- **Modern UI**: Built with Jetpack Compose and Material 3 design
- **MVVM Architecture**: Clean architecture implementation with proper state management
- **Dark/Light Theme**: Supports dynamic theming and system theme preferences

## 📱 How It Works

The app acts as a text processing service that other applications can invoke. When another app sends text to this app:

1. The app receives the intent with text data
2. Displays the received text in a user-friendly interface
3. Provides a text input field for the user to enter a response
4. Returns the response data back to the calling application

## 🛠️ Technical Specifications

- **Language**: Kotlin 2.2.0
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Minimum SDK**: Android 9.0 (API level 28)
- **Target SDK**: Android 15 (API level 36)
- **Build System**: Gradle with Kotlin DSL

### Dependencies

- **AndroidX Core KTX**: 1.16.0
- **Lifecycle Runtime KTX**: 2.9.2
- **Activity Compose**: 1.10.1
- **Compose BOM**: 2025.07.00
- **Material 3**: Latest version from Compose BOM

## 🏗️ Architecture

The app follows the MVVM (Model-View-ViewModel) pattern:

```
📁 app/src/main/java/org/dhis2/customintent/
├── 📁 ui/
│   ├── 📁 screen/
│   │   └── IntentScreen.kt          # Main UI screen
│   ├── 📁 state/
│   │   └── IntentUiState.kt         # UI state management
│   ├── 📁 theme/
│   │   ├── Theme.kt                 # Material 3 theming
│   │   ├── Color.kt                 # Color definitions
│   │   └── Type.kt                  # Typography definitions
│   └── 📁 viewmodel/
│       └── MainViewModel.kt         # Business logic and state
└── MainActivity.kt                  # Entry point and intent handling
```

### Key Components

- **MainActivity**: Entry point that handles incoming intents and sets up the UI
- **MainViewModel**: Manages app state and processes intent data using Kotlin Coroutines
- **IntentScreen**: Jetpack Compose UI that displays received text and response input
- **IntentUiState**: Sealed classes for state management (Loading, Success, Error)

## 📋 Supported Intent Actions

### Standard Android Intents
- `ACTION_SEND` with `text/plain` MIME type

### Custom Intents
- `org.dhis2.customintent.ACTION_PROCESS_TEXT` - Custom action for text processing

The app registers these intent filters in the AndroidManifest.xml and can be invoked by other applications using:

```kotlin
val intent = Intent("org.dhis2.customintent.ACTION_PROCESS_TEXT").apply {
    putExtra(Intent.EXTRA_TEXT, "Your text here")
    putExtra("string2", "Additional data")
    putExtra("boolean", true)
    putExtra("integer", 42)
}
startActivityForResult(intent, REQUEST_CODE)
```

## 🔧 Setup and Installation

### Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or higher
- Android SDK with API level 28+

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/andresmr/CustomIntentApp.git
   cd CustomIntentApp
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository folder

3. **Build the project**
   ```bash
   ./gradlew clean build
   ```

4. **Run on device/emulator**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

## 📖 Usage

### As a Standalone App
Launch the app directly from the launcher. It will display a message indicating no text was received and provide an interface to enter response text.

### Via Intent from Another App
Other applications can invoke this app by sending intents with text data:

```kotlin
// Example: Sending text to CustomIntentApp
val intent = Intent().apply {
    action = "org.dhis2.customintent.ACTION_PROCESS_TEXT"
    putExtra(Intent.EXTRA_TEXT, "Process this text")
    putExtra("additionalData", "Extra information")
}

if (intent.resolveActivity(packageManager) != null) {
    startActivityForResult(intent, REQUEST_CODE)
}
```

### Receiving Response
The calling app can receive the response in `onActivityResult`:

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    
    if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
        val response = data?.getStringExtra("CUSTOM_RESPONSE")
        // Process the response
    }
}
```

## 🧪 Testing

The project includes both unit and instrumented tests:

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Test Files
- `ExampleUnitTest.kt` - Basic unit test example
- `ExampleInstrumentedTest.kt` - Basic instrumented test example

## 🤝 Contributing

We welcome contributions! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Follow Kotlin coding conventions
4. Use meaningful commit messages
5. Add tests for new functionality
6. Ensure all tests pass
7. Submit a pull request

### Code Style
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Add KDoc comments for public APIs
- Maintain MVVM architecture patterns

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🐛 Issues and Support

If you encounter any issues or have questions:

1. Check existing [Issues](https://github.com/andresmr/CustomIntentApp/issues)
2. Create a new issue with detailed information
3. Include device information, Android version, and steps to reproduce

## 🔮 Future Enhancements

- [ ] Support for additional MIME types (images, files)
- [ ] Plugin system for custom text processors
- [ ] Response templates and saved responses
- [ ] Integration with cloud services
- [ ] Accessibility improvements
- [ ] Internationalization (i18n) support

## 👥 Authors

- **andresmr** - *Initial work* - [andresmr](https://github.com/andresmr)

## 🙏 Acknowledgments

- Built with [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Follows [Material Design 3](https://m3.material.io/) guidelines
- Uses [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)