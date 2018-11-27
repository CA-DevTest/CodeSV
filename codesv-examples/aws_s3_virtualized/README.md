# AWS S3 Demo virtualization with CodeSV

In this example, we are going to virtualize the AWS S3 client from AWS SDK to be able to create tests for uncommon edge cases (like AccessDenied, NonExistingBucket or OperationAborted).

## How to run it
1. For running AWS SDK on your local machine, you need to setup your environment (AWS Profile). For more information see this [page](https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/setup-credentials.html).
2. Create your bucket in AWS S3 to be sure it works with the real endpoint.
3. Add the host of your AWS S3 bucket to your KeyStore/TrustStore. AWS SDK client works with HTTPS. For tests in this example we are using _keystore.jks_ file from test resources.
4. Virtualize!

## Where are definitions for virtualized endpoints defined?
In this example, we are using our repository feature from CodeSV 1.3 and all our definitions for virtualization are stored in repository classes located in repository package under test folder (_S3CodeSvDemoBucketRepository.java_ and _S3CodeSvRepoTxnName.java_).