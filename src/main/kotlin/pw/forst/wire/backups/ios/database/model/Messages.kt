package pw.forst.wire.backups.ios.database.model

import org.jetbrains.exposed.sql.Table

internal object Messages : Table("ZMESSAGE") {
    val id = integer("Z_PK")
    val senderId = (integer("ZSENDER") references Users.id).nullable()
    val timestamp = double("ZSERVERTIMESTAMP")
    val conversationId = (integer("ZVISIBLEINCONVERSATION") references Conversations.id).nullable()
    val updatedTimestamp = double("ZUPDATEDTIMESTAMP").nullable()
    override val primaryKey = PrimaryKey(GenericMessageData.id)
}