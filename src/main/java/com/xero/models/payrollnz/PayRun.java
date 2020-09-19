/*
 * Xero Payroll NZ
 * This is the Xero Payroll API for orgs in the NZ region.
 *
 * The version of the OpenAPI document: 2.3.0
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.payrollnz;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.threeten.bp.LocalDate;

/** PayRun */
public class PayRun {
  StringUtil util = new StringUtil();

  @JsonProperty("payRunID")
  private UUID payRunID;

  @JsonProperty("payrollCalendarID")
  private UUID payrollCalendarID;

  @JsonProperty("periodStartDate")
  private LocalDate periodStartDate;

  @JsonProperty("periodEndDate")
  private LocalDate periodEndDate;

  @JsonProperty("paymentDate")
  private LocalDate paymentDate;

  @JsonProperty("totalCost")
  private Double totalCost;

  @JsonProperty("totalPay")
  private Double totalPay;
  /** Pay run status */
  public enum PayRunStatusEnum {
    DRAFT("Draft"),

    POSTED("Posted");

    private String value;

    PayRunStatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PayRunStatusEnum fromValue(String value) {
      for (PayRunStatusEnum b : PayRunStatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("payRunStatus")
  private PayRunStatusEnum payRunStatus;
  /** Pay run type */
  public enum PayRunTypeEnum {
    SCHEDULED("Scheduled"),

    UNSCHEDULED("Unscheduled"),

    EARLIERYEARUPDATE("EarlierYearUpdate");

    private String value;

    PayRunTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PayRunTypeEnum fromValue(String value) {
      for (PayRunTypeEnum b : PayRunTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("payRunType")
  private PayRunTypeEnum payRunType;
  /** Calendar type of the pay run */
  public enum CalendarTypeEnum {
    WEEKLY("Weekly"),

    FORTNIGHTLY("Fortnightly"),

    FOURWEEKLY("FourWeekly"),

    MONTHLY("Monthly"),

    ANNUAL("Annual"),

    QUARTERLY("Quarterly");

    private String value;

    CalendarTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static CalendarTypeEnum fromValue(String value) {
      for (CalendarTypeEnum b : CalendarTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("calendarType")
  private CalendarTypeEnum calendarType;

  @JsonProperty("postedDateTime")
  private LocalDate postedDateTime;

  @JsonProperty("paySlips")
  private List<PaySlip> paySlips = new ArrayList<PaySlip>();

  public PayRun payRunID(UUID payRunID) {
    this.payRunID = payRunID;
    return this;
  }

  /**
   * Xero unique identifier for the pay run
   *
   * @return payRunID
   */
  @ApiModelProperty(value = "Xero unique identifier for the pay run")
  public UUID getPayRunID() {
    return payRunID;
  }

  public void setPayRunID(UUID payRunID) {
    this.payRunID = payRunID;
  }

  public PayRun payrollCalendarID(UUID payrollCalendarID) {
    this.payrollCalendarID = payrollCalendarID;
    return this;
  }

  /**
   * Xero unique identifier for the payroll calendar
   *
   * @return payrollCalendarID
   */
  @ApiModelProperty(value = "Xero unique identifier for the payroll calendar")
  public UUID getPayrollCalendarID() {
    return payrollCalendarID;
  }

  public void setPayrollCalendarID(UUID payrollCalendarID) {
    this.payrollCalendarID = payrollCalendarID;
  }

  public PayRun periodStartDate(LocalDate periodStartDate) {
    this.periodStartDate = periodStartDate;
    return this;
  }

  /**
   * Period start date of the payroll calendar
   *
   * @return periodStartDate
   */
  @ApiModelProperty(value = "Period start date of the payroll calendar")
  public LocalDate getPeriodStartDate() {
    return periodStartDate;
  }

  public void setPeriodStartDate(LocalDate periodStartDate) {
    this.periodStartDate = periodStartDate;
  }

  public PayRun periodEndDate(LocalDate periodEndDate) {
    this.periodEndDate = periodEndDate;
    return this;
  }

  /**
   * Period end date of the payroll calendar
   *
   * @return periodEndDate
   */
  @ApiModelProperty(value = "Period end date of the payroll calendar")
  public LocalDate getPeriodEndDate() {
    return periodEndDate;
  }

  public void setPeriodEndDate(LocalDate periodEndDate) {
    this.periodEndDate = periodEndDate;
  }

  public PayRun paymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
    return this;
  }

  /**
   * Payment date of the pay run
   *
   * @return paymentDate
   */
  @ApiModelProperty(value = "Payment date of the pay run")
  public LocalDate getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
  }

  public PayRun totalCost(Double totalCost) {
    this.totalCost = totalCost;
    return this;
  }

  /**
   * Total cost of the pay run
   *
   * @return totalCost
   */
  @ApiModelProperty(value = "Total cost of the pay run")
  public Double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(Double totalCost) {
    this.totalCost = totalCost;
  }

  public PayRun totalPay(Double totalPay) {
    this.totalPay = totalPay;
    return this;
  }

  /**
   * Total pay of the pay run
   *
   * @return totalPay
   */
  @ApiModelProperty(value = "Total pay of the pay run")
  public Double getTotalPay() {
    return totalPay;
  }

  public void setTotalPay(Double totalPay) {
    this.totalPay = totalPay;
  }

  public PayRun payRunStatus(PayRunStatusEnum payRunStatus) {
    this.payRunStatus = payRunStatus;
    return this;
  }

  /**
   * Pay run status
   *
   * @return payRunStatus
   */
  @ApiModelProperty(value = "Pay run status")
  public PayRunStatusEnum getPayRunStatus() {
    return payRunStatus;
  }

  public void setPayRunStatus(PayRunStatusEnum payRunStatus) {
    this.payRunStatus = payRunStatus;
  }

  public PayRun payRunType(PayRunTypeEnum payRunType) {
    this.payRunType = payRunType;
    return this;
  }

  /**
   * Pay run type
   *
   * @return payRunType
   */
  @ApiModelProperty(value = "Pay run type")
  public PayRunTypeEnum getPayRunType() {
    return payRunType;
  }

  public void setPayRunType(PayRunTypeEnum payRunType) {
    this.payRunType = payRunType;
  }

  public PayRun calendarType(CalendarTypeEnum calendarType) {
    this.calendarType = calendarType;
    return this;
  }

  /**
   * Calendar type of the pay run
   *
   * @return calendarType
   */
  @ApiModelProperty(value = "Calendar type of the pay run")
  public CalendarTypeEnum getCalendarType() {
    return calendarType;
  }

  public void setCalendarType(CalendarTypeEnum calendarType) {
    this.calendarType = calendarType;
  }

  public PayRun postedDateTime(LocalDate postedDateTime) {
    this.postedDateTime = postedDateTime;
    return this;
  }

  /**
   * Posted date time of the pay run
   *
   * @return postedDateTime
   */
  @ApiModelProperty(value = "Posted date time of the pay run")
  public LocalDate getPostedDateTime() {
    return postedDateTime;
  }

  public void setPostedDateTime(LocalDate postedDateTime) {
    this.postedDateTime = postedDateTime;
  }

  public PayRun paySlips(List<PaySlip> paySlips) {
    this.paySlips = paySlips;
    return this;
  }

  public PayRun addPaySlipsItem(PaySlip paySlipsItem) {
    if (this.paySlips == null) {
      this.paySlips = new ArrayList<PaySlip>();
    }
    this.paySlips.add(paySlipsItem);
    return this;
  }

  /**
   * Get paySlips
   *
   * @return paySlips
   */
  @ApiModelProperty(value = "")
  public List<PaySlip> getPaySlips() {
    return paySlips;
  }

  public void setPaySlips(List<PaySlip> paySlips) {
    this.paySlips = paySlips;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PayRun payRun = (PayRun) o;
    return Objects.equals(this.payRunID, payRun.payRunID)
        && Objects.equals(this.payrollCalendarID, payRun.payrollCalendarID)
        && Objects.equals(this.periodStartDate, payRun.periodStartDate)
        && Objects.equals(this.periodEndDate, payRun.periodEndDate)
        && Objects.equals(this.paymentDate, payRun.paymentDate)
        && Objects.equals(this.totalCost, payRun.totalCost)
        && Objects.equals(this.totalPay, payRun.totalPay)
        && Objects.equals(this.payRunStatus, payRun.payRunStatus)
        && Objects.equals(this.payRunType, payRun.payRunType)
        && Objects.equals(this.calendarType, payRun.calendarType)
        && Objects.equals(this.postedDateTime, payRun.postedDateTime)
        && Objects.equals(this.paySlips, payRun.paySlips);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        payRunID,
        payrollCalendarID,
        periodStartDate,
        periodEndDate,
        paymentDate,
        totalCost,
        totalPay,
        payRunStatus,
        payRunType,
        calendarType,
        postedDateTime,
        paySlips);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PayRun {\n");
    sb.append("    payRunID: ").append(toIndentedString(payRunID)).append("\n");
    sb.append("    payrollCalendarID: ").append(toIndentedString(payrollCalendarID)).append("\n");
    sb.append("    periodStartDate: ").append(toIndentedString(periodStartDate)).append("\n");
    sb.append("    periodEndDate: ").append(toIndentedString(periodEndDate)).append("\n");
    sb.append("    paymentDate: ").append(toIndentedString(paymentDate)).append("\n");
    sb.append("    totalCost: ").append(toIndentedString(totalCost)).append("\n");
    sb.append("    totalPay: ").append(toIndentedString(totalPay)).append("\n");
    sb.append("    payRunStatus: ").append(toIndentedString(payRunStatus)).append("\n");
    sb.append("    payRunType: ").append(toIndentedString(payRunType)).append("\n");
    sb.append("    calendarType: ").append(toIndentedString(calendarType)).append("\n");
    sb.append("    postedDateTime: ").append(toIndentedString(postedDateTime)).append("\n");
    sb.append("    paySlips: ").append(toIndentedString(paySlips)).append("\n");
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
