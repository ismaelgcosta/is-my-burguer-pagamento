# precisa começar com TF_VAR_
variable "TF_VAR_POSTGRES_USER" {
  description = "The master username for the database."
  type        = string
  sensitive   = true
}

# precisa começar com TF_VAR_
variable "TF_VAR_POSTGRES_PASSWORD" {
  description = "The master password for the database."
  type        = string
  sensitive   = true
}

# precisa começar com TF_VAR_
variable "TF_VAR_MONGODB_HOST" {
  description = "The host for the mongodb database."
  type        = string
  sensitive   = true
}

# precisa começar com TF_VAR_
variable "TF_VAR_MONGODB_PASSWORD" {
  description = "The password for the mongodb database."
  type        = string
  sensitive   = true
}

# precisa começar com TF_VAR_
variable "TF_VAR_MONGODB_USERNAME" {
  description = "The username for the mongodb database."
  type        = string
  sensitive   = true
}

# precisa começar com TF_VAR_
variable "TF_VAR_COGNITO_PASSWORD" {
  description = "The master password for the COGNITO CLIENT."
  type        = string
  sensitive   = true
}

# precisa começar com TF_VAR_
variable "TF_VAR_IMAGE_VERSION" {
  description = "The number of the new image version."
  type        = string
}