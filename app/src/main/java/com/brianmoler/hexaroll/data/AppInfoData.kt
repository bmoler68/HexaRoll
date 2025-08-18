package com.brianmoler.hexaroll.data

/**
 * AppInfoData - Central storage for application information
 * 
 * Contains all app-related information such as URLs, version data,
 * and copyright information in a centralized location for easy
 * maintenance and reference throughout the application.
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
     * Note: Version name should be kept in sync with the version
     * defined in build.gradle.kts for consistency.
     */
    object Version {
        const val VERSION_NAME = "1.0.0"
        const val VERSION_CODE = 1
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
     */
    fun getVersionString(): String = "Version ${Version.VERSION_NAME}"
    
    /**
     * Helper function to get full app information
     */
    fun getAppInfoSummary(): String = """
        ${getVersionString()}
        ${Info.COPYRIGHT_TEXT}
    """.trimIndent()
}
