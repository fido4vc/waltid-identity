package id.walt.oid4vc

internal actual fun ldpVerificationSidecarBaseUrl(): String =
    System.getenv("FIDO_VERIFIER_SIDECAR_URL") ?: "http://localhost:8081"
