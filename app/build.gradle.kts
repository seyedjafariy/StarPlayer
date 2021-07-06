import org.jetbrains.kotlin.utils.addToStdlib.safeAs

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.safeArgs)

}

android {
    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion(AndroidSdk.bulid)

    defaultConfig {
        applicationId = "com.worldsnas.starplayer"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    viewBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(Support.supportLegacy)
    testImplementation(Testing.junit)
    testImplementation(Testing.mockServer)
    testImplementation(Testing.coreTesting)
    testImplementation(Testing.mockito)

    testImplementation(Kotlin.coroutinesAndroid)
    testImplementation(Testing.coroutinesTest)
    androidTestImplementation(Kotlin.coroutinesAndroid)
    androidTestImplementation(Testing.coroutinesTest)

    androidTestImplementation(Testing.supportJunitExt)
    androidTestImplementation(Testing.espressoCore)
    androidTestImplementation(Testing.coreTesting)

    implementation(ArchComponent.lifecycleExtension)

    implementation(Kotlin.kotlinStd7)
    implementation(Support.compat)
    implementation(AndroidKts.coreKts)

    implementation(DaggerLibs.dagger)
    kapt(DaggerLibs.daggerCompiler)

    implementation(Utils.exoplayer)

    implementation(Support.constraintLayout)
    implementation(Ui.circularImageView)
    implementation(Support.recyclerView)

    implementation(Ui.navigationFragment)
    implementation(Ui.navigationUi)

    implementation(MoshiLibs.moshiKotlin)
    implementation(MoshiLibs.moshiRetrofit)


    implementation(Network.okHttpLogging)
    implementation(Network.retrofit)
    implementation(Network.okHttp)
}
