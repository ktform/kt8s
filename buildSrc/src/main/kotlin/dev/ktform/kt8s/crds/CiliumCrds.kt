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

object CiliumCrds : Crds {
  override val crdUrls: Map<String, String> = mapOf(
    "bgpadvertisement.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumbgpadvertisements.yaml",
    "bgpclusterconfig.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumbgpclusterconfigs.yaml",
    "bgpnodeconfigoverride.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumbgpnodeconfigoverrides.yaml",
    "bgpnodeconfig.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumbgpnodeconfigs.yaml",
    "bgppeerconfig.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumbgppeerconfigs.yaml",
    "cidrgroup.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumcidrgroups.yaml",
    "clusterwideenvoyconfig.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumclusterwideenvoyconfigs.yaml",
    "clusterwidenetworkpolicy.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumclusterwidenetworkpolicies.yaml",
    "egressgatewaypolicy.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumegressgatewaypolicies.yaml",
    "endpoint.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumendpoints.yaml",
    "envoyconfig.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumenvoyconfigs.yaml",
    "identity.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumidentities.yaml",
    "localredirectpolicy.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumlocalredirectpolicies.yaml",
    "networkpolicy.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumnetworkpolicies.yaml",
    "nodeconfig.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumnodeconfigs.yaml",
    "node.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2/ciliumnodes.yaml",

    // v2alpha1
    "bgppeeringpolicies.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2alpha1/ciliumbgppeeringpolicies.yaml",
    "endpointslices.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2alpha1/ciliumendpointslices.yaml",
    "gatewayclassconfigs.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2alpha1/ciliumgatewayclassconfigs.yaml",
    "l2announcementpolicies.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2alpha1/ciliuml2announcementpolicies.yaml",
    "podippools.yaml" to "https://raw.githubusercontent.com/cilium/cilium/refs/heads/main/pkg/k8s/apis/cilium.io/client/crds/v2alpha1/ciliumpodippools.yaml",
  )
}
