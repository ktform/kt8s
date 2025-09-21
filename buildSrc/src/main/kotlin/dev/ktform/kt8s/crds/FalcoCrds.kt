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
package dev.ktform.kt8s.crds

object FalcoCrds : Crds {
  override val crdUrls: Map<String, String> = mapOf(
    "Config.yaml" to "https://raw.githubusercontent.com/falcosecurity/falco-operator/refs/heads/main/config/crd/bases/artifact.falcosecurity.dev_configs.yaml",
    "Plugin.yaml" to "https://raw.githubusercontent.com/falcosecurity/falco-operator/refs/heads/main/config/crd/bases/artifact.falcosecurity.dev_plugins.yaml",
    "RulesFile.yaml" to "https://raw.githubusercontent.com/falcosecurity/falco-operator/refs/heads/main/config/crd/bases/artifact.falcosecurity.dev_rulesfiles.yaml",
    "Falco.yaml" to "https://raw.githubusercontent.com/falcosecurity/falco-operator/refs/heads/main/config/crd/bases/instance.falcosecurity.dev_falcos.yaml",
  )
}