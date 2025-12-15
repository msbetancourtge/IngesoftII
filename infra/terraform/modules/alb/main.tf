variable "vpc_id" { type = string }
variable "public_subnet_ids" { type = list(string) }
variable "alb_sg_id" { type = string }
variable "enable_https" {
  type        = bool
  description = "Enable HTTPS listener and HTTP->HTTPS redirect"
  default     = false
}
variable "certificate_arn" {
  type        = string
  description = "ACM certificate ARN (required if enable_https is true)"
  default     = null
}

resource "aws_lb" "this" {
  name               = "clickmunch-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [var.alb_sg_id]
  subnets            = var.public_subnet_ids
}

resource "aws_lb_target_group" "gateway" {
  name     = "gateway-tg"
  port     = 8080
  protocol = "HTTP"
  vpc_id   = var.vpc_id
  health_check {
    path                = "/actuator/health"
    matcher             = "200"
    healthy_threshold   = 2
    unhealthy_threshold = 2
    interval            = 30
    timeout             = 5
  }
}

resource "aws_lb_listener" "https" {
  count             = var.enable_https ? 1 : 0
  load_balancer_arn = aws_lb.this.arn
  port              = 443
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-2016-08"
  certificate_arn   = var.certificate_arn

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.gateway.arn
  }
}

resource "aws_lb_listener" "http" {
  count             = var.enable_https ? 1 : 0
  load_balancer_arn = aws_lb.this.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type = "redirect"
    redirect {
      port        = "443"
      protocol    = "HTTPS"
      status_code = "HTTP_301"
    }
  }
}

resource "aws_lb_listener" "http_only" {
  count             = var.enable_https ? 0 : 1
  load_balancer_arn = aws_lb.this.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.gateway.arn
  }
}

output "target_group_arn" { value = aws_lb_target_group.gateway.arn }
output "alb_dns" { value = aws_lb.this.dns_name }
