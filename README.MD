# Function Chaining
* Simple poc of function chaining using riff / knative.

### Pre-requisites
* [riff 0.5+](https://github.com/projectriff/riff)
* [minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/) +  kubernetes 1.16|1.17 OR kubernetes v1.16 /
* knative / minikube / kubernetes setup as described in the riff docs

Once the above are installed:
### DNS
If riff was installed with a custom DNS, update `aggregation/src/main/resources/application.properties`  with the appropriate suffix of your DNS.
Default values used are shown below.
```$xslt
transactions.url=http://knative-transactions.default.svc.cluster.local
balance.url=http://knative-balance.default.svc.cluster.local
```

```
./build.sh
```
