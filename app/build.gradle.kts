plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
}

kotlin {
    jvmToolchain(21)
}

android {
    namespace = "com.kblack.passsecureext"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kblack.passsecureext"
        minSdk = 30
        targetSdk = 35
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.bundles.androidxCoreComponents)
    implementation(libs.material3)
    implementation(libs.koin.android)
    implementation(libs.bundles.navigation)
    implementation(libs.zxcvbn4j)
    implementation(libs.androidFastScrollKt)
}