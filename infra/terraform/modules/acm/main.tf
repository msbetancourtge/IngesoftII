variable "domain_name" { type = string }

# For MVP, issue a public cert in the same region as ALB.
resource "aws_acm_certificate" "cert" {
  domain_name       = var.domain_name
  validation_method = "DNS"
}

output "certificate_arn" { value = aws_acm_certificate.cert.arn }
