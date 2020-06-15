FROM golang@sha256:0991060a1447cf648bab7f6bb60335d1243930e38420bee8fec3db1267b84cfa as builder
ENV GO111MODULE=on
RUN apk update && apk add --no-cache git ca-certificates tzdata && update-ca-certificates
ENV USER=appuser
ENV UID=10001
RUN adduser \
   --disabled-password \
   --gecos "" \
   --home "/nonexistent" \
   --shell "/sbin/nologin" \
   --no-create-home \
   --uid "${UID}" \
   "${USER}"
WORKDIR $GOPATH/src/mypackage/myapp/
COPY . .
RUN go mod vendor
RUN ls
RUN CGO_ENABLED=0 GOOS=linux go build -ldflags="-w -s" -o /go/bin/hello -mod vendor main.go
FROM scratch
COPY --from=builder /usr/share/zoneinfo /usr/share/zoneinfo
COPY --from=builder /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/
COPY --from=builder /etc/passwd /etc/passwd
COPY --from=builder /etc/group /etc/group
COPY --from=builder /go/bin/hello /go/bin/hello
USER appuser:appuser
ENTRYPOINT ["/go/bin/hello"]
EXPOSE 8080