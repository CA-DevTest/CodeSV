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
import com.ca.codesv.protocols.transaction.TxnRepoStore;
import com.ca.codesv.protocols.transaction.TxnRepoStoreBuilder;
import com.ca.codesv.sdk.annotation.TransactionClassRepository;
import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TransactionClassRepository(repoClasses = {S3CodeSvDemoBucketRepository.class})
public class ReportServiceCodeSvTest {

    @Rule
    public VirtualServerRule vs = new VirtualServerRule();

    private TxnRepoStore store = new TxnRepoStoreBuilder().build(this);

    @Before
    public void setUp() {
        System.setProperty("javax.net.ssl.trustStore",
                Thread.currentThread().getContextClassLoader().getResource("keystore.jks").getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
    }

    @Test
    public void processReportWithCodeSvOk() {
        store.useTransaction("successful report upload");

        ReportDraft reportDraft = new ReportDraft("My virtualized testing report",
                "My Application",
                "Lorem ipsum",
                new AppEnv("VS_env", "localhost"));

        ReportUploadResult result = ReportService.getInstance().processReport(reportDraft);

        assertTrue(result.isSuccess());
    }


    @Test
    public void processReportWithoutPermissions() {
        store.useTransaction("access denied");

        ReportDraft reportDraft = new ReportDraft("My virtualized testing report",
                "My Application",
                "Lorem ipsum",
                new AppEnv("VS_env", "localhost"));

        ReportUploadResult result = ReportService.getInstance().processReport(reportDraft);

        assertTrue("Return code doesnt match", result.getErrorCode().contentEquals("AccessDenied"));
    }
}