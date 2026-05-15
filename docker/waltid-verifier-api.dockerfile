# Multi-stage build for walt.id Verifier API.
#
# The Verifier API's LdSignaturePolicy reads FIDO_VERIFIER_SIDECAR_URL at
# construction time. Set it at deploy time so the verifier reaches the sidecar
# by service name across the Docker compose network (e.g., "http://verifier-sidecar:8081").

# --- builder ----------------------------------------------------------------
FROM gradle:jdk21 AS builder
WORKDIR /build
COPY . /build

RUN gradle :waltid-services:waltid-verifier-api:installDist \
    --no-daemon -x test

# --- runtime ----------------------------------------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /waltid-verifier-api

COPY --from=builder \
    /build/waltid-services/waltid-verifier-api/build/install/waltid-verifier-api/ \
    .
COPY --from=builder /build/waltid-services/waltid-verifier-api/config ./config

EXPOSE 7003
CMD ["./bin/waltid-verifier-api"]
