package id.walt.policies.policies

/**
 * Platform-specific resolver for the default base URL used by [LdSignaturePolicy].
 *
 * - JVM: reads the `FIDO_VERIFIER_SIDECAR_URL` env var, falls back to `http://localhost:8081`.
 * - JS / iOS: returns the static fallback.
 */
internal expect fun ldSignaturePolicyDefaultBaseUrl(): String
