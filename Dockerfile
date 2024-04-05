FROM --platform=linux/amd64 ubuntu as base

RUN apt-get update && apt-get install -y git

FROM scratch as source

WORKDIR /app

COPY . .
COPY .git .

FROM scratch as appmaps

WORKDIR /app

COPY tmp/appmap .

FROM base as final

WORKDIR /app

COPY --from=source /app /app
COPY --from=appmaps /app /app
COPY appmap /usr/local/bin/appmap

# Make sure that the AppMap directories as-built on the GitHub runner also
# resolve inside the container.
RUN mkdir -p /home/runner/work/spring-petclinic
RUN ln -s /app /home/runner/work/spring-petclinic/spring-petclinic

EXPOSE 30102

CMD /usr/local/bin/appmap context-provider -d /app -p "${PORT:-30102}"
