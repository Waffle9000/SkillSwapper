plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "org.setu.skillswapper"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "org.setu.skillswapper"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}