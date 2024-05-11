data "aws_caller_identity" "current" {}

data "aws_availability_zones" "available" {}

data "aws_eks_cluster" "cluster" {
  name = "is-my-burguer"
}

data "aws_eks_cluster_auth" "cluster" {
  depends_on = [data.aws_eks_cluster.cluster]
  name       = data.aws_eks_cluster.cluster.name
}

data "terraform_remote_state" "is-my-burguer-db" {
  backend = "remote"

  config = {
    organization = "is-my-burguer"
    workspaces = {
      name = "is-my-burguer-db"
    }
  }
}