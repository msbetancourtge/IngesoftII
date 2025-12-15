variable "vpc_id" { type = string }
variable "private_subnet_ids" { type = list(string) }
variable "rds_sg_id" { type = string }
variable "db_username" { type = string }
variable "db_password" { type = string }

locals {
  services = ["auth", "restaurant", "geo", "menu"]
}

resource "aws_db_subnet_group" "this" {
  name       = "clickmunch-rds-subnets"
  subnet_ids = var.private_subnet_ids
}

resource "aws_db_instance" "svc" {
  for_each                 = toset(local.services)
  allocated_storage        = 20
  engine                   = "postgres"
  engine_version           = "16"
  instance_class           = "db.t4g.micro"
  db_name                  = "${each.key}_db"
  username                 = var.db_username
  password                 = var.db_password
  db_subnet_group_name     = aws_db_subnet_group.this.name
  vpc_security_group_ids   = [var.rds_sg_id]
  publicly_accessible      = false
  skip_final_snapshot      = true
}

output "auth_endpoint" { value = aws_db_instance.svc["auth"].address }
output "restaurant_endpoint" { value = aws_db_instance.svc["restaurant"].address }
output "geo_endpoint" { value = aws_db_instance.svc["geo"].address }
output "menu_endpoint" { value = aws_db_instance.svc["menu"].address }
