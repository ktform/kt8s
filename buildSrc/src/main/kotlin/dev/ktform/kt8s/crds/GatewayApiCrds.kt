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

object GatewayApiCrds : Crds {
  override val crdUrls: Map<String, String> = mapOf(
    "BackendTlsPolicies.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.k8s.io_backendtlspolicies.yaml",
    "GatewayClasses.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.k8s.io_gatewayclasses.yaml",
    "Gateways.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.k8s.io_gateways.yaml",
    "GrpcRoutes.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.k8s.io_grpcroutes.yaml",
    "HttpRoutes.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.k8s.io_httproutes.yaml",
    "ReferenceGrants.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.k8s.io_referencegrants.yaml",
    "TcpRoutes.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.k8s.io_tcproutes.yaml",
    "TlsRoutes.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.k8s.io_tlsroutes.yaml",
    "UdpRoutes.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.k8s.io_udproutes.yaml",
    "XBackendTrafficPolicies.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.x-k8s.io_xbackendtrafficpolicies.yaml",
    "XListenerSets.yaml" to "https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/refs/heads/main/config/crd/experimental/gateway.networking.x-k8s.io_xlistenersets.yaml",
  )
}