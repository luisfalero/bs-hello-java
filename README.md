# BS HELLO JAVA

- Variables

```shell
source ocp4.config
```

- Login in OCP4

```shell
oc login -u ${OCP4_USER} -p ${OCP4_PASSWORD} ${OCP4_MASTER_API}
```

- Access project

```shell
oc project ${OCP4_PROJECT}
```

- Compiliar

```shell
mvn clean install
podman build -t quay.io/${OCP4_QUAY_USER}/bs-hello-java:1.0 .
podman push quay.io/${OCP4_QUAY_USER}/bs-hello-java:1.0
```

- Create Secret

```shell
oc create secret generic bs-hello-java-v1 \
    --from-literal system.user1="User01 V1" \
    --from-literal system.user2="User02 V1" \
    --from-literal system.password1="Pwd01 V1" \
    --from-literal system.password2="Pwd02 V2" \
    --from-literal opentracing.jaeger.service-name="bs-hello-java-v1" \
    --from-literal opentracing.jaeger.const-sampler.decision="true" \
    --from-literal opentracing.jaeger.sampler.param="1" \
    --from-literal opentracing.jaeger.log-spans="false" \
    --from-literal opentracing.jaeger.http-sender.url="http://jaeger-collector.istio-system.svc:14268/api/traces" \
    --from-literal opentracing.jaeger.enable-b3-propagation="true"
```

```shell
oc create secret generic bs-hello-java-v2 \
    --from-literal system.user1="User01 V2" \
    --from-literal system.user2="User02 V2" \
    --from-literal system.password1="Pwd01 V2" \
    --from-literal system.password2="Pwd02 V2" \
    --from-literal opentracing.jaeger.service-name="bs-hello-java-v2" \
    --from-literal opentracing.jaeger.const-sampler.decision="true" \
    --from-literal opentracing.jaeger.sampler.param="1" \
    --from-literal opentracing.jaeger.log-spans="false" \
    --from-literal opentracing.jaeger.http-sender.url="http://jaeger-collector.istio-system.svc:14268/api/traces" \
    --from-literal opentracing.jaeger.enable-b3-propagation="true"
```

- Create App BS

```shell
oc apply -f ./src/main/resources/yaml/app/deployment-v1.yaml -n ${OCP4_PROJECT}
oc apply -f ./src/main/resources/yaml/app/deployment-v2.yaml -n ${OCP4_PROJECT}
oc apply -f ./src/main/resources/yaml/app/service.yaml -n ${OCP4_PROJECT}
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
export URL=$(oc -n ${OCP4_PROJECT} get routes bs-hello-java -o jsonpath='{.spec.host}')

curl https://${URL}/actuator/health | jq
curl https://${URL}/bs/v1/hello | jq
curl https://${URL}/bs/v1/propagation?description=ServiceMesh | jq
```

```shell
oc -n istio-system edit istio-ingressgateway
```

```yaml
kind: Route
apiVersion: route.openshift.io/v1
metadata:
  name: istio-ingressgateway
spec:
  host: istio-ingressgateway-istio-system.apps.xxxx.xxxx
  to:
    kind: Service
    name: istio-ingressgateway
    weight: 100
  port:
    targetPort: 8080
  tls:
    termination: edge
    insecureEdgeTerminationPolicy: Redirect
  wildcardPolicy: None
```

- Service Mesh

```shell
oc -n istio-system patch routes/istio-ingressgateway \
  --patch '{"spec":{"tls":{"termination":"edge","insecureEdgeTerminationPolicy":"Redirect"}}}' --type=merge

export URL=$(oc -n istio-system get routes istio-ingressgateway -o jsonpath='{.spec.host}')
oc apply -f ./src/main/resources/yaml/istio/gateway.yaml -n ${OCP4_PROJECT}
oc apply -f ./src/main/resources/yaml/istio/destinationrule.yaml -n ${OCP4_PROJECT}
oc apply -f ./src/main/resources/yaml/istio/virtualservice.yaml -n ${OCP4_PROJECT}

curl https://${URL}/bs/v1/hello | jq
curl https://${URL}/bs/v1/propagation?description=ServiceMesh | jq

for X in {1..100}; do curl https://${URL}/bs/v1/propagation?description=ServiceMesh; echo; done;
```

##node-role.kubernetes.io/worker: ''