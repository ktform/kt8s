package dev.ktform.kt8s.resources

interface Resource {
  val apiVersion: String
  val kind: String
  val group: String
  val version: String
}