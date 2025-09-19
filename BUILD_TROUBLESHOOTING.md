# Build Troubleshooting Guide

This document provides solutions for common build issues in the Custom Intent App project.

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
| AGP Version | Kotlin Version | Gradle Version | JDK Version |
|-------------|----------------|----------------|-------------|
| 8.1.2       | 1.9.10         | 8.0+           | 17          |
| 8.0.2       | 1.8.21         | 8.0+           | 17          |
| 7.4.2       | 1.8.21         | 7.5+           | 11          |

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

1. **Update AGP version** to latest stable (8.1.2)
2. **Ensure Kotlin compatibility** with AGP version
3. **Add Android SDK setup** to CI workflows
4. **Enable Gradle wrapper validation**
5. **Test locally** in Android Studio first
6. **Check network connectivity** to Google repositories

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