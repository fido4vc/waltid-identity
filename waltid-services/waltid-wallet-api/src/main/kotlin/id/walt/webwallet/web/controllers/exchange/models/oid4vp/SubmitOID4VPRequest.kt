package id.walt.webwallet.web.controllers.exchange.models.oid4vp

import id.walt.oid4vc.data.dif.PresentationSubmission
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class SubmitOID4VPRequest(
    val did: String,
    val presentationRequest: String,
    val presentationSubmission: PresentationSubmission,
    val selectedCredentialIdList: List<String>,
    val disclosures: Map<String, List<String>>? = null,
    val w3cJwtVpProof: String? = null,
    val w3cLdVpProof: JsonObject? = null,
    val ietfSdJwtVpProofs: List<IETFSdJwtVpTokenProof>? = null,
) {

    companion object {

        fun build(
            response: PrepareOID4VPResponse,
            disclosures: Map<String, List<String>>? = null,
            w3cJwtVpProof: String? = null,
            ietfSdJwtVpProofs: List<IETFSdJwtVpTokenProof>? = null,
            w3cLdVpProof: JsonObject? = null,
        ) =
            SubmitOID4VPRequest(
                did = response.did,
                presentationRequest = response.presentationRequest,
                presentationSubmission = response.presentationSubmission,
                selectedCredentialIdList = response.selectedCredentialIdList,
                disclosures = disclosures,
                w3cJwtVpProof = w3cJwtVpProof,
                w3cLdVpProof = w3cLdVpProof,
                ietfSdJwtVpProofs = ietfSdJwtVpProofs,
            )
    }
}