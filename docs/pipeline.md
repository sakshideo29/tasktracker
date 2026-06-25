# CI/CD Pipeline Overview

This document explains the CI/CD pipeline used in the TaskTracker application.

---

# 1. Pull Request Pipeline (CI)

This pipeline runs automatically on every pull request to the `main` branch.

## What it does:

1. Checkout the code
2. Set up Java 21 environment
3. Cache Maven dependencies for faster builds
4. Run tests using Maven
5. Generate code coverage report using JaCoCo
6. Enforce minimum 80% test coverage

## What makes it fail:

- Any test failure
- Code coverage below 80%
- Compilation errors

## How to fix failures:

- Fix broken unit tests
- Add missing test cases
- Improve coverage of service and controller layers

---

# 2. Main Branch Pipeline (Release)

This pipeline runs when code is merged into the `main` branch.

## What it does:

1. Builds the Java application
2. Creates a Docker image
3. Tags image with commit SHA
4. Scans Docker image using Trivy for vulnerabilities

## What makes it fail:

- Build errors
- Docker build failures
- HIGH or CRITICAL security vulnerabilities detected by Trivy

## How to fix failures:

- Fix compilation or dependency issues
- Update vulnerable dependencies in `pom.xml`
- Upgrade base Docker image versions

---

# 3. Docker Build Stage

The Dockerfile uses a multi-stage build:

## Stage 1 (Build stage):
- Uses Maven image
- Compiles code
- Runs tests

## Stage 2 (Runtime stage):
- Uses lightweight JRE image
- Only copies final JAR file
- Runs app using non-root user

---

# 4. Security Scanning (Trivy)

Trivy scans the final Docker image.

It checks:
- OS vulnerabilities
- Java library vulnerabilities
- Known CVEs (Common Vulnerabilities and Exposures)

Pipeline fails if HIGH or CRITICAL issues are found.

---

# 5. Branch Protection Rules

The `main` branch is protected.

Requirements:
- Pull request approval required
- CI checks must pass
- No direct push allowed

---

# 6. Common Issues & Fixes

## Test failures:
- Fix unit tests
- Check recent code changes

## Coverage failure:
- Add missing unit tests
- Increase service layer test coverage

## Docker issues:
- Check Dockerfile syntax
- Ensure correct JAR path

## Security issues:
- Update dependencies in `pom.xml`
- Run `mvn dependency:tree`

---

# 7. Summary

This pipeline ensures:

- Code quality (tests + coverage)
- Security (Trivy scan)
- Reliable builds (Docker multi-stage)
- Controlled releases (branch protection)