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

package com.ca.codesv.example.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.ca.codesv.engine.junit4.VirtualServerRule;
import com.ca.codesv.example.repository.pojo.ServiceResult;
import com.ca.codesv.example.repository.repositories.DefaultRepository;
import com.ca.codesv.example.repository.repositories.DevRepository;
import com.ca.codesv.example.repository.repositories.QaRepository;
import com.ca.codesv.protocols.transaction.TxnRepoStore;
import com.ca.codesv.protocols.transaction.TxnRepoStoreBuilder;
import com.ca.codesv.protocols.transaction.UseTransactionRule;
import com.ca.codesv.sdk.annotation.TransactionClassRepository;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;

/**
 * Example showing usage of class repositories in tests.
 *
 * @author CA
 */
@TransactionClassRepository(repoClasses = {QaRepository.class, DevRepository.class,
		DefaultRepository.class})
public class ClassRepositoryExample {

	@Rule
	public VirtualServerRule vs = new VirtualServerRule();

	private TxnRepoStore store = new TxnRepoStoreBuilder().build(this);

	private static final String DEV_SERVICE_NAME = "Dev service";
	private static final String QA_SERVICE_NAME = "Qa service";

	@Test
	public void simpleByNameTest() throws Exception {
		String transactionName = "simpleResponse";
		store.useTransaction(transactionName);

		ServiceResult result = getResult("/simpleDefaultService");

		assertEquals(transactionName, result.getTxnName());
		assertTrue(result.getVsName() == null
				|| result.getVsName().equalsIgnoreCase("Qa service")
				|| result.getVsName().equalsIgnoreCase("Dev service"));
	}

	@Test
	public void simpleMultipleCallTest() throws Exception {
		String simpleTransactionName = "simpleResponse";
		String commonTransactionName = "commonResponse";

		store.useTransaction(simpleTransactionName);
		store.useTransaction(commonTransactionName);

		ServiceResult result = getResult("/simpleDefaultService");

		assertEquals(simpleTransactionName, result.getTxnName());

		result = getResult("/commonService");
		assertEquals(commonTransactionName, result.getTxnName());
	}

	@Test
	public void simpleByNameVsAndTagDevTest() throws Exception {
		String transactionName = "simpleResponse";

		UseTransactionRule rule = new UseTransactionRule.RuleBuilder("Dev service")
				.forTransaction(transactionName)
				.build();

		store.useTransactionWithRule(rule);

		ServiceResult result = getResult("/simpleDefaultService");

		assertEquals(transactionName, result.getTxnName());
		assertEquals(DEV_SERVICE_NAME, result.getVsName());
	}

	@Test
	public void testVsNameIgnoreCase() throws Exception {
		String transactionName = "simpleResponse";

		UseTransactionRule rule = new UseTransactionRule.RuleBuilder("DeV sERVice")
				.forTransaction(transactionName)
				.build();

		store.useTransactionWithRule(rule);

		ServiceResult result = getResult("/simpleDefaultService");

		assertEquals(transactionName, result.getTxnName());
		assertEquals(DEV_SERVICE_NAME, result.getVsName());
	}

	@Test
	public void testRightTaggedTransaction() throws Exception {
		String transactionName = "simpleResponse";

		UseTransactionRule rule = UseTransactionRule.RuleBuilder.crossServiceBuilder()
																														.forTransaction(transactionName)
																														.withTag("COMMON")
																														.withTag("QA")
																														.build();

		store.useTransactionWithRule(rule);

		ServiceResult result = getResult("/simpleDefaultService");

		assertEquals(transactionName, result.getTxnName());
		assertEquals(QA_SERVICE_NAME, result.getVsName());
	}

	@Test
	public void testQaTransactionFromNonNamedRepositoryWithSameName() throws Exception {
		String transactionName = "anotherResponse";

		UseTransactionRule rule = UseTransactionRule.RuleBuilder.crossServiceBuilder()
																														.forTransaction(transactionName)
																														.withTag("QA")
																														.build();

		store.useTransactionWithRule(rule);

		ServiceResult result = getResult("/anotherService");

		assertEquals(transactionName, result.getTxnName());
		assertNull(result.getVsName());
		assertTrue(Arrays.asList(result.getTags()).contains("QA"));
		assertFalse(Arrays.asList(result.getTags()).contains("DEV"));
	}

	@Test
	public void testDevTransactionFromNonNamedRepositoryWithSameName() throws Exception {
		String transactionName = "anotherResponse";

		UseTransactionRule rule = UseTransactionRule.RuleBuilder.crossServiceBuilder()
																														.forTransaction(transactionName)
																														.withTag("DEV")
																														.build();

		store.useTransactionWithRule(rule);

		ServiceResult result = getResult("/anotherService");

		assertEquals(transactionName, result.getTxnName());
		assertNull(result.getVsName());
		assertTrue(Arrays.asList(result.getTags()).contains("DEV"));
		assertFalse(Arrays.asList(result.getTags()).contains("QA"));
	}

	@Test
	public void testMultipleRuleUsage() throws Exception {
		store.useTransaction("commonResponse");

		UseTransactionRule rule = new UseTransactionRule.RuleBuilder("Default service")
																														.withTag("QA")
																														.build();

		store.useTransactionWithRule(rule);

		ServiceResult resultCommon = getResult("/commonService");
		ServiceResult resultQa = getResult("/anotherService");

		assertEquals("commonResponse", resultCommon.getTxnName());
		assertNull(resultCommon.getTags());

		assertTrue(Arrays.asList(resultQa.getTags()).contains("QA"));
		assertEquals("anotherResponse", resultQa.getTxnName());
	}

	private ServiceResult getResult(String path) throws IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(DefaultRepository.SERVICE_URL + path);

		HttpResponse response = client.execute(request);

		String responseBody = EntityUtils.toString(response.getEntity());

		return new Gson().fromJson(responseBody, ServiceResult.class);
	}
}
