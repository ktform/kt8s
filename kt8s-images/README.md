## kt8s container image dsl

Constructs dockerfiles's using an opinionated Kotlin DSL.

Targets multiple Linux container distributions, provides simple and efficient [Distroless](https://github.com/GoogleContainerTools/distroless) packaging,
relevant [slsa.dev](https://slsa.dev) security attestations and container image signing. Does not rely on any custom package management, or tooling due to added Total Cost Of
Ownership.

## 🦋 Usage

## 🌋 Motivation

[Distroless](https://github.com/GoogleContainerTools/distroless) is mostly about packaging Debian with Bazel, for Google needs.

[Chainguard](https://www.chainguard.dev/) is mostly about upselling various forms of Alpine overengineering, not necessarily bad, but not applicable to any other distros.

Broadcom ~~killed~~ discontinued [Bitnami](https://github.com/bitnami/charts/issues/35164) charts.

Thus, it's a complete **free for all**, and free should be simple and convenient, with all the complexity hidden from the prying eyes.

## 📝 License

**kt8s-images** is, and **forever will be**, licensed under the terms of the
[Mozilla Public License 2.0](../LICENSE).