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

/** Address */
public class Address {
  StringUtil util = new StringUtil();

  @JsonProperty("addressLine1")
  private String addressLine1;

  @JsonProperty("addressLine2")
  private String addressLine2;

  @JsonProperty("city")
  private String city;

  @JsonProperty("postCode")
  private String postCode;

  @JsonProperty("countryName")
  private String countryName;

  public Address addressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
    return this;
  }

  /**
   * Address line 1 for employee home address
   *
   * @return addressLine1
   */
  @ApiModelProperty(
      example = "123 Main St",
      required = true,
      value = "Address line 1 for employee home address")
  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public Address addressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
    return this;
  }

  /**
   * Address line 2 for employee home address
   *
   * @return addressLine2
   */
  @ApiModelProperty(example = "Apt 4", value = "Address line 2 for employee home address")
  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public Address city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Suburb for employee home address
   *
   * @return city
   */
  @ApiModelProperty(example = "Fulham", required = true, value = "Suburb for employee home address")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Address postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

  /**
   * PostCode for employee home address
   *
   * @return postCode
   */
  @ApiModelProperty(
      example = "SW6 6EY",
      required = true,
      value = "PostCode for employee home address")
  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public Address countryName(String countryName) {
    this.countryName = countryName;
    return this;
  }

  /**
   * Country of HomeAddress
   *
   * @return countryName
   */
  @ApiModelProperty(example = "United Kingdom", value = "Country of HomeAddress")
  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(this.addressLine1, address.addressLine1)
        && Objects.equals(this.addressLine2, address.addressLine2)
        && Objects.equals(this.city, address.city)
        && Objects.equals(this.postCode, address.postCode)
        && Objects.equals(this.countryName, address.countryName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressLine1, addressLine2, city, postCode, countryName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Address {\n");
    sb.append("    addressLine1: ").append(toIndentedString(addressLine1)).append("\n");
    sb.append("    addressLine2: ").append(toIndentedString(addressLine2)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    countryName: ").append(toIndentedString(countryName)).append("\n");
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
