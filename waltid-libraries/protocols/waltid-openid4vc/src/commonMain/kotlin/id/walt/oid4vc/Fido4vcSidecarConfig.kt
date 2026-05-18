package id.walt.oid4vc

/**
 * Platform-specific resolver for the base URL used by [OpenID4VC.verifyLdpSignature]
 * to reach the fido-vc-verifier-sidecar.
 *
 * - JVM: reads the `FIDO_VERIFIER_SIDECAR_URL` env var, falls back to `http://localhost:8081`.
 * - JS / iOS: returns the static fallback.
 */
internal expect fun ldpVerificationSidecarBaseUrl(): String
