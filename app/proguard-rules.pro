# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep model classes
-keep class com.apkbuilderai.** { *; }

# Keep encryption classes
-keep class javax.crypto.** { *; }

# Keep Android specific classes
-keep class android.** { *; }

# Optimization
-optimizationpasses 5
-verbose

# Keep line numbers for debugging
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
