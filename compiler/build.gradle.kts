plugins {
    id("jp.takuji31.kotlinfest2022.kotlin-library-conventions")
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:1.7.21-1.0.8")
    implementation("com.squareup:kotlinpoet:1.12.0")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")

    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.4.9")
    testImplementation("com.google.truth:truth:1.1.3")
}