package pw.forst.wire.backups.ios.database

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Disabled
class MessagesConverterKtTest {
    @Test
    fun `test get some data from db`() {
        val data = withDatabase(
            "/Users/lukas/work/wire/android-db-decryption/ignored-assets/store.wiredatabase"
        ) {
            getGenericMessages()
        }
        assertTrue { data.isNotEmpty() }

        println(data)
        println(data.size)

        assertEquals(550, data.size)
    }

}
