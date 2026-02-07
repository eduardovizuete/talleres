variable "sqs_queue_arn" {
  description = "ARN of the SQS queue"
  type        = string
}
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.0"
    }
  }
}

provider "aws" {
  region     = var.aws_region
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}

variable "aws_region" {
  description = "AWS region to deploy resources"
  type        = string
}

variable "aws_access_key" {
  description = "AWS access key"
  type        = string
}

variable "aws_secret_key" {
  description = "AWS secret key"
  type        = string
}

variable "resource_prefix" {
  description = "Prefix for all resource names"
  type        = string
  default     = "workshop-demo-aws-eduviz"
}

variable "sqs_queue_url" {
  description = "SQS queue URL for Lambda to publish messages"
  type        = string
}

resource "aws_iam_role" "lambda_exec" {
  name = "${var.resource_prefix}-lambda-exec"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "lambda.amazonaws.com"
      }
    }]
  })
}

resource "aws_iam_role_policy_attachment" "lambda_basic" {
  role       = aws_iam_role.lambda_exec.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

# Permitir que la Lambda envíe mensajes a la cola SQS
resource "aws_iam_role_policy" "lambda_sqs_send" {
  name = "${var.resource_prefix}-lambda-sqs-send"
  role = aws_iam_role.lambda_exec.id

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = "sqs:SendMessage",
        Resource = var.sqs_queue_arn
      }
    ]
  })
}

resource "aws_lambda_function" "sqs_publisher" {
  function_name = "${var.resource_prefix}-sqs-publisher"
  role          = aws_iam_role.lambda_exec.arn
  handler       = "lambda_function.lambda_handler"
  runtime       = "python3.12"
  filename      = "${path.module}/lambda.zip"
  source_code_hash = filebase64sha256("${path.module}/lambda.zip")
  environment {
    variables = {
      SQS_QUEUE_URL = var.sqs_queue_url
    }
  }
}

resource "aws_lambda_function_url" "sqs_publisher_url" {
  function_name      = aws_lambda_function.sqs_publisher.function_name
  authorization_type = "NONE"
}

output "lambda_function_url" {
  value = aws_lambda_function_url.sqs_publisher_url.function_url
  description = "Public URL for the Lambda function."
}
