## kt8s container image dsl

Constructs dockerfiles's using an opinionated Kotlin DSL.

Targets multiple Linux container distributions, provides simple and efficient [Distroless](https://github.com/GoogleContainerTools/distroless) packaging, 
relevant [slsa.dev](https://slsa.dev) security attestations and container image signing. Does not rely on any custom package management, or tooling due to added Total Cost Of Ownership.


## 🦋 Usage

```kotlin
import dev.ktform.kt8s.container.dockerfile
import dev.ktform.kt8s.container.image

val classicDockerfile = dockerfile {
  from("debian:11-slim")
  run("apt-get update")
  run("apt-get install -y nodejs yarn aws-cli terraform")
}

val kt8sImage = image {
  distro("alpine") {
    dependencies {
      buildAndRun("nodejs", "aws-cli")
      build("yarn", "terraform")
      run("aws-cli", "curl")
    }
    
    build {
      all('yarn', 'yarn build', 'yarn cleanup')
      debian('yarn', 'yarn build', 'yarn cleanup', 'yarn cleanup-debian')
    }
  }

  endpoints {
    healthcheck("https://service:3000/health")
    metrics("https://service:3000/metrics")
    service("https://service:8080")
  }

  probes {
    liveness("curl -f http://localhost:3000/health")
    readiness("curl -f http://localhost:8080/health")
    startup("curl -f http://localhost:3000")
  }

  stopGracefullySignal(Signal.SIGTERM)
  stopImmediatelySignal(Signal.SIGKILL)
  reloadConfigSignal(Signal.SIGHUP)
}

```

## 🌋 Motivation

[Distroless](https://github.com/GoogleContainerTools/distroless) is mostly about packaging Debian with Bazel, for Google needs.

[Chainguard](https://www.chainguard.dev/) is mostly about upselling various forms of Alpine overengineering, not necessarily bad, but not applicable to any other distros. 

Broadcom ~~killed~~ discontinued [Bitnami](https://github.com/bitnami/charts/issues/35164) charts.

Thus it's a complete **free for all**, and free should be simple and convenient, with all the complexity hidden from the prying eyes.

## 📝 License

**kt8s-image-dsl** is, and **forever will be**, licensed under the terms of the
[Mozilla Public License 2.0](../LICENSE).