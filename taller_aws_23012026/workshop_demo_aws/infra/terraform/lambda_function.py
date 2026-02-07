import os
import json
import boto3

def lambda_handler(event, context):
    sqs = boto3.client('sqs')
    queue_url = os.environ['SQS_QUEUE_URL']

    # CORS headers
    cors_headers = {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Methods': 'OPTIONS,POST'
    }


    # Detect HTTP method for both Lambda Function URL and API Gateway
    method = event.get('httpMethod')
    if not method:
        # Try AWS HTTP API format
        method = event.get('requestContext', {}).get('http', {}).get('method', '')
    method = (method or '').upper()

    # Handle preflight (OPTIONS) request
    if method == 'OPTIONS':
        return {
            'statusCode': 200,
            'headers': cors_headers,
            'body': json.dumps({'message': 'CORS preflight'})
        }

    try:
        body = event.get('body')
        if not body:
            return {
                'statusCode': 400,
                'headers': cors_headers,
                'body': json.dumps({'error': "Missing request body"})
            }
        data = json.loads(body)
        email = data.get('email')
        products = data.get('products')
        if not email or not isinstance(email, str) or not email.strip():
            return {
                'statusCode': 400,
                'headers': cors_headers,
                'body': json.dumps({'error': "'email' is required and must be a non-empty string"})
            }
        if not products or not isinstance(products, str) or not products.strip():
            return {
                'statusCode': 400,
                'headers': cors_headers,
                'body': json.dumps({'error': "'products' is required and must be a non-empty string"})
            }
        # Forward the validated message as JSON string
        message = json.dumps({'email': email, 'products': products})
        sqs.send_message(QueueUrl=queue_url, MessageBody=message)
        return {
            'statusCode': 200,
            'headers': cors_headers,
            'body': json.dumps({'status': 'Message sent', 'email': email, 'products': products})
        }
    except Exception as e:
        return {
            'statusCode': 500,
            'headers': cors_headers,
            'body': json.dumps({'error': str(e)})
        }
