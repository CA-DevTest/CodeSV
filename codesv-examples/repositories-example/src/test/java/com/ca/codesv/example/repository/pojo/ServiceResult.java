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

package com.ca.codesv.example.repository.pojo;

/**
 * POJO class used for tests with class repositories.
 *
 * @author CA
 */
public class ServiceResult {

	private String txnName;
	private String[] tags;
	private String vsName;

	public ServiceResult() {
	}

	public String getTxnName() {
		return txnName;
	}

	public void setTxnName(String txnName) {
		this.txnName = txnName;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String... tags) {
		this.tags = tags;
	}

	public String getVsName() {
		return vsName;
	}

	public void setVsName(String vsName) {
		this.vsName = vsName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ServiceResult that = (ServiceResult) o;

		if (getTxnName() != null ? !getTxnName().equals(that.getTxnName())
				: that.getTxnName() != null) {
			return false;
		}
		if (!getTags().equals(that.getTags())) {
			return false;
		}
		return getVsName().equals(that.getVsName());
	}

	@Override
	public int hashCode() {
		int result = getTxnName() != null ? getTxnName().hashCode() : 0;
		result = 31 * result + getTags().hashCode();
		result = 31 * result + getVsName().hashCode();
		return result;
	}
}
