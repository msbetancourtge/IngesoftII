variable "cluster_name" { type = string }
variable "private_subnet_ids" { type = list(string) }
variable "ecs_sg_id" { type = string }
variable "alb_target_group_arn" { type = string }
variable "images" { type = map(string) }
variable "db_endpoints" { type = map(string) }
variable "db_username" { type = string }
variable "db_password" { type = string }
variable "execution_role_arn" {
  type        = string
  description = "Existing ECS task execution role ARN (leave empty to create)"
  default     = ""
}

resource "aws_ecs_cluster" "this" {
  name = var.cluster_name
}

resource "aws_iam_role" "task_execution" {
  count              = var.execution_role_arn == "" ? 1 : 0
  name               = "clickmunch-ecs-task-execution"
  assume_role_policy = data.aws_iam_policy_document.task_assume.json
}

data "aws_iam_policy_document" "task_assume" {
  statement {
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

resource "aws_iam_role_policy_attachment" "task_execution" {
  count      = var.execution_role_arn == "" ? 1 : 0
  role       = aws_iam_role.task_execution[0].name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

locals {
  execution_role_arn = var.execution_role_arn != "" ? var.execution_role_arn : aws_iam_role.task_execution[0].arn
}

locals {
  services = {
    api_gateway = {
      port = 8080
      env  = {}
    }
    auth = {
      port = 8081
      env  = {
        DB_HOST     = var.db_endpoints["auth"]
        DB_USER     = var.db_username
        DB_PASSWORD = var.db_password
      }
    }
    restaurant = {
      port = 8082
      env  = {
        DB_HOST     = var.db_endpoints["restaurant"]
        DB_USER     = var.db_username
        DB_PASSWORD = var.db_password
      }
    }
    geo = {
      port = 8083
      env  = {
        DB_HOST     = var.db_endpoints["geo"]
        DB_USER     = var.db_username
        DB_PASSWORD = var.db_password
      }
    }
    menu = {
      port = 8084
      env  = {
        DB_HOST     = var.db_endpoints["menu"]
        DB_USER     = var.db_username
        DB_PASSWORD = var.db_password
      }
    }
  }
}

resource "aws_ecs_task_definition" "svc" {
  for_each                 = local.services
  family                   = "clickmunch-${each.key}"
  cpu                      = 256
  memory                   = 512
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  execution_role_arn       = local.execution_role_arn
  container_definitions    = jsonencode([
    {
      name      = each.key
      image     = var.images[each.key]
      essential = true
      portMappings = [
        {
          containerPort = each.value.port
          hostPort      = each.value.port
          protocol      = "tcp"
        }
      ]
      environment = [for k, v in each.value.env : { name = k, value = v }]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = "/ecs/clickmunch-${each.key}"
          awslogs-region        = data.aws_region.current.name
          awslogs-stream-prefix = "ecs"
        }
      }
    }
  ])
}

data "aws_region" "current" {}

resource "aws_cloudwatch_log_group" "svc" {
  for_each = local.services
  name     = "/ecs/clickmunch-${each.key}"
  retention_in_days = 7
}

resource "aws_ecs_service" "svc" {
  for_each            = local.services
  name                = "clickmunch-${each.key}"
  cluster             = aws_ecs_cluster.this.id
  task_definition     = aws_ecs_task_definition.svc[each.key].arn
  desired_count       = 1
  launch_type         = "FARGATE"
  enable_execute_command = true

  network_configuration {
    subnets         = var.private_subnet_ids
    security_groups = [var.ecs_sg_id]
    assign_public_ip = false
  }

  dynamic "load_balancer" {
    for_each = each.key == "api_gateway" ? [1] : []
    content {
      target_group_arn = var.alb_target_group_arn
      container_name   = "api_gateway"
      container_port   = local.services["api_gateway"].port
    }
  }
}

output "cluster_id" { value = aws_ecs_cluster.this.id }
