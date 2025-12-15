variable "aws_region" {
  description = "AWS region to deploy into"
  type        = string
}

variable "vpc_cidr" {
  description = "CIDR block for the VPC"
  type        = string
}

variable "domain_name" {
  description = "Domain name used for ACM certificate"
  type        = string
}

variable "db_username" {
  description = "Database master username"
  type        = string
  sensitive   = true
}

variable "db_password" {
  description = "Database master password"
  type        = string
  sensitive   = true
}

variable "cluster_name" {
  description = "ECS cluster name"
  type        = string
  default     = "clickmunch"
}

variable "images" {
  description = "Map of service -> image URI"
  type        = map(string)
}
