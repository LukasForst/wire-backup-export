package pw.forst.wire.android.backups.database.model

import org.jetbrains.exposed.sql.Table

object Assets2 : Table("Assets2") {
    val id = text("_id")
    val token = text("token").nullable()
    val name = text("name")
    val encryption = text("encryption")
    val mime = text("mime")
    val sha = blob("sha")
    val sourceT = text("source").nullable()
    val preview = text("preview").nullable()
    val details = text("details")
    val conversationId = text("conv_id").nullable()

    override val primaryKey = PrimaryKey(id)
}