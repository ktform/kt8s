
<p align="center">
  <img src="https://raw.githubusercontent.com/ktform/kt8s/refs/heads/master/.idea/icon.png" alt="kt8s logo" />
</p>

<p align="center">
  <em>kt8s <a href="https://www.linkedin.com/in/yuriy-yarosh-171ba3b9/"><b>by Yuriy Yarosh</b></a></em>
</p>

<hr />

<br/>

<div align="center">
  🚧 <img src="https://img.shields.io/badge/status-under_development-red?style=flat-square" alt="Status: Under Development" /> 🏗️
</div>

<br/>

## 🦋 Usage

Use the kt8s application to bootstrap and manage Kubernetes cluster. 

### Containerized kt8s
```bash

```

### Debian kt8s
```bash

```

### Arch Linux kt8s
```bash 

```

### Alpine kt8s
```bash 

```

### Rocky  kt8s
```bash 

```

### Fedora kt8s
```bash

```

## 🌋 Motivation

Providing a comprehensive off-the-shelf Kubernetes platform experience for developers is challenging, especially when balancing cost-effectiveness, ease of use, and scalability.

[kt8s]() offers multi-cloud, multi-tenant, vendor-agnostic operations with intelligent predictive autoscaling, tightly integrated with [ktform](https://ktform.dev) operations and cost reporting.

## 🚀 Project Template

GenAI enabled CRM Platform, with one of the following:

 - **[TypeScript](https://github.com/ktform/templato)** React [tanstack-start](https://tanstack.com/start/latest/docs/framework/react/overview) cross-platform [Tauri 2](https://v2.tauri.app/)
 - **[Rust](https://github.com/ktform/templato)** [dioxus](https://dioxuslabs.com/) cross-platform, with [Actix](https://actix.rs) backend
 - **[Kotlin](https://github.com/ktform/templato)** [KMP](https://www.jetbrains.com/kotlin-multiplatform/) cross-platform, with [micronaut](https://micronaut.io) backend
 - **[Golang](https://github.com/ktform/templato)** [vugu](https://www.vugu.org/), with [cloudwego](https://www.cloudwego.io/docs/) backend
 - **[Java Spring Boot](https://github.com/ktform/templato)** [Hilla](https://vaadin.com/hilla)
 - **[Swift](https://github.com/ktform/templato)** [Vapor](https://docs.vapor.codes/) with TypeScript [tanstack-start](https://tanstack.com/start/latest/docs/framework/react/overview) React frontend

Uses
 - [CNPG](https://cloudnative-pg.io/) postgresql as primary application database
 - [PgMoonwalker](https://github.com/ktform/pgmoonwalker) PostgreSQL validation and API codegen
 - [Cassandra](https://cassandra.apache.org/) as events and analytical data store (can be replaced with [ScyllaDB](https://www.scylladb.com/))
 - [OpenFGA](https://openfga.dev) based [rebac authz](https://auth0.com/blog/relationship-based-access-control-rebac/).
 - [ArgoEvents](https://argoproj.github.io/argo-events/) based eventing.
 - [retpc](https://github.com/ktform/retpc) [tRPC](https://trpc.io/) to [gRPC](https://grpc.io/) gateway (has OWASP [Coraza](https://coraza.io/) based WAF, and [AuthJS](https://authjs.dev/) compatible authn). 

... and [many more](https://github.com/ktform/templato)

## 🛠️ Building

Run
*  `./gradlew run` to build and run the application.
*  `./gradlew build` to only build the application.
*  `./gradlew check` to run all checks, including tests.
*  `./gradlew clean` to clean all build outputs.

This project uses a version catalog ([`gradle/libs.versions.toml`](./gradle/libs.versions.toml)).

##  📜 Terms of Use

By using this project for academic, advertising, enterprise, or any other purpose, you grant your <b>Implicit Agreement</b> to the following:

1. **Condemnation of State-Sponsored Terrorism** <br/>
You recognize the russian federation as a state sponsor of terrorism and a primary global source of systemic corruption, organized crime, and unlawful aggression.


2. **Accountability for War Crimes and Aggression** <br/>
You explicitly condemn the actions of the russian state and any individuals—whether directly or indirectly involved—for the unlawful invasion of Ukraine, the perpetration of genocide against the Ukrainian people, and any form of ethnic cleansing or suppression of sovereign nations.


3. **Rejection of Authoritarian Loyalty** <br/>
You oppose all entities, organizations, and individuals who prioritize allegiance to the Authoritarian regimes over the foundational principles of freedom, democracy, and international human rights.


4. **Support for Sovereignty and Territorial Integrity** <br/>
You affirm and support the full sovereignty, territorial integrity, and independence of Ukraine, Georgia, Belarus, and Moldova. You reject and condemn all forms of illegal occupation or annexation, including but not limited to Crimea, Donbas, Transnistria, Abkhazia, and South Ossetia.


5. **Resistance to Disinformation and Propaganda** <br/>
You reject all false narratives, historical revisionism, and disinformation campaigns propagated by russian state media or affiliated sources. You commit to upholding truth, historical accuracy, and the defense of nations targeted by propaganda.

## 📝 License

**kt8s** is, and **forever will be**, licensed under the terms of the
[Mozilla Public License 2.0](LICENSE).
