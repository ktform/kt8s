## PR Documentation

To ensure clarity for users and maintainers, any pull request that introduces or changes user-facing behavior **must** be documented. This guide outlines when and how to document your changes.

### When to Document

If your PR includes any of the following, it requires documentation:

* Adding a command or flags to the CLI.
* Adding new Helm charts or components.
* Changing the JSON schema for Kubernetes resources or CRDs.
* Other changes to user-facing functionality.

### When Documentation is Not Required

Changes that **do not** require documentation include:

* Internal bug fixes with no user-visible impact.
* Adding or modifying test cases.
* Refactoring code with no behavioral changes.
* Changes to logging.

If you are unsure whether your change requires documentation, please ask in the [Discord KtForm Server](https://discord.gg/s3jyDg5SR6).

## How to Document Your Changes

In the "Proposed Changes" section of your pull request, provide a clear "before and after" story that explains the impact of your changes.

Your description should include:

1.  **Explanation**: Describe how the system worked *before* your changes. Link to the relevant code on the `master` branch.
2.  **New Behavior**: Explain what your PR does and what the resulting behavior is.
3.  **Proof**: Provide a reproducible example or test case that demonstrates the new behavior and proves your change works as intended.

### Example

> **1. Previous Behavior:**
> Prior to this PR, CRDs were downloaded synchronously via the `writeAllCrds()` method, as tested by `testWriteAllCrdsToEmptyDirectory()`.
>
> **2. New Behavior:**
> This PR introduces asynchronous processing using `CompletableFuture` to download and process CRDs concurrently. As a result, `writeAllCrds()` is now significantly faster.
>
> **3. Proof:**
> To test, run the `./gradlew build` command and observe the reduced execution time in the `downloadCrds` task.