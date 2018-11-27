/******************************************************************************
 *
 * Copyright (c) 2018 CA.  All rights reserved.
 *
 * This software and all information contained therein is confidential and
 * proprietary and shall not be duplicated, used, disclosed or disseminated
 * in any way except as authorized by the applicable license agreement,
 * without the express written permission of CA. All authorized reproductions
 * must be marked with this language.
 *
 * EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT
 * PERMITTED BY APPLICABLE LAW, CA PROVIDES THIS SOFTWARE WITHOUT
 * WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY IMPLIED
 * WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  IN
 * NO EVENT WILL CA BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY
 * LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM THE USE OF THIS SOFTWARE,
 * INCLUDING WITHOUT LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION,
 * GOODWILL, OR LOST DATA, EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR
 * DAMAGE.
 *
 ******************************************************************************/
package com.ca.codesv.codesv_aws_service;

public enum ValidationResult {
    UPLOADED("", 200, "Report has been uploaded"),
    //ERROR_ACCESS_DENIED("AccessDenied", 403, "Access Denied."),
    //ERROR_OPERATION_ABORTED("OperationAborted", 409, "Operation was aborted."),
    ERROR_NONEXISTING_BUCKET("NoSuchBucket", 404, "Bucket does NOT exist.");


    private String awsCode;
    private int httpCode;
    private String message;

    ValidationResult(String code, int httpCode, String message) {
        this.awsCode = code;
        this.httpCode = httpCode;
        this.message = message;
    }

    public String getAwsCode() {
        return awsCode;
    }

    public void setAwsCode(String awsCode) {
        this.awsCode = awsCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static  ValidationResult findValueByAwsCode(String code) throws UnknownValidationResultException{
        for (ValidationResult result : values()) {
            if (result.awsCode.equalsIgnoreCase(code)) {
                return result;
            }
        }
        throw new UnknownValidationResultException("Unknown response from AWS for code " + code);
    }
}
