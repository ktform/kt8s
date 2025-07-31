## kt8s compiler

Transforms kubernetes Resource JSON schema into a Kotlin Data Classes.

## 🦋 Usage

Used by [kt8s-client](../kt8s-client) to generate relevant Data Classes.

## 🌋 Motivation

[KSP](https://kotlinlang.org/docs/ksp-overview.html) and [KotlinPoet](https://square.github.io/kotlinpoet/) driven codegen is relatively very simple with custom JSON Schema parser. 

Should be much simpler to support, than common CDK setups.

## 📝 License

**kt8s-image-compiler** is, and **forever will be**, licensed under the terms of the
[Mozilla Public License 2.0](../LICENSE).