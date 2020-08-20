#!/bin/bash
riff knative deployer delete knative-balance
riff function delete balance
riff knative deployer delete knative-transactions
riff function delete transactions
riff knative deployer delete knative-aggregation
riff function delete aggregation


riff function create balance --local-path ./balance/ --handler balance
riff function create transactions --local-path ./transactions/ --handler transactions
riff function create aggregation --local-path ./aggregation/ --handler aggregation
riff knative deployer create knative-balance --function-ref balance --ingress-policy External --tail
riff knative deployer create knative-transactions --function-ref transactions --ingress-policy External --tail
riff knative deployer create knative-aggregation --function-ref aggregation --ingress-policy External --tail


MINIKUBE_IP=$(minikube ip)
INGRESS_PORT=$(kubectl get svc envoy-external  --namespace projectcontour --output 'jsonpath={.spec.ports[?(@.port==80)].nodePort}')
echo $MINIKUBE_IP:$INGRESS_PORT

curl http://$MINIKUBE_IP:$INGRESS_PORT/ -w '\n' \
-H 'Host: knative-balance.default.example.com' \
-H 'Content-Type: application/json' \
-H 'Accept: application/json' \
-d "userA"

curl -v http://$MINIKUBE_IP:$INGRESS_PORT/ -w '\n' \
-H 'Host: knative-transactions.default.example.com' \
-H 'Content-Type: application/json' \
-H 'Accept: application/json' \
-d "userA"

curl http://$MINIKUBE_IP:$INGRESS_PORT/ -w '\n' \
-H 'Host: knative-aggregation.default.example.com' \
-H 'Content-Type: application/json' \
-H 'Accept: application/json' \
-d "userA"



