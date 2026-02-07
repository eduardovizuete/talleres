# Infraestructura principal para AWS

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
