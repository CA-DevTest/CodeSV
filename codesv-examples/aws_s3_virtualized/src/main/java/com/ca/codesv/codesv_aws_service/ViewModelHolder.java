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

import java.util.HashMap;
import java.util.Map;

public class ViewModelHolder {

    public static final String PROCESS_RESULT = "processResult";
    public static final String PROCESS_RESULT_DETAIL = "processResultDetail";

    Map<String, String> viewAttributes = new HashMap<>();

    public ViewModelHolder() {
    }

    public void addValidatedResult(ValidationResult validationResult) {
        viewAttributes.put(PROCESS_RESULT, validationResult.getAwsCode());
        viewAttributes.put(PROCESS_RESULT_DETAIL, validationResult.getMessage());
    }


    public Map<String, String> getViewAttributes() {
        return viewAttributes;
    }
}
