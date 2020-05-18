object AndroidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
    const val bulid = "29.0.2"
}

object Versions {

    const val buildToolsVersion = "3.6.1"

    const val jUnitVersion = "4.13-beta-3"
    const val assertJVersion = "3.12.2"
    const val supportTestVersion = "1.1.0"
    const val supportCoreVersion = "1.0.0"
    const val espressoVersion = "3.1.0-alpha3"
    const val mockkVersion = "1.9.3"
    const val fakeItVersion = "v0.5"

    const val coreTestingVersion = "2.0.1"
    const val mockitoVersion = "2.1.0"
    const val mockServerVersion = "3.12.0"

    const val kotlinVersion = "1.3.61"
    const val coroutinesVersion = "1.3.3"
    const val detektPluginVersion = "1.0.0-RC16"
    const val arrowVersion = "0.9.0"

    const val supportLibraryVersion = "1.1.0"
    const val googleMaterialVersion = "1.0.0-rc01"
    const val pagingComponentVersion = "1.0.0-alpha6"
    const val constraintLayoutVersion = "1.1.3"
    const val archComponentVersion = "2.2.0"
    const val multidexVersion = "2.0.0"

    const val androidKotlinExtVersion1 = "1.0.0"
    const val androidKotlinExtVersion2 = "2.0.0"

    const val daggerVersion = "2.24"
    const val javaxVersion = "1.0"
    const val jetbrainsAnnotationVersion = "15.0"
    const val javaxInjectVersion = "1"

    const val rxJavaVersion = "2.1.6"
    const val rxKotlinVersion = "2.4.0-RC3"
    const val rxBindingVersion = "3.0.0"
    const val rxAndroidVersion = "2.0.1"
    const val rxBroadCastVersion = "2.0.0"
    const val rxPreferencesVersion = "2.0.0"

    const val butterKnifeVersion = "10.1.0"

    const val timberVersion = "4.7.1"

    const val retrofitVersion = "2.6.0"
    const val okHttpVersion = "4.0.1"

    const val moshiVersion = "1.8.0"
    const val koshiVersion = "2.0.1"

    const val conductorVersion = "3.0.0-rc2"

    const val leakCanaryVersion = "1.6.3"

    const val objectboxVersion = "2.3.4"
    const val sqlDelightVersion = "1.2.0"

    const val zXingVersion = "1.9.8"

    const val frescoVersion = "2.0.0"

    const val lottieVersion = "3.0.0"

    const val recyclerViewItemDecorationVersion = "1.0.0"

    const val imagePickerVersion = "1.5"

    const val materialDialogsVersion = "0.9.6.0"
    const val roundCardViewVersion = "1.0.0"
    const val progressVersion = "2.1.3"
    const val roundImageViewVersion = "2.3.0"
    const val btmNavVersion = "2.1.0"

    const val jsonTestVersion = "20140107"

    const val bottomNavVersion = "2.1.0"
    const val epoxyVersion = "3.7.0"
    const val flipperVersion = "0.31.1"
    const val chuckerVersion = "3.1.2"
    const val exoplayerVersion = "2.11.3"

    const val circularImageView = "3.1.0"
    const val navigationComponentVersion = "2.3.0-alpha03"

}

object BuildPlugins {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
    const val safeArgsClassPath =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationComponentVersion}"


}

object Testing {
    //testing
    const val junit = "junit:junit:${Versions.jUnitVersion}"
    const val assertJ = "org.assertj:assertj-core:${Versions.assertJVersion}"

    const val mockkUnit = "io.mockk:mockk:${Versions.mockkVersion}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockkVersion}"

    const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoVersion}"
    const val mockServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockServerVersion}"

    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"

    const val supportTestRunner = "androidx.test:runner:${Versions.supportTestVersion}"
    const val supportTestCore = "androidx.test:core:${Versions.supportTestVersion}"
    const val supportTestRule = "androidx.test:rules:${Versions.supportTestVersion}"
    const val supportJunitExt = "androidx.test.ext:junit:${Versions.supportCoreVersion}"
    const val supportCore = "androidx.test:core:${Versions.supportTestVersion}"

    const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTestingVersion}"


    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val espressoIntents =
        "androidx.test.espresso:espresso-intents:${Versions.espressoVersion}"
    const val espressoContrib =
        "androidx.test.espresso:espresso-contrib:${Versions.espressoVersion}"

    const val jsonTest = "org.json:json:${Versions.jsonTestVersion}"

    const val fakeIt = "com.github.moove-it:fakeit:${Versions.fakeItVersion}"

    const val robolectric = "org.robolectric:robolectric:4.2"
}

object Kotlin {
    //kotlin
    const val kotlinStd7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    const val kotlinStd8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesCommon =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.coroutinesVersion}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val coroutinesRx2 =
        "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${Versions.coroutinesVersion}"
}

object ArrowKotlin {
    const val arrowCore = "io.arrow-kt:arrow-core-data:${Versions.arrowVersion}"
    const val arrowExtensions = "io.arrow-kt:arrow-core-extensions:${Versions.arrowVersion}"

    const val arrowSyntax = "io.arrow-kt:arrow-syntax:${Versions.arrowVersion}"
    const val arrowTypeClasses = "io.arrow-kt:arrow-typeclasses:${Versions.arrowVersion}"

    const val arrowExtras = "io.arrow-kt:arrow-extras-data:${Versions.arrowVersion}"
    const val arrowExtrasExtensions = "io.arrow-kt:arrow-extras-extensions:${Versions.arrowVersion}"

    const val arrowEffectData = "io.arrow-kt:arrow-effects-data:${Versions.arrowVersion}"
    const val arrowEffectExtensions =
        "io.arrow-kt:arrow-effects-extensions:${Versions.arrowVersion}"

    const val arrowEffectRx = "io.arrow-kt:arrow-effects-rx2-data:${Versions.arrowVersion}"
    const val arrowEffectRxExtensions =
        "io.arrow-kt:arrow-effects-rx2-extensions:${Versions.arrowVersion}"

    const val arrowEffectCoroutine =
        "io.arrow-kt:arrow-effects-kotlinx-coroutines-data:${Versions.arrowVersion}"
    const val arrowEffectCoroutineExtensions =
        "io.arrow-kt:arrow-effects-kotlinx-coroutines-extensions:${Versions.arrowVersion}"

    const val arrowCompiler = "io.arrow-kt:arrow-meta:${Versions.arrowVersion}"
}

object Support {
    //support
    const val compat = "androidx.appcompat:appcompat:${Versions.supportLibraryVersion}"
    const val androidxCore = "androidx.core:core:${Versions.supportLibraryVersion}"
    const val design = "com.google.android.material:material:${Versions.googleMaterialVersion}"
    const val cardView = "androidx.cardview:cardview:${Versions.supportLibraryVersion}"
    const val annotation = "androidx.annotation:annotation:${Versions.supportLibraryVersion}"
    const val vectorDrawable =
        "androidx.vectordrawable:vectordrawable:${Versions.supportLibraryVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.supportLibraryVersion}"
    const val multiDex = "androidx.multidex:multidex:${Versions.multidexVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val supportLegacy = "androidx.legacy:legacy-support-v4:1.0.0"
    const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0-alpha04"
}

object ArchComponent {
    //architecture comps
    const val lifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime:${Versions.archComponentVersion}"
    const val lifecycleExtension =
        "androidx.lifecycle:lifecycle-extensions:${Versions.archComponentVersion}"
    const val lifecycleCompiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.archComponentVersion}"
}

object AndroidKts {
    //androidKTS
    const val coreKts = "androidx.core:core-ktx:${Versions.androidKotlinExtVersion1}"
    const val fragmentKts = "androidx.fragment:fragment-ktx:${Versions.androidKotlinExtVersion1}"
    const val collectionKts =
        "androidx.collection:collection-ktx:${Versions.androidKotlinExtVersion1}"
    const val viewModelKts =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidKotlinExtVersion2}"
}

object Firebase {
    const val firebaseCore = "com.google.firebase:firebase-core:17.0.1}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics:17.0.1}"
    const val firebaseCrashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1}"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging:19.0.1}"
    const val firebaseRemoteConfig = "com.google.firebase:firebase-config:18.0.0"
}

object Network {
    //network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:4.0.1}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"
}

object RxJava {
    //rxjava
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0-RC3}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"
    const val rxPreferences =
        "com.f2prateek.rx.preferences2:rx-preferences:${Versions.rxPreferencesVersion}"
    const val rxJavaRetrofit = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"

    const val rxBindingKotlin = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxBindingVersion}"
    const val rxBindingCompat =
        "com.jakewharton.rxbinding3:rxbinding-appcompat:${Versions.rxBindingVersion}"
    const val rxBindingMaterial =
        "com.jakewharton.rxbinding3:rxbinding-material:${Versions.rxBindingVersion}"
}

object MoshiLibs {
    //moshi
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshiVersion}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}"
    const val moshiRetrofit = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}"
    const val kotshi = "se.ansman.kotshi:api:${Versions.koshiVersion}"
    const val kotshiCompiler = "se.ansman.kotshi:compiler:${Versions.koshiVersion}"
}

object DaggerLibs {
    //Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"
    const val daggerAndroid = "com.google.dagger:dagger-android-support:${Versions.daggerVersion}"
    const val daggerAndroidCompiler =
        "com.google.dagger:dagger-android-processor:${Versions.daggerVersion}"
    const val findBugs = "com.google.code.findbugs:jsr305:3.0.2"
}

object AnnotationLibs {
    const val javaxAnnotation = "javax.annotation:jsr250-api:${Versions.javaxVersion}"
    const val injectAnnotation = "javax.inject:javax.inject:${Versions.javaxInjectVersion}"
    const val jetbrainsAnnotation = "org.jetbrains:annotations:17.0.0"
}

object Ui {
    //Ui
    const val roundCardView =
        "com.github.captain-miao:optroundcardview:${Versions.roundCardViewVersion}"
    const val recyclerViewtemDecoration =
        "com.bignerdranch.android:simple-item-decoration:${Versions.recyclerViewItemDecorationVersion}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottieVersion}"
    const val bottomBar = "com.aurelhubert:ahbottomnavigation:${Versions.bottomNavVersion}"
    const val circularImageView = "de.hdodenhof:circleimageview:${Versions.circularImageView}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponentVersion}"
    const val navigationUi =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationComponentVersion}"
}

object Utils {
    //Utils
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val fresco = "com.facebook.fresco:fresco:${Versions.frescoVersion}"
    const val frescoOkHttp = "com.facebook.fresco:imagepipeline-okhttp3:${Versions.frescoVersion}"
    const val photoDraweeView = "me.relex:photodraweeview:2.0.0}"
    const val conductor = "com.bluelinelabs:conductor:${Versions.conductorVersion}"
    const val leakCanary =
        "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanaryVersion}"
    const val leakCanaryNoOp =
        "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakCanaryVersion}"
    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxyVersion}"
    const val epoxyCompiler = "com.airbnb.android:epoxy-processor:${Versions.epoxyVersion}"
    const val flipper = "com.facebook.flipper:flipper:${Versions.flipperVersion}"
    const val flipperNoOp = "com.facebook.flipper:flipper-noop:${Versions.flipperVersion}"
    const val flipperNetworkPlugin =
        "com.facebook.flipper:flipper-network-plugin:${Versions.flipperVersion}"
    const val flipperFrescoPlugin =
        "com.facebook.flipper:flipper-fresco-plugin:${Versions.flipperVersion}"
    const val chucker = "com.github.ChuckerTeam.Chucker:library:${Versions.chuckerVersion}"
    const val chuckerNoop =
        "com.github.ChuckerTeam.Chucker:library-no-op:${Versions.chuckerVersion}"
    const val soLoader = "com.facebook.soloader:soloader:0.5.1}"
    const val stetho = "com.facebook.stetho:stetho:1.5.1"
    const val exoplayer = "com.google.android.exoplayer:exoplayer:${Versions.exoplayerVersion}"
}

object Database {
    const val sqlDelightAndroidAdapter =
        "com.squareup.sqldelight:android-driver:${Versions.sqlDelightVersion}"
    const val sqlDelightRuntime =
        "com.squareup.sqldelight:runtime-jvm:${Versions.sqlDelightVersion}"
    const val sqlDelightCoroutines =
        "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelightVersion}"
    const val sqlDelightJVM = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelightVersion}"
}

object ObjectboxLib {
    const val objectboxJava = "io.objectbox:objectbox-java-api:${Versions.objectboxVersion}"
    const val objectboxKotlin = "io.objectbox:objectbox-kotlin:${Versions.objectboxVersion}"
    const val objectboxRxJava = "io.objectbox:objectbox-rxjava:${Versions.objectboxVersion}"
    const val objectboxBrowser =
        "io.objectbox:objectbox-android-objectbrowser:${Versions.objectboxVersion}"
    const val objectboxAndroid = "io.objectbox:objectbox-android:${Versions.objectboxVersion}"
}

object ImageSlider {
    const val nineOldAndroidAnim = "com.nineoldandroids:library:2.4.0"
}

object ButterKnifeLib {
    const val butterKnife = "com.jakewharton:butterknife:${Versions.butterKnifeVersion}"
    const val butterKnifeCompiler =
        "com.jakewharton:butterknife-compiler:${Versions.butterKnifeVersion}"
}

object Modules {
    const val appModule = ":app"
    const val kotlinTestHelper = ":kotlintesthelpers"
    const val domain = ":domain"
    const val panther = ":panther"
    const val daggerCore = ":daggercore"
    const val base = ":base"
    const val core = ":core"
    const val mvi = ":mvi"
    const val navigation = ":navigation"
    const val home = ":home"
    const val movieDetail = ":moviedetail"
    const val slider = ":slider"
    const val gallery = ":gallery"
    const val db = ":db"
    const val androidCore = ":androidcore"
}

object Plugins {
    const val objectboxPlugin = "io.objectbox"
}