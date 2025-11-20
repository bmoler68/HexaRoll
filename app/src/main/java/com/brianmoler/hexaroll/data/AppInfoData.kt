package com.brianmoler.hexaroll.data

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.util.Properties

/**
 * AppInfoData - Central storage for application information
 * 
 * Contains all app-related information such as URLs, version data,
 * and copyright information in a centralized location for easy
 * maintenance and reference throughout the application.
 * 
 * Version information is dynamically retrieved from the build configuration
 * to ensure consistency between displayed version and actual app version.
 * 
 * URLs are loaded exclusively from the secrets.properties file in assets.
 * The secrets file must be present and properly configured.
 */
object AppInfoData {
    
    // Cache for loaded secrets - using @Volatile for thread-safe visibility
    @Volatile
    private var secretsCache: Properties? = null
    
    // Synchronization lock for thread-safe cache initialization
    private val cacheLock = Any()
    
    /**
     * Loads secrets from the assets/secrets.properties file
     * 
     * Uses double-checked locking pattern for thread-safe lazy initialization.
     * This ensures that the file is only loaded once, even when accessed from
     * multiple threads simultaneously.
     * 
     * @param context The context for accessing assets
     * @return Properties object containing the secrets
     * @throws IllegalStateException if the secrets file is not found or cannot be read
     */
    private fun loadSecrets(context: Context): Properties {
        // First check (fast path) - no synchronization needed if cache is already loaded
        val cached = secretsCache
        if (cached != null) {
            return cached
        }
        
        // Synchronize only when cache needs to be initialized
        return synchronized(cacheLock) {
            // Second check (double-checked locking) - another thread might have loaded it
            val cachedAgain = secretsCache
            if (cachedAgain != null) {
                cachedAgain
            } else {
                // Load the file and cache it
                try {
                    val properties = Properties()
                    context.assets.open("secrets.properties").use { inputStream ->
                        properties.load(inputStream)
                    }
                    secretsCache = properties
                    properties
                } catch (e: Exception) {
                    throw IllegalStateException(
                        "secrets.properties file not found in assets. " +
                        "Please copy secrets.properties.example to secrets.properties and fill in the required values.",
                        e
                    )
                }
            }
        }
    }
    
    /**
     * Gets a property value from secrets file
     * 
     * @param context The context for accessing assets
     * @param key The property key
     * @return The property value
     * @throws IllegalStateException if the property is not found or is empty
     */
    private fun getSecretProperty(context: Context, key: String): String {
        val secrets = loadSecrets(context)
        val value = secrets.getProperty(key)?.takeIf { it.isNotBlank() }
            ?: throw IllegalStateException(
                "Required property '$key' not found in secrets.properties. " +
                "Please ensure all required properties are set in assets/secrets.properties."
            )
        return value
    }
    
    /**
     * Application URLs
     * 
     * URLs are loaded exclusively from the secrets.properties file in assets.
     * The secrets file must be present and contain all required URL properties.
     */
    object Urls {
        /**
         * Gets the About page URL from secrets file
         * 
         * @param context The context for accessing assets
         * @return The About page URL
         * @throws IllegalStateException if the URL is not found in secrets.properties
         */
        fun getAboutPage(context: Context): String {
            return getSecretProperty(context, "about.page.url")
        }
        
        /**
         * Gets the Privacy Policy URL from secrets file
         * 
         * @param context The context for accessing assets
         * @return The Privacy Policy URL
         * @throws IllegalStateException if the URL is not found in secrets.properties
         */
        fun getPrivacyPolicy(context: Context): String {
            return getSecretProperty(context, "privacy.policy.url")
        }
    }
    
    /**
     * Application version information
     * 
     * Version information is dynamically retrieved from the build configuration
     * to ensure consistency between displayed version and actual app version.
     */
    object Version {
        /**
         * Gets the version name from the build configuration.
         * 
         * @param context The context for accessing package information
         * @return The version name string, or "Unknown" if retrieval fails
         */
        fun getVersionName(context: Context): String {
            return try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName ?: "Unknown"
            } catch (e: PackageManager.NameNotFoundException) {
                "Unknown"
            }
        }
        
        /**
         * Gets the version code from the build configuration.
         * 
         * Handles both modern (Android 9+) and legacy version code formats.
         * Returns a default value if the package information cannot be retrieved.
         * 
         * @param context The context for accessing package information
         * @return The version code as an integer
         */
        fun getVersionCode(context: Context): Int {
            return try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageInfo.longVersionCode.toInt()
                } else {
                    @Suppress("DEPRECATION")
                    packageInfo.versionCode
                }
            } catch (e: PackageManager.NameNotFoundException) {
                10000
            }
        }
    }
    
    /**
     * Application metadata
     */
    object Info {
        const val DEVELOPER_NAME = "Brian Moler"
        const val RELEASE_YEAR = "2025"
    }
    
    /**
     * Helper function to get formatted version string
     * 
     * @param context The context for accessing package information
     * @return A formatted string containing the version information
     */
    fun getVersionString(context: Context): String = "Version ${Version.getVersionName(context)}"
    
    /**
     * Helper function to get formatted copyright string with MIT license information
     */
    fun getCopyrightString(): String = "Copyright (c) ${Info.RELEASE_YEAR} ${Info.DEVELOPER_NAME}\nLicensed under the MIT License"
    
    /**
     * Helper function to get full app information
     * 
     * @param context The context for accessing package information
     * @return A formatted string containing version and copyright information
     */
    fun getAppInfoSummary(context: Context): String = """
        ${getVersionString(context)}
        ${getCopyrightString()}
    """.trimIndent()

}
