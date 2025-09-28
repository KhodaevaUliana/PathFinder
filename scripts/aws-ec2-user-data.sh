#!/bin/bash
set -e

# Update system
yum update -y

# Install Java 21 (Amazon Corretto)
rpm --import https://yum.corretto.aws/corretto.key
curl -L https://yum.corretto.aws/corretto.repo | tee /etc/yum.repos.d/corretto.repo
yum install -y java-21-amazon-corretto-devel

# Verify Java installation
java -version || echo "Java not installed correctly"

# Install dependencies
yum install -y ruby wget

# Install CodeDeploy agent
cd /home/ec2-user
wget https://aws-codedeploy-eu-central-1.s3.eu-central-1.amazonaws.com/latest/install
chmod +x ./install
./install auto

# Enable and start CodeDeploy agent
systemctl enable codedeploy-agent
systemctl start codedeploy-agent
systemctl status codedeploy-agent --no-pager
