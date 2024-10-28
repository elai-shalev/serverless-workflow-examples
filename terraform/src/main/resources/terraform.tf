provider "aws" {
  region = "us-east-1" # Specify your AWS region
}

resource "aws_s3_bucket" "my_bucket" {
  bucket = "my-unique-bucket-name-12345" # Change to a unique bucket name
  acl    = "private"                       # Access control list setting

  tags = {
    Name        = "MyBucket"
    Environment = "Dev"
  }
}