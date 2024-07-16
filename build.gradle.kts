// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath ("com.android.tools.build:gradle:7.4.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}
plugins {
    id("com.android.application") version "8.2.2" apply false
    id ("com.android.library") version "7.4.1" apply false

    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}