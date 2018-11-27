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

public class ViewReportForm {

    private String reportNameInput;
    private String applicationNameInput;
    private String descriptionTextArea;
    private String envNameInput;
    private String envHostInput;

    public ViewReportForm() {
    }

    public String getReportNameInput() {
        return reportNameInput;
    }

    public void setReportNameInput(String reportNameInput) {
        this.reportNameInput = reportNameInput;
    }

    public String getApplicationNameInput() {
        return applicationNameInput;
    }

    public void setApplicationNameInput(String applicationNameInput) {
        this.applicationNameInput = applicationNameInput;
    }

    public String getDescriptionTextArea() {
        return descriptionTextArea;
    }

    public void setDescriptionTextArea(String descriptionTextArea) {
        this.descriptionTextArea = descriptionTextArea;
    }

    public String getEnvNameInput() {
        return envNameInput;
    }

    public void setEnvNameInput(String envNameInput) {
        this.envNameInput = envNameInput;
    }

    public String getEnvHostInput() {
        return envHostInput;
    }

    public void setEnvHostInput(String envHostInput) {
        this.envHostInput = envHostInput;
    }
}
