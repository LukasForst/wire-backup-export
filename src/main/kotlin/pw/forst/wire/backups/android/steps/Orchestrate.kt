package pw.forst.wire.backups.android.steps

import pw.forst.wire.backups.utils.initSodium
import java.io.File
import java.util.UUID

/**
 * Decrypts the backup and export the database.
 * Returns database file or null when it was not possible to extract the database.
 */
fun decryptAndExtract(databaseFilePath: String, password: String, userId: String, pathToNewFolder: String = "tmp"): DecryptionResult? =
    decryptAndExtract(
        File(databaseFilePath),
        password.toByteArray(),
        UUID.fromString(userId),
        pathToNewFolder
    )

/**
 * Decrypts the backup and export the database.
 * Returns database file or null when it was not possible to extract the database.
 */
fun decryptAndExtract(databaseFile: File, password: ByteArray, userId: UUID, pathToNewFolder: String = "tmp"): DecryptionResult? =
    initSodium().let {
        decryptDatabase(databaseFile, password, userId)?.let {
            val (metadata, db) = extractBackup(it, userId, pathToNewFolder)
            DecryptionResult(metadata, db)
        }
    }

data class DecryptionResult(
    val metadata: ExportMetadata,
    val databaseFile: File
)
