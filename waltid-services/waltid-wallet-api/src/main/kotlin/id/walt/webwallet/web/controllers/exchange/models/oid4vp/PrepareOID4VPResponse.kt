package id.walt.webwallet.web.controllers.exchange.models.oid4vp

import id.walt.oid4vc.data.dif.PresentationSubmission
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class PrepareOID4VPResponse(
    val did: String,
    val presentationRequest: String,
    val selectedCredentialIdList: List<String>,
    val presentationSubmission: PresentationSubmission,
    val w3CJwtVpProofParameters: W3cJwtVpProofParameters? = null,
    val w3cLdVpProofParameters: W3cLdVpProofParameters? = null,
    val ietfSdJwtVpProofParameters: List<IETFSdJwtVpProofParameters>? = null,
) {

    companion object {

        fun build(
            request: PrepareOID4VPRequest,
            presentationSubmission: PresentationSubmission,
            w3CJwtVpProofParameters: W3cJwtVpProofParameters? = null,
            ietfSdJwtVpProofParameters: List<IETFSdJwtVpProofParameters>? = null,
            w3cLdVpProofParameters: W3cLdVpProofParameters? = null
        ) =
            PrepareOID4VPResponse(
                did = request.did,
                presentationRequest = request.presentationRequest,
                selectedCredentialIdList = request.selectedCredentialIdList,
                presentationSubmission = presentationSubmission,
                w3CJwtVpProofParameters = w3CJwtVpProofParameters,
                ietfSdJwtVpProofParameters = ietfSdJwtVpProofParameters,
                w3cLdVpProofParameters = w3cLdVpProofParameters,
            )
    }
}