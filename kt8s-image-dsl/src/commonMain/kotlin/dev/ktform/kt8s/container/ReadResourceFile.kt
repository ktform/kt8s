package dev.ktform.kt8s.container

import kotlinx.io.files.Path

expect class ReadResourceFile {
  fun readResource(name: Path): String
}