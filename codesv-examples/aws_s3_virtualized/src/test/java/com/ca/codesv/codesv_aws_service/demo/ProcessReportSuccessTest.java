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

package com.ca.codesv.codesv_aws_service.demo;

import com.ca.codesv.codesv_aws_service.*;
import com.ca.codesv.codesv_aws_service.repository.S3CodeSvDemoBucketRepository;
import com.ca.codesv.engine.junit4.VirtualServerRule;
import com.ca.codesv.sdk.annotation.TransactionClassRepository;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ProcessReportSuccessTest extends BaseTest {

    @Rule
    @TransactionClassRepository(repoClasses = {S3CodeSvDemoBucketRepository.class})
    public VirtualServerRule vs = new VirtualServerRule(this);

    @Test
    public void processReportForm() throws UnknownValidationResultException {
        vs.useTransaction(S3CodeSvDemoBucketRepository.SUCCESSFUL_REPORT_UPLOAD_TXN);

        // Prepare view model which needs to be tested
        ViewModelHolder viewModelHolder = new ViewModelHolder();

        // Convert form POJO to ReportDraft POJO
        ReportDraft reportDraft = new ReportDraft(
                reportForm.getReportNameInput(),
                reportForm.getApplicationNameInput(),
                reportForm.getDescriptionTextArea(),
                new AppEnv(reportForm.getEnvNameInput(), reportForm.getEnvHostInput()));

        // Use creator service to process draft, upload to S3 as MyTest object to prepared bucket and get custom result validation
        ValidationResult validationResult =
                ReportCreatorService.getInstance().uploadReport(reportDraft, "MyTest1");

        // Push validation result to view model holder to be able to return correct UI notifications to users
        viewModelHolder.addValidatedResult(validationResult);

        assertFalse("View attributes can not be empty.", viewModelHolder.getViewAttributes().isEmpty());


    }
}
