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
package dev.ktform.kt8s.resources

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param accessModes accessModes contains all ways the volume can be mounted. More info:
 *   https://kubernetes.io/docs/concepts/storage/persistent-volumes#access-modes
 * @param awsElasticBlockStore awsElasticBlockStore represents an AWS Disk resource that is attached
 *   to a kubelet's host machine and then exposed to the pod. Deprecated: AWSElasticBlockStore is
 *   deprecated. All operations for the in-tree awsElasticBlockStore type are redirected to the
 *   ebs.csi.aws.com CSI driver. More info:
 *   https://kubernetes.io/docs/concepts/storage/volumes#awselasticblockstore
 * @param azureDisk azureDisk represents an Azure Data Disk mount on the host and bind mount to the
 *   pod. Deprecated: AzureDisk is deprecated. All operations for the in-tree azureDisk type are
 *   redirected to the disk.csi.azure.com CSI driver.
 * @param azureFile azureFile represents an Azure File Service mount on the host and bind mount to
 *   the pod. Deprecated: AzureFile is deprecated. All operations for the in-tree azureFile type are
 *   redirected to the file.csi.azure.com CSI driver.
 * @param capacity capacity is the description of the persistent volume's resources and capacity.
 *   More info: https://kubernetes.io/docs/concepts/storage/persistent-volumes#capacity
 * @param cephfs cephFS represents a Ceph FS mount on the host that shares a pod's lifetime.
 *   Deprecated: CephFS is deprecated and the in-tree cephfs type is no longer supported.
 * @param cinder cinder represents a cinder volume attached and mounted on kubelets host machine.
 *   Deprecated: Cinder is deprecated. All operations for the in-tree cinder type are redirected to
 *   the cinder.csi.openstack.org CSI driver. More info:
 *   https://examples.k8s.io/mysql-cinder-pd/README.md
 * @param claimRef claimRef is part of a bi-directional binding between PersistentVolume and
 *   PersistentVolumeClaim. Expected to be non-nil when bound. claim.VolumeName is the authoritative
 *   bind between PV and PVC. More info:
 *   https://kubernetes.io/docs/concepts/storage/persistent-volumes#binding
 * @param csi csi represents storage that is handled by an external CSI driver.
 * @param fc fc represents a Fibre Channel resource that is attached to a kubelet's host machine and
 *   then exposed to the pod.
 * @param flexVolume flexVolume represents a generic volume resource that is provisioned/attached
 *   using an exec based plugin. Deprecated: FlexVolume is deprecated. Consider using a CSIDriver
 *   instead.
 * @param flocker flocker represents a Flocker volume attached to a kubelet's host machine and
 *   exposed to the pod for its usage. This depends on the Flocker control service being running.
 *   Deprecated: Flocker is deprecated and the in-tree flocker type is no longer supported.
 * @param gcePersistentDisk gcePersistentDisk represents a GCE Disk resource that is attached to a
 *   kubelet's host machine and then exposed to the pod. Provisioned by an admin. Deprecated:
 *   GCEPersistentDisk is deprecated. All operations for the in-tree gcePersistentDisk type are
 *   redirected to the pd.csi.storage.gke.io CSI driver. More info:
 *   https://kubernetes.io/docs/concepts/storage/volumes#gcepersistentdisk
 * @param glusterfs glusterfs represents a Glusterfs volume that is attached to a host and exposed
 *   to the pod. Provisioned by an admin. Deprecated: Glusterfs is deprecated and the in-tree
 *   glusterfs type is no longer supported. More info:
 *   https://examples.k8s.io/volumes/glusterfs/README.md
 * @param hostPath hostPath represents a directory on the host. Provisioned by a developer or
 *   tester. This is useful for single-node development and testing only! On-host storage is not
 *   supported in any way and WILL NOT WORK in a multi-node cluster. More info:
 *   https://kubernetes.io/docs/concepts/storage/volumes#hostpath
 * @param iscsi iscsi represents an ISCSI Disk resource that is attached to a kubelet's host machine
 *   and then exposed to the pod. Provisioned by an admin.
 * @param local local represents directly-attached storage with node affinity
 * @param mountOptions mountOptions is the list of mount options, e.g. ["ro", "soft"]. Not
 *   validated - mount will simply fail if one is invalid. More info:
 *   https://kubernetes.io/docs/concepts/storage/persistent-volumes/#mount-options
 * @param nfs nfs represents an NFS mount on the host. Provisioned by an admin. More info:
 *   https://kubernetes.io/docs/concepts/storage/volumes#nfs
 * @param nodeAffinity nodeAffinity defines constraints that limit what nodes this volume can be
 *   accessed from. This field influences the scheduling of pods that use this volume.
 * @param persistentVolumeReclaimPolicy persistentVolumeReclaimPolicy defines what happens to a
 *   persistent volume when released from its claim. Valid options are Retain (default for manually
 *   created PersistentVolumes), Delete (default for dynamically provisioned PersistentVolumes), and
 *   Recycle (deprecated). Recycle must be supported by the volume plugin underlying this
 *   PersistentVolume. More info:
 *   https://kubernetes.io/docs/concepts/storage/persistent-volumes#reclaiming
 * @param photonPersistentDisk photonPersistentDisk represents a PhotonController persistent disk
 *   attached and mounted on kubelets host machine. Deprecated: PhotonPersistentDisk is deprecated
 *   and the in-tree photonPersistentDisk type is no longer supported.
 * @param portworxVolume portworxVolume represents a portworx volume attached and mounted on
 *   kubelets host machine. Deprecated: PortworxVolume is deprecated. All operations for the in-tree
 *   portworxVolume type are redirected to the pxd.portworx.com CSI driver when the
 *   CSIMigrationPortworx feature-gate is on.
 * @param quobyte quobyte represents a Quobyte mount on the host that shares a pod's lifetime.
 *   Deprecated: Quobyte is deprecated and the in-tree quobyte type is no longer supported.
 * @param rbd rbd represents a Rados Block Device mount on the host that shares a pod's lifetime.
 *   Deprecated: RBD is deprecated and the in-tree rbd type is no longer supported. More info:
 *   https://examples.k8s.io/volumes/rbd/README.md
 * @param scaleIO scaleIO represents a ScaleIO persistent volume attached and mounted on Kubernetes
 *   nodes. Deprecated: ScaleIO is deprecated and the in-tree scaleIO type is no longer supported.
 * @param storageClassName storageClassName is the name of StorageClass to which this persistent
 *   volume belongs. Empty value means that this volume does not belong to any StorageClass.
 * @param storageos storageOS represents a StorageOS volume that is attached to the kubelet's host
 *   machine and mounted into the pod. Deprecated: StorageOS is deprecated and the in-tree storageos
 *   type is no longer supported. More info: https://examples.k8s.io/volumes/storageos/README.md
 * @param volumeAttributesClassName Name of VolumeAttributesClass to which this persistent volume
 *   belongs. Empty value is not allowed. When this field is not set, it indicates that this volume
 *   does not belong to any VolumeAttributesClass. This field is mutable and can be changed by the
 *   CSI driver after a volume has been updated successfully to a new class. For an unbound
 *   PersistentVolume, the volumeAttributesClassName will be matched with unbound
 *   PersistentVolumeClaims during the binding process. This is a beta field and requires enabling
 *   VolumeAttributesClass feature (off by default).
 * @param volumeMode volumeMode defines if a volume is intended to be used with a formatted
 *   filesystem or to remain in raw block state. Value of Filesystem is implied when not included in
 *   spec.
 * @param vsphereVolume vsphereVolume represents a vSphere volume attached and mounted on kubelets
 *   host machine. Deprecated: VsphereVolume is deprecated. All operations for the in-tree
 *   vsphereVolume type are redirected to the csi.vsphere.vmware.com CSI driver.
 */
@Serializable
public data class PersistentVolumeSpec(
    public val accessModes: List<String>,
    public val awsElasticBlockStore: AWSElasticBlockStoreVolumeSource,
    public val azureDisk: AzureDiskVolumeSource,
    public val azureFile: AzureFilePersistentVolumeSource,
    public val capacity: StringOrNumber,
    public val cephfs: CephFSPersistentVolumeSource,
    public val cinder: CinderPersistentVolumeSource,
    public val claimRef: ObjectReference,
    public val csi: CSIPersistentVolumeSource,
    public val fc: FCVolumeSource,
    public val flexVolume: FlexPersistentVolumeSource,
    public val flocker: FlockerVolumeSource,
    public val gcePersistentDisk: GCEPersistentDiskVolumeSource,
    public val glusterfs: GlusterfsPersistentVolumeSource,
    public val hostPath: HostPathVolumeSource,
    public val iscsi: ISCSIPersistentVolumeSource,
    public val local: LocalVolumeSource,
    public val mountOptions: List<String>,
    public val nfs: NFSVolumeSource,
    public val nodeAffinity: VolumeNodeAffinity,
    public val persistentVolumeReclaimPolicy: String,
    public val photonPersistentDisk: PhotonPersistentDiskVolumeSource,
    public val portworxVolume: PortworxVolumeSource,
    public val quobyte: QuobyteVolumeSource,
    public val rbd: RBDPersistentVolumeSource,
    public val scaleIO: ScaleIOPersistentVolumeSource,
    public val storageClassName: String,
    public val storageos: StorageOSPersistentVolumeSource,
    public val volumeAttributesClassName: String,
    public val volumeMode: String,
    public val vsphereVolume: VsphereVirtualDiskVolumeSource,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PersistentVolumeSpec"
}
