# Lambda para entorno LocalStack

resource "aws_iam_role" "lambda_exec" {
  name = "workshop-demo-local-lambda-exec"
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

resource "aws_lambda_function" "sqs_publisher" {
  function_name = "workshop-demo-local-sqs-publisher"
  role          = aws_iam_role.lambda_exec.arn
  handler       = "lambda_function.lambda_handler"
  runtime       = "python3.12"
  filename      = "${path.module}/lambda.zip"
  source_code_hash = filebase64sha256("${path.module}/lambda.zip")
  environment {
    variables = {
      SQS_QUEUE_URL = aws_sqs_queue.orders.id
    }
  }
}

resource "aws_lambda_function_url" "sqs_publisher_url" {
  function_name      = aws_lambda_function.sqs_publisher.function_name
  authorization_type = "NONE"
}
