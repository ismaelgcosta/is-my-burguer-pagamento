# Is My Burguer Pagamento [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ismaelgcosta_is-my-burguer-pagamento&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ismaelgcosta_is-my-burguer-pagamento)

Projeto para aplicação de conhecimentos da Fase 4 da Pós-Graduação em SOFTWARE ARCHITECTURE da FIAP

## Estrutura na AWS

![alt text](/docs/is-my-burguer-api.drawio.png)

# Requisitos necessários para compilar o projeto

Ter o docker instalado na sua máquina:

* [Windows](https://docs.docker.com/windows/started)

* [OS X](https://docs.docker.com/mac/started/)

* [Linux](https://docs.docker.com/linux/started/)

Ter o Java 20 instalado na sua máquina:

[JDK 20](https://jdk.java.net/java-se-ri/20)

# Terraform

## Requirements

| Name | Version |
|------|---------|
| <a name="requirement_aws"></a> [aws](#requirement\_aws) | ~> 5.38.0 |
| <a name="requirement_kubectl"></a> [kubectl](#requirement\_kubectl) | >= 1.7.0 |
| <a name="requirement_kubernetes"></a> [kubernetes](#requirement\_kubernetes) | ~> 2.26.0 |

## Providers

| Name | Version |
|------|---------|
| <a name="provider_aws"></a> [aws](#provider\_aws) | 5.38.0 |
| <a name="provider_kubectl"></a> [kubectl](#provider\_kubectl) | 1.14.0 |
| <a name="provider_kubernetes"></a> [kubernetes](#provider\_kubernetes) | 2.26.0 |
| <a name="provider_terraform"></a> [terraform](#provider\_terraform) | n/a |

## Modules

No modules.

## Resources

| Name | Type |
|------|------|
| [kubectl_manifest.is-my-burguer-pagamento-deployment](https://registry.terraform.io/providers/gavinbunney/kubectl/latest/docs/resources/manifest) | resource |
| [kubectl_manifest.is-my-burguer-pagamento-hpa](https://registry.terraform.io/providers/gavinbunney/kubectl/latest/docs/resources/manifest) | resource |
| [kubectl_manifest.is-my-burguer-pagamento-svc](https://registry.terraform.io/providers/gavinbunney/kubectl/latest/docs/resources/manifest) | resource |
| [kubernetes_secret.is-my-burguer-pagamento-db](https://registry.terraform.io/providers/hashicorp/kubernetes/latest/docs/resources/secret) | resource |
| [aws_availability_zones.available](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/data-sources/availability_zones) | data source |
| [aws_caller_identity.current](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/data-sources/caller_identity) | data source |
| [aws_eks_cluster.cluster](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/data-sources/eks_cluster) | data source |
| [aws_eks_cluster_auth.cluster](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/data-sources/eks_cluster_auth) | data source |
| [terraform_remote_state.is-my-burguer-db](https://registry.terraform.io/providers/hashicorp/terraform/latest/docs/data-sources/remote_state) | data source |

## Inputs

| Name | Description | Type | Default | Required |
|------|-------------|------|---------|:--------:|
| <a name="input_TF_VAR_IMAGE_VERSION"></a> [TF\_VAR\_IMAGE\_VERSION](#input\_TF\_VAR\_IMAGE\_VERSION) | The number of the new image version. | `string` | n/a | yes |
| <a name="input_TF_VAR_MONGODB_PAGAMENTO_PASSWORD"></a> [TF\_VAR\_MONGODB\_PAGAMENTO\_PASSWORD](#input\_TF\_VAR\_MONGODB\_PAGAMENTO\_PASSWORD) | The password for the mongodb database. | `string` | n/a | yes |
| <a name="input_TF_VAR_MONGODB_PAGAMENTO_USERNAME"></a> [TF\_VAR\_MONGODB\_PAGAMENTO\_USERNAME](#input\_TF\_VAR\_MONGODB\_PAGAMENTO\_USERNAME) | The username for the mongodb database. | `string` | n/a | yes |
| <a name="input_TF_VAR_SERVICE_DISCOVERY_PASSWORD"></a> [TF\_VAR\_SERVICE\_DISCOVERY\_PASSWORD](#input\_TF\_VAR\_SERVICE\_DISCOVERY\_PASSWORD) | The master password for the sd admin. | `string` | n/a | yes |
| <a name="input_TF_VAR_SERVICE_DISCOVERY_USERNAME"></a> [TF\_VAR\_SERVICE\_DISCOVERY\_USERNAME](#input\_TF\_VAR\_SERVICE\_DISCOVERY\_USERNAME) | The master username for sd admin. | `string` | n/a | yes |

## Outputs

No outputs.
