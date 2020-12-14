import com.charleskorn.kaml.Yaml
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import org.junit.jupiter.api.Test

class TestKotlinSerialization {
    @Serializable
    data class Credentials(
        val publicKey: String,
        val privateKey: String,
    )

    @Test
    fun objectsToJsonAndBack() {
        val credentials = Credentials("publicKey", "privateKey")

        val stringValue = Json.encodeToString(credentials)
        println(stringValue)

        val credentialsDecoded = Json.decodeFromString<Credentials>(stringValue)
        println(credentialsDecoded)

    }

    @Test
    fun createJsonManually() {
        val credentials = JsonObject(
            mapOf(
                "publicKey" to JsonPrimitive("publicKey"),
                "privateKey" to JsonPrimitive("privateKey")
            )
        )

        val array = JsonArray(listOf(credentials))
        println(array.toString())
    }

    @Test
    fun createJsonManuallyDsl() {
        val credentials = buildJsonArray {
            addJsonObject {
                put("publicKey", "publickey")
                put("privateKey", "privateKey")
            }
        }

        println(credentials.toString())
    }

    @Test
    fun prettyPrintJson() {
        val format = Json { prettyPrint = true }
        val input = """
            {"publicKey":"publicKey","privateKey":"privateKey"}
        """.trimIndent()

        val jsonElement = format.decodeFromString<JsonElement>(input)
        val bodyInPrettyPrint = format.encodeToString(jsonElement)

        println(bodyInPrettyPrint)
    }

    @Test
    fun customYamlFormatter() {
        val yamlEncoded =
            """
                publicKey: "publicKey"
                privateKey: "privateKey"
            """.trimIndent()

        val credentials =
            Yaml.default.decodeFromString(
                Credentials.serializer(),
                yamlEncoded
            )

        println(credentials)
    }
}