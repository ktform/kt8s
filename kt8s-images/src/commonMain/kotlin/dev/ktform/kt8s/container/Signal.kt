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

/** Unix signals for container lifecycle management */
enum class Signal(val value: Int, val description: String) {
    SIGTERM(15, "Termination signal - graceful shutdown"),
    SIGKILL(9, "Kill signal - immediate termination"),
    SIGHUP(1, "Hangup signal - reload configuration"),
    SIGINT(2, "Interrupt signal - user interrupt"),
    SIGQUIT(3, "Quit signal - quit from keyboard"),
    SIGUSR1(10, "User-defined signal 1"),
    SIGUSR2(12, "User-defined signal 2");

    override fun toString(): String = name
}
