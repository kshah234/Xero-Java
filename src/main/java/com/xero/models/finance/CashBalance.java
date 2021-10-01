/*
 * Xero Finance API
 * The Finance API is a collection of endpoints which customers can use in the course of a loan application, which may assist lenders to gain the confidence they need to provide capital.
 *
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.finance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

/** CashBalance */
public class CashBalance {
  StringUtil util = new StringUtil();

  @JsonProperty("openingCashBalance")
  private Double openingCashBalance;

  @JsonProperty("closingCashBalance")
  private Double closingCashBalance;

  @JsonProperty("netCashMovement")
  private Double netCashMovement;
  /**
   * Opening balance of cash and cash equivalents
   *
   * @param openingCashBalance Double
   * @return CashBalance
   */
  public CashBalance openingCashBalance(Double openingCashBalance) {
    this.openingCashBalance = openingCashBalance;
    return this;
  }

  /**
   * Opening balance of cash and cash equivalents
   *
   * @return openingCashBalance
   */
  @ApiModelProperty(value = "Opening balance of cash and cash equivalents")
  /**
   * Opening balance of cash and cash equivalents
   *
   * @return openingCashBalance Double
   */
  public Double getOpeningCashBalance() {
    return openingCashBalance;
  }

  /**
   * Opening balance of cash and cash equivalents
   *
   * @param openingCashBalance Double
   */
  public void setOpeningCashBalance(Double openingCashBalance) {
    this.openingCashBalance = openingCashBalance;
  }

  /**
   * Closing balance of cash and cash equivalents
   *
   * @param closingCashBalance Double
   * @return CashBalance
   */
  public CashBalance closingCashBalance(Double closingCashBalance) {
    this.closingCashBalance = closingCashBalance;
    return this;
  }

  /**
   * Closing balance of cash and cash equivalents
   *
   * @return closingCashBalance
   */
  @ApiModelProperty(value = "Closing balance of cash and cash equivalents")
  /**
   * Closing balance of cash and cash equivalents
   *
   * @return closingCashBalance Double
   */
  public Double getClosingCashBalance() {
    return closingCashBalance;
  }

  /**
   * Closing balance of cash and cash equivalents
   *
   * @param closingCashBalance Double
   */
  public void setClosingCashBalance(Double closingCashBalance) {
    this.closingCashBalance = closingCashBalance;
  }

  /**
   * Net movement of cash and cash equivalents for the period
   *
   * @return netCashMovement
   */
  @ApiModelProperty(value = "Net movement of cash and cash equivalents for the period")
  /**
   * Net movement of cash and cash equivalents for the period
   *
   * @return netCashMovement Double
   */
  public Double getNetCashMovement() {
    return netCashMovement;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CashBalance cashBalance = (CashBalance) o;
    return Objects.equals(this.openingCashBalance, cashBalance.openingCashBalance)
        && Objects.equals(this.closingCashBalance, cashBalance.closingCashBalance)
        && Objects.equals(this.netCashMovement, cashBalance.netCashMovement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(openingCashBalance, closingCashBalance, netCashMovement);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CashBalance {\n");
    sb.append("    openingCashBalance: ").append(toIndentedString(openingCashBalance)).append("\n");
    sb.append("    closingCashBalance: ").append(toIndentedString(closingCashBalance)).append("\n");
    sb.append("    netCashMovement: ").append(toIndentedString(netCashMovement)).append("\n");
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
