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

package com.ca.codesv.codesv_aws_service.backup;

import com.ca.codesv.codesv_aws_service.AppEnv;
import com.ca.codesv.codesv_aws_service.ReportDraft;
import com.ca.codesv.codesv_aws_service.ReportService;
import com.ca.codesv.codesv_aws_service.ReportUploadResult;
import com.ca.codesv.codesv_aws_service.repository.S3CodeSvDemoBucketRepository;
import com.ca.codesv.engine.junit4.VirtualServerRule;
import com.ca.codesv.sdk.annotation.TransactionClassRepository;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ReportServiceParameterizedTest {

    @Rule
    @TransactionClassRepository(repoClasses = {S3CodeSvDemoBucketRepository.class})
    public VirtualServerRule vs = new VirtualServerRule(this);

    @Parameterized.Parameter
    public String transactionName;

    @BeforeClass
    public static void setUp() {
        System.setProperty("javax.net.ssl.trustStore",
                Thread.currentThread().getContextClassLoader().getResource("keystore.jks").getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
    }


    @Parameterized.Parameters(name = "{index}: transactionName - {0}")
    public static Collection transactionName() {
        return Arrays.asList("successful report upload", "nonexisting bucket", "access denied");
    }

    @Test
    public void processReport() {
        vs.useTransaction(transactionName);
        ReportDraft reportDraft = new ReportDraft("My testing report",
                "My Application",
                "Lorem ipsum",
                new AppEnv("dev_env", "localhost"));

        ReportUploadResult result = ReportService.getInstance().processReport(reportDraft);

        assertTrue(result.isSuccess() || !result.getErrorCode().isEmpty());

    }
}