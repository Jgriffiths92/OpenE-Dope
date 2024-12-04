plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.opene_dope"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.opene_dope"
        minSdk = 33
        targetSdk = 34
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.commons.csv) // Or latest version
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui) // Replace with the latest version
    implementation(libs.androidx.activity.compose) // Replace with the latest version if needed
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.core)
    implementation(libs.androidx.ui.geometry.android)
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.activity:activity-compose")
    implementation(libs.androidx.foundation.layout.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}