# Multi-stage build for walt.id Wallet API.
# Build context: this repo's root.

# --- builder ----------------------------------------------------------------
FROM gradle:jdk21 AS builder
WORKDIR /build
COPY . /build

RUN gradle :waltid-services:waltid-wallet-api:installDist \
    --no-daemon -x test

# --- runtime ----------------------------------------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /waltid-wallet-api

COPY --from=builder \
    /build/waltid-services/waltid-wallet-api/build/install/waltid-wallet-api/ \
    .
COPY --from=builder /build/waltid-services/waltid-wallet-api/config ./config

EXPOSE 7001
CMD ["./bin/waltid-wallet-api"]
