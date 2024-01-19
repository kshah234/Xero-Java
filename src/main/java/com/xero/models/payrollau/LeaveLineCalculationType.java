/*
 * Xero Payroll AU API
 * This is the Xero Payroll API for orgs in Australia region.
 *
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.payrollau;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Calculation type for leave line for Opening Balance on Employee */
public enum LeaveLineCalculationType {

  /** NOCALCULATIONREQUIRED */
  NOCALCULATIONREQUIRED("NOCALCULATIONREQUIRED"),

  /** FIXEDAMOUNTEACHPERIOD */
  FIXEDAMOUNTEACHPERIOD("FIXEDAMOUNTEACHPERIOD"),

  /** ENTERRATEINPAYTEMPLATE */
  ENTERRATEINPAYTEMPLATE("ENTERRATEINPAYTEMPLATE"),

  /** BASEDONORDINARYEARNINGS */
  BASEDONORDINARYEARNINGS("BASEDONORDINARYEARNINGS");

  private String value;

  LeaveLineCalculationType(String value) {
    this.value = value;
  }

  /** @return String value */
  @JsonValue
  public String getValue() {
    return value;
  }

  /**
   * toString
   *
   * @return String value
   */
  @Override
  public String toString() {
    return String.valueOf(value);
  }

  /**
   * fromValue
   *
   * @param value String
   */
  @JsonCreator
  public static LeaveLineCalculationType fromValue(String value) {
    for (LeaveLineCalculationType b : LeaveLineCalculationType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
