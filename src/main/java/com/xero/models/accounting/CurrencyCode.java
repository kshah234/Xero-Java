/*
 * Xero Accounting API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.accounting;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** 3 letter alpha code for the currency – see list of currency codes */
public enum CurrencyCode {

  /** AED */
  AED("AED"),

  /** AFN */
  AFN("AFN"),

  /** ALL */
  ALL("ALL"),

  /** AMD */
  AMD("AMD"),

  /** ANG */
  ANG("ANG"),

  /** AOA */
  AOA("AOA"),

  /** ARS */
  ARS("ARS"),

  /** AUD */
  AUD("AUD"),

  /** AWG */
  AWG("AWG"),

  /** AZN */
  AZN("AZN"),

  /** BAM */
  BAM("BAM"),

  /** BBD */
  BBD("BBD"),

  /** BDT */
  BDT("BDT"),

  /** BGN */
  BGN("BGN"),

  /** BHD */
  BHD("BHD"),

  /** BIF */
  BIF("BIF"),

  /** BMD */
  BMD("BMD"),

  /** BND */
  BND("BND"),

  /** BOB */
  BOB("BOB"),

  /** BRL */
  BRL("BRL"),

  /** BSD */
  BSD("BSD"),

  /** BTN */
  BTN("BTN"),

  /** BWP */
  BWP("BWP"),

  /** BYN */
  BYN("BYN"),

  /** BYR */
  BYR("BYR"),

  /** BZD */
  BZD("BZD"),

  /** CAD */
  CAD("CAD"),

  /** CDF */
  CDF("CDF"),

  /** CHF */
  CHF("CHF"),

  /** CLF */
  CLF("CLF"),

  /** CLP */
  CLP("CLP"),

  /** CNY */
  CNY("CNY"),

  /** COP */
  COP("COP"),

  /** CRC */
  CRC("CRC"),

  /** CUC */
  CUC("CUC"),

  /** CUP */
  CUP("CUP"),

  /** CVE */
  CVE("CVE"),

  /** CZK */
  CZK("CZK"),

  /** DJF */
  DJF("DJF"),

  /** DKK */
  DKK("DKK"),

  /** DOP */
  DOP("DOP"),

  /** DZD */
  DZD("DZD"),

  /** EEK */
  EEK("EEK"),

  /** EGP */
  EGP("EGP"),

  /** ERN */
  ERN("ERN"),

  /** ETB */
  ETB("ETB"),

  /** EUR */
  EUR("EUR"),

  /** FJD */
  FJD("FJD"),

  /** FKP */
  FKP("FKP"),

  /** GBP */
  GBP("GBP"),

  /** GEL */
  GEL("GEL"),

  /** GHS */
  GHS("GHS"),

  /** GIP */
  GIP("GIP"),

  /** GMD */
  GMD("GMD"),

  /** GNF */
  GNF("GNF"),

  /** GTQ */
  GTQ("GTQ"),

  /** GYD */
  GYD("GYD"),

  /** HKD */
  HKD("HKD"),

  /** HNL */
  HNL("HNL"),

  /** HRK */
  HRK("HRK"),

  /** HTG */
  HTG("HTG"),

  /** HUF */
  HUF("HUF"),

  /** IDR */
  IDR("IDR"),

  /** ILS */
  ILS("ILS"),

  /** INR */
  INR("INR"),

  /** IQD */
  IQD("IQD"),

  /** IRR */
  IRR("IRR"),

  /** ISK */
  ISK("ISK"),

  /** JMD */
  JMD("JMD"),

  /** JOD */
  JOD("JOD"),

  /** JPY */
  JPY("JPY"),

  /** KES */
  KES("KES"),

  /** KGS */
  KGS("KGS"),

  /** KHR */
  KHR("KHR"),

  /** KMF */
  KMF("KMF"),

  /** KPW */
  KPW("KPW"),

  /** KRW */
  KRW("KRW"),

  /** KWD */
  KWD("KWD"),

  /** KYD */
  KYD("KYD"),

  /** KZT */
  KZT("KZT"),

  /** LAK */
  LAK("LAK"),

  /** LBP */
  LBP("LBP"),

  /** LKR */
  LKR("LKR"),

  /** LRD */
  LRD("LRD"),

  /** LSL */
  LSL("LSL"),

  /** LTL */
  LTL("LTL"),

  /** LVL */
  LVL("LVL"),

  /** LYD */
  LYD("LYD"),

  /** MAD */
  MAD("MAD"),

  /** MDL */
  MDL("MDL"),

  /** MGA */
  MGA("MGA"),

  /** MKD */
  MKD("MKD"),

  /** MMK */
  MMK("MMK"),

  /** MNT */
  MNT("MNT"),

  /** MOP */
  MOP("MOP"),

  /** MRO */
  MRO("MRO"),

  /** MRU */
  MRU("MRU"),

  /** MUR */
  MUR("MUR"),

  /** MVR */
  MVR("MVR"),

  /** MWK */
  MWK("MWK"),

  /** MXN */
  MXN("MXN"),

  /** MXV */
  MXV("MXV"),

  /** MYR */
  MYR("MYR"),

  /** MZN */
  MZN("MZN"),

  /** NAD */
  NAD("NAD"),

  /** NGN */
  NGN("NGN"),

  /** NIO */
  NIO("NIO"),

  /** NOK */
  NOK("NOK"),

  /** NPR */
  NPR("NPR"),

  /** NZD */
  NZD("NZD"),

  /** OMR */
  OMR("OMR"),

  /** PAB */
  PAB("PAB"),

  /** PEN */
  PEN("PEN"),

  /** PGK */
  PGK("PGK"),

  /** PHP */
  PHP("PHP"),

  /** PKR */
  PKR("PKR"),

  /** PLN */
  PLN("PLN"),

  /** PYG */
  PYG("PYG"),

  /** QAR */
  QAR("QAR"),

  /** RON */
  RON("RON"),

  /** RSD */
  RSD("RSD"),

  /** RUB */
  RUB("RUB"),

  /** RWF */
  RWF("RWF"),

  /** SAR */
  SAR("SAR"),

  /** SBD */
  SBD("SBD"),

  /** SCR */
  SCR("SCR"),

  /** SDG */
  SDG("SDG"),

  /** SEK */
  SEK("SEK"),

  /** SGD */
  SGD("SGD"),

  /** SHP */
  SHP("SHP"),

  /** SKK */
  SKK("SKK"),

  /** SLE */
  SLE("SLE"),

  /** SLL */
  SLL("SLL"),

  /** SOS */
  SOS("SOS"),

  /** SRD */
  SRD("SRD"),

  /** STN */
  STN("STD"),

  /** STD */
  STD("STN"),

  /** SVC */
  SVC("SVC"),

  /** SYP */
  SYP("SYP"),

  /** SZL */
  SZL("SZL"),

  /** THB */
  THB("THB"),

  /** TJS */
  TJS("TJS"),

  /** TMT */
  TMT("TMT"),

  /** TND */
  TND("TND"),

  /** TOP */
  TOP("TOP"),

  /** TRY_LIRA */
  TRY_LIRA("TRY"),

  /** TTD */
  TTD("TTD"),

  /** TWD */
  TWD("TWD"),

  /** TZS */
  TZS("TZS"),

  /** UAH */
  UAH("UAH"),

  /** UGX */
  UGX("UGX"),

  /** USD */
  USD("USD"),

  /** UYU */
  UYU("UYU"),

  /** UZS */
  UZS("UZS"),

  /** VEF */
  VEF("VEF"),

  /** VES */
  VES("VES"),

  /** VND */
  VND("VND"),

  /** VUV */
  VUV("VUV"),

  /** WST */
  WST("WST"),

  /** XAF */
  XAF("XAF"),

  /** XCD */
  XCD("XCD"),

  /** XOF */
  XOF("XOF"),

  /** XPF */
  XPF("XPF"),

  /** YER */
  YER("YER"),

  /** ZAR */
  ZAR("ZAR"),

  /** ZMW */
  ZMW("ZMW"),

  /** ZMK */
  ZMK("ZMK"),

  /** ZWD */
  ZWD("ZWD");

  private String value;

  CurrencyCode(String value) {
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
  public static CurrencyCode fromValue(String value) {
    for (CurrencyCode b : CurrencyCode.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
