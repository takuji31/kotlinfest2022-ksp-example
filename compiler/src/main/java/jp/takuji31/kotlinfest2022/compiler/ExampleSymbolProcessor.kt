package jp.takuji31.kotlinfest2022.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import jp.takuji31.kotlinfest2022.compiler.annotation.SimpleGeneration
import jp.takuji31.kotlinfest2022.compiler.visitor.SimpleGenerationVisitor

class ExampleSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        // Simple code generation
        val symbolsWithAnnotation = resolver
            .getSymbolsWithAnnotation(SimpleGeneration::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()

        symbolsWithAnnotation
            .filter { it.validate() }
            .forEach { it.accept(SimpleGenerationVisitor(codeGenerator, logger), Unit) }

        return symbolsWithAnnotation.filter { !it.validate() }.toList()
    }
}