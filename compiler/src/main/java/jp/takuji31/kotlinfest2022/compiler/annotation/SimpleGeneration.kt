package jp.takuji31.kotlinfest2022.compiler.annotation

/**
 * インターフェースから抽象クラスを作るだけのアノテーション
 */
@Target(AnnotationTarget.CLASS)
annotation class SimpleGeneration