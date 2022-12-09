package jp.takuji31.kotlinfest2022.compiler.visitor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

class SimpleGenerationVisitor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : KSVisitorVoid() {
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        if (classDeclaration.classKind != ClassKind.INTERFACE) {
            logger.error("Only interface allowed", classDeclaration)
            return
        }
        val packageName = classDeclaration.packageName.asString()
        val className = ClassName(packageName, "Abstract" + classDeclaration.simpleName.asString())
        val typeSpec = TypeSpec.classBuilder(className)
            .addModifiers(KModifier.ABSTRACT)
            .addSuperinterface(classDeclaration.toClassName())

        FileSpec.builder(packageName, className.simpleName)
            .addType(typeSpec.build())
            .build()
            .writeTo(
                codeGenerator,
                Dependencies(
                    aggregating = false,
                    classDeclaration.containingFile!!
                )
            )
    }
}