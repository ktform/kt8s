package dev.ktform.kt8s.compiler

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*

class K8SResourceClientGenerator(
    private val environment: SymbolProcessorEnvironment
) : SymbolProcessor {
    private val logger = environment.logger
    private val codeGenerator = environment.codeGenerator

  override fun process(resolver: Resolver): List<KSAnnotated> {
    TODO("Not yet implemented")
  }
//    private val schemaDefinitions: Option<Map<JSONSchema.DefinitionName, JSONSchema.Definition>> by lazy {
//        loadKubernetesSchema()
//    }

//    private fun loadKubernetesSchema(): Option<Map<JSONSchema.DefinitionName, JSONSchema.Definition>> {
//        return try {
//            val resourceFile = findSchemaFile().getOrElse {
//                logger.error("K8SResourceClientGenerator: Could not find schema file")
//                return None
//            }
//
//            logger.info("K8SResourceClientGenerator: Loading schema from ${resourceFile.absolutePath}")
//
//            schemaParser.parseSchemaFile(resourceFile).fold(
//                ifLeft = { error ->
//                    logger.error("K8SResourceClientGenerator: Failed to parse schema: $error")
//                    None
//                },
//                ifRight = { definitions ->
//                    logger.info("K8SResourceClientGenerator: Loaded ${definitions.size} schema definitions")
//
//                    // Log some sample definitions for verification
//                    definitions.values.take(3).forEach { definition ->
//                        logger.info("K8SResourceClientGenerator: Sample definition - ${definition.name.value}: ${definition.description.take(100)}...")
//                    }
//
//                    Some(definitions)
//                }
//            )
//        } catch (e: Exception) {
//            logger.error("K8SResourceClientGenerator: Unexpected error loading schema: ${e.message}")
//            None
//        }
//    }



//    override fun process(resolver: Resolver): List<KSAnnotated> {
//        logger.info("K8SResourceClientGenerator: Starting process")
//
//        schemaDefinitions.fold(
//            ifEmpty = {
//                logger.warn("K8SResourceClientGenerator: No schema definitions loaded, skipping processing")
//            },
//            ifSome = { definitions ->
//                processWithDefinitions(definitions)
//            }
//        )
//
//        return emptyList()
//    }
//
//    private fun processWithDefinitions(definitions: Map<JSONSchema.DefinitionName, JSONSchema.Definition>) {
//
//    }
}

class K8SResourceClientGeneratorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        K8SResourceClientGenerator(environment)
}