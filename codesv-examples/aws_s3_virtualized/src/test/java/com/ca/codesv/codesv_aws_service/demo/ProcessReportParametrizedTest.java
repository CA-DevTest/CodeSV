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
import com.ca.codesv.model.ClassTransactionRepository;
import com.ca.codesv.sdk.annotation.TransactionClassRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class ProcessReportParametrizedTest extends BaseTest {

    @Rule
    @TransactionClassRepository(repoClasses = {S3CodeSvDemoBucketRepository.class})
    public VirtualServerRule vs = new VirtualServerRule(this);

    @Parameterized.Parameter(0)
    public String transactionName;

    @Parameterized.Parameter(1)
    public String reportName;

    @Parameterized.Parameters(name = "{index}: transactionName - {0} for report {1}")
    public static Collection transactionName() {
        List<String> transactionNames = ClassTransactionRepository.getTransactionNames(S3CodeSvDemoBucketRepository.class);

        return DemoUtils.prepareParameters(transactionNames);
    }

    @Test
    public void processReport() throws UnknownValidationResultException {
        vs.useTransaction(transactionName);

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
                ReportCreatorService.getInstance().uploadReport(reportDraft, reportName);

        assertNotNull("Validation result cannot be null.", validationResult);

    }

}
