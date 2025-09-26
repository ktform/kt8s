# Contributing to Kt8s

We welcome contributions from the community!

This guide outlines how to get started.

Before contributing, please review our [Code of Conduct](CODE_OF_CONDUCT.md) and [Governance](GOVERNANCE.md) policies.

## Getting Started

*   **Developer Guide**: To learn about the codebase and development processes, refer to the [development guide](DEVELOPMENT.md).
*   **Good First Issues**: If you're new to the project, look for issues labeled `good first issue`. These are great starting points. You can find them [here](https://github.com/ktform/kt8s/issues?q=is%3Aissue+is%3Aopen+label%3A%22good+first+issue%22).

## Submitting a Pull Request

To ensure a smooth review process, please follow these guidelines when submitting a pull request:

1.  **Follow the Template**: Fill out the [pull request template](PULL_REQUEST_TEMPLATE.md) completely.
2.  **Provide Proof**: As outlined in our [documentation guide](DOCUMENTING.md), include a clear "before and after" story with a reproducible example or test case that demonstrates your changes.
3.  **Add Tests**: All new features and bug fixes must include corresponding tests. Run `./gradlew check` to ensure all tests and checks pass before submitting.
4.  **Update Documentation**: If your change affects user-facing behavior, update the relevant documentation (e.g., `README.md` or other guides).

## Documentation

For significant changes to user-facing functionality, documentation updates are required. Please see our [documentation guide](DOCUMENTING.md) for more details.
