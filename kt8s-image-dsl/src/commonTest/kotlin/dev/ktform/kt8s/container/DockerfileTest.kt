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

import dev.ktform.kt8s.container.GoldenFileTestCases.Companion.getOrUpdateExpected
import dev.ktform.kt8s.container.dsl.dockerfile
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlinx.io.files.Path

class DockerfileTest : FunSpec(

  {
    data class DockerfileTestCase(
      val description: String,
      val dockerfile: Dockerfile.() -> Unit,
    ) {
      private val goldenFile = Path("src/commonTest/resources/DockerfileTest.json")
      private val key = description.lowercase().replace(Regex("[^a-z0-9]+"), "_")

      fun isExpected() {
        dockerfile(dockerfile).buildString()
          .let { output -> output shouldBe output.getOrUpdateExpected(key, goldenFile) }
      }
    }

    context("FROM instruction") {
      withData(
        nameFn = { "FROM ${it.description}" },
        DockerfileTestCase(
          description = "simple image",
          dockerfile = { from("ubuntu") },
        ),
        DockerfileTestCase(
          description = "image with tag",
          dockerfile = { from("ubuntu", "20.04") },
        ),
        DockerfileTestCase(
          description = "image with alias",
          dockerfile = { from("ubuntu", alias = "base") },
        ),
        DockerfileTestCase(
          description = "image with tag and alias",
          dockerfile = { from("ubuntu", "20.04", "base") },
        ),
        DockerfileTestCase(
          description = "image with platform",
          dockerfile = { from("ubuntu", platform = "linux/amd64") },
        ),
        DockerfileTestCase(
          description = "image with all parameters",
          dockerfile = { from("ubuntu", "20.04", "base", "linux/amd64") },
        ),
        DockerfileTestCase(
          description = "scratch image",
          dockerfile = { fromScratch() },
        ),
      ) { it.isExpected() }
    }

    context("RUN instruction") {
      withData(
        nameFn = { "RUN ${it.description}" },
        DockerfileTestCase(
          description = "run single command",
          dockerfile = { run("apt-get update") },
        ),
        DockerfileTestCase(
          description = "run multiple commands",
          dockerfile = { run("apt-get update", "apt-get install -y curl") },
        ),
      ) { it.isExpected() }
    }

    context("COPY instruction") {
      withData(
        nameFn = { "COPY ${it.description}" },
        DockerfileTestCase(
          description = "simple copy",
          dockerfile = { copy("src/", "/app/") },
        ),
        DockerfileTestCase(
          description = "copy with from stage",
          dockerfile = { copy("app.jar", "/app/", from = "builder") },
        ),
        DockerfileTestCase(
          description = "copy with chown",
          dockerfile = { copy("app.jar", "/app/", chown = "user:group") },
        ),
        DockerfileTestCase(
          description = "copy with chmod",
          dockerfile = { copy("script.sh", "/usr/local/bin/", chmod = "755") },
        ),
        DockerfileTestCase(
          description = "copy with all options",
          dockerfile = { copy("app.jar", "/app/", from = "builder", chown = "user:group", chmod = "644") },
        ),
      ) { it.isExpected() }
    }

    context("ADD instruction") {
      withData(
        nameFn = { "ADD ${it.description}" },
        DockerfileTestCase(
          description = "simple add",
          dockerfile = { add("file.tar.gz", "/app/") },
        ),
        DockerfileTestCase(
          description = "add with chown",
          dockerfile = { add("file.tar.gz", "/app/", chown = "user:group") },
        ),
        DockerfileTestCase(
          description = "add with chmod",
          dockerfile = { add("file.tar.gz", "/app/", chmod = "755") },
        ),
        DockerfileTestCase(
          description = "add with checksum",
          dockerfile = { add("file.tar.gz", "/app/", checksum = "sha256:abc123") },
        ),
        DockerfileTestCase(
          description = "add with all options",
          dockerfile = { add("file.tar.gz", "/app/", chown = "user:group", chmod = "755", checksum = "sha256:abc123") },
        ),
      ) { it.isExpected() }
    }

    context("WORKDIR instruction") {
      withData(
        nameFn = { "WORKDIR ${it.description}" },
        DockerfileTestCase(
          description = "absolute path",
          dockerfile = { workdir("/app") },
        ),
        DockerfileTestCase(
          description = "relative path",
          dockerfile = { workdir("src") },
        ),
      ) { it.isExpected() }
    }

    context("EXPOSE instruction") {
      withData(
        nameFn = { "EXPOSE ${it.description}" },
        DockerfileTestCase(
          description = "default protocol (tcp)",
          dockerfile = { expose(8080) },
        ),
        DockerfileTestCase(
          description = "explicit tcp protocol",
          dockerfile = { expose(8080, "tcp") },
        ),
        DockerfileTestCase(
          description = "udp protocol",
          dockerfile = { expose(53, "udp") },
        ),
      ) { it.isExpected() }
    }

    context("ENV instruction") {
      withData(
        nameFn = { "ENV ${it.description}" },
        DockerfileTestCase(
          description = "single variable",
          dockerfile = { env("NODE_ENV", "production") },
        ),
        DockerfileTestCase(
          description = "multiple variables using vararg",
          dockerfile = { env("NODE_ENV" to "production", "PORT" to "3000") },
        ),
      ) { it.isExpected() }
    }

    context("CMD instruction") {
      withData(
        nameFn = { "CMD ${it.description}" },
        DockerfileTestCase(
          description = "cmd single command",
          dockerfile = { cmd("node", "app.js") },
        ),
        DockerfileTestCase(
          description = "cmd with arguments",
          dockerfile = { cmd("java", "-jar", "app.jar", "--server.port=8080") },
        ),
      ) { it.isExpected() }
    }

    context("ENTRYPOINT instruction") {
      withData(
        nameFn = { "ENTRYPOINT ${it.description}" },
        DockerfileTestCase(
          description = "exec form",
          dockerfile = { entrypoint("java", "-jar", "app.jar") },
        ),
        DockerfileTestCase(
          description = "shell form",
          dockerfile = { entrypointShell("java -jar app.jar") },
        ),
      ) { it.isExpected() }
    }

    context("LABEL instruction") {
      withData(
        nameFn = { "LABEL ${it.description}" },
        DockerfileTestCase(
          description = "single label",
          dockerfile = { label("version", "1.0") },
        ),
        DockerfileTestCase(
          description = "multiple labels",
          dockerfile = { label("version" to "1.0", "maintainer" to "team@example.com") },
        ),
      ) { it.isExpected() }
    }

    context("USER instruction") {
      withData(
        nameFn = { "USER ${it.description}" },
        DockerfileTestCase(
          description = "user only",
          dockerfile = { user("appuser") },
        ),
        DockerfileTestCase(
          description = "user with group",
          dockerfile = { user("appuser", "appgroup") },
        ),
      ) { it.isExpected() }
    }

    context("VOLUME instruction") {
      withData(
        nameFn = { "VOLUME ${it.description}" },
        DockerfileTestCase(
          description = "single volume",
          dockerfile = { volume("/data") },
        ),
        DockerfileTestCase(
          description = "multiple volumes",
          dockerfile = { volume("/data", "/logs", "/config") },
        ),
      ) { it.isExpected() }
    }

    context("ARG instruction") {
      withData(
        nameFn = { "ARG ${it.description}" },
        DockerfileTestCase(
          description = "arg without default",
          dockerfile = { arg("BUILD_VERSION") },
        ),
        DockerfileTestCase(
          description = "arg with default value",
          dockerfile = { arg("NODE_VERSION", "18") },
        ),
      ) { it.isExpected() }
    }

    context("HEALTHCHECK instruction") {
      withData(
        nameFn = { "HEALTHCHECK ${it.description}" },
        DockerfileTestCase(
          description = "disable healthcheck",
          dockerfile = { healthcheck() },
        ),
        DockerfileTestCase(
          description = "simple healthcheck",
          dockerfile = { healthcheck(cmd = "curl -f http://localhost:8080/health") },
        ),
        DockerfileTestCase(
          description = "healthcheck with all options",
          dockerfile = {
            healthcheck(
              interval = "30s",
              timeout = "10s",
              startPeriod = "5s",
              retries = 3,
              cmd = "curl -f http://localhost:8080/health",
            )
          },
        ),
      ) { it.isExpected() }
    }

    context("STOPSIGNAL instruction") {
      withData(
        nameFn = { "STOPSIGNAL ${it.description}" },
        DockerfileTestCase(
          description = "SIGTERM",
          dockerfile = { stopsignal("SIGTERM") },
        ),
        DockerfileTestCase(
          description = "SIGKILL",
          dockerfile = { stopsignal("SIGKILL") },
        ),
      ) { it.isExpected() }
    }

    context("SHELL instruction") {
      withData(
        nameFn = { "SHELL ${it.description}" },
        DockerfileTestCase(
          description = "bash shell",
          dockerfile = { shell("/bin/bash", "-c") },
        ),
        DockerfileTestCase(
          description = "powershell",
          dockerfile = { shell("powershell", "-command") },
        ),
      ) { it.isExpected() }
    }

    context("ONBUILD instruction") {
      withData(
        nameFn = { "ONBUILD ${it.description}" },
        DockerfileTestCase(
          description = "onbuild copy",
          dockerfile = { onbuild("COPY . /app") },
        ),
        DockerfileTestCase(
          description = "onbuild run",
          dockerfile = { onbuild("RUN make /app") },
        ),
      ) { it.isExpected() }
    }

    context("RUN instruction with advanced semantics") {
      withData(
        nameFn = { "RUN ${it.description}" },
        DockerfileTestCase(
          description = "with bind mount",
          dockerfile = {
            run("make build", listOf(Dockerfile.BindMount("/host/src", "/app/src", readonly = true)))
          },
        ),
        DockerfileTestCase(
          description = "with cache mount",
          dockerfile = {
            run("go build .", listOf(Dockerfile.CacheMount("/go/pkg/mod", id = "go-mod")))
          },
        ),
        DockerfileTestCase(
          description = "with tmpfs mount",
          dockerfile = {
            run("npm test", listOf(Dockerfile.TmpfsMount("/tmp", size = "100m")))
          },
        ),
        DockerfileTestCase(
          description = "with secret mount",
          dockerfile = {
            run($$"echo $SECRET", listOf(Dockerfile.SecretMount("mysecret", target = "/run/secrets/mysecret")))
          },
        ),
        DockerfileTestCase(
          description = "with ssh mount",
          dockerfile = {
            run("git clone git@github.com:user/repo.git", listOf(Dockerfile.SshMount()))
          },
        ),
        DockerfileTestCase(
          description = "with multiple mounts",
          dockerfile = {
            run("npm install", listOf(
              Dockerfile.CacheMount("/root/.npm", id = "npm-cache"),
              Dockerfile.BindMount("/host/package.json", "/app/package.json", readonly = true)
            ))
          },
        ),
        DockerfileTestCase(
          description = "with network none",
          dockerfile = {
            run("go build .", network = Dockerfile.RunNetwork.NONE)
          },
        ),
        DockerfileTestCase(
          description = "with security no-new-privs",
          dockerfile = {
            run("make install", security = Dockerfile.RunSecurity.NO_NEW_PRIVS)
          },
        ),
        DockerfileTestCase(
          description = "with all options",
          dockerfile = {
            run("go build .",
              listOf(Dockerfile.CacheMount("/go/pkg/mod", id = "go-mod")),
              Dockerfile.RunNetwork.NONE,
              Dockerfile.RunSecurity.NO_NEW_PRIVS
            )
          },
        ),
      ) { it.isExpected() }
    }

    context("RUN instruction with DSL configuration") {
      withData(
        nameFn = { "RUN DSL ${it.description}" },
        DockerfileTestCase(
          description = "with bind mount using DSL",
          dockerfile = {
            run("make build") {
              bindMount("/host/src", "/app/src", readonly = true)
            }
          },
        ),
        DockerfileTestCase(
          description = "with cache mount using DSL",
          dockerfile = {
            run("go build .") {
              cacheMount("/go/pkg/mod", id = "go-mod")
              cacheMount("/root/.cache/go-build", id = "go-build")
            }
          },
        ),
        DockerfileTestCase(
          description = "with tmpfs mount using DSL",
          dockerfile = {
            run("npm test") {
              tmpfsMount("/tmp", size = "100m", mode = "1777")
            }
          },
        ),
        DockerfileTestCase(
          description = "with secret mount using DSL",
          dockerfile = {
            run("cat /run/secrets/token") {
              secretMount("api-token", target = "/run/secrets/token", required = true)
            }
          },
        ),
        DockerfileTestCase(
          description = "with ssh mount using DSL",
          dockerfile = {
            run("git clone git@github.com:user/repo.git") {
              sshMount("github", target = "/root/.ssh/id_rsa")
            }
          },
        ),
        DockerfileTestCase(
          description = "with network and security using DSL",
          dockerfile = {
            run("make install") {
              network(Dockerfile.RunNetwork.NONE)
              security(Dockerfile.RunSecurity.NO_NEW_PRIVS)
            }
          },
        ),
        DockerfileTestCase(
          description = "with complex configuration using DSL",
          dockerfile = {
            run("npm ci && npm run build") {
              cacheMount("/root/.npm", id = "npm-cache")
              bindMount("/host/src", "/app/src", readonly = true)
              tmpfsMount("/tmp", size = "500m")
              network(Dockerfile.RunNetwork.DEFAULT)
              security(Dockerfile.RunSecurity.NO_NEW_PRIVS)
            }
          },
        ),
      ) { it.isExpected() }
    }

    context("Fluent DSL configuration") {
      withData(
        nameFn = { "Fluent ${it.description}" },
        DockerfileTestCase(
          description = "ENV with DSL block",
          dockerfile = {
            env {
              "NODE_ENV" to "production"
              "PORT" to "3000"
              "DEBUG" to "false"
            }
          },
        ),
        DockerfileTestCase(
          description = "LABEL with DSL block",
          dockerfile = {
            label {
              "version" to "1.0.0"
              "maintainer" to "team@company.com"
              "description" to "My awesome app"
            }
          },
        ),
        DockerfileTestCase(
          description = "HEALTHCHECK with DSL block",
          dockerfile = {
            healthcheck {
              interval = "30s"
              timeout = "10s"
              retries = 3
              cmd = "curl -f http://localhost:3000/health"
            }
          },
        ),
      ) { it.isExpected() }
    }

    context("Method chaining fluency") {
      test("should support fluent method chaining") {
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

        dockerfile.buildString() shouldBe expected
      }
    }

    context("Complex Dockerfile scenarios") {
      withData(
        nameFn = { "Complex ${it.description}" },
        DockerfileTestCase(
          description = "Node.js application",
          dockerfile = {
            from("node", "18-alpine")
            workdir("/app")
            copy("package*.json", "./")
            run("npm ci --only=production")
            copy(".", ".")
            expose(3000)
            user("node")
            cmd("node", "server.js")
          }
        ),
        DockerfileTestCase(
          description = "Multi-stage Java build",
          dockerfile = {
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
        ),
      ) { it.isExpected() }
    }
  },
)
