package pw.forst.wire.android.backups.database.converters

import ai.blindspot.ktoolz.extensions.parseJson
import ai.blindspot.ktoolz.extensions.whenNull
import com.fasterxml.jackson.databind.JsonNode
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import pw.forst.wire.android.backups.database.dto.LikingsDto
import pw.forst.wire.android.backups.database.dto.MessageDto
import pw.forst.wire.android.backups.database.model.Likings
import pw.forst.wire.android.backups.database.model.Messages

@Suppress("unused") // we need to force it to run inside transaction
fun Transaction.getLikings() =
    (Likings leftJoin Messages)
        .slice(Likings.messageId, Likings.userId, Messages.conversationId, Likings.timestamp)
        .selectAll()
        .map {
            LikingsDto(
                messageId = it[Likings.messageId].toUuid(),
                userId = it[Likings.userId].toUuid(),
                conversationId = it[Messages.conversationId].toUuid(),
                time = convertToStringTime(it[Likings.timestamp])
            )
        }


@Suppress("unused") // we need to force it to run inside transaction
fun Transaction.getTextMessages() =
    Messages
        .slice(
            Messages.messageType, Messages.id, Messages.conversationId, Messages.userId, Messages.time,
            Messages.quote, Messages.editTime, Messages.content
        ).select {
            Messages.messageType eq "Text"
        }.map { row ->
            MessageDto(
                id = row[Messages.id].toUuid(),
                conversationId = row[Messages.conversationId].toUuid(),
                userId = row[Messages.userId].toUuid(),
                time = convertToStringTime(row[Messages.time]),
                content = parseContent(row[Messages.content]),
                quote = row[Messages.quote]?.toUuid(),
                edited = row[Messages.editTime] != 0L
            )
        }

private fun parseContent(content: String?): String =
    requireNotNull(content) { "Content was null!" }.let {
        parseJson<JsonNode>(it)
            ?.get(0)
            ?.get("content")
            ?.asText()
            .whenNull { print("No content.") }
            ?: ""
    }