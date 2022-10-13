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

- Create .jar in local

```shell
mvn clean -DskipTests install
```

- Deploy in OCP4

```shell
mvn clean -DskipTests oc:deploy -Popenshift
```