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

import static com.ca.codesv.protocols.http.fluent.HttpFluentInterface.forGet;
import static com.ca.codesv.protocols.http.fluent.HttpFluentInterface.okMessage;

import com.ca.codesv.example.repository.pojo.ServiceResult;
import com.ca.codesv.sdk.annotation.TransactionDefinition;
import com.ca.codesv.sdk.annotation.VirtualServiceRepository;
import com.google.gson.Gson;

/**
 * Dev repository.
 *
 * @author CA
 */
@VirtualServiceRepository(serviceName = "Dev service")
public class DevRepository {
	@TransactionDefinition(name = "simpleResponse", tags = {"COMMON", "DEV"})
	public void virtualizedSimpleDefaultResponse() {
		ServiceResult result = new ServiceResult();
		result.setTags("COMMON", "DEV");
		result.setTxnName("simpleResponse");
		result.setVsName("Dev service");

		forGet(DefaultRepository.SERVICE_URL + "/simpleDefaultService").doReturn(
				okMessage()
						.withJsonBody(new Gson().toJson(result))
		);
	}

}
