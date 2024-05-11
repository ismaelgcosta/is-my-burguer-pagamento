resource "kubectl_manifest" "is-my-burguer-pagamento-deployment" {
  depends_on = [
    data.aws_eks_cluster.cluster,
    kubernetes_secret.is-my-burguer-pagamento-db
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
            - name: SERVICE_DISCOVERY_USERNAME
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-sd
                  key: username
            - name: SERVICE_DISCOVERY_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-sd
                  key: password
          image: docker.io/ismaelgcosta/is-my-burguer-pagamento:${var.TF_VAR_IMAGE_VERSION}
          ports:
            - containerPort: 8743
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
      port: 8743
      targetPort: 8743
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
  maxReplicas: 2
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
        averageUtilization: 10 # para forçar o kubernets escalar com 10% de cpu
status:
  observedGeneration: 0
  lastScaleTime:
  currentReplicas: 2
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
    host = "${data.terraform_remote_state.is-my-burguer-db.outputs.mongodb_endpoint_host}",
    username = "${var.TF_VAR_MONGODB_PAGAMENTO_USERNAME}",
    password = "${var.TF_VAR_MONGODB_PAGAMENTO_PASSWORD}"
  }

  type = "kubernetes.io/basic-auth"

}



