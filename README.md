# BS HELLO JAVA

- Access project

```shell
oc project <project-name>
```

- Create project BS

```shell
oc new-app --name=bs-hello-java \
    java:11~https://github.com/luisfalero/bs-hello-java.git \
    --as-deployment-config

oc logs -f bc/bs-hello-java
```

- Access logs

```shell
oc logs -f bc/bs-hello-java
```

- Expose Route HTTP

```shell
oc expose svc/bs-hello-java --name=bs-hello-java --port=8080
```

- Expose Route HTTPS

```shell
oc create route edge --service=bs-hello-java --port=8080
```