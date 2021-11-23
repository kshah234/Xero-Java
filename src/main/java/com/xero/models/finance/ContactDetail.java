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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** ContactDetail */
public class ContactDetail {
  StringUtil util = new StringUtil();

  @JsonProperty("contactId")
  private UUID contactId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("total")
  private Double total;

  @JsonProperty("totalDetail")
  private ContactTotalDetail totalDetail;

  @JsonProperty("totalOther")
  private ContactTotalOther totalOther;

  @JsonProperty("accountCodes")
  private List<String> accountCodes = new ArrayList<String>();
  /**
   * ID of the contact associated with the transactions. Transactions with no contact will be
   * grouped under the special ID: 86793108-198C-46D8-90A3-43C1D12686CE. Transactions that are
   * receive or spend bank transfers will be grouped under the special ID:
   * 207322B3-6A58-4BE7-80F1-430123914AD6
   *
   * @param contactId UUID
   * @return ContactDetail
   */
  public ContactDetail contactId(UUID contactId) {
    this.contactId = contactId;
    return this;
  }

  /**
   * ID of the contact associated with the transactions. Transactions with no contact will be
   * grouped under the special ID: 86793108-198C-46D8-90A3-43C1D12686CE. Transactions that are
   * receive or spend bank transfers will be grouped under the special ID:
   * 207322B3-6A58-4BE7-80F1-430123914AD6
   *
   * @return contactId
   */
  @ApiModelProperty(
      value =
          "ID of the contact associated with the transactions.    Transactions with no contact"
              + " will be grouped under the special ID: 86793108-198C-46D8-90A3-43C1D12686CE.   "
              + " Transactions that are receive or spend bank transfers will be grouped under the"
              + " special ID: 207322B3-6A58-4BE7-80F1-430123914AD6")
  /**
   * ID of the contact associated with the transactions. Transactions with no contact will be
   * grouped under the special ID: 86793108-198C-46D8-90A3-43C1D12686CE. Transactions that are
   * receive or spend bank transfers will be grouped under the special ID:
   * 207322B3-6A58-4BE7-80F1-430123914AD6
   *
   * @return contactId UUID
   */
  public UUID getContactId() {
    return contactId;
  }

  /**
   * ID of the contact associated with the transactions. Transactions with no contact will be
   * grouped under the special ID: 86793108-198C-46D8-90A3-43C1D12686CE. Transactions that are
   * receive or spend bank transfers will be grouped under the special ID:
   * 207322B3-6A58-4BE7-80F1-430123914AD6
   *
   * @param contactId UUID
   */
  public void setContactId(UUID contactId) {
    this.contactId = contactId;
  }

  /**
   * Name of the contact associated with the transactions. If no contact is associated with the
   * transactions this will appear as “None Provided”, For receive or spend bank transfer
   * transactions, this will appear as “Bank Transfer”.
   *
   * @param name String
   * @return ContactDetail
   */
  public ContactDetail name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the contact associated with the transactions. If no contact is associated with the
   * transactions this will appear as “None Provided”, For receive or spend bank transfer
   * transactions, this will appear as “Bank Transfer”.
   *
   * @return name
   */
  @ApiModelProperty(
      value =
          "Name of the contact associated with the transactions.    If no contact is associated"
              + " with the transactions this will appear as “None Provided”,    For receive or"
              + " spend bank transfer transactions, this will appear as “Bank Transfer”.")
  /**
   * Name of the contact associated with the transactions. If no contact is associated with the
   * transactions this will appear as “None Provided”, For receive or spend bank transfer
   * transactions, this will appear as “Bank Transfer”.
   *
   * @return name String
   */
  public String getName() {
    return name;
  }

  /**
   * Name of the contact associated with the transactions. If no contact is associated with the
   * transactions this will appear as “None Provided”, For receive or spend bank transfer
   * transactions, this will appear as “Bank Transfer”.
   *
   * @param name String
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Total value for the contact
   *
   * @param total Double
   * @return ContactDetail
   */
  public ContactDetail total(Double total) {
    this.total = total;
    return this;
  }

  /**
   * Total value for the contact
   *
   * @return total
   */
  @ApiModelProperty(value = "Total value for the contact")
  /**
   * Total value for the contact
   *
   * @return total Double
   */
  public Double getTotal() {
    return total;
  }

  /**
   * Total value for the contact
   *
   * @param total Double
   */
  public void setTotal(Double total) {
    this.total = total;
  }

  /**
   * totalDetail
   *
   * @param totalDetail ContactTotalDetail
   * @return ContactDetail
   */
  public ContactDetail totalDetail(ContactTotalDetail totalDetail) {
    this.totalDetail = totalDetail;
    return this;
  }

  /**
   * Get totalDetail
   *
   * @return totalDetail
   */
  @ApiModelProperty(value = "")
  /**
   * totalDetail
   *
   * @return totalDetail ContactTotalDetail
   */
  public ContactTotalDetail getTotalDetail() {
    return totalDetail;
  }

  /**
   * totalDetail
   *
   * @param totalDetail ContactTotalDetail
   */
  public void setTotalDetail(ContactTotalDetail totalDetail) {
    this.totalDetail = totalDetail;
  }

  /**
   * totalOther
   *
   * @param totalOther ContactTotalOther
   * @return ContactDetail
   */
  public ContactDetail totalOther(ContactTotalOther totalOther) {
    this.totalOther = totalOther;
    return this;
  }

  /**
   * Get totalOther
   *
   * @return totalOther
   */
  @ApiModelProperty(value = "")
  /**
   * totalOther
   *
   * @return totalOther ContactTotalOther
   */
  public ContactTotalOther getTotalOther() {
    return totalOther;
  }

  /**
   * totalOther
   *
   * @param totalOther ContactTotalOther
   */
  public void setTotalOther(ContactTotalOther totalOther) {
    this.totalOther = totalOther;
  }

  /**
   * A list of account codes involved in transactions.
   *
   * @param accountCodes List&lt;&gt;
   * @return ContactDetail
   */
  public ContactDetail accountCodes(List<String> accountCodes) {
    this.accountCodes = accountCodes;
    return this;
  }

  /**
   * A list of account codes involved in transactions.
   *
   * @param accountCodesItem String
   * @return ContactDetail
   */
  public ContactDetail addAccountCodesItem(String accountCodesItem) {
    if (this.accountCodes == null) {
      this.accountCodes = new ArrayList<String>();
    }
    this.accountCodes.add(accountCodesItem);
    return this;
  }

  /**
   * A list of account codes involved in transactions.
   *
   * @return accountCodes
   */
  @ApiModelProperty(value = "A list of account codes involved in transactions.")
  /**
   * A list of account codes involved in transactions.
   *
   * @return accountCodes List<String>
   */
  public List<String> getAccountCodes() {
    return accountCodes;
  }

  /**
   * A list of account codes involved in transactions.
   *
   * @param accountCodes List&lt;&gt;
   */
  public void setAccountCodes(List<String> accountCodes) {
    this.accountCodes = accountCodes;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContactDetail contactDetail = (ContactDetail) o;
    return Objects.equals(this.contactId, contactDetail.contactId)
        && Objects.equals(this.name, contactDetail.name)
        && Objects.equals(this.total, contactDetail.total)
        && Objects.equals(this.totalDetail, contactDetail.totalDetail)
        && Objects.equals(this.totalOther, contactDetail.totalOther)
        && Objects.equals(this.accountCodes, contactDetail.accountCodes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contactId, name, total, totalDetail, totalOther, accountCodes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContactDetail {\n");
    sb.append("    contactId: ").append(toIndentedString(contactId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    totalDetail: ").append(toIndentedString(totalDetail)).append("\n");
    sb.append("    totalOther: ").append(toIndentedString(totalOther)).append("\n");
    sb.append("    accountCodes: ").append(toIndentedString(accountCodes)).append("\n");
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
