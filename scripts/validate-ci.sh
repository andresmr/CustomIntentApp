#!/bin/bash

# CI Validation Script for Custom Intent App
# This script validates that all CI/CD components are properly configured

set -e

echo "🔍 Validating CI/CD Configuration..."
echo "=================================="

# Check if required files exist
check_file() {
    if [ -f "$1" ]; then
        echo "✅ $1"
    else
        echo "❌ $1 (missing)"
        return 1
    fi
}

echo ""
echo "📋 Checking required files:"
check_file ".github/workflows/ci.yml"
check_file ".github/workflows/pr-validation.yml"
check_file ".github/workflows/release.yml"
check_file ".github/workflows/codeql.yml"
check_file ".github/workflows/issue-triage.yml"
check_file ".github/dependabot.yml"
check_file ".github/CONTRIBUTING.md"
check_file ".github/pull_request_template.md"
check_file ".github/ISSUE_TEMPLATE/bug_report.md"
check_file ".github/ISSUE_TEMPLATE/feature_request.md"
check_file ".github/ISSUE_TEMPLATE/documentation.md"
check_file ".github/ISSUE_TEMPLATE/config.yml"

echo ""
echo "🔧 Checking build configuration:"
check_file "build.gradle.kts"
check_file "app/build.gradle.kts"
check_file "gradle/libs.versions.toml"
check_file "settings.gradle.kts"

echo ""
echo "🧪 Checking test files:"
check_file "app/src/test/java/org/dhis2/customintent/ExampleUnitTest.kt"

echo ""
echo "📚 Checking documentation:"
check_file "README.md"
check_file "LICENSE"

# Validate workflow syntax (basic YAML check)
echo ""
echo "🔍 Validating workflow syntax:"
for workflow in .github/workflows/*.yml; do
    if command -v python3 &> /dev/null; then
        if python3 -c "import yaml; yaml.safe_load(open('$workflow'))" 2>/dev/null; then
            echo "✅ $(basename $workflow) - Valid YAML"
        else
            echo "❌ $(basename $workflow) - Invalid YAML"
        fi
    else
        echo "⚠️  Python3 not available for YAML validation"
        break
    fi
done

# Check if gradlew is executable
echo ""
echo "⚡ Checking build tools:"
if [ -x "./gradlew" ]; then
    echo "✅ gradlew is executable"
else
    echo "❌ gradlew is not executable (run: chmod +x gradlew)"
fi

echo ""
echo "🎉 CI/CD validation complete!"
echo ""
echo "📋 Next steps:"
echo "1. Push these changes to trigger the CI workflows"
echo "2. Create a test PR to validate the PR workflows"
echo "3. Review the workflow runs in GitHub Actions"
echo "4. Configure any required GitHub repository settings"
echo ""
echo "🔧 Repository settings to configure:"
echo "- Branch protection rules for main/develop"
echo "- Required status checks"
echo "- Automatic dependency updates"
echo "- Security and analysis features"