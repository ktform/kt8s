package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param action What action was taken/failed regarding to the Regarding object.
 * @param count The number of times this event has occurred.
 * @param eventTime Time when this Event was first observed.
 * @param firstTimestamp The time at which the event was first recorded. (Time of server receipt is in TypeMeta.)
 * @param involvedObject The object that this event is about.
 * @param lastTimestamp The time at which the most recent occurrence of this event was recorded.
 * @param message A human-readable description of the status of this operation.
 * @param metadata Standard object's metadata. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param reason This should be a short, machine understandable string that gives the reason for the transition into the object's current status.
 * @param related Optional secondary object for more complex actions.
 * @param reportingComponent Name of the controller that emitted this Event, e.g. `kubernetes.io/kubelet`.
 * @param reportingInstance ID of the controller instance, e.g. `kubelet-xyzf`.
 * @param series Data about the Event series this event represents or nil if it's a singleton Event.
 * @param source The component reporting this event. Should be a short machine understandable string.
 * @param type Type of this event (Normal, Warning), new types could be added in the future
 */
@Serializable
public data class Event(
  public val action: String,
  public val count: Int,
  public val eventTime: KubernetesMicroTime,
  public val firstTimestamp: KubernetesTime,
  public val involvedObject: ObjectReference,
  public val lastTimestamp: KubernetesTime,
  public val message: String,
  public val metadata: ObjectMeta,
  public val reason: String,
  public val related: ObjectReference,
  public val reportingComponent: String,
  public val reportingInstance: String,
  public val series: EventSeries,
  public val source: EventSource,
  public val type: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "v1"

  @Transient
  override val group: String = ""

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "Event"
}