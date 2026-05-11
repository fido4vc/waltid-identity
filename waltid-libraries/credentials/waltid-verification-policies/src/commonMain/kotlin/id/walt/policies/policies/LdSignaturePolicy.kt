package id.walt.policies.policies

import id.walt.crypto.exceptions.VerificationException
import id.walt.crypto.keys.jwk.JWKKey
import id.walt.policies.JwtVerificationPolicy
import id.walt.sdjwt.SDJwtVC
import id.walt.w3c.utils.VCFormat
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.*
import kotlinx.serialization.json.JsonElement
import love.forte.plugin.suspendtrans.annotation.JsPromise
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

class LdSignaturePolicy(var baseUrl: String = ldSignaturePolicyDefaultBaseUrl()) : JwtVerificationPolicy() {
    override val name = "signature_ld-vp"
    override val description =
        "Checks a JSON-LD presentation by verifying its cryptographic signature using the key referenced by the DID in `verificationMethod`."
    override val supportedVCFormats = setOf(VCFormat.ldp_vp)

    companion object {
        // Static fallback. The actual default value is resolved at construction
        // time via ldSignaturePolicyDefaultBaseUrl(), which on JVM reads the
        // FIDO_VERIFIER_SIDECAR_URL env var. Override in containerized
        // deployments where the verifier sidecar is reachable by a service name
        // (e.g., "http://verifier-sidecar:8081") rather than via localhost.
        const val DEFAULT_BASE_URL = "http://localhost:8081"
    }

    private val http = HttpClient {
        expectSuccess = false
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            url(baseUrl)
        }
    }

    @OptIn(ExperimentalJsExport::class)
    @JvmBlocking
    @JvmAsync
    @JsPromise
    @JsExport.Ignore
    override suspend fun verify(credential: String, args: Any?, context: Map<String, Any>): Result<JsonElement> {
        return runCatching {
            val ldp = Json.parseToJsonElement(credential) as JsonObject
            
            val response = http.post("/verify") {
                setBody(ldp)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            
            val responseBody = response.body<JsonObject>()
            val verified = responseBody["verified"]?.jsonPrimitive?.content?.toBoolean() 
                ?: throw VerificationException("Invalid response format: missing 'verified' field")
            
            if (!verified) {
                val error = responseBody["error"]?.jsonPrimitive?.content 
                    ?: "Signature verification failed"
                throw VerificationException(error)
            }
            ldp
        }
    }
}