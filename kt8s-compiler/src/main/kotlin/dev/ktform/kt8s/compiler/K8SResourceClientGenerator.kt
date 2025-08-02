package dev.ktform.kt8s.compiler

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import kotlinx.serialization.json.*
import java.net.URL

class K8SResourceClientGenerator(
    private val environment: SymbolProcessorEnvironment
) : SymbolProcessor {
    private val logger = environment.logger
    private val codeGenerator = environment.codeGenerator

    // Download and parse the cdk8s schema
    private val schemaUrl = "https://raw.githubusercontent.com/cdk8s-team/cdk8s/refs/heads/master/kubernetes-schemas/v1.33.0/_definitions.json"
    private val schemaJson: JsonObject by lazy {
        val text = URL(schemaUrl).readText()
        Json.parseToJsonElement(text).jsonObject
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.info("K8SResourceClientGenerator: Starting process")
        // TODO: Find annotated classes/interfaces and generate clients using schemaJson
        return emptyList()
    }
}

class K8SResourceClientGeneratorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        K8SResourceClientGenerator(environment)
}