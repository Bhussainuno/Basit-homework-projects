provider "aws" {
  region = "us-east-1"  # Change if needed
}

# Store Discord Bot Token in Secrets Manager
resource "aws_secretsmanager_secret" "discord_bot_token" {
  name = "discord-bot-token"
}

resource "aws_secretsmanager_secret_version" "discord_bot_token_value" {
  secret_id     = aws_secretsmanager_secret.discord_bot_token.id
  secret_string = "your-discord-bot-token"  # Replace with actual token
}

# IAM Role for AWS Lambda
resource "aws_iam_role" "lambda_role" {
  name = "discord_lambda_role"

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

# IAM Policy for Lambda Permissions
resource "aws_iam_policy_attachment" "lambda_basic_execution" {
  name       = "lambda_basic_execution"
  roles      = [aws_iam_role.lambda_role.name]
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

# AWS Lambda Function for Discord Bot
resource "aws_lambda_function" "discord_bot" {
  function_name    = "discord-bot"
  role            = aws_iam_role.lambda_role.arn
  handler         = "bot_code.lambda_handler"  # Entry point in Python
  runtime         = "python3.9"
  filename        = "bot_function.zip"
  source_code_hash = filebase64sha256("bot_function.zip")

  environment {
    variables = {
      DISCORD_TOKEN = aws_secretsmanager_secret.discord_bot_token.id
    }
  }
}

# API Gateway to Trigger Lambda
resource "aws_apigatewayv2_api" "discord_api" {
  name          = "discord-bot-api"
  protocol_type = "HTTP"
}

# Create an API Gateway Integration with Lambda
resource "aws_apigatewayv2_integration" "lambda_integration" {
  api_id           = aws_apigatewayv2_api.discord_api.id
  integration_uri  = aws_lambda_function.discord_bot.invoke_arn
  integration_type = "AWS_PROXY"
}

# Route API Requests to Lambda
resource "aws_apigatewayv2_route" "default_route" {
  api_id    = aws_apigatewayv2_api.discord_api.id
  route_key = "POST /webhook"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

# Deploy API Gateway
resource "aws_apigatewayv2_stage" "default" {
  api_id      = aws_apigatewayv2_api.discord_api.id
  name        = "$default"
  auto_deploy = true
}

# Output API Gateway URL
output "api_gateway_url" {
  value = aws_apigatewayv2_api.discord_api.api_endpoint
}
