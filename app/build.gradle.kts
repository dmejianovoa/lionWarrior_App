plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.damianmejia.lionwarrior"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.damianmejia.lionwarrior"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)

    // Retrofit: cliente HTTP para consumir la API REST
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // Convierte el JSON de la API a objetos Java (y viceversa) con Gson
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // Muestra en el Logcat las peticiones y respuestas (útil para depurar)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // Mockito: simular dependencias en las pruebas unitarias
        testImplementation("org.mockito:mockito-core:5.14.2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)

}