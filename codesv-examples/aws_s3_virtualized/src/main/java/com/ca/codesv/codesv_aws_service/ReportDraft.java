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

import java.util.Date;
import java.util.Objects;

public class ReportDraft {

    private String reportName;
    private Date creationDate;
    private String applicationName;
    private String description;
    private AppEnv appEnv;

    public ReportDraft(String reportName) {
        this(reportName, "", "", new AppEnv("",""));
    }

    public ReportDraft(String reportName, String applicationName, String description, AppEnv appEnv) {
        this.reportName = reportName;
        this.applicationName = applicationName;
        this.description = description;
        this.appEnv = appEnv;
        this.creationDate = new Date();
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppEnv getAppEnv() {
        return appEnv;
    }

    public void setAppEnv(AppEnv appEnv) {
        this.appEnv = appEnv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportDraft that = (ReportDraft) o;
        return Objects.equals(reportName, that.reportName) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(applicationName, that.applicationName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(appEnv, that.appEnv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportName, creationDate, applicationName, description, appEnv);
    }
}
