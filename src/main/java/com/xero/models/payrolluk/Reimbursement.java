/*
 * Xero Payroll UK
 * This is the Xero Payroll API for orgs in the UK region.
 *
 * The version of the OpenAPI document: 2.3.0
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.payrolluk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import java.util.UUID;

/** Reimbursement */
public class Reimbursement {
  StringUtil util = new StringUtil();

  @JsonProperty("reimbursementID")
  private UUID reimbursementID;

  @JsonProperty("name")
  private String name;

  @JsonProperty("accountID")
  private UUID accountID;

  @JsonProperty("currentRecord")
  private Boolean currentRecord;

  public Reimbursement reimbursementID(UUID reimbursementID) {
    this.reimbursementID = reimbursementID;
    return this;
  }

  /**
   * Xero unique identifier for a reimbursement
   *
   * @return reimbursementID
   */
  @ApiModelProperty(value = "Xero unique identifier for a reimbursement")
  public UUID getReimbursementID() {
    return reimbursementID;
  }

  public void setReimbursementID(UUID reimbursementID) {
    this.reimbursementID = reimbursementID;
  }

  public Reimbursement name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the reimbursement
   *
   * @return name
   */
  @ApiModelProperty(required = true, value = "Name of the reimbursement")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Reimbursement accountID(UUID accountID) {
    this.accountID = accountID;
    return this;
  }

  /**
   * Xero unique identifier for the account used for the reimbursement
   *
   * @return accountID
   */
  @ApiModelProperty(
      required = true,
      value = "Xero unique identifier for the account used for the reimbursement")
  public UUID getAccountID() {
    return accountID;
  }

  public void setAccountID(UUID accountID) {
    this.accountID = accountID;
  }

  public Reimbursement currentRecord(Boolean currentRecord) {
    this.currentRecord = currentRecord;
    return this;
  }

  /**
   * Indicates that whether the reimbursement is active
   *
   * @return currentRecord
   */
  @ApiModelProperty(value = "Indicates that whether the reimbursement is active")
  public Boolean getCurrentRecord() {
    return currentRecord;
  }

  public void setCurrentRecord(Boolean currentRecord) {
    this.currentRecord = currentRecord;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Reimbursement reimbursement = (Reimbursement) o;
    return Objects.equals(this.reimbursementID, reimbursement.reimbursementID)
        && Objects.equals(this.name, reimbursement.name)
        && Objects.equals(this.accountID, reimbursement.accountID)
        && Objects.equals(this.currentRecord, reimbursement.currentRecord);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reimbursementID, name, accountID, currentRecord);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Reimbursement {\n");
    sb.append("    reimbursementID: ").append(toIndentedString(reimbursementID)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    accountID: ").append(toIndentedString(accountID)).append("\n");
    sb.append("    currentRecord: ").append(toIndentedString(currentRecord)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
