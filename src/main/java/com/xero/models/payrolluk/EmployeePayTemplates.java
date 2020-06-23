/*
 * Xero Payroll UK
 * This is the Xero Payroll API for orgs in the UK region.
 *
 * The version of the OpenAPI document: 2.2.4
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.xero.models.payrolluk;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xero.models.payrolluk.EarningsTemplate;
import com.xero.models.payrolluk.Pagination;
import com.xero.models.payrolluk.Problem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import com.xero.api.StringUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * EmployeePayTemplates
 */

public class EmployeePayTemplates {
  StringUtil util = new StringUtil();

  @JsonProperty("pagination")
  private Pagination pagination;

  @JsonProperty("problem")
  private Problem problem;

  @JsonProperty("earningTemplates")
  private List<EarningsTemplate> earningTemplates = new ArrayList<EarningsTemplate>();
  public EmployeePayTemplates pagination(Pagination pagination) {
    this.pagination = pagination;
    return this;
  }

   /**
   * Get pagination
   * @return pagination
  **/
  @ApiModelProperty(value = "")
  public Pagination getPagination() {
    return pagination;
  }

  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }

  public EmployeePayTemplates problem(Problem problem) {
    this.problem = problem;
    return this;
  }

   /**
   * Get problem
   * @return problem
  **/
  @ApiModelProperty(value = "")
  public Problem getProblem() {
    return problem;
  }

  public void setProblem(Problem problem) {
    this.problem = problem;
  }

  public EmployeePayTemplates earningTemplates(List<EarningsTemplate> earningTemplates) {
    this.earningTemplates = earningTemplates;
    return this;
  }

  public EmployeePayTemplates addEarningTemplatesItem(EarningsTemplate earningTemplatesItem) {
    if (this.earningTemplates == null) {
      this.earningTemplates = new ArrayList<EarningsTemplate>();
    }
    this.earningTemplates.add(earningTemplatesItem);
    return this;
  }

   /**
   * Get earningTemplates
   * @return earningTemplates
  **/
  @ApiModelProperty(value = "")
  public List<EarningsTemplate> getEarningTemplates() {
    return earningTemplates;
  }

  public void setEarningTemplates(List<EarningsTemplate> earningTemplates) {
    this.earningTemplates = earningTemplates;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeePayTemplates employeePayTemplates = (EmployeePayTemplates) o;
    return Objects.equals(this.pagination, employeePayTemplates.pagination) &&
        Objects.equals(this.problem, employeePayTemplates.problem) &&
        Objects.equals(this.earningTemplates, employeePayTemplates.earningTemplates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pagination, problem, earningTemplates);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmployeePayTemplates {\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
    sb.append("    problem: ").append(toIndentedString(problem)).append("\n");
    sb.append("    earningTemplates: ").append(toIndentedString(earningTemplates)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
