provider "aws" {
  access_key          = "test"
  secret_key          = "test"
  region              = "us-east-1"
  s3_force_path_style = true
  endpoints {
    sqs = "http://localhost:4566"
    sns = "http://localhost:4566"
    s3  = "http://localhost:4566"
  }
}

resource "aws_sqs_queue" "orders" {
  name = "workshop-demo"
}

resource "aws_sns_topic" "invoices" {
  name = "workshop-demo-invoices"
}

resource "aws_s3_bucket" "invoices" {
  bucket        = "workshop-demo-bucket"
  force_destroy = true
}

resource "aws_s3_bucket_object" "invoice_prefix" {
  bucket  = aws_s3_bucket.invoices.id
  key     = "invoices/"
  content = ""
}

output "sqs_queue_url" {
  value = aws_sqs_queue.orders.id
}

output "sns_topic_arn" {
  value = aws_sns_topic.invoices.arn
}

output "s3_bucket_name" {
  value = aws_s3_bucket.invoices.bucket
}
