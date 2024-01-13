package tachiyomi.domain.sync

import tachiyomi.core.preference.Preference
import tachiyomi.core.preference.PreferenceStore
import java.util.UUID

class SyncPreferences(
    private val preferenceStore: PreferenceStore,
) {
    fun syncHost() = preferenceStore.getString("sync_host", "https://sync.tachiyomi.org")
    fun syncAPIKey() = preferenceStore.getString("sync_api_key", "")
    fun lastSyncTimestamp() = preferenceStore.getLong(Preference.appStateKey("last_sync_timestamp"), 0L)

    fun syncInterval() = preferenceStore.getInt("sync_interval", 0)
    fun syncService() = preferenceStore.getInt("sync_service", 0)

    fun googleDriveAccessToken() = preferenceStore.getString(
        Preference.appStateKey("google_drive_access_token"),
        "",
    )

    fun googleDriveRefreshToken() = preferenceStore.getString(
        Preference.appStateKey("google_drive_refresh_token"),
        "",
    )

    fun uniqueDeviceID(): String {
        val uniqueIDPreference = preferenceStore.getString("unique_device_id", "")

        // Retrieve the current value of the preference
        var uniqueID = uniqueIDPreference.get()
        if (uniqueID.isBlank()) {
            uniqueID = UUID.randomUUID().toString()
            uniqueIDPreference.set(uniqueID)
        }

        return uniqueID
    }

    fun getSyncOptions(): SyncOptions {
        return SyncOptions(
            libraryEntries = preferenceStore.getBoolean("library_entries", true).get(),
            categories = preferenceStore.getBoolean("categories", true).get(),
            chapters = preferenceStore.getBoolean("chapters", true).get(),
            tracking = preferenceStore.getBoolean("tracking", true).get(),
            history = preferenceStore.getBoolean("history", true).get(),
            appSettings = preferenceStore.getBoolean("appSettings", true).get(),
            sourceSettings = preferenceStore.getBoolean("sourceSettings", true).get(),
            privateSettings = preferenceStore.getBoolean("privateSettings", true).get(),
        )
    }

    fun setSyncOptions(syncOptions: SyncOptions) {
        preferenceStore.getBoolean("library_entries", true).set(syncOptions.libraryEntries)
        preferenceStore.getBoolean("categories", true).set(syncOptions.categories)
        preferenceStore.getBoolean("chapters", true).set(syncOptions.chapters)
        preferenceStore.getBoolean("tracking", true).set(syncOptions.tracking)
        preferenceStore.getBoolean("history", true).set(syncOptions.history)
        preferenceStore.getBoolean("appSettings", true).set(syncOptions.appSettings)
        preferenceStore.getBoolean("sourceSettings", true).set(syncOptions.sourceSettings)
        preferenceStore.getBoolean("privateSettings", true).set(syncOptions.privateSettings)
    }
}

data class SyncOptions(
    val libraryEntries: Boolean = true,
    val categories: Boolean = true,
    val chapters: Boolean = true,
    val tracking: Boolean = true,
    val history: Boolean = true,
    val appSettings: Boolean = true,
    val sourceSettings: Boolean = true,
    val privateSettings: Boolean = false,
)
