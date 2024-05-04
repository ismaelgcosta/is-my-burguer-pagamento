resource "kubectl_manifest" "is-my-burguer-pagamento-deployment" {
  depends_on = [
    data.aws_eks_cluster.cluster,
    kubernetes_secret.is-my-burguer-pagamento-db,
    kubernetes_secret.is-my-burguer-cognito
  ]
  yaml_body = <<YAML
apiVersion: apps/v1
kind: Deployment
metadata:
  name: is-my-burguer-pagamento
  namespace: is-my-burguer
  labels:
    name: is-my-burguer-pagamento
    app: is-my-burguer-pagamento
spec:
  replicas: 1
  selector:
    matchLabels:
      app: is-my-burguer-pagamento
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: is-my-burguer-pagamento
    spec:
      containers:
        - name: is-my-burguer-pagamento
          resources:
            limits:
              cpu: "1"
              memory: "300Mi"
            requests:
              cpu: "300m"
              memory: "300Mi"
          env:
            - name: MONGODB_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-pagamento-db
                  key: password
            - name: MONGODB_USER
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-pagamento-db
                  key: username
            - name: MONGODB_HOST
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-pagamento-db
                  key: host
            - name: CLIENT_CREDENTIALS_ID
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-cognito
                  key: username
            - name: CLIENT_CREDENTIALS_SECRET
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-cognito
                  key: password
            - name: CLIENT_DOMAIN
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-cognito
                  key: cognito_domain
            - name: AWS_COGNITO_USER_POOL_ID
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-cognito
                  key: user-pool-id
            - name: AWS_REGION
              value: ${local.region}
            - name: AWS_API_GATEWAY_ID
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-cognito
                  key: api-gateway
          image: docker.io/ismaelgcosta/is-my-burguer-pagamento:${var.TF_VAR_IMAGE_VERSION}
          ports:
            - containerPort: 8943
      restartPolicy: Always
status: {}
YAML
}


resource "kubectl_manifest" "is-my-burguer-pagamento-svc" {
  depends_on = [
    data.aws_eks_cluster.cluster,
    kubectl_manifest.is-my-burguer-pagamento-deployment
  ]
  yaml_body = <<YAML
apiVersion: v1
kind: Service
metadata:
  name: is-my-burguer-pagamento-svc
  namespace: is-my-burguer
spec:
  selector:
    app: is-my-burguer-pagamento
  ports:
    - name: https
      protocol: TCP
      port: 8943
      targetPort: 8943
YAML
}

resource "kubectl_manifest" "is-my-burguer-pagamento-hpa" {
  depends_on = [
    data.aws_eks_cluster.cluster,
    kubectl_manifest.is-my-burguer-pagamento-deployment,
    kubectl_manifest.is-my-burguer-pagamento-svc
  ]
  yaml_body = <<YAML
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: is-my-burguer-pagamento-hpa
  namespace: is-my-burguer
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: is-my-burguer-pagamento
    namespace: is-my-burguer
  minReplicas: 2
  maxReplicas: 4
  behavior:
    scaleDown:
      stabilizationWindowSeconds: 0 # para forçar o kubernets a zerar a janela de tempo e escalar imediatamente
    scaleUp:
      stabilizationWindowSeconds: 0 # para forçar o kubernets a zerar a janela de tempo e escalar imediatamente
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 1 # para forçar o kubernets escalar com 1% de cpu
status:
  observedGeneration: 0
  lastScaleTime:
  currentReplicas: 0
  desiredReplicas: 2
  currentMetrics:
  - type: Resource
    resource:
      name: cpu
YAML
}


resource "kubernetes_secret" "is-my-burguer-pagamento-db" {
  metadata {
    name      = "is-my-burguer-pagamento-db"
    namespace = "is-my-burguer"
  }

  immutable = false

  data = {
    host = "${var.TF_VAR_MONGODB_HOST}",
    username = "${var.TF_VAR_MONGODB_USERNAME}",
    password = "${var.TF_VAR_MONGODB_PASSWORD}"
  }

  type = "kubernetes.io/basic-auth"

}

resource "kubernetes_secret" "is-my-burguer-cognito" {

  metadata {
    name      = "is-my-burguer-cognito"
    namespace = "is-my-burguer"
  }

  immutable = false

  data = {
    user-pool-id= "${data.aws_cognito_user_pool_client.is-my-burguer-pagamento-client.user_pool_id}"
    api-gateway= "${data.terraform_remote_state.is-my-burguer-cognito.outputs.api_gateway_domain}"
    cognito_domain= "${data.terraform_remote_state.is-my-burguer-cognito.outputs.cognito_domain}"
    username = "${data.terraform_remote_state.is-my-burguer-cognito.outputs.is-my-burguer-api-client-id}",
    password = "${data.aws_cognito_user_pool_client.is-my-burguer-pagamento-client.client_secret}"
  }

  type = "kubernetes.io/basic-auth"

}



