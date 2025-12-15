terraform {
  required_version = ">= 1.6.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}

# Call modules (kept in single folder for MVP simplicity)
module "network" {
  source = "./modules/network"
  vpc_cidr = var.vpc_cidr
}

module "security" {
  source   = "./modules/security"
  vpc_id   = module.network.vpc_id
  alb_sg_id = null
}

module "acm" {
  source      = "./modules/acm"
  domain_name = var.domain_name
}

module "alb" {
  source            = "./modules/alb"
  vpc_id            = module.network.vpc_id
  public_subnet_ids = module.network.public_subnet_ids
  alb_sg_id         = module.security.alb_sg_id
  certificate_arn   = module.acm.certificate_arn
}

module "rds" {
  source              = "./modules/rds"
  vpc_id              = module.network.vpc_id
  private_subnet_ids  = module.network.private_subnet_ids
  rds_sg_id           = module.security.rds_sg_id
  db_username         = var.db_username
  db_password         = var.db_password
}

module "ecs" {
  source              = "./modules/ecs"
  cluster_name        = var.cluster_name
  private_subnet_ids  = module.network.private_subnet_ids
  ecs_sg_id           = module.security.ecs_sg_id
  alb_target_group_arn = module.alb.target_group_arn
  db_username         = var.db_username
  db_password         = var.db_password
  # Images from ECR
  images = var.images
  # DB endpoints from RDS module
  db_endpoints = {
    auth       = module.rds.auth_endpoint
    restaurant = module.rds.restaurant_endpoint
    geo        = module.rds.geo_endpoint
    menu       = module.rds.menu_endpoint
  }
}

output "alb_dns_name" {
  value = module.alb.alb_dns
}
