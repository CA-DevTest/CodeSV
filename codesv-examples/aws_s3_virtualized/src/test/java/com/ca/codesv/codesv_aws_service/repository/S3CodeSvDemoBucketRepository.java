package com.ca.codesv.codesv_aws_service.repository;

import com.ca.codesv.model.ClassTransactionRepository;
import com.ca.codesv.sdk.annotation.TransactionDefinition;

import static com.ca.codesv.protocols.http.fluent.HttpFluentInterface.*;

/**
 * This is my demo repository for transaction related to bucket with name codesvdemo.
 */
public class S3CodeSvDemoBucketRepository extends ClassTransactionRepository {

    public static final String SUCCESSFUL_UPLOAD_TXN = "successful upload";
    public static final String SUCCESSFUL_REPORT_UPLOAD_TXN = "successful report upload";
    public static final String NON_EXISTING_BUCKET_TXN = "nonexisting bucket";
    public static final String ACCESS_DENIED_TXN = "access denied";
    public static final String OPERATION_ABORTED_TXN = "operation aborted";


    public static void setupKeyStore() {
        System.setProperty("javax.net.ssl.trustStore",
                Thread.currentThread().getContextClassLoader().getResource("keystore.jks").getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
    }

    @TransactionDefinition(name = S3CodeSvDemoBucketRepository.SUCCESSFUL_REPORT_UPLOAD_TXN, tags = "DEV")
    public void successfulReportUpload() {
        S3CodeSvDemoBucketRepository.setupKeyStore();
        forPut("https://codesvdemobucket.s3.eu-central-1.amazonaws.com/MyTest1")
                .usingHttps(
                        withSecureProtocol("TLS")
                                .keystorePath(
                                        this.getClass().getClassLoader().getResource("keystore.jks").getPath())
                                .keyPassword("password")
                                .keystorePassword("password")
                )
                .doReturn(
                        okMessage()
                                .withHeader("x-amz-id-2", "dq+K9e8FxXzXJNJKODsqjsJhmt6A7YRvUmtY8mF8POR1mIKVDu2uw9U9wFhHhlm15oBYFEZD7jg=")
                                .withHeader("x-amz-request-id", "0F31CAF4007AC502")
                                //.withHeader("ETag", "\"DHFMt59z8kOYKq3Gid0a4A==\"")
                                .withHeader("Server", "AmazonS3")
                );
    }


    @TransactionDefinition(name = S3CodeSvDemoBucketRepository.NON_EXISTING_BUCKET_TXN, tags = "DEV")
    public void uploadToNonexistingBucket() {
        S3CodeSvDemoBucketRepository.setupKeyStore();
        forPut("https://codesvdemobucket.s3.eu-central-1.amazonaws.com/MyTest2")
                .usingHttps(
                        withSecureProtocol("TLS")
                                .keystorePath(
                                        this.getClass().getClassLoader().getResource("keystore.jks").getPath())
                                .keyPassword("password")
                                .keystorePassword("password")
                )
                .doReturn(
                        aMessage(404)
                                .withXmlBody("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                        "<Error>\n" +
                                        "  <Code>NoSuchBucket</Code>\n" +
                                        "  <Message>The specified bucket does not exist.</Message>\n" +
                                        "  <Resource>/codesvdemobucket/MyTest2</Resource> \n" +
                                        "  <RequestId>4442587FB7D0A2F9</RequestId>\n" +
                                        "</Error>")
                );
    }

    @TransactionDefinition(name = S3CodeSvDemoBucketRepository.ACCESS_DENIED_TXN, tags = "DEV")
    public void accessDeniedBucket() {
        S3CodeSvDemoBucketRepository.setupKeyStore();
        forPut("https://codesvdemobucket.s3.eu-central-1.amazonaws.com/MyTest3")
                .usingHttps(
                        withSecureProtocol("TLS")
                                .keystorePath(
                                        this.getClass().getClassLoader().getResource("keystore.jks").getPath())
                                .keyPassword("password")
                                .keystorePassword("password")
                )
                .doReturn(
                        aMessage(403)
                                .withXmlBody("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                        "<Error>\n" +
                                        "  <Code>AccessDenied</Code>\n" +
                                        "  <Message>Access Denied.</Message>\n" +
                                        "  <Resource>/codesvdemobucket/MyTest3</Resource> \n" +
                                        "  <RequestId>4442587FB7D0A2F9</RequestId>\n" +
                                        "</Error>")
                );
    }

    @TransactionDefinition(name = S3CodeSvDemoBucketRepository.OPERATION_ABORTED_TXN, tags = "DISASTER")
    public void operationAbortedBucket() {
        S3CodeSvDemoBucketRepository.setupKeyStore();
        forPut("https://codesvdemobucket.s3.eu-central-1.amazonaws.com/MyTest4")
                .usingHttps(
                        withSecureProtocol("TLS")
                                .keystorePath(
                                        this.getClass().getClassLoader().getResource("keystore.jks").getPath())
                                .keyPassword("password")
                                .keystorePassword("password")
                )
                .doReturn(
                        aMessage(409)
                                .withXmlBody("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                        "<Error>\n" +
                                        "  <Code>OperationAborted</Code>\n" +
                                        "  <Message>A conflicting conditional operation is currently in progress against this resource. Try again.</Message>\n" +
                                        "  <Resource>/codesvdemobucket/MyTest4</Resource> \n" +
                                        "  <RequestId>4442587FB7D0A2F9</RequestId>\n" +
                                        "</Error>")
                );
    }
}
