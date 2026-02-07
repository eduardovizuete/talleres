output "sqs_queue_url" {
  value = aws_sqs_queue.orders.id
}

output "sqs_queue_arn" {
  value = aws_sqs_queue.orders.arn
}

output "sns_topic_arn" {
  value = aws_sns_topic.invoices.arn
}

output "s3_bucket_name" {
  value = aws_s3_bucket.invoices.bucket
}
