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

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReportService {

    private static ReportService instance;
    private static final Gson gson = new GsonBuilder().create();
    private AmazonS3 s3;

    private ReportService() {
    }

    public static ReportService getInstance() {
        if (instance == null) {
            instance = new ReportService();
        }
        return instance;
    }

    public ReportUploadResult processReport(ReportDraft reportDraft) {
        return processReport(reportDraft, "MyTest");
    }

    public ReportUploadResult processReport(ReportDraft reportDraft, String reportName) {
        s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(
                        new ProfileCredentialsProvider("codesv_demo"))
                .build();

        String jsonReport = gson.toJson(reportDraft);
        try {
            PutObjectResult result = s3.putObject("codesvdemobucket", reportName, jsonReport);
        } catch (AmazonServiceException ex) {
            return new ReportUploadResult(ex.getErrorCode(), ex.getErrorMessage());
        }

        return new ReportUploadResult(jsonReport);
    }
}
