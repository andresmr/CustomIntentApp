# Build Troubleshooting Guide

This document provides solutions for common build issues in the Custom Intent App project.

## 🚨 Kotlin Compose Plugin Resolution Issues

### Problem
```
Plugin [id: 'org.jetbrains.kotlin.plugin.compose', version: 'X.X.X'] was not found in any of the following sources
```

### Root Cause
The Kotlin Compose plugin introduced in Kotlin 1.8.20+ may not be available for certain versions or may require specific repository configuration.

### Solutions

#### Option 1: Remove Kotlin Compose Plugin (Recommended for older versions)
If using Kotlin 1.9.10 or facing plugin resolution issues:

1. **Remove from `gradle/libs.versions.toml`**:
   ```toml
   [plugins]
   android-application = { id = "com.android.application", version.ref = "agp" }
   kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
   # Remove this line: kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
   ```

2. **Remove from root `build.gradle.kts`**:
   ```kotlin
   plugins {
       alias(libs.plugins.android.application) apply false
       alias(libs.plugins.kotlin.android) apply false
       // Remove this line: alias(libs.plugins.kotlin.compose) apply false
   }
   ```

3. **Remove from app `build.gradle.kts`**:
   ```kotlin
   plugins {
       alias(libs.plugins.android.application)
       alias(libs.plugins.kotlin.android)
       // Remove this line: alias(libs.plugins.kotlin.compose)
   }
   ```

4. **Add manual Compose compiler configuration** in `app/build.gradle.kts`:
   ```kotlin
   android {
       buildFeatures {
           compose = true
       }
       composeOptions {
           kotlinCompilerExtensionVersion = "1.5.4"
       }
   }
   ```

#### Option 2: Use Compatible Kotlin Version
For projects that need the Kotlin Compose plugin:

```toml
[versions]
agp = "8.1.2"
kotlin = "1.8.21"  # Use older version with stable plugin support
```

## 🚨 Android Gradle Plugin (AGP) Resolution Issues

### Problem
```
Plugin [id: 'com.android.application', version: 'X.X.X'] was not found in any of the following sources
```

### Root Cause
The Android Gradle Plugin cannot be downloaded from Google's Maven repository, typically due to:
- Network restrictions in CI environments
- Blocked access to `dl.google.com`
- Incompatible AGP versions
- Repository configuration issues

### Solutions

#### Option 1: Update AGP Version (Recommended)
Update `gradle/libs.versions.toml` to use a stable, widely available AGP version:

```toml
[versions]
agp = "8.1.2"  # Use stable version
kotlin = "1.9.10"  # Compatible Kotlin version
```

#### Option 2: Local Development Workaround
For local development when facing network issues:

1. **Use Android Studio's built-in Gradle wrapper**:
   - Open the project in Android Studio
   - Let Android Studio handle Gradle sync and downloads
   - Use the IDE's build system instead of command line

2. **Configure proxy settings** (if behind corporate firewall):
   ```bash
   # Add to gradle.properties
   systemProp.https.proxyHost=your-proxy-host
   systemProp.https.proxyPort=your-proxy-port
   ```

#### Option 3: CI Environment Configuration
For GitHub Actions and CI environments:

1. **Ensure Android SDK is properly set up**:
   ```yaml
   - name: Setup Android SDK
     uses: android-actions/setup-android@v3
   ```

2. **Use repository mirrors** if available:
   ```kotlin
   // In settings.gradle.kts
   pluginManagement {
       repositories {
           gradlePluginPortal()
           google() // Try Google first
           mavenCentral() // Fallback to Maven Central
           // Add other mirrors if needed
       }
   }
   ```

3. **Add network retry logic**:
   ```yaml
   - name: Build with retry
     uses: nick-invision/retry@v2
     with:
       timeout_minutes: 10
       max_attempts: 3
       command: ./gradlew build
   ```

## 🔧 Gradle Configuration

### Compatible Version Matrix
| AGP Version | Kotlin Version | Compose Compiler | Kotlin Compose Plugin |
|-------------|----------------|------------------|----------------------|
| 8.1.2       | 1.9.10         | 1.5.4            | Not available        |
| 8.0.2       | 1.8.21         | 1.4.8            | Available            |
| 7.4.2       | 1.8.21         | 1.4.8            | Not needed           |

### Recommended Configuration (Stable)
```toml
[versions]
agp = "8.1.2"
kotlin = "1.9.10"
coreKtx = "1.12.0"
lifecycleRuntimeKtx = "2.7.0"
activityCompose = "1.8.2"
composeBom = "2023.10.01"

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
# No kotlin-compose plugin needed for manual configuration
```

### Gradle Wrapper Validation
Always validate the Gradle wrapper to ensure security:

```yaml
- name: Validate Gradle Wrapper
  uses: gradle/wrapper-validation-action@v2
```

## 🏗️ CI/CD Considerations

### Build Script Modifications
Update Gradle commands to be more resilient:

```bash
# Use --continue to not fail on first error
./gradlew build --continue

# Add retries for network issues
./gradlew build --retry

# Increase timeout for slow networks
./gradlew build --gradle-user-home=/tmp/gradle
```

### Dependency Caching
Proper caching reduces network dependency:

```yaml
- name: Cache Gradle packages
  uses: actions/cache@v4
  with:
    path: |
      ~/.gradle/caches
      ~/.gradle/wrapper
    key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*') }}
    restore-keys: |
      ${{ runner.os }}-gradle-
```

## 📋 Quick Fix Checklist

1. **Remove Kotlin Compose plugin** if using Kotlin 1.9.10
2. **Add manual Compose compiler configuration**
3. **Update AGP version** to latest stable (8.1.2)
4. **Ensure Kotlin compatibility** with AGP version
5. **Add Android SDK setup** to CI workflows
6. **Enable Gradle wrapper validation**
7. **Test locally** in Android Studio first
8. **Check network connectivity** to Google repositories

## 🆘 Getting Help

If build issues persist:

1. **Check the GitHub Actions logs** for specific error details
2. **Verify repository settings** allow access to required domains
3. **Create an issue** with the complete build log
4. **Test the build locally** to isolate CI vs. local issues

## 🔄 CI Workflow Updates

The CI workflows have been updated to include:
- Android SDK setup
- Gradle wrapper validation  
- Better error handling with `--continue` flag
- Improved caching strategies
- Retry mechanisms for network issues

These changes should resolve most build issues in the CI environment while maintaining local development compatibility.