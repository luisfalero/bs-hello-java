# BS HELLO JAVA

- Login in OCP4

```shell
oc login -u ${OCP4_USER} -p ${OCP4_PASSWORD} ${OCP4_MASTER_API}
```

- Access project

```shell
oc project ${OCP4_PROJECT}
```

- Create ConfigMap

```shell
oc create cm bs-hello-java \
    --from-literal system.user1="LUIS FALERO" \
    --from-literal system.user2="JUAN PEREZ"
```

- Create Secret

```shell
oc create secret generic bs-hello-java \
    --from-literal system.password1="123JUAN456" \
    --from-literal system.password2="123PEREZ456"
```

- Create App BS

```shell
oc new-app --name=bs-hello-java \
    java:8~https://github.com/luisfalero/bs-hello-java.git \
    --as-deployment-config
```

- Access logs

```shell
oc logs -f bc/bs-hello-java
```

- Referencing ConfigMap

```shell
oc set env dc/bs-hello-java --from cm/bs-hello-java
```

- Referencing Secret

```shell
oc set env dc/bs-hello-java --from secret/bs-hello-java
```

- Validate Environment

```shell
oc set env dc/bs-hello-java --list 
```

- Expose Route HTTP

```shell
oc expose svc/bs-hello-java --name=bs-hello-java --port=8080
```

- Expose Route HTTPS

```shell
oc create route edge --service=bs-hello-java --port=8080
```

- Checking the Health Status

```shell
curl ${URL}/actuator/health
```