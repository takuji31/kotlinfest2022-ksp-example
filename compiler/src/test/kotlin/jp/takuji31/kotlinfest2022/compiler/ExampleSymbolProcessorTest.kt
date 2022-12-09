package jp.takuji31.kotlinfest2022.compiler

import com.google.common.truth.Truth.assertThat
import com.tschuchort.compiletesting.*
import org.junit.jupiter.api.Test

internal class ExampleSymbolProcessorTest {

    @Test
    fun process() {
        val compilation = KotlinCompilation().apply {
            sources = listOf(source)
            inheritClassPath = true
            symbolProcessorProviders = listOf(ExampleSymbolProcessorProvider())
            kspWithCompilation = true
        }
        val result = compilation.compile()

        assertThat(result.exitCode)
            .isEqualTo(KotlinCompilation.ExitCode.OK)

        val file =
            compilation.kspSourcesDir.walkTopDown().filter { it.isFile && it.name == "AbstractSimpleInterface.kt" }
                .firstOrNull()

        assertThat(file?.readText())
            .isEqualTo(
                """
                package jp.takuji31.kotlinfest2022.compiler

                public abstract class AbstractSimpleInterface : SimpleInterface

            """.trimIndent()
            )
    }

    companion object {
        val source = SourceFile.kotlin(
            "ExampleClass.kt", """
            package jp.takuji31.kotlinfest2022.compiler

            import jp.takuji31.kotlinfest2022.compiler.annotation.SimpleGeneration

            @SimpleGeneration
            interface SimpleInterface {
                fun printHelloWorld()
            }
        """.trimIndent()
        )
    }
}