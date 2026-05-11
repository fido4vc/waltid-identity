package id.walt.policies.policies

internal actual fun ldSignaturePolicyDefaultBaseUrl(): String =
    System.getenv("FIDO_VERIFIER_SIDECAR_URL") ?: "http://localhost:8081"
