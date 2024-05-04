data "aws_caller_identity" "current" {}

data "aws_availability_zones" "available" {}

data "aws_eks_cluster" "cluster" {
  name = "is-my-burguer"
}

data "aws_eks_cluster_auth" "cluster" {
  depends_on = [data.aws_eks_cluster.cluster]
  name       = data.aws_eks_cluster.cluster.name
}

data "aws_cognito_user_pool_client" "is-my-burguer-pagamento-client" {
  client_id = data.terraform_remote_state.is-my-burguer-cognito.outputs.is-my-burguer-api-client-id
  user_pool_id = data.terraform_remote_state.is-my-burguer-cognito.outputs.cognito_id
}

#
#data "aws_db_instance" "is-my-burguer-postgres" {
#  db_instance_identifier = data.terraform_remote_state.is-my-burguer-postgres.outputs.database_instance
#}
#
#data "terraform_remote_state" "is-my-burguer-postgres" {
#  backend = "remote"
#
#  config = {
#    organization = "is-my-burguer"
#    workspaces   = {
#      name = "is-my-burguer-postgres"
#    }
#  }
#}

data "terraform_remote_state" "is-my-burguer-cognito" {
  backend = "remote"

  config = {
    organization = "is-my-burguer"
    workspaces   = {
      name = "is-my-burguer-cognito"
    }
  }
}
