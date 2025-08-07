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

import dev.ktform.kt8s.container.dsl.dockerfile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BasicDockerfileTest : FunSpec(
  {
    context("Check basic JS processing") {
      test("should support fluent method chaining") {
        dockerfile {
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
        }.buildString() shouldBe """FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN --mount=type=cache,target=/root/.npm,id=npm-cache,sharing=shared --network=default npm ci --only=production
COPY . .
EXPOSE 3000/tcp
ENV NODE_ENV=production
USER node
CMD ["npm", "start"]"""
      }
    }
  })
