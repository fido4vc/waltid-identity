# Multi-stage build for walt.id Issuer API.

# --- builder ----------------------------------------------------------------
FROM gradle:jdk21 AS builder
WORKDIR /build
COPY . /build

RUN gradle :waltid-services:waltid-issuer-api:installDist \
    --no-daemon -x test -x integrationTest

# --- runtime ----------------------------------------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /waltid-issuer-api

COPY --from=builder \
    /build/waltid-services/waltid-issuer-api/build/install/waltid-issuer-api/ \
    .

EXPOSE 7002
CMD ["./bin/waltid-issuer-api"]
