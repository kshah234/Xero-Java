/*
 * Accounting API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 2.0.7
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.xero.models.accounting;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xero.models.accounting.HistoryRecord;
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
 * HistoryRecords
 */

public class HistoryRecords {
  StringUtil util = new StringUtil();

  
  @JsonProperty("HistoryRecords")
  private List<HistoryRecord> historyRecords = new ArrayList<HistoryRecord>();
  public HistoryRecords historyRecords(List<HistoryRecord> historyRecords) {
    this.historyRecords = historyRecords;
    return this;
  }

  public HistoryRecords addHistoryRecordsItem(HistoryRecord historyRecordsItem) {
    if (this.historyRecords == null) {
      this.historyRecords = new ArrayList<HistoryRecord>();
    }
    this.historyRecords.add(historyRecordsItem);
    return this;
  }

   /**
   * Get historyRecords
   * @return historyRecords
  **/
  @ApiModelProperty(value = "")
  public List<HistoryRecord> getHistoryRecords() {
    return historyRecords;
  }

  public void setHistoryRecords(List<HistoryRecord> historyRecords) {
    this.historyRecords = historyRecords;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HistoryRecords historyRecords = (HistoryRecords) o;
    return Objects.equals(this.historyRecords, historyRecords.historyRecords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(historyRecords);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HistoryRecords {\n");
    sb.append("    historyRecords: ").append(toIndentedString(historyRecords)).append("\n");
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

