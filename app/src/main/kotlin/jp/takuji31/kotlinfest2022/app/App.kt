/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package jp.takuji31.kotlinfest2022.app

fun main() {
    Hello().printHelloWorld()
}

class Hello : AbstractSimpleInterface() {
    override fun printHelloWorld() {
        println("Hello KSP world!")
    }
}