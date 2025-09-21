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

import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

@DockerfileDsl
class DockerfileCommands {

    private val instructions = mutableListOf<String>()

    fun from(
        image: String,
        tag: String? = null,
        alias: String? = null,
        platform: String? = null,
    ): DockerfileCommands {
        val imageRef = buildString {
            append(image)
            tag?.let { append(":$it") }
            alias?.let { append(" AS $it") }
        }
        val instruction = buildString {
            platform?.let { append("--platform=$it ") }
            append("FROM $imageRef")
        }
        instructions.add(instruction)
        return this
    }

    fun fromScratch(): DockerfileCommands = from("scratch")

    fun run(vararg commands: String): DockerfileCommands {
        commands.forEach { cmd -> instructions.add("RUN $cmd") }
        return this
    }

    fun run(
        command: String,
        mounts: List<RunMount> = emptyList(),
        network: RunNetwork? = null,
        security: RunSecurity? = null,
    ): DockerfileCommands {
        val instruction = buildString {
            append("RUN")

            mounts.forEach { mount -> append(" --mount=${mount.toMountString()}") }

            network?.let { append(" --network=${it.value}") }
            security?.let { append(" --security=${it.value}") }

            append(" $command")
        }
        instructions.add(instruction)
        return this
    }

    fun run(command: String, block: RunConfiguration.() -> Unit): DockerfileCommands {
        val config = RunConfiguration().apply(block)
        return run(command, config.mounts, config.network, config.security)
    }

    fun copy(
        src: String,
        dest: String,
        from: String? = null,
        chown: String? = null,
        chmod: String? = null,
    ): DockerfileCommands {
        val instruction = buildString {
            append("COPY")
            from?.let { append(" --from=$it") }
            chown?.let { append(" --chown=$it") }
            chmod?.let { append(" --chmod=$it") }
            append(" $src $dest")
        }
        instructions.add(instruction)
        return this
    }

    fun add(
        src: String,
        dest: String,
        chown: String? = null,
        chmod: String? = null,
        checksum: String? = null,
    ): DockerfileCommands {
        val instruction = buildString {
            append("ADD")
            chown?.let { append(" --chown=$it") }
            chmod?.let { append(" --chmod=$it") }
            checksum?.let { append(" --checksum=$it") }
            append(" $src $dest")
        }
        instructions.add(instruction)
        return this
    }

    fun workdir(path: String): DockerfileCommands {
        instructions.add("WORKDIR $path")
        return this
    }

    fun expose(port: Int, protocol: String = "tcp"): DockerfileCommands {
        instructions.add("EXPOSE $port/$protocol")
        return this
    }

    fun env(key: String, value: String): DockerfileCommands {
        instructions.add("ENV $key=$value")
        return this
    }

    fun env(vararg pairs: Pair<String, String>): DockerfileCommands {
        pairs.forEach { (key, value) -> instructions.add("ENV $key=$value") }
        return this
    }

    fun env(block: EnvConfiguration.() -> Unit): DockerfileCommands {
        val config = EnvConfiguration().apply(block)
        config.variables.forEach { (key, value) -> instructions.add("ENV $key=$value") }
        return this
    }

    fun cmd(vararg command: String): DockerfileCommands {
        instructions.add("CMD [${command.joinToString(", ") { "\"$it\"" }}]")
        return this
    }

    fun entrypoint(vararg command: String): DockerfileCommands {
        instructions.add("ENTRYPOINT [${command.joinToString(", ") { "\"$it\"" }}]")
        return this
    }

    fun entrypointShell(command: String): DockerfileCommands {
        instructions.add("ENTRYPOINT $command")
        return this
    }

    fun label(key: String, value: String): DockerfileCommands {
        instructions.add("LABEL $key=\"$value\"")
        return this
    }

    fun label(vararg pairs: Pair<String, String>): DockerfileCommands {
        val labels = pairs.joinToString(" ") { (key, value) -> "$key=\"$value\"" }
        instructions.add("LABEL $labels")
        return this
    }

    fun label(block: LabelConfiguration.() -> Unit): DockerfileCommands {
        val config = LabelConfiguration().apply(block)
        val labels = config.labels.entries.joinToString(" ") { (key, value) -> "$key=\"$value\"" }
        instructions.add("LABEL $labels")
        return this
    }

    fun user(user: String, group: String? = null): DockerfileCommands {
        val userSpec = if (group != null) "$user:$group" else user
        instructions.add("USER $userSpec")
        return this
    }

    fun volume(vararg paths: String): DockerfileCommands {
        if (paths.size == 1) {
            instructions.add("VOLUME ${paths[0]}")
        } else {
            instructions.add("VOLUME [${paths.joinToString(", ") { "\"$it\"" }}]")
        }
        return this
    }

    fun arg(name: String, defaultValue: String? = null): DockerfileCommands {
        val instruction = if (defaultValue != null) "ARG $name=$defaultValue" else "ARG $name"
        instructions.add(instruction)
        return this
    }

    fun healthcheck(
        interval: String? = null,
        timeout: String? = null,
        startPeriod: String? = null,
        retries: Int? = null,
        cmd: String? = null,
    ): DockerfileCommands {
        val instruction = buildString {
            append("HEALTHCHECK")
            interval?.let { append(" --interval=$it") }
            timeout?.let { append(" --timeout=$it") }
            startPeriod?.let { append(" --start-period=$it") }
            retries?.let { append(" --retries=$it") }
            if (cmd != null) {
                append(" CMD $cmd")
            } else {
                append(" NONE")
            }
        }
        instructions.add(instruction)
        return this
    }

    fun healthcheck(block: HealthcheckConfiguration.() -> Unit): DockerfileCommands {
        val config = HealthcheckConfiguration().apply(block)
        return healthcheck(
            config.interval,
            config.timeout,
            config.startPeriod,
            config.retries,
            config.cmd,
        )
    }

    fun stopsignal(signal: String): DockerfileCommands {
        instructions.add("STOPSIGNAL $signal")
        return this
    }

    fun shell(vararg shell: String): DockerfileCommands {
        instructions.add("SHELL [${shell.joinToString(", ") { "\"$it\"" }}]")
        return this
    }

    fun onbuild(instruction: String): DockerfileCommands {
        instructions.add("ONBUILD $instruction")
        return this
    }

    fun buildString(): String = instructions.joinToString("\n")

    fun writeTo(path: Path): DockerfileCommands {
        SystemFileSystem.sink(path, false).use { sink ->
            val buffer = buildString().toBuffer()
            sink.write(buffer, buffer.size)
        }
        return this
    }

    data class EnvConfiguration(val variables: MutableMap<String, String> = mutableMapOf()) {
        infix fun String.to(value: String) {
            variables[this] = value
        }
    }

    data class LabelConfiguration(val labels: MutableMap<String, String> = mutableMapOf()) {
        infix fun String.to(value: String) {
            labels[this] = value
        }
    }

    data class HealthcheckConfiguration(
        var interval: String? = null,
        var timeout: String? = null,
        var startPeriod: String? = null,
        var retries: Int? = null,
        var cmd: String? = null,
    )

    sealed class RunMount {
        abstract fun toMountString(): String
    }

    data class BindMount(
        val source: String,
        val target: String,
        val readonly: Boolean = false,
        val from: String? = null,
    ) : RunMount() {
        override fun toMountString(): String = buildString {
            append("type=bind,source=$source,target=$target")
            if (readonly) append(",readonly")
            from?.let { append(",from=$it") }
        }
    }

    data class CacheMount(
        val target: String,
        val id: String? = null,
        val readonly: Boolean = false,
        val sharing: CacheSharing = CacheSharing.SHARED,
        val from: String? = null,
        val source: String? = null,
        val mode: String? = null,
        val uid: String? = null,
        val gid: String? = null,
    ) : RunMount() {
        override fun toMountString(): String = buildString {
            append("type=cache,target=$target")
            id?.let { append(",id=$it") }
            if (readonly) append(",readonly")
            append(",sharing=${sharing.value}")
            from?.let { append(",from=$it") }
            source?.let { append(",source=$it") }
            mode?.let { append(",mode=$it") }
            uid?.let { append(",uid=$it") }
            gid?.let { append(",gid=$it") }
        }
    }

    data class TmpfsMount(val target: String, val size: String? = null, val mode: String? = null) :
        RunMount() {
        override fun toMountString(): String = buildString {
            append("type=tmpfs,target=$target")
            size?.let { append(",size=$it") }
            mode?.let { append(",mode=$it") }
        }
    }

    data class SecretMount(
        val id: String,
        val target: String? = null,
        val required: Boolean = false,
        val mode: String? = null,
        val uid: String? = null,
        val gid: String? = null,
    ) : RunMount() {
        override fun toMountString(): String = buildString {
            append("type=secret,id=$id")
            target?.let { append(",target=$it") }
            if (required) append(",required")
            mode?.let { append(",mode=$it") }
            uid?.let { append(",uid=$it") }
            gid?.let { append(",gid=$it") }
        }
    }

    data class SshMount(
        val id: String = "default",
        val target: String? = null,
        val required: Boolean = false,
        val mode: String? = null,
        val uid: String? = null,
        val gid: String? = null,
    ) : RunMount() {
        override fun toMountString(): String = buildString {
            append("type=ssh,id=$id")
            target?.let { append(",target=$it") }
            if (required) append(",required")
            mode?.let { append(",mode=$it") }
            uid?.let { append(",uid=$it") }
            gid?.let { append(",gid=$it") }
        }
    }

    enum class CacheSharing(val value: String) {
        SHARED("shared"),
        PRIVATE("private"),
        LOCKED("locked"),
    }

    enum class RunNetwork(val value: String) {
        DEFAULT("default"),
        NONE("none"),
        HOST("host"),
    }

    enum class RunSecurity(val value: String) {
        INHERIT("inherit"),
        NO_NEW_PRIVS("no-new-privs"),
    }

    data class RunConfiguration(
        var mounts: MutableList<RunMount> = mutableListOf(),
        var network: RunNetwork? = null,
        var security: RunSecurity? = null,
    ) {
        fun mount(mount: RunMount) {
            mounts.add(mount)
        }

        fun bindMount(
            source: String,
            target: String,
            readonly: Boolean = false,
            from: String? = null,
        ) {
            mounts.add(BindMount(source, target, readonly, from))
        }

        fun cacheMount(
            target: String,
            id: String? = null,
            readonly: Boolean = false,
            sharing: CacheSharing = CacheSharing.SHARED,
        ) {
            mounts.add(CacheMount(target, id, readonly, sharing))
        }

        fun tmpfsMount(target: String, size: String? = null, mode: String? = null) {
            mounts.add(TmpfsMount(target, size, mode))
        }

        fun secretMount(
            id: String,
            target: String? = null,
            required: Boolean = false,
            mode: String? = null,
        ) {
            mounts.add(SecretMount(id, target, required, mode))
        }

        fun sshMount(
            id: String = "default",
            target: String? = null,
            required: Boolean = false,
            mode: String? = null,
        ) {
            mounts.add(SshMount(id, target, required, mode))
        }

        fun network(network: RunNetwork) {
            this.network = network
        }

        fun security(security: RunSecurity) {
            this.security = security
        }
    }
}
