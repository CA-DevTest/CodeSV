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

package com.ca.codesv.example.repository.repositories;

import static com.ca.codesv.protocols.http.fluent.HttpFluentInterface.forAnyRequest;
import static com.ca.codesv.protocols.http.fluent.HttpFluentInterface.forGet;
import static com.ca.codesv.protocols.http.fluent.HttpFluentInterface.okMessage;

import com.ca.codesv.example.repository.pojo.ServiceResult;
import com.ca.codesv.sdk.annotation.TransactionDefinition;
import com.google.gson.Gson;

/**
 * Default repository.
 *
 * @author CA
 */
public class DefaultRepository {

	public static final String SERVICE_URL = "http://www.carepositories.com";

	/**
	 * Demo method.
	 */
	@TransactionDefinition(name = "simpleResponse")
	public void virtualizedSimpleDefaultResponse() {
		ServiceResult result = new ServiceResult();
		result.setTags(TransactionDefinition.DEFAULT_TAG);
		result.setTxnName("simpleResponse");

		forGet(SERVICE_URL + "/simpleDefaultService").doReturn(
				okMessage()
						.withJsonBody(new Gson().toJson(result))
		);
	}

	/**
	 * Demo method.
	 */
	@TransactionDefinition(name = "anotherResponse", tags = "DEV")
	public void devResponse() {
		ServiceResult result = new ServiceResult();
		result.setTags("DEV");
		result.setTxnName("anotherResponse");

		forGet(SERVICE_URL + "/anotherService").doReturn(
				okMessage()
						.withJsonBody(new Gson().toJson(result))
		);
	}

	/**
	 * Demo method.
	 */
	@TransactionDefinition(name = "anotherResponse", tags = "QA")
	public void qaResponse() {
		ServiceResult result = new ServiceResult();
		result.setTags("QA");
		result.setTxnName("anotherResponse");

		forAnyRequest(SERVICE_URL + "/anotherService").doReturn(
				okMessage()
						.withJsonBody(new Gson().toJson(result))
		);
	}

	/**
	 * Demo method.
	 */
	@TransactionDefinition(name = "commonResponse")
	public void commonResponse() {
		ServiceResult result = new ServiceResult();
		result.setTxnName("commonResponse");

		forGet(SERVICE_URL + "/commonService").doReturn(
				okMessage()
						.withJsonBody(new Gson().toJson(result))
		);
	}
}
