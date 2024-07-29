package io.amplicode.http

import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.DependsOn
import kotlin.script.experimental.dependencies.Repository
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    fileExtension = "http.kts",
    displayName = "HTTP Script",
    compilationConfiguration = HttpScriptCompilationConfiguration::class,
    evaluationConfiguration = HttpScriptEvaluationConfiguration::class
)
abstract class HttpKotlinScript

object HttpScriptCompilationConfiguration: ScriptCompilationConfiguration({
    // Implicit imports for all scripts of this type
    defaultImports(DependsOn::class, Repository::class)
    jvm {
        // Extract the whole classpath from context classloader and use it as dependencies
        dependenciesFromCurrentContext(wholeClasspath = true)
    }
    implicitReceivers(MyRequestBuilder::class)
    // Callbacks
    refineConfiguration {
        // Process specified annotations with the provided handler
        onAnnotations(
            DependsOn::class,
            Repository::class,
            handler = ::configureMavenDepsOnAnnotations
        )
    }
}) {
    private fun readResolve(): Any = HttpScriptCompilationConfiguration
}

object HttpScriptEvaluationConfiguration : ScriptEvaluationConfiguration({
    scriptsInstancesSharing(false)
    constructorArgs()
}) {
    private fun readResolve(): Any = HttpScriptEvaluationConfiguration
}