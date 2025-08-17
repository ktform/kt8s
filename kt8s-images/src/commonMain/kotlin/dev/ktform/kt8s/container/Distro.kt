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

sealed class Distro(
    val name: String,
    val versions: List<String>,
    val install: String,
    val update: String,
    val upgrade: String,
    val cleanup: String,
    val securityUpdate: String,
    val unpack: String,
    val keepCache: String,
    val addRepo: (String, String) -> String,
) {

    object Alpine :
        Distro(
            "Alpine",
            listOf(),
            "apk add",
            "apk -U update",
            "apk -U upgrade -q",
            "apk cache --purge",
            listOf(
                    "apk -U update",
                    "apk -U upgrade --force-refresh",
                    "update-ca-certificates",
                    "openssl rehash",
                )
                .joinCommands(),
            listOf(
                    "mkdir -p apks/var/cache/apk",
                    "apk index -o apks/var/cache/apk/APKINDEX.00000000.tar.gz *.apk",
                    $$"for apk in *.apk; do tar xfz \"$apk\" --directory apks --overwrite; done",
                    "rm -rf apks/.* || true",
                )
                .joinCommands(),
            "",
            { repo, key -> listOf("").joinCommands() },
        )

    object Debian :
        Distro(
            "Debian",
            listOf(),
            "DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends",
            "DEBIAN_FRONTEND=noninteractive apt-get update -y",
            "DEBIAN_FRONTEND=noninteractive apt-get upgrade -y",
            listOf(
                    "apt-get purge -y --auto-remove",
                    $$"find /usr -name '*.pyc' -type f -exec bash -c 'for pyc; do dpkg -S \"$pyc\" &> /dev/null || rm -vf \"$pyc\"; done' -- '{}' +",
                    "rm -rf /var/lib/apt/lists/*",
                )
                .joinCommands(),
            listOf(
                    "DEBIAN_FRONTEND=noninteractive apt-get update -y",
                    "DEBIAN_FRONTEND=noninteractive apt-get upgrade -y",
                    "update-ca-certificates",
                    "openssl rehash",
                    "DEBIAN_FRONTEND=noninteractive apt-get install -y $(debsecan --suite $(lsb_release -cs) --format packages --only-fixed)",
                )
                .joinCommands(),
            listOf(
                    "mkdir -p /dpkg/var/lib/dpkg/status.d/",
                    $$"for deb in *.deb; do \\\n$${ident}package_name=\"$(dpkg-deb -I ${deb} | awk '/^ Package: .*$/ {print $2}')\"",
                    $$"echo \"Processing: ${package_name}\"",
                    $$"dpkg --ctrl-tarfile $deb | tar -Oxf - ./control > /dpkg/var/lib/dpkg/status.d/${package_name}",
                    $$"dpkg --extract $deb /dpkg || exit 10",
                    "done",
                    "find /dpkg/ -type d -empty -delete",
                    "rm -rf /dpkg/usr/share/doc/*",
                )
                .joinCommands(),
            "",
            { repo, key -> listOf("").joinCommands() },
        )

    object Photon :
        Distro(
            "PhotonOS",
            listOf(),
            "tdnf install -y --best --refresh --noplugins --setopt=install_weak_deps=0 --setopt=keepcache=1",
            "tdnf makecache",
            "tdnf upgrade -y --best --refresh --noplugins --setopt=install_weak_deps=0 --setopt=keepcache=1",
            "tdnf clean all",
            listOf(
                    "tdnf -y --best --refresh --security upgrade",
                    "openssl rehash_ca_certificates.sh",
                )
                .joinCommands(),
            "",
            "",
            { repo, key -> listOf("").joinCommands() },
        )

    object Oracle :
        Distro(
            "Oracle",
            listOf(),
            "microdnf install -y --refresh --best --nodocs --noplugins --setopt=install_weak_deps=0 --setopt=keepcache=1",
            "microdnf upgrade -y --refresh --best --nodocs --noplugins --setopt=install_weak_deps=0 --setopt=keepcache=1",
            "microdnf upgrade -y --refresh --best --nodocs --noplugins --setopt=install_weak_deps=0 --setopt=keepcache=1",
            "microdnf clean all && rm -rf /var/cache/yum",
            listOf(
                    "microdnf upgrade -y --refresh --best --nodocs --noplugins --setopt=install_weak_deps=0 --setopt=keepcache=1",
                    "update-ca-trust",
                    "openssl rehash",
                )
                .joinCommands(),
            listOf(
                    $$"for pkg in /tmp/*.rpm; do \\\n$${ident}rpm2cpio \"$pkg\" | cpio -idmv",
                    "done",
                )
                .joinCommands(),
            "",
            { repo, key -> listOf("").joinCommands() },
        )

    object Rocky :
        Distro(
            "Rocky",
            listOf(),
            Oracle.install,
            Oracle.update,
            Oracle.upgrade,
            Oracle.cleanup,
            Oracle.securityUpdate,
            Oracle.unpack,
            Oracle.keepCache,
            Oracle.addRepo,
        )

    object UBI :
        Distro(
            "RedHat UBI",
            listOf(),
            Oracle.install,
            Oracle.update,
            Oracle.upgrade,
            Oracle.cleanup,
            Oracle.securityUpdate,
            Oracle.unpack,
            Oracle.keepCache,
            Oracle.addRepo,
        )

    companion object {
        val ident = " ".repeat(4)

        fun List<Package>.forAllDistros() = all.entries.associate { (_, distro) -> distro to this }

        fun List<Package>.forRPMDistros() = rpm.entries.associate { (_, distro) -> distro to this }

        fun List<Package>.forNonRPMDistros() =
            nonRpm.entries.associate { (_, distro) -> distro to this }

        fun Map<Distro, List<Package>>.plus(other: Map<Distro, List<Package>>) =
            this.entries.associate { (k, v) -> k to v + other[k].orEmpty() }

        val nonRpm = mapOf<String, Distro>("alpine" to Alpine, "debian" to Debian)

        val rpm =
            mapOf<String, Distro>(
                "photon" to Photon,
                "oracle" to Oracle,
                "rocky" to Rocky,
                "ubi" to UBI,
            )

        val all =
            mapOf<String, Distro>(
                "alpine" to Alpine,
                "debian" to Debian,
                "photon" to Photon,
                "oracle" to Oracle,
                "rocky" to Rocky,
                "ubi" to UBI,
            )
    }
}
