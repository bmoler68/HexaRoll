package com.brianmoler.hexaroll.data

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * AppInfoData - Central storage for application information
 * 
 * Contains all app-related information such as URLs, version data,
 * and copyright information in a centralized location for easy
 * maintenance and reference throughout the application.
 * 
 * Version information is dynamically retrieved from the build configuration
 * to ensure consistency between displayed version and actual app version.
 */
object AppInfoData {
    
    /**
     * Application URLs
     */
    object Urls {
        const val ABOUT_PAGE = "https://www.brianmoler.com/appdocs/HexaRoll/HexaRollDetails.html"
        const val PRIVACY_POLICY = "https://www.brianmoler.com/appdocs/HexaRoll/HexaRollPrivacyPolicy.html"
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
        const val COPYRIGHT_TEXT = "© 2025 Brian Moler. All rights reserved."
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
     * Helper function to get formatted copyright string
     */
    fun getCopyrightString(): String = "© ${Info.RELEASE_YEAR} ${Info.DEVELOPER_NAME}. All rights reserved."
    
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
