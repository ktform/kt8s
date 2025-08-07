/*
 * Copyright (C) 2016-2025 Yuriy Yarosh
 * All rights reserved.
 *
 * SPDX-License-Identifier: MPL-2.0
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.ktform.kt8s.container

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.GoldenFileTestCases.Companion.getOrUpdateExpected
import dev.ktform.kt8s.container.dsl.dockerfile
import kotlinx.io.files.Path
import kotlin.test.Test

class DockerfileTest {

  private val goldenFile = Path("src/commonTest/resources/DockerfileTest.json")

  private fun assertDockerfileOutput(description: String, dockerfile: Dockerfile.() -> Unit) {
    val key = description.lowercase().replace(Regex("[^a-z0-9]+"), "_")
    val output = dockerfile(dockerfile).buildString()
    assertThat(output).isEqualTo(output.getOrUpdateExpected(key, goldenFile))
  }

  // FROM instruction tests
  @Test fun testFromInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "simple image" to { from("ubuntu") },
      "image with tag" to { from("ubuntu", "20.04") },
      "image with alias" to { from("ubuntu", alias = "base") },
      "image with tag and alias" to { from("ubuntu", "20.04", "base") },
      "image with platform" to { from("ubuntu", platform = "linux/amd64") },
      "image with all parameters" to { from("ubuntu", "20.04", "base", "linux/amd64") },
      "scratch image" to { fromScratch() }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // RUN instruction tests
  @Test fun testRunInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "run single command" to { run("apt-get update") },
      "run multiple commands" to { run("apt-get update", "apt-get install -y curl") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // COPY instruction tests
  @Test fun testCopyInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "simple copy" to { copy("src/", "/app/") },
      "copy with from stage" to { copy("app.jar", "/app/", from = "builder") },
      "copy with chown" to { copy("app.jar", "/app/", chown = "user:group") },
      "copy with chmod" to { copy("script.sh", "/usr/local/bin/", chmod = "755") },
      "copy with all options" to { copy("app.jar", "/app/", from = "builder", chown = "user:group", chmod = "644") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // ADD instruction tests
  @Test fun testAddInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "simple add" to { add("file.tar.gz", "/app/") },
      "add with chown" to { add("file.tar.gz", "/app/", chown = "user:group") },
      "add with chmod" to { add("file.tar.gz", "/app/", chmod = "755") },
      "add with checksum" to { add("file.tar.gz", "/app/", checksum = "sha256:abc123") },
      "add with all options" to { add("file.tar.gz", "/app/", chown = "user:group", chmod = "755", checksum = "sha256:abc123") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // WORKDIR instruction tests
  @Test fun testWorkdirInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "absolute path" to { workdir("/app") },
      "relative path" to { workdir("src") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // EXPOSE instruction tests
  @Test fun testExposeInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "default protocol (tcp)" to { expose(8080) },
      "explicit tcp protocol" to { expose(8080, "tcp") },
      "udp protocol" to { expose(53, "udp") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // ENV instruction tests
  @Test fun testEnvInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "single variable" to { env("NODE_ENV", "production") },
      "multiple variables using vararg" to { env("NODE_ENV" to "production", "PORT" to "3000") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // CMD instruction tests
  @Test fun testCmdInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "cmd single command" to { cmd("node", "app.js") },
      "cmd with arguments" to { cmd("java", "-jar", "app.jar", "--server.port=8080") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // ENTRYPOINT instruction tests
  @Test fun testEntrypointInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "exec form" to { entrypoint("java", "-jar", "app.jar") },
      "shell form" to { entrypointShell("java -jar app.jar") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // LABEL instruction tests
  @Test fun testLabelInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "single label" to { label("version", "1.0") },
      "multiple labels" to { label("version" to "1.0", "maintainer" to "team@example.com") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // USER instruction tests
  @Test fun testUserInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "user only" to { user("appuser") },
      "user with group" to { user("appuser", "appgroup") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // VOLUME instruction tests
  @Test fun testVolumeInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "single volume" to { volume("/data") },
      "multiple volumes" to { volume("/data", "/logs", "/config") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // ARG instruction tests
  @Test fun testArgInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "arg without default" to { arg("BUILD_VERSION") },
      "arg with default value" to { arg("NODE_VERSION", "18") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // HEALTHCHECK instruction tests
  @Test fun testHealthcheckInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "disable healthcheck" to { healthcheck() },
      "simple healthcheck" to { healthcheck(cmd = "curl -f http://localhost:8080/health") },
      "healthcheck with all options" to {
        healthcheck(
          interval = "30s",
          timeout = "10s",
          startPeriod = "5s",
          retries = 3,
          cmd = "curl -f http://localhost:8080/health",
        )
      }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // STOPSIGNAL instruction tests
  @Test fun testStopsignalInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "SIGTERM" to { stopsignal("SIGTERM") },
      "SIGKILL" to { stopsignal("SIGKILL") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // SHELL instruction tests
  @Test fun testShellInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "bash shell" to { shell("/bin/bash", "-c") },
      "powershell" to { shell("powershell", "-command") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // ONBUILD instruction tests
  @Test fun testOnbuildInstructions() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "onbuild copy" to { onbuild("COPY . /app") },
      "onbuild run" to { onbuild("RUN make /app") }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // RUN instruction with advanced semantics tests
  @Test fun testRunAdvancedSemantics() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "with bind mount" to {
        run("make build", listOf(Dockerfile.BindMount("/host/src", "/app/src", readonly = true)))
      },
      "with cache mount" to {
        run("go build .", listOf(Dockerfile.CacheMount("/go/pkg/mod", id = "go-mod")))
      },
      "with tmpfs mount" to {
        run("npm test", listOf(Dockerfile.TmpfsMount("/tmp", size = "100m")))
      },
      "with secret mount" to {
        run($$"echo $SECRET", listOf(Dockerfile.SecretMount("mysecret", target = "/run/secrets/mysecret")))
      },
      "with ssh mount" to {
        run("git clone git@github.com:user/repo.git", listOf(Dockerfile.SshMount()))
      },
      "with multiple mounts" to {
        run("npm install", listOf(
          Dockerfile.CacheMount("/root/.npm", id = "npm-cache"),
          Dockerfile.BindMount("/host/package.json", "/app/package.json", readonly = true)
        ))
      },
      "with network none" to {
        run("go build .", network = Dockerfile.RunNetwork.NONE)
      },
      "with security no-new-privs" to {
        run("make install", security = Dockerfile.RunSecurity.NO_NEW_PRIVS)
      },
      "with all options" to {
        run("go build .",
          listOf(Dockerfile.CacheMount("/go/pkg/mod", id = "go-mod")),
          Dockerfile.RunNetwork.NONE,
          Dockerfile.RunSecurity.NO_NEW_PRIVS
        )
      }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // RUN instruction with DSL configuration tests
  @Test fun testRunDslConfiguration() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "with bind mount using DSL" to {
        run("make build") {
          bindMount("/host/src", "/app/src", readonly = true)
        }
      },
      "with cache mount using DSL" to {
        run("go build .") {
          cacheMount("/go/pkg/mod", id = "go-mod")
          cacheMount("/root/.cache/go-build", id = "go-build")
        }
      },
      "with tmpfs mount using DSL" to {
        run("npm test") {
          tmpfsMount("/tmp", size = "100m", mode = "1777")
        }
      },
      "with secret mount using DSL" to {
        run("cat /run/secrets/token") {
          secretMount("api-token", target = "/run/secrets/token", required = true)
        }
      },
      "with ssh mount using DSL" to {
        run("git clone git@github.com:user/repo.git") {
          sshMount("github", target = "/root/.ssh/id_rsa")
        }
      },
      "with network and security using DSL" to {
        run("make install") {
          network(Dockerfile.RunNetwork.NONE)
          security(Dockerfile.RunSecurity.NO_NEW_PRIVS)
        }
      },
      "with complex configuration using DSL" to {
        run("npm ci && npm run build") {
          cacheMount("/root/.npm", id = "npm-cache")
          bindMount("/host/src", "/app/src", readonly = true)
          tmpfsMount("/tmp", size = "500m")
          network(Dockerfile.RunNetwork.DEFAULT)
          security(Dockerfile.RunSecurity.NO_NEW_PRIVS)
        }
      }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // Fluent DSL configuration tests
  @Test fun testFluentDslConfiguration() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "ENV with DSL block" to {
        env {
          "NODE_ENV" to "production"
          "PORT" to "3000"
          "DEBUG" to "false"
        }
      },
      "LABEL with DSL block" to {
        label {
          "version" to "1.0.0"
          "maintainer" to "team@company.com"
          "description" to "My awesome app"
        }
      },
      "HEALTHCHECK with DSL block" to {
        healthcheck {
          interval = "30s"
          timeout = "10s"
          retries = 3
          cmd = "curl -f http://localhost:3000/health"
        }
      }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }

  // Method chaining fluency test
  @Test fun testFluentMethodChaining() {
    val dockerfile = dockerfile {
        from("node", "18-alpine")
            .workdir("/app")
            .copy("package*.json", "./")
            .run("npm ci --only=production") {
                cacheMount("/root/.npm", id = "npm-cache")
                network(Dockerfile.RunNetwork.DEFAULT)
            }
            .copy(".", ".")
            .expose(3000)
            .env("NODE_ENV", "production")
            .user("node")
            .cmd("npm", "start")
    }

    val expected = """FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN --mount=type=cache,target=/root/.npm,id=npm-cache,sharing=shared --network=default npm ci --only=production
COPY . .
EXPOSE 3000/tcp
ENV NODE_ENV=production
USER node
CMD ["npm", "start"]"""

    assertThat(dockerfile.buildString()).isEqualTo(expected)
  }

  // Complex Dockerfile scenarios tests
  @Test fun testComplexScenarios() {
    val testCases: Map<String, Dockerfile.() -> Unit> = mapOf(
      "Node.js application" to {
        from("node", "18-alpine")
        workdir("/app")
        copy("package*.json", "./")
        run("npm ci --only=production")
        copy(".", ".")
        expose(3000)
        user("node")
        cmd("node", "server.js")
      },
      "Multi-stage Java build" to {
        from("maven", "3.8-openjdk-17", "builder")
        workdir("/app")
        copy("pom.xml", ".")
        copy("src", "src")
        run("mvn clean package -DskipTests")

        from("openjdk", "17-jre-slim")
        workdir("/app")
        copy("app.jar", "/app/", from = "builder")
        expose(8080)
        healthcheck(
          interval = "30s",
          timeout = "3s",
          cmd = "curl -f http://localhost:8080/actuator/health || exit 1",
        )
        entrypoint("java", "-jar", "app.jar")
      }
    )
    
    testCases.forEach { (description, dockerfile) ->
      assertDockerfileOutput(description, dockerfile)
    }
  }
}
