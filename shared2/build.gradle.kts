//plugins {
//    id 'org.jetbrains.kotlin.multiplatform'
//    id 'maven-publish'
//}
//apply plugin: 'com.android.library'
//apply plugin: 'kotlin-android-extensions'
//apply plugin: 'kotlin-native-cocoapods'
//
//android {
//    compileSdkVersion 29
//    buildToolsVersion "29.0.2"
//
//
//    defaultConfig {
//        minSdkVersion 15
//        targetSdkVersion 29
//        versionCode 1
//        versionName "1.0"
//
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles 'consumer-rules.pro'
//    }
//
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//
//}
//kotlin {
//    // This is for iPhone emulator
//    // Switch here to iosArm64 (or iosArm32) to build library for iPhone device
//    targets {
////        final def iOSTarget = System.getenv('SDK_NAME')?.startsWith("iphoneos") \
////                              ? presets.iosArm64 : presets.iosX64
////
////        fromPreset(iOSTarget, 'ios') {
////            binaries {
////                framework('MultiCore')
////            }
////        }
//        iosArm64("iosa")
//
////        fromPreset(presets.jvm, 'android')
//        android("android") {
//            // you can also publish both "release" and "debug"
//            publishLibraryVariants("release")
//        }
//    }
//
//    version = "1.0.0"
//
//    cocoapods {
//        summary = "This is a sample summary"
//        homepage = "www.example.com"
//    }
//
//
//
//    sourceSets {
//        commonMain {
//            dependencies {
//                implementation kotlin('stdlib-common')
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.3")
//                implementation("io.ktor:ktor-client-core:1.2.6")
//            }
//        }
//        commonTest {
//            dependencies {
//                implementation kotlin('test-common')
//                implementation kotlin('test-annotations-common')
//            }
//        }
//
//
//        iosMain {
//            dependencies {
//                implementation kotlin('stdlib')
//                implementation("org.jetbrains.kotlin:kotlin-stdlib")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-native:1.3.3")
//                implementation("io.ktor:ktor-client-ios:1.2.6")
//
//            }
//        }
//        iosTest {
//        }
//
//
//    }
//
//}
//dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-stdlib")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
//    implementation("io.ktor:ktor-client-android:1.2.6")
//
////    // LiveData and ViewModel
////    implementation 'androidx.lifecycle:lifecycle-extensions:2.1.0'
//    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0'
//}
//
//configurations {
//    compileClasspath
//}

import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.native.cocoapods")
}


version = "1.0.0"

kotlin {
    cocoapods {
        summary = "Shared module for Android and iOS"
        homepage = "Link to a Kotlin/Native module homepage"
    }

    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        compilations {
            val main by getting {
                kotlinOptions.freeCompilerArgs = listOf("-Xobjc-generics")
            }
        }
    }
    android()


    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.3")
        implementation("io.ktor:ktor-client-core:1.2.6")
    }

    sourceSets["iosMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.3")
        implementation("io.ktor:ktor-client-ios:1.2.6")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
    implementation("io.ktor:ktor-client-android:1.2.6")

    // LiveData and ViewModel
    val lifecycleVersion = "2.2.0-rc03"
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            consumerProguardFiles("consumer-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}