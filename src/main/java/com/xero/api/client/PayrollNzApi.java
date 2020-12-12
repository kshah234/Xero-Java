package com.xero.api.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.xero.api.ApiClient;
import com.xero.api.XeroApiExceptionHandler;
import com.xero.models.payrollnz.Benefit;
import com.xero.models.payrollnz.Deduction;
import com.xero.models.payrollnz.DeductionObject;
import com.xero.models.payrollnz.Deductions;
import com.xero.models.payrollnz.EarningsRate;
import com.xero.models.payrollnz.EarningsRateObject;
import com.xero.models.payrollnz.EarningsRates;
import com.xero.models.payrollnz.EarningsTemplate;
import com.xero.models.payrollnz.EarningsTemplateObject;
import com.xero.models.payrollnz.Employee;
import com.xero.models.payrollnz.EmployeeEarningsTemplates;
import com.xero.models.payrollnz.EmployeeLeave;
import com.xero.models.payrollnz.EmployeeLeaveBalances;
import com.xero.models.payrollnz.EmployeeLeaveObject;
import com.xero.models.payrollnz.EmployeeLeaveSetup;
import com.xero.models.payrollnz.EmployeeLeaveSetupObject;
import com.xero.models.payrollnz.EmployeeLeaveType;
import com.xero.models.payrollnz.EmployeeLeaveTypeObject;
import com.xero.models.payrollnz.EmployeeLeaveTypes;
import com.xero.models.payrollnz.EmployeeLeaves;
import com.xero.models.payrollnz.EmployeeObject;
import com.xero.models.payrollnz.EmployeeOpeningBalance;
import com.xero.models.payrollnz.EmployeeOpeningBalancesObject;
import com.xero.models.payrollnz.EmployeePayTemplates;
import com.xero.models.payrollnz.EmployeeTax;
import com.xero.models.payrollnz.EmployeeTaxObject;
import com.xero.models.payrollnz.Employees;
import com.xero.models.payrollnz.Employment;
import com.xero.models.payrollnz.EmploymentObject;
import com.xero.models.payrollnz.LeavePeriods;
import com.xero.models.payrollnz.LeaveType;
import com.xero.models.payrollnz.LeaveTypeObject;
import com.xero.models.payrollnz.LeaveTypes;
import com.xero.models.payrollnz.PayRun;
import com.xero.models.payrollnz.PayRunCalendar;
import com.xero.models.payrollnz.PayRunCalendarObject;
import com.xero.models.payrollnz.PayRunCalendars;
import com.xero.models.payrollnz.PayRunObject;
import com.xero.models.payrollnz.PayRuns;
import com.xero.models.payrollnz.PaySlip;
import com.xero.models.payrollnz.PaySlipObject;
import com.xero.models.payrollnz.PaySlips;
import com.xero.models.payrollnz.PaymentMethod;
import com.xero.models.payrollnz.PaymentMethodObject;
import com.xero.models.payrollnz.Reimbursement;
import com.xero.models.payrollnz.ReimbursementObject;
import com.xero.models.payrollnz.Reimbursements;
import com.xero.models.payrollnz.SalaryAndWage;
import com.xero.models.payrollnz.SalaryAndWageObject;
import com.xero.models.payrollnz.SalaryAndWages;
import com.xero.models.payrollnz.Settings;
import com.xero.models.payrollnz.StatutoryDeductionObject;
import com.xero.models.payrollnz.StatutoryDeductions;
import com.xero.models.payrollnz.SuperannuationObject;
import com.xero.models.payrollnz.Superannuations;
import com.xero.models.payrollnz.Timesheet;
import com.xero.models.payrollnz.TimesheetLine;
import com.xero.models.payrollnz.TimesheetLineObject;
import com.xero.models.payrollnz.TimesheetObject;
import com.xero.models.payrollnz.Timesheets;
import com.xero.models.payrollnz.TrackingCategories;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.LocalDate;

public class PayrollNzApi {
  private ApiClient apiClient;
  private static PayrollNzApi instance = null;
  private String userAgent = "Default";
  private String version = "4.5.0";
  static final Logger logger = LoggerFactory.getLogger(PayrollNzApi.class);

  public PayrollNzApi() {
    this(new ApiClient());
  }

  public static PayrollNzApi getInstance(ApiClient apiClient) {
    if (instance == null) {
      instance = new PayrollNzApi(apiClient);
    }
    return instance;
  }

  public PayrollNzApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public String getUserAgent() {
    return this.userAgent + " [Xero-Java-" + this.version + "]";
  }

  /**
   * approve a timesheet
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheetID Identifier for the timesheet
   * @param accessToken Authorization token for user set in header of each request
   * @return TimesheetObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimesheetObject approveTimesheet(String accessToken, String xeroTenantId, UUID timesheetID)
      throws IOException {
    try {
      TypeReference<TimesheetObject> typeRef = new TypeReference<TimesheetObject>() {};
      HttpResponse response =
          approveTimesheetForHttpResponse(accessToken, xeroTenantId, timesheetID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : approveTimesheet -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<TimesheetObject> errorTypeRef = new TypeReference<TimesheetObject>() {};
        TimesheetObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "TimesheetObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse approveTimesheetForHttpResponse(
      String accessToken, String xeroTenantId, UUID timesheetID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling approveTimesheet");
    } // verify the required parameter 'timesheetID' is set
    if (timesheetID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetID' when calling approveTimesheet");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling approveTimesheet");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TimesheetID", timesheetID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets/{TimesheetID}/Approve");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * create a new deduction
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param deduction The deduction parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return DeductionObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public DeductionObject createDeduction(
      String accessToken, String xeroTenantId, Deduction deduction) throws IOException {
    try {
      TypeReference<DeductionObject> typeRef = new TypeReference<DeductionObject>() {};
      HttpResponse response = createDeductionForHttpResponse(accessToken, xeroTenantId, deduction);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createDeduction -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<DeductionObject> errorTypeRef = new TypeReference<DeductionObject>() {};
        DeductionObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "DeductionObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createDeductionForHttpResponse(
      String accessToken, String xeroTenantId, Deduction deduction) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createDeduction");
    } // verify the required parameter 'deduction' is set
    if (deduction == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'deduction' when calling createDeduction");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createDeduction");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Deductions");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(deduction);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * create a new earnings rate
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param earningsRate The earningsRate parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EarningsRateObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EarningsRateObject createEarningsRate(
      String accessToken, String xeroTenantId, EarningsRate earningsRate) throws IOException {
    try {
      TypeReference<EarningsRateObject> typeRef = new TypeReference<EarningsRateObject>() {};
      HttpResponse response =
          createEarningsRateForHttpResponse(accessToken, xeroTenantId, earningsRate);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEarningsRate -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EarningsRateObject> errorTypeRef = new TypeReference<EarningsRateObject>() {};
        EarningsRateObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EarningsRateObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEarningsRateForHttpResponse(
      String accessToken, String xeroTenantId, EarningsRate earningsRate) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createEarningsRate");
    } // verify the required parameter 'earningsRate' is set
    if (earningsRate == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'earningsRate' when calling createEarningsRate");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createEarningsRate");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/EarningsRates");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(earningsRate);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * creates employees
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employee The employee parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeObject createEmployee(String accessToken, String xeroTenantId, Employee employee)
      throws IOException {
    try {
      TypeReference<EmployeeObject> typeRef = new TypeReference<EmployeeObject>() {};
      HttpResponse response = createEmployeeForHttpResponse(accessToken, xeroTenantId, employee);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployee -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeObject> errorTypeRef = new TypeReference<EmployeeObject>() {};
        EmployeeObject object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEmployeeForHttpResponse(
      String accessToken, String xeroTenantId, Employee employee) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createEmployee");
    } // verify the required parameter 'employee' is set
    if (employee == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employee' when calling createEmployee");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createEmployee");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employee);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * creates employee earnings template records
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param earningsTemplate The earningsTemplate parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EarningsTemplateObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EarningsTemplateObject createEmployeeEarningsTemplate(
      String accessToken, String xeroTenantId, UUID employeeId, EarningsTemplate earningsTemplate)
      throws IOException {
    try {
      TypeReference<EarningsTemplateObject> typeRef =
          new TypeReference<EarningsTemplateObject>() {};
      HttpResponse response =
          createEmployeeEarningsTemplateForHttpResponse(
              accessToken, xeroTenantId, employeeId, earningsTemplate);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployeeEarningsTemplate -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EarningsTemplateObject> errorTypeRef =
            new TypeReference<EarningsTemplateObject>() {};
        EarningsTemplateObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(
            e.getStatusCode(), "EarningsTemplateObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEmployeeEarningsTemplateForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, EarningsTemplate earningsTemplate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createEmployeeEarningsTemplate");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling"
              + " createEmployeeEarningsTemplate");
    } // verify the required parameter 'earningsTemplate' is set
    if (earningsTemplate == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'earningsTemplate' when calling"
              + " createEmployeeEarningsTemplate");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createEmployeeEarningsTemplate");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Employees/{EmployeeId}/PayTemplates/earnings");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(earningsTemplate);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * creates employee leave records
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param employeeLeave The employeeLeave parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeLeaveObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeLeaveObject createEmployeeLeave(
      String accessToken, String xeroTenantId, UUID employeeId, EmployeeLeave employeeLeave)
      throws IOException {
    try {
      TypeReference<EmployeeLeaveObject> typeRef = new TypeReference<EmployeeLeaveObject>() {};
      HttpResponse response =
          createEmployeeLeaveForHttpResponse(accessToken, xeroTenantId, employeeId, employeeLeave);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployeeLeave -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeLeaveObject> errorTypeRef =
            new TypeReference<EmployeeLeaveObject>() {};
        EmployeeLeaveObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeLeaveObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEmployeeLeaveForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, EmployeeLeave employeeLeave)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createEmployeeLeave");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling createEmployeeLeave");
    } // verify the required parameter 'employeeLeave' is set
    if (employeeLeave == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeLeave' when calling createEmployeeLeave");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createEmployeeLeave");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/Leave");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employeeLeave);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to set-up leave for a specific employee. This is required before viewing,
   * configuring and requesting leave for an employee
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param employeeLeaveSetup The employeeLeaveSetup parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeLeaveSetupObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeLeaveSetupObject createEmployeeLeaveSetup(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      EmployeeLeaveSetup employeeLeaveSetup)
      throws IOException {
    try {
      TypeReference<EmployeeLeaveSetupObject> typeRef =
          new TypeReference<EmployeeLeaveSetupObject>() {};
      HttpResponse response =
          createEmployeeLeaveSetupForHttpResponse(
              accessToken, xeroTenantId, employeeId, employeeLeaveSetup);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployeeLeaveSetup -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeLeaveSetupObject> errorTypeRef =
            new TypeReference<EmployeeLeaveSetupObject>() {};
        EmployeeLeaveSetupObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(
            e.getStatusCode(), "EmployeeLeaveSetupObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEmployeeLeaveSetupForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      EmployeeLeaveSetup employeeLeaveSetup)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createEmployeeLeaveSetup");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling createEmployeeLeaveSetup");
    } // verify the required parameter 'employeeLeaveSetup' is set
    if (employeeLeaveSetup == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeLeaveSetup' when calling"
              + " createEmployeeLeaveSetup");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createEmployeeLeaveSetup");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/leaveSetup");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employeeLeaveSetup);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * creates employee leave type records
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param employeeLeaveType The employeeLeaveType parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeLeaveTypeObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeLeaveTypeObject createEmployeeLeaveType(
      String accessToken, String xeroTenantId, UUID employeeId, EmployeeLeaveType employeeLeaveType)
      throws IOException {
    try {
      TypeReference<EmployeeLeaveTypeObject> typeRef =
          new TypeReference<EmployeeLeaveTypeObject>() {};
      HttpResponse response =
          createEmployeeLeaveTypeForHttpResponse(
              accessToken, xeroTenantId, employeeId, employeeLeaveType);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployeeLeaveType -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeLeaveTypeObject> errorTypeRef =
            new TypeReference<EmployeeLeaveTypeObject>() {};
        EmployeeLeaveTypeObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(
            e.getStatusCode(), "EmployeeLeaveTypeObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEmployeeLeaveTypeForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, EmployeeLeaveType employeeLeaveType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createEmployeeLeaveType");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling createEmployeeLeaveType");
    } // verify the required parameter 'employeeLeaveType' is set
    if (employeeLeaveType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeLeaveType' when calling"
              + " createEmployeeLeaveType");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createEmployeeLeaveType");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/LeaveTypes");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employeeLeaveType);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * creates employee opening balances
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param employeeOpeningBalance The employeeOpeningBalance parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeOpeningBalancesObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeOpeningBalancesObject createEmployeeOpeningBalances(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      List<EmployeeOpeningBalance> employeeOpeningBalance)
      throws IOException {
    try {
      TypeReference<EmployeeOpeningBalancesObject> typeRef =
          new TypeReference<EmployeeOpeningBalancesObject>() {};
      HttpResponse response =
          createEmployeeOpeningBalancesForHttpResponse(
              accessToken, xeroTenantId, employeeId, employeeOpeningBalance);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployeeOpeningBalances -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeOpeningBalancesObject> errorTypeRef =
            new TypeReference<EmployeeOpeningBalancesObject>() {};
        EmployeeOpeningBalancesObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(
            e.getStatusCode(), "EmployeeOpeningBalancesObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEmployeeOpeningBalancesForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      List<EmployeeOpeningBalance> employeeOpeningBalance)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createEmployeeOpeningBalances");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling createEmployeeOpeningBalances");
    } // verify the required parameter 'employeeOpeningBalance' is set
    if (employeeOpeningBalance == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeOpeningBalance' when calling"
              + " createEmployeeOpeningBalances");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createEmployeeOpeningBalances");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/openingBalances");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employeeOpeningBalance);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * creates employee payment method
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param paymentMethod The paymentMethod parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return PaymentMethodObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PaymentMethodObject createEmployeePaymentMethod(
      String accessToken, String xeroTenantId, UUID employeeId, PaymentMethod paymentMethod)
      throws IOException {
    try {
      TypeReference<PaymentMethodObject> typeRef = new TypeReference<PaymentMethodObject>() {};
      HttpResponse response =
          createEmployeePaymentMethodForHttpResponse(
              accessToken, xeroTenantId, employeeId, paymentMethod);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployeePaymentMethod -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PaymentMethodObject> errorTypeRef =
            new TypeReference<PaymentMethodObject>() {};
        PaymentMethodObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PaymentMethodObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEmployeePaymentMethodForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, PaymentMethod paymentMethod)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createEmployeePaymentMethod");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling createEmployeePaymentMethod");
    } // verify the required parameter 'paymentMethod' is set
    if (paymentMethod == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paymentMethod' when calling"
              + " createEmployeePaymentMethod");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createEmployeePaymentMethod");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/PaymentMethods");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(paymentMethod);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * creates employee salary and wage record
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param salaryAndWage The salaryAndWage parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return SalaryAndWageObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SalaryAndWageObject createEmployeeSalaryAndWage(
      String accessToken, String xeroTenantId, UUID employeeId, SalaryAndWage salaryAndWage)
      throws IOException {
    try {
      TypeReference<SalaryAndWageObject> typeRef = new TypeReference<SalaryAndWageObject>() {};
      HttpResponse response =
          createEmployeeSalaryAndWageForHttpResponse(
              accessToken, xeroTenantId, employeeId, salaryAndWage);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployeeSalaryAndWage -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<SalaryAndWageObject> errorTypeRef =
            new TypeReference<SalaryAndWageObject>() {};
        SalaryAndWageObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "SalaryAndWageObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEmployeeSalaryAndWageForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, SalaryAndWage salaryAndWage)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createEmployeeSalaryAndWage");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling createEmployeeSalaryAndWage");
    } // verify the required parameter 'salaryAndWage' is set
    if (salaryAndWage == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'salaryAndWage' when calling"
              + " createEmployeeSalaryAndWage");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createEmployeeSalaryAndWage");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/SalaryAndWages");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(salaryAndWage);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * creates employment
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param employment The employment parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EmploymentObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmploymentObject createEmployment(
      String accessToken, String xeroTenantId, UUID employeeId, Employment employment)
      throws IOException {
    try {
      TypeReference<EmploymentObject> typeRef = new TypeReference<EmploymentObject>() {};
      HttpResponse response =
          createEmploymentForHttpResponse(accessToken, xeroTenantId, employeeId, employment);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployment -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmploymentObject> errorTypeRef = new TypeReference<EmploymentObject>() {};
        EmploymentObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmploymentObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEmploymentForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, Employment employment)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createEmployment");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling createEmployment");
    } // verify the required parameter 'employment' is set
    if (employment == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employment' when calling createEmployment");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createEmployment");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/Employment");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employment);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * create a new leave type
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param leaveType The leaveType parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return LeaveTypeObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LeaveTypeObject createLeaveType(
      String accessToken, String xeroTenantId, LeaveType leaveType) throws IOException {
    try {
      TypeReference<LeaveTypeObject> typeRef = new TypeReference<LeaveTypeObject>() {};
      HttpResponse response = createLeaveTypeForHttpResponse(accessToken, xeroTenantId, leaveType);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createLeaveType -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<LeaveTypeObject> errorTypeRef = new TypeReference<LeaveTypeObject>() {};
        LeaveTypeObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "LeaveTypeObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createLeaveTypeForHttpResponse(
      String accessToken, String xeroTenantId, LeaveType leaveType) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createLeaveType");
    } // verify the required parameter 'leaveType' is set
    if (leaveType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'leaveType' when calling createLeaveType");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createLeaveType");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/LeaveTypes");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(leaveType);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * creates multiple employee earnings template records
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param earningsTemplate The earningsTemplate parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeEarningsTemplates
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeEarningsTemplates createMultipleEmployeeEarningsTemplate(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      List<EarningsTemplate> earningsTemplate)
      throws IOException {
    try {
      TypeReference<EmployeeEarningsTemplates> typeRef =
          new TypeReference<EmployeeEarningsTemplates>() {};
      HttpResponse response =
          createMultipleEmployeeEarningsTemplateForHttpResponse(
              accessToken, xeroTenantId, employeeId, earningsTemplate);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createMultipleEmployeeEarningsTemplate -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeEarningsTemplates> errorTypeRef =
            new TypeReference<EmployeeEarningsTemplates>() {};
        EmployeeEarningsTemplates object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(
            e.getStatusCode(), "EmployeeEarningsTemplates", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createMultipleEmployeeEarningsTemplateForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      List<EarningsTemplate> earningsTemplate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createMultipleEmployeeEarningsTemplate");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling"
              + " createMultipleEmployeeEarningsTemplate");
    } // verify the required parameter 'earningsTemplate' is set
    if (earningsTemplate == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'earningsTemplate' when calling"
              + " createMultipleEmployeeEarningsTemplate");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createMultipleEmployeeEarningsTemplate");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/paytemplateearnings");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(earningsTemplate);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * create a pay run
   *
   * <p><b>200</b> - created payrun results
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payRun The payRun parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRunObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRunObject createPayRun(String accessToken, String xeroTenantId, PayRun payRun)
      throws IOException {
    try {
      TypeReference<PayRunObject> typeRef = new TypeReference<PayRunObject>() {};
      HttpResponse response = createPayRunForHttpResponse(accessToken, xeroTenantId, payRun);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPayRun -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PayRunObject> errorTypeRef = new TypeReference<PayRunObject>() {};
        PayRunObject object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PayRunObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPayRunForHttpResponse(
      String accessToken, String xeroTenantId, PayRun payRun) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPayRun");
    } // verify the required parameter 'payRun' is set
    if (payRun == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payRun' when calling createPayRun");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPayRun");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayRuns");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(payRun);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * create a new payrun calendar
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payRunCalendar The payRunCalendar parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRunCalendarObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRunCalendarObject createPayRunCalendar(
      String accessToken, String xeroTenantId, PayRunCalendar payRunCalendar) throws IOException {
    try {
      TypeReference<PayRunCalendarObject> typeRef = new TypeReference<PayRunCalendarObject>() {};
      HttpResponse response =
          createPayRunCalendarForHttpResponse(accessToken, xeroTenantId, payRunCalendar);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPayRunCalendar -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PayRunCalendarObject> errorTypeRef =
            new TypeReference<PayRunCalendarObject>() {};
        PayRunCalendarObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PayRunCalendarObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPayRunCalendarForHttpResponse(
      String accessToken, String xeroTenantId, PayRunCalendar payRunCalendar) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPayRunCalendar");
    } // verify the required parameter 'payRunCalendar' is set
    if (payRunCalendar == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payRunCalendar' when calling createPayRunCalendar");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPayRunCalendar");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayRunCalendars");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(payRunCalendar);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * create a new reimbursement
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param reimbursement The reimbursement parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return ReimbursementObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReimbursementObject createReimbursement(
      String accessToken, String xeroTenantId, Reimbursement reimbursement) throws IOException {
    try {
      TypeReference<ReimbursementObject> typeRef = new TypeReference<ReimbursementObject>() {};
      HttpResponse response =
          createReimbursementForHttpResponse(accessToken, xeroTenantId, reimbursement);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createReimbursement -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<ReimbursementObject> errorTypeRef =
            new TypeReference<ReimbursementObject>() {};
        ReimbursementObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "ReimbursementObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createReimbursementForHttpResponse(
      String accessToken, String xeroTenantId, Reimbursement reimbursement) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createReimbursement");
    } // verify the required parameter 'reimbursement' is set
    if (reimbursement == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'reimbursement' when calling createReimbursement");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createReimbursement");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Reimbursements");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(reimbursement);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * create a new superannuation
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param benefit The benefit parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return SuperannuationObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SuperannuationObject createSuperannuation(
      String accessToken, String xeroTenantId, Benefit benefit) throws IOException {
    try {
      TypeReference<SuperannuationObject> typeRef = new TypeReference<SuperannuationObject>() {};
      HttpResponse response =
          createSuperannuationForHttpResponse(accessToken, xeroTenantId, benefit);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createSuperannuation -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<SuperannuationObject> errorTypeRef =
            new TypeReference<SuperannuationObject>() {};
        SuperannuationObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "SuperannuationObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createSuperannuationForHttpResponse(
      String accessToken, String xeroTenantId, Benefit benefit) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createSuperannuation");
    } // verify the required parameter 'benefit' is set
    if (benefit == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'benefit' when calling createSuperannuation");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createSuperannuation");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/superannuations");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(benefit);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * create a new timesheet
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheet The timesheet parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return TimesheetObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimesheetObject createTimesheet(
      String accessToken, String xeroTenantId, Timesheet timesheet) throws IOException {
    try {
      TypeReference<TimesheetObject> typeRef = new TypeReference<TimesheetObject>() {};
      HttpResponse response = createTimesheetForHttpResponse(accessToken, xeroTenantId, timesheet);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createTimesheet -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<TimesheetObject> errorTypeRef = new TypeReference<TimesheetObject>() {};
        TimesheetObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "TimesheetObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createTimesheetForHttpResponse(
      String accessToken, String xeroTenantId, Timesheet timesheet) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createTimesheet");
    } // verify the required parameter 'timesheet' is set
    if (timesheet == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheet' when calling createTimesheet");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createTimesheet");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(timesheet);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * create a new timesheet line
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheetID Identifier for the timesheet
   * @param timesheetLine The timesheetLine parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return TimesheetLineObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimesheetLineObject createTimesheetLine(
      String accessToken, String xeroTenantId, UUID timesheetID, TimesheetLine timesheetLine)
      throws IOException {
    try {
      TypeReference<TimesheetLineObject> typeRef = new TypeReference<TimesheetLineObject>() {};
      HttpResponse response =
          createTimesheetLineForHttpResponse(accessToken, xeroTenantId, timesheetID, timesheetLine);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createTimesheetLine -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<TimesheetLineObject> errorTypeRef =
            new TypeReference<TimesheetLineObject>() {};
        TimesheetLineObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "TimesheetLineObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createTimesheetLineForHttpResponse(
      String accessToken, String xeroTenantId, UUID timesheetID, TimesheetLine timesheetLine)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createTimesheetLine");
    } // verify the required parameter 'timesheetID' is set
    if (timesheetID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetID' when calling createTimesheetLine");
    } // verify the required parameter 'timesheetLine' is set
    if (timesheetLine == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetLine' when calling createTimesheetLine");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createTimesheetLine");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TimesheetID", timesheetID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets/{TimesheetID}/Lines");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(timesheetLine);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * deletes an employee earnings template record
   *
   * <p><b>200</b> - deletion successful
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param payTemplateEarningID Id for single pay template earnings object
   * @param accessToken Authorization token for user set in header of each request
   * @return EarningsTemplateObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EarningsTemplateObject deleteEmployeeEarningsTemplate(
      String accessToken, String xeroTenantId, UUID employeeId, UUID payTemplateEarningID)
      throws IOException {
    try {
      TypeReference<EarningsTemplateObject> typeRef =
          new TypeReference<EarningsTemplateObject>() {};
      HttpResponse response =
          deleteEmployeeEarningsTemplateForHttpResponse(
              accessToken, xeroTenantId, employeeId, payTemplateEarningID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteEmployeeEarningsTemplate -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse deleteEmployeeEarningsTemplateForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, UUID payTemplateEarningID)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " deleteEmployeeEarningsTemplate");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling"
              + " deleteEmployeeEarningsTemplate");
    } // verify the required parameter 'payTemplateEarningID' is set
    if (payTemplateEarningID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payTemplateEarningID' when calling"
              + " deleteEmployeeEarningsTemplate");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " deleteEmployeeEarningsTemplate");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);
    uriVariables.put("PayTemplateEarningID", payTemplateEarningID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/Employees/{EmployeeId}/PayTemplates/earnings/{PayTemplateEarningID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * deletes an employee leave record
   *
   * <p><b>200</b> - successful response
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param leaveID Leave id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeLeaveObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeLeaveObject deleteEmployeeLeave(
      String accessToken, String xeroTenantId, UUID employeeId, UUID leaveID) throws IOException {
    try {
      TypeReference<EmployeeLeaveObject> typeRef = new TypeReference<EmployeeLeaveObject>() {};
      HttpResponse response =
          deleteEmployeeLeaveForHttpResponse(accessToken, xeroTenantId, employeeId, leaveID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteEmployeeLeave -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeLeaveObject> errorTypeRef =
            new TypeReference<EmployeeLeaveObject>() {};
        EmployeeLeaveObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeLeaveObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse deleteEmployeeLeaveForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, UUID leaveID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteEmployeeLeave");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling deleteEmployeeLeave");
    } // verify the required parameter 'leaveID' is set
    if (leaveID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'leaveID' when calling deleteEmployeeLeave");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteEmployeeLeave");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);
    uriVariables.put("LeaveID", leaveID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/Leave/{LeaveID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * deletes an employee salary and wages record
   *
   * <p><b>200</b> - deletion successful
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param salaryAndWagesID Id for single salary and wages object
   * @param accessToken Authorization token for user set in header of each request
   * @return SalaryAndWageObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SalaryAndWageObject deleteEmployeeSalaryAndWage(
      String accessToken, String xeroTenantId, UUID employeeId, UUID salaryAndWagesID)
      throws IOException {
    try {
      TypeReference<SalaryAndWageObject> typeRef = new TypeReference<SalaryAndWageObject>() {};
      HttpResponse response =
          deleteEmployeeSalaryAndWageForHttpResponse(
              accessToken, xeroTenantId, employeeId, salaryAndWagesID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteEmployeeSalaryAndWage -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse deleteEmployeeSalaryAndWageForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, UUID salaryAndWagesID)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteEmployeeSalaryAndWage");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling deleteEmployeeSalaryAndWage");
    } // verify the required parameter 'salaryAndWagesID' is set
    if (salaryAndWagesID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'salaryAndWagesID' when calling"
              + " deleteEmployeeSalaryAndWage");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteEmployeeSalaryAndWage");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);
    uriVariables.put("SalaryAndWagesID", salaryAndWagesID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Employees/{EmployeeId}/SalaryAndWages/{SalaryAndWagesID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * delete a timesheet
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheetID Identifier for the timesheet
   * @param accessToken Authorization token for user set in header of each request
   * @return TimesheetLine
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimesheetLine deleteTimesheet(String accessToken, String xeroTenantId, UUID timesheetID)
      throws IOException {
    try {
      TypeReference<TimesheetLine> typeRef = new TypeReference<TimesheetLine>() {};
      HttpResponse response =
          deleteTimesheetForHttpResponse(accessToken, xeroTenantId, timesheetID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteTimesheet -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse deleteTimesheetForHttpResponse(
      String accessToken, String xeroTenantId, UUID timesheetID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteTimesheet");
    } // verify the required parameter 'timesheetID' is set
    if (timesheetID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetID' when calling deleteTimesheet");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteTimesheet");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TimesheetID", timesheetID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets/{TimesheetID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * delete a timesheet line
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheetID Identifier for the timesheet
   * @param timesheetLineID Identifier for the timesheet line
   * @param accessToken Authorization token for user set in header of each request
   * @return TimesheetLine
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimesheetLine deleteTimesheetLine(
      String accessToken, String xeroTenantId, UUID timesheetID, UUID timesheetLineID)
      throws IOException {
    try {
      TypeReference<TimesheetLine> typeRef = new TypeReference<TimesheetLine>() {};
      HttpResponse response =
          deleteTimesheetLineForHttpResponse(
              accessToken, xeroTenantId, timesheetID, timesheetLineID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteTimesheetLine -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse deleteTimesheetLineForHttpResponse(
      String accessToken, String xeroTenantId, UUID timesheetID, UUID timesheetLineID)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteTimesheetLine");
    } // verify the required parameter 'timesheetID' is set
    if (timesheetID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetID' when calling deleteTimesheetLine");
    } // verify the required parameter 'timesheetLineID' is set
    if (timesheetLineID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetLineID' when calling deleteTimesheetLine");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteTimesheetLine");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TimesheetID", timesheetID);
    uriVariables.put("TimesheetLineID", timesheetLineID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Timesheets/{TimesheetID}/Lines/{TimesheetLineID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieve a single deduction by id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param deductionId Identifier for the deduction
   * @param accessToken Authorization token for user set in header of each request
   * @return DeductionObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public DeductionObject getDeduction(String accessToken, String xeroTenantId, UUID deductionId)
      throws IOException {
    try {
      TypeReference<DeductionObject> typeRef = new TypeReference<DeductionObject>() {};
      HttpResponse response = getDeductionForHttpResponse(accessToken, xeroTenantId, deductionId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getDeduction -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<DeductionObject> errorTypeRef = new TypeReference<DeductionObject>() {};
        DeductionObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "DeductionObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getDeductionForHttpResponse(
      String accessToken, String xeroTenantId, UUID deductionId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getDeduction");
    } // verify the required parameter 'deductionId' is set
    if (deductionId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'deductionId' when calling getDeduction");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getDeduction");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("deductionId", deductionId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Deductions/{deductionId}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches deductions
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param accessToken Authorization token for user set in header of each request
   * @return Deductions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Deductions getDeductions(String accessToken, String xeroTenantId, Integer page)
      throws IOException {
    try {
      TypeReference<Deductions> typeRef = new TypeReference<Deductions>() {};
      HttpResponse response = getDeductionsForHttpResponse(accessToken, xeroTenantId, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getDeductions -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<Deductions> errorTypeRef = new TypeReference<Deductions>() {};
        Deductions object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "Deductions", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getDeductionsForHttpResponse(
      String accessToken, String xeroTenantId, Integer page) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getDeductions");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getDeductions");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Deductions");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieve a single earnings rates by id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param earningsRateID Identifier for the earnings rate
   * @param accessToken Authorization token for user set in header of each request
   * @return EarningsRateObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EarningsRateObject getEarningsRate(
      String accessToken, String xeroTenantId, UUID earningsRateID) throws IOException {
    try {
      TypeReference<EarningsRateObject> typeRef = new TypeReference<EarningsRateObject>() {};
      HttpResponse response =
          getEarningsRateForHttpResponse(accessToken, xeroTenantId, earningsRateID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEarningsRate -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EarningsRateObject> errorTypeRef = new TypeReference<EarningsRateObject>() {};
        EarningsRateObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EarningsRateObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEarningsRateForHttpResponse(
      String accessToken, String xeroTenantId, UUID earningsRateID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEarningsRate");
    } // verify the required parameter 'earningsRateID' is set
    if (earningsRateID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'earningsRateID' when calling getEarningsRate");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEarningsRate");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EarningsRateID", earningsRateID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/EarningsRates/{EarningsRateID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches earnings rates
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param accessToken Authorization token for user set in header of each request
   * @return EarningsRates
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EarningsRates getEarningsRates(String accessToken, String xeroTenantId, Integer page)
      throws IOException {
    try {
      TypeReference<EarningsRates> typeRef = new TypeReference<EarningsRates>() {};
      HttpResponse response = getEarningsRatesForHttpResponse(accessToken, xeroTenantId, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEarningsRates -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EarningsRates> errorTypeRef = new TypeReference<EarningsRates>() {};
        EarningsRates object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EarningsRates", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEarningsRatesForHttpResponse(
      String accessToken, String xeroTenantId, Integer page) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEarningsRates");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEarningsRates");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/EarningsRates");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches employees
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeObject getEmployee(String accessToken, String xeroTenantId, UUID employeeId)
      throws IOException {
    try {
      TypeReference<EmployeeObject> typeRef = new TypeReference<EmployeeObject>() {};
      HttpResponse response = getEmployeeForHttpResponse(accessToken, xeroTenantId, employeeId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployee -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeObject> errorTypeRef = new TypeReference<EmployeeObject>() {};
        EmployeeObject object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeeForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployee");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployee");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployee");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * search employee leave balances
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeLeaveBalances
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeLeaveBalances getEmployeeLeaveBalances(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    try {
      TypeReference<EmployeeLeaveBalances> typeRef = new TypeReference<EmployeeLeaveBalances>() {};
      HttpResponse response =
          getEmployeeLeaveBalancesForHttpResponse(accessToken, xeroTenantId, employeeId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployeeLeaveBalances -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeLeaveBalances> errorTypeRef =
            new TypeReference<EmployeeLeaveBalances>() {};
        EmployeeLeaveBalances object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeLeaveBalances", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeeLeaveBalancesForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployeeLeaveBalances");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployeeLeaveBalances");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployeeLeaveBalances");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/LeaveBalances");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches employee leave periods
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param startDate Filter by start date
   * @param endDate Filter by end date
   * @param accessToken Authorization token for user set in header of each request
   * @return LeavePeriods
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LeavePeriods getEmployeeLeavePeriods(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      LocalDate startDate,
      LocalDate endDate)
      throws IOException {
    try {
      TypeReference<LeavePeriods> typeRef = new TypeReference<LeavePeriods>() {};
      HttpResponse response =
          getEmployeeLeavePeriodsForHttpResponse(
              accessToken, xeroTenantId, employeeId, startDate, endDate);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployeeLeavePeriods -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<LeavePeriods> errorTypeRef = new TypeReference<LeavePeriods>() {};
        LeavePeriods object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "LeavePeriods", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeeLeavePeriodsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      LocalDate startDate,
      LocalDate endDate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployeeLeavePeriods");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployeeLeavePeriods");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployeeLeavePeriods");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/LeavePeriods");
    if (startDate != null) {
      String key = "startDate";
      Object value = startDate;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (endDate != null) {
      String key = "endDate";
      Object value = endDate;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches employee leave types
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeLeaveTypes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeLeaveTypes getEmployeeLeaveTypes(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    try {
      TypeReference<EmployeeLeaveTypes> typeRef = new TypeReference<EmployeeLeaveTypes>() {};
      HttpResponse response =
          getEmployeeLeaveTypesForHttpResponse(accessToken, xeroTenantId, employeeId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployeeLeaveTypes -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeLeaveTypes> errorTypeRef = new TypeReference<EmployeeLeaveTypes>() {};
        EmployeeLeaveTypes object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeLeaveTypes", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeeLeaveTypesForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployeeLeaveTypes");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployeeLeaveTypes");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployeeLeaveTypes");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/LeaveTypes");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * search employee leave records
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeLeaves
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeLeaves getEmployeeLeaves(String accessToken, String xeroTenantId, UUID employeeId)
      throws IOException {
    try {
      TypeReference<EmployeeLeaves> typeRef = new TypeReference<EmployeeLeaves>() {};
      HttpResponse response =
          getEmployeeLeavesForHttpResponse(accessToken, xeroTenantId, employeeId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployeeLeaves -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeLeaves> errorTypeRef = new TypeReference<EmployeeLeaves>() {};
        EmployeeLeaves object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeLeaves", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeeLeavesForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployeeLeaves");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployeeLeaves");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployeeLeaves");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/Leave");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieve employee openingbalances
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeOpeningBalancesObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeOpeningBalancesObject getEmployeeOpeningBalances(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    try {
      TypeReference<EmployeeOpeningBalancesObject> typeRef =
          new TypeReference<EmployeeOpeningBalancesObject>() {};
      HttpResponse response =
          getEmployeeOpeningBalancesForHttpResponse(accessToken, xeroTenantId, employeeId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployeeOpeningBalances -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeOpeningBalancesObject> errorTypeRef =
            new TypeReference<EmployeeOpeningBalancesObject>() {};
        EmployeeOpeningBalancesObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(
            e.getStatusCode(), "EmployeeOpeningBalancesObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeeOpeningBalancesForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployeeOpeningBalances");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployeeOpeningBalances");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployeeOpeningBalances");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/openingBalances");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches employee pay templates
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeePayTemplates
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeePayTemplates getEmployeePayTemplates(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    try {
      TypeReference<EmployeePayTemplates> typeRef = new TypeReference<EmployeePayTemplates>() {};
      HttpResponse response =
          getEmployeePayTemplatesForHttpResponse(accessToken, xeroTenantId, employeeId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployeePayTemplates -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeePayTemplates> errorTypeRef =
            new TypeReference<EmployeePayTemplates>() {};
        EmployeePayTemplates object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeePayTemplates", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeePayTemplatesForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployeePayTemplates");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployeePayTemplates");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployeePayTemplates");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/PayTemplates");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieves an employee&#39;s payment method
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return PaymentMethodObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PaymentMethodObject getEmployeePaymentMethod(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    try {
      TypeReference<PaymentMethodObject> typeRef = new TypeReference<PaymentMethodObject>() {};
      HttpResponse response =
          getEmployeePaymentMethodForHttpResponse(accessToken, xeroTenantId, employeeId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployeePaymentMethod -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PaymentMethodObject> errorTypeRef =
            new TypeReference<PaymentMethodObject>() {};
        PaymentMethodObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PaymentMethodObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeePaymentMethodForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployeePaymentMethod");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployeePaymentMethod");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployeePaymentMethod");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/PaymentMethods");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * get employee salary and wages record by id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param salaryAndWagesID Id for single pay template earnings object
   * @param accessToken Authorization token for user set in header of each request
   * @return SalaryAndWages
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SalaryAndWages getEmployeeSalaryAndWage(
      String accessToken, String xeroTenantId, UUID employeeId, UUID salaryAndWagesID)
      throws IOException {
    try {
      TypeReference<SalaryAndWages> typeRef = new TypeReference<SalaryAndWages>() {};
      HttpResponse response =
          getEmployeeSalaryAndWageForHttpResponse(
              accessToken, xeroTenantId, employeeId, salaryAndWagesID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployeeSalaryAndWage -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<SalaryAndWages> errorTypeRef = new TypeReference<SalaryAndWages>() {};
        SalaryAndWages object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "SalaryAndWages", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeeSalaryAndWageForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, UUID salaryAndWagesID)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployeeSalaryAndWage");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployeeSalaryAndWage");
    } // verify the required parameter 'salaryAndWagesID' is set
    if (salaryAndWagesID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'salaryAndWagesID' when calling"
              + " getEmployeeSalaryAndWage");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployeeSalaryAndWage");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);
    uriVariables.put("SalaryAndWagesID", salaryAndWagesID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Employees/{EmployeeId}/SalaryAndWages/{SalaryAndWagesID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieves an employee&#39;s salary and wages
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param accessToken Authorization token for user set in header of each request
   * @return SalaryAndWages
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SalaryAndWages getEmployeeSalaryAndWages(
      String accessToken, String xeroTenantId, UUID employeeId, Integer page) throws IOException {
    try {
      TypeReference<SalaryAndWages> typeRef = new TypeReference<SalaryAndWages>() {};
      HttpResponse response =
          getEmployeeSalaryAndWagesForHttpResponse(accessToken, xeroTenantId, employeeId, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployeeSalaryAndWages -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<SalaryAndWages> errorTypeRef = new TypeReference<SalaryAndWages>() {};
        SalaryAndWages object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "SalaryAndWages", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeeSalaryAndWagesForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, Integer page) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployeeSalaryAndWages");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployeeSalaryAndWages");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployeeSalaryAndWages");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/SalaryAndWages");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches tax records for an employee
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeTaxObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeTaxObject getEmployeeTax(String accessToken, String xeroTenantId, UUID employeeId)
      throws IOException {
    try {
      TypeReference<EmployeeTaxObject> typeRef = new TypeReference<EmployeeTaxObject>() {};
      HttpResponse response = getEmployeeTaxForHttpResponse(accessToken, xeroTenantId, employeeId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployeeTax -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeTaxObject> errorTypeRef = new TypeReference<EmployeeTaxObject>() {};
        EmployeeTaxObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeTaxObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeeTaxForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployeeTax");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployeeTax");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployeeTax");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/Tax");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches employees
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param firstName Filter by first name
   * @param lastName Filter by last name
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param accessToken Authorization token for user set in header of each request
   * @return Employees
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Employees getEmployees(
      String accessToken, String xeroTenantId, String firstName, String lastName, Integer page)
      throws IOException {
    try {
      TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
      HttpResponse response =
          getEmployeesForHttpResponse(accessToken, xeroTenantId, firstName, lastName, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployees -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<Employees> errorTypeRef = new TypeReference<Employees>() {};
        Employees object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "Employees", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeesForHttpResponse(
      String accessToken, String xeroTenantId, String firstName, String lastName, Integer page)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployees");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployees");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees");
    if (firstName != null) {
      String key = "firstName";
      Object value = firstName;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (lastName != null) {
      String key = "lastName";
      Object value = lastName;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieve a single leave type by id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param leaveTypeID Identifier for the leave type
   * @param accessToken Authorization token for user set in header of each request
   * @return LeaveTypeObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LeaveTypeObject getLeaveType(String accessToken, String xeroTenantId, UUID leaveTypeID)
      throws IOException {
    try {
      TypeReference<LeaveTypeObject> typeRef = new TypeReference<LeaveTypeObject>() {};
      HttpResponse response = getLeaveTypeForHttpResponse(accessToken, xeroTenantId, leaveTypeID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getLeaveType -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<LeaveTypeObject> errorTypeRef = new TypeReference<LeaveTypeObject>() {};
        LeaveTypeObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "LeaveTypeObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getLeaveTypeForHttpResponse(
      String accessToken, String xeroTenantId, UUID leaveTypeID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getLeaveType");
    } // verify the required parameter 'leaveTypeID' is set
    if (leaveTypeID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'leaveTypeID' when calling getLeaveType");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getLeaveType");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("LeaveTypeID", leaveTypeID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/LeaveTypes/{LeaveTypeID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches leave types
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param activeOnly Filters leave types by active status. By default the API returns all leave
   *     types.
   * @param accessToken Authorization token for user set in header of each request
   * @return LeaveTypes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LeaveTypes getLeaveTypes(
      String accessToken, String xeroTenantId, Integer page, Boolean activeOnly)
      throws IOException {
    try {
      TypeReference<LeaveTypes> typeRef = new TypeReference<LeaveTypes>() {};
      HttpResponse response =
          getLeaveTypesForHttpResponse(accessToken, xeroTenantId, page, activeOnly);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getLeaveTypes -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<LeaveTypes> errorTypeRef = new TypeReference<LeaveTypes>() {};
        LeaveTypes object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "LeaveTypes", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getLeaveTypesForHttpResponse(
      String accessToken, String xeroTenantId, Integer page, Boolean activeOnly)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getLeaveTypes");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getLeaveTypes");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/LeaveTypes");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (activeOnly != null) {
      String key = "ActiveOnly";
      Object value = activeOnly;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieve a single pay run by id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payRunID Identifier for the pay run
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRunObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRunObject getPayRun(String accessToken, String xeroTenantId, UUID payRunID)
      throws IOException {
    try {
      TypeReference<PayRunObject> typeRef = new TypeReference<PayRunObject>() {};
      HttpResponse response = getPayRunForHttpResponse(accessToken, xeroTenantId, payRunID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayRun -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PayRunObject> errorTypeRef = new TypeReference<PayRunObject>() {};
        PayRunObject object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PayRunObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPayRunForHttpResponse(
      String accessToken, String xeroTenantId, UUID payRunID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayRun");
    } // verify the required parameter 'payRunID' is set
    if (payRunID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payRunID' when calling getPayRun");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayRun");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PayRunID", payRunID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayRuns/{PayRunID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieve a single payrun calendar by id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payrollCalendarID Identifier for the payrun calendars
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRunCalendarObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRunCalendarObject getPayRunCalendar(
      String accessToken, String xeroTenantId, UUID payrollCalendarID) throws IOException {
    try {
      TypeReference<PayRunCalendarObject> typeRef = new TypeReference<PayRunCalendarObject>() {};
      HttpResponse response =
          getPayRunCalendarForHttpResponse(accessToken, xeroTenantId, payrollCalendarID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayRunCalendar -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PayRunCalendarObject> errorTypeRef =
            new TypeReference<PayRunCalendarObject>() {};
        PayRunCalendarObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PayRunCalendarObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPayRunCalendarForHttpResponse(
      String accessToken, String xeroTenantId, UUID payrollCalendarID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayRunCalendar");
    } // verify the required parameter 'payrollCalendarID' is set
    if (payrollCalendarID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payrollCalendarID' when calling getPayRunCalendar");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayRunCalendar");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PayrollCalendarID", payrollCalendarID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/PayRunCalendars/{PayrollCalendarID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches payrun calendars
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRunCalendars
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRunCalendars getPayRunCalendars(String accessToken, String xeroTenantId, Integer page)
      throws IOException {
    try {
      TypeReference<PayRunCalendars> typeRef = new TypeReference<PayRunCalendars>() {};
      HttpResponse response = getPayRunCalendarsForHttpResponse(accessToken, xeroTenantId, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayRunCalendars -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PayRunCalendars> errorTypeRef = new TypeReference<PayRunCalendars>() {};
        PayRunCalendars object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PayRunCalendars", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPayRunCalendarsForHttpResponse(
      String accessToken, String xeroTenantId, Integer page) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayRunCalendars");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayRunCalendars");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayRunCalendars");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches pay runs
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param status By default get payruns will return all the payruns for an organization. You can
   *     add GET https://api.xero.com/payroll.xro/2.0/payRuns?statu&#x3D;{PayRunStatus} to filter
   *     the payruns by status.
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRuns
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRuns getPayRuns(String accessToken, String xeroTenantId, Integer page, String status)
      throws IOException {
    try {
      TypeReference<PayRuns> typeRef = new TypeReference<PayRuns>() {};
      HttpResponse response = getPayRunsForHttpResponse(accessToken, xeroTenantId, page, status);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayRuns -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PayRuns> errorTypeRef = new TypeReference<PayRuns>() {};
        PayRuns object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PayRuns", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPayRunsForHttpResponse(
      String accessToken, String xeroTenantId, Integer page, String status) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayRuns");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayRuns");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayRuns");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (status != null) {
      String key = "status";
      Object value = status;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieve a single payslip by id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param paySlipID Identifier for the payslip
   * @param accessToken Authorization token for user set in header of each request
   * @return PaySlipObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PaySlipObject getPaySlip(String accessToken, String xeroTenantId, UUID paySlipID)
      throws IOException {
    try {
      TypeReference<PaySlipObject> typeRef = new TypeReference<PaySlipObject>() {};
      HttpResponse response = getPaySlipForHttpResponse(accessToken, xeroTenantId, paySlipID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPaySlip -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PaySlipObject> errorTypeRef = new TypeReference<PaySlipObject>() {};
        PaySlipObject object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PaySlipObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPaySlipForHttpResponse(
      String accessToken, String xeroTenantId, UUID paySlipID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPaySlip");
    } // verify the required parameter 'paySlipID' is set
    if (paySlipID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paySlipID' when calling getPaySlip");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPaySlip");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PaySlipID", paySlipID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PaySlips/{PaySlipID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches payslips
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payRunID PayrunID which specifies the containing payrun of payslips to retrieve. By
   *     default, the API does not group payslips by payrun.
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param accessToken Authorization token for user set in header of each request
   * @return PaySlips
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PaySlips getPaySlips(String accessToken, String xeroTenantId, UUID payRunID, Integer page)
      throws IOException {
    try {
      TypeReference<PaySlips> typeRef = new TypeReference<PaySlips>() {};
      HttpResponse response = getPaySlipsForHttpResponse(accessToken, xeroTenantId, payRunID, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPaySlips -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PaySlips> errorTypeRef = new TypeReference<PaySlips>() {};
        PaySlips object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PaySlips", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPaySlipsForHttpResponse(
      String accessToken, String xeroTenantId, UUID payRunID, Integer page) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPaySlips");
    } // verify the required parameter 'payRunID' is set
    if (payRunID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payRunID' when calling getPaySlips");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPaySlips");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PaySlips");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (payRunID != null) {
      String key = "PayRunID";
      Object value = payRunID;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieve a single reimbursement by id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param reimbursementID Identifier for the reimbursement
   * @param accessToken Authorization token for user set in header of each request
   * @return ReimbursementObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReimbursementObject getReimbursement(
      String accessToken, String xeroTenantId, UUID reimbursementID) throws IOException {
    try {
      TypeReference<ReimbursementObject> typeRef = new TypeReference<ReimbursementObject>() {};
      HttpResponse response =
          getReimbursementForHttpResponse(accessToken, xeroTenantId, reimbursementID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReimbursement -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<ReimbursementObject> errorTypeRef =
            new TypeReference<ReimbursementObject>() {};
        ReimbursementObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "ReimbursementObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReimbursementForHttpResponse(
      String accessToken, String xeroTenantId, UUID reimbursementID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReimbursement");
    } // verify the required parameter 'reimbursementID' is set
    if (reimbursementID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'reimbursementID' when calling getReimbursement");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReimbursement");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReimbursementID", reimbursementID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Reimbursements/{ReimbursementID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches reimbursements
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param accessToken Authorization token for user set in header of each request
   * @return Reimbursements
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Reimbursements getReimbursements(String accessToken, String xeroTenantId, Integer page)
      throws IOException {
    try {
      TypeReference<Reimbursements> typeRef = new TypeReference<Reimbursements>() {};
      HttpResponse response = getReimbursementsForHttpResponse(accessToken, xeroTenantId, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReimbursements -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<Reimbursements> errorTypeRef = new TypeReference<Reimbursements>() {};
        Reimbursements object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "Reimbursements", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReimbursementsForHttpResponse(
      String accessToken, String xeroTenantId, Integer page) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReimbursements");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReimbursements");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Reimbursements");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches settings
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accessToken Authorization token for user set in header of each request
   * @return Settings
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Settings getSettings(String accessToken, String xeroTenantId) throws IOException {
    try {
      TypeReference<Settings> typeRef = new TypeReference<Settings>() {};
      HttpResponse response = getSettingsForHttpResponse(accessToken, xeroTenantId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getSettings -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<Settings> errorTypeRef = new TypeReference<Settings>() {};
        Settings object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "Settings", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getSettingsForHttpResponse(String accessToken, String xeroTenantId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getSettings");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getSettings");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Settings");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieve a single statutory deduction by id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param id Identifier for the statutory deduction
   * @param accessToken Authorization token for user set in header of each request
   * @return StatutoryDeductionObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public StatutoryDeductionObject getStatutoryDeduction(
      String accessToken, String xeroTenantId, UUID id) throws IOException {
    try {
      TypeReference<StatutoryDeductionObject> typeRef =
          new TypeReference<StatutoryDeductionObject>() {};
      HttpResponse response = getStatutoryDeductionForHttpResponse(accessToken, xeroTenantId, id);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getStatutoryDeduction -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getStatutoryDeductionForHttpResponse(
      String accessToken, String xeroTenantId, UUID id) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getStatutoryDeduction");
    } // verify the required parameter 'id' is set
    if (id == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'id' when calling getStatutoryDeduction");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getStatutoryDeduction");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("Id", id);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/StatutoryDeductions/{Id}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches statutory deductions
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param accessToken Authorization token for user set in header of each request
   * @return StatutoryDeductions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public StatutoryDeductions getStatutoryDeductions(
      String accessToken, String xeroTenantId, Integer page) throws IOException {
    try {
      TypeReference<StatutoryDeductions> typeRef = new TypeReference<StatutoryDeductions>() {};
      HttpResponse response =
          getStatutoryDeductionsForHttpResponse(accessToken, xeroTenantId, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getStatutoryDeductions -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getStatutoryDeductionsForHttpResponse(
      String accessToken, String xeroTenantId, Integer page) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getStatutoryDeductions");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getStatutoryDeductions");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/StatutoryDeductions");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches for a unique superannuation
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param superannuationID Identifier for the superannuation
   * @param accessToken Authorization token for user set in header of each request
   * @return SuperannuationObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SuperannuationObject getSuperannuation(
      String accessToken, String xeroTenantId, UUID superannuationID) throws IOException {
    try {
      TypeReference<SuperannuationObject> typeRef = new TypeReference<SuperannuationObject>() {};
      HttpResponse response =
          getSuperannuationForHttpResponse(accessToken, xeroTenantId, superannuationID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getSuperannuation -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getSuperannuationForHttpResponse(
      String accessToken, String xeroTenantId, UUID superannuationID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getSuperannuation");
    } // verify the required parameter 'superannuationID' is set
    if (superannuationID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'superannuationID' when calling getSuperannuation");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getSuperannuation");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("SuperannuationID", superannuationID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/superannuations/{SuperannuationID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches statutory deductions
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param accessToken Authorization token for user set in header of each request
   * @return Superannuations
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Superannuations getSuperannuations(String accessToken, String xeroTenantId, Integer page)
      throws IOException {
    try {
      TypeReference<Superannuations> typeRef = new TypeReference<Superannuations>() {};
      HttpResponse response = getSuperannuationsForHttpResponse(accessToken, xeroTenantId, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getSuperannuations -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getSuperannuationsForHttpResponse(
      String accessToken, String xeroTenantId, Integer page) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getSuperannuations");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getSuperannuations");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/superannuations");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * retrieve a single timesheet by id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheetID Identifier for the timesheet
   * @param accessToken Authorization token for user set in header of each request
   * @return TimesheetObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimesheetObject getTimesheet(String accessToken, String xeroTenantId, UUID timesheetID)
      throws IOException {
    try {
      TypeReference<TimesheetObject> typeRef = new TypeReference<TimesheetObject>() {};
      HttpResponse response = getTimesheetForHttpResponse(accessToken, xeroTenantId, timesheetID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTimesheet -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<TimesheetObject> errorTypeRef = new TypeReference<TimesheetObject>() {};
        TimesheetObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "TimesheetObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTimesheetForHttpResponse(
      String accessToken, String xeroTenantId, UUID timesheetID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTimesheet");
    } // verify the required parameter 'timesheetID' is set
    if (timesheetID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetID' when calling getTimesheet");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTimesheet");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TimesheetID", timesheetID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets/{TimesheetID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches timesheets
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 100.
   * @param employeeId By default get Timesheets will return the timesheets for all employees in an
   *     organization. You can add GET
   *     https://…/timesheets?filter&#x3D;employeeId&#x3D;&#x3D;{EmployeeId} to get only the
   *     timesheets of a particular employee.
   * @param payrollCalendarId By default get Timesheets will return all the timesheets for an
   *     organization. You can add GET
   *     https://…/timesheets?filter&#x3D;payrollCalendarId&#x3D;&#x3D;{PayrollCalendarID} to filter
   *     the timesheets by payroll calendar id
   * @param accessToken Authorization token for user set in header of each request
   * @return Timesheets
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Timesheets getTimesheets(
      String accessToken,
      String xeroTenantId,
      Integer page,
      UUID employeeId,
      UUID payrollCalendarId)
      throws IOException {
    try {
      TypeReference<Timesheets> typeRef = new TypeReference<Timesheets>() {};
      HttpResponse response =
          getTimesheetsForHttpResponse(
              accessToken, xeroTenantId, page, employeeId, payrollCalendarId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTimesheets -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<Timesheets> errorTypeRef = new TypeReference<Timesheets>() {};
        Timesheets object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "Timesheets", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTimesheetsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      Integer page,
      UUID employeeId,
      UUID payrollCalendarId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTimesheets");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTimesheets");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (employeeId != null) {
      String key = "employeeId";
      Object value = employeeId;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (payrollCalendarId != null) {
      String key = "payrollCalendarId";
      Object value = payrollCalendarId;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * searches tracking categories
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accessToken Authorization token for user set in header of each request
   * @return TrackingCategories
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TrackingCategories getTrackingCategories(String accessToken, String xeroTenantId)
      throws IOException {
    try {
      TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
      HttpResponse response = getTrackingCategoriesForHttpResponse(accessToken, xeroTenantId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTrackingCategories -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<TrackingCategories> errorTypeRef = new TypeReference<TrackingCategories>() {};
        TrackingCategories object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "TrackingCategories", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTrackingCategoriesForHttpResponse(String accessToken, String xeroTenantId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTrackingCategories");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTrackingCategories");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/settings/trackingCategories");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * revert a timesheet to draft
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheetID Identifier for the timesheet
   * @param accessToken Authorization token for user set in header of each request
   * @return TimesheetObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimesheetObject revertTimesheet(String accessToken, String xeroTenantId, UUID timesheetID)
      throws IOException {
    try {
      TypeReference<TimesheetObject> typeRef = new TypeReference<TimesheetObject>() {};
      HttpResponse response =
          revertTimesheetForHttpResponse(accessToken, xeroTenantId, timesheetID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : revertTimesheet -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<TimesheetObject> errorTypeRef = new TypeReference<TimesheetObject>() {};
        TimesheetObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "TimesheetObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse revertTimesheetForHttpResponse(
      String accessToken, String xeroTenantId, UUID timesheetID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling revertTimesheet");
    } // verify the required parameter 'timesheetID' is set
    if (timesheetID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetID' when calling revertTimesheet");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling revertTimesheet");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TimesheetID", timesheetID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets/{TimesheetID}/RevertToDraft");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * updates employee
   *
   * <p><b>200</b> - successful response
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param employee The employee parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeObject updateEmployee(
      String accessToken, String xeroTenantId, UUID employeeId, Employee employee)
      throws IOException {
    try {
      TypeReference<EmployeeObject> typeRef = new TypeReference<EmployeeObject>() {};
      HttpResponse response =
          updateEmployeeForHttpResponse(accessToken, xeroTenantId, employeeId, employee);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateEmployee -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeObject> errorTypeRef = new TypeReference<EmployeeObject>() {};
        EmployeeObject object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateEmployeeForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, Employee employee)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateEmployee");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling updateEmployee");
    } // verify the required parameter 'employee' is set
    if (employee == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employee' when calling updateEmployee");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateEmployee");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employee);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * updates employee earnings template records
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param payTemplateEarningID Id for single pay template earnings object
   * @param earningsTemplate The earningsTemplate parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EarningsTemplateObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EarningsTemplateObject updateEmployeeEarningsTemplate(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      UUID payTemplateEarningID,
      EarningsTemplate earningsTemplate)
      throws IOException {
    try {
      TypeReference<EarningsTemplateObject> typeRef =
          new TypeReference<EarningsTemplateObject>() {};
      HttpResponse response =
          updateEmployeeEarningsTemplateForHttpResponse(
              accessToken, xeroTenantId, employeeId, payTemplateEarningID, earningsTemplate);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateEmployeeEarningsTemplate -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EarningsTemplateObject> errorTypeRef =
            new TypeReference<EarningsTemplateObject>() {};
        EarningsTemplateObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(
            e.getStatusCode(), "EarningsTemplateObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateEmployeeEarningsTemplateForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      UUID payTemplateEarningID,
      EarningsTemplate earningsTemplate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateEmployeeEarningsTemplate");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling"
              + " updateEmployeeEarningsTemplate");
    } // verify the required parameter 'payTemplateEarningID' is set
    if (payTemplateEarningID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payTemplateEarningID' when calling"
              + " updateEmployeeEarningsTemplate");
    } // verify the required parameter 'earningsTemplate' is set
    if (earningsTemplate == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'earningsTemplate' when calling"
              + " updateEmployeeEarningsTemplate");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateEmployeeEarningsTemplate");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);
    uriVariables.put("PayTemplateEarningID", payTemplateEarningID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/Employees/{EmployeeId}/PayTemplates/earnings/{PayTemplateEarningID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(earningsTemplate);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * updates employee leave records
   *
   * <p><b>200</b> - successful response
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param leaveID Leave id for single object
   * @param employeeLeave The employeeLeave parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeLeaveObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeLeaveObject updateEmployeeLeave(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      UUID leaveID,
      EmployeeLeave employeeLeave)
      throws IOException {
    try {
      TypeReference<EmployeeLeaveObject> typeRef = new TypeReference<EmployeeLeaveObject>() {};
      HttpResponse response =
          updateEmployeeLeaveForHttpResponse(
              accessToken, xeroTenantId, employeeId, leaveID, employeeLeave);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateEmployeeLeave -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeLeaveObject> errorTypeRef =
            new TypeReference<EmployeeLeaveObject>() {};
        EmployeeLeaveObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeLeaveObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateEmployeeLeaveForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      UUID leaveID,
      EmployeeLeave employeeLeave)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateEmployeeLeave");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling updateEmployeeLeave");
    } // verify the required parameter 'leaveID' is set
    if (leaveID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'leaveID' when calling updateEmployeeLeave");
    } // verify the required parameter 'employeeLeave' is set
    if (employeeLeave == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeLeave' when calling updateEmployeeLeave");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateEmployeeLeave");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);
    uriVariables.put("LeaveID", leaveID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/Leave/{LeaveID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employeeLeave);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * updates employee salary and wages record
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param salaryAndWagesID Id for single pay template earnings object
   * @param salaryAndWage The salaryAndWage parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return SalaryAndWageObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SalaryAndWageObject updateEmployeeSalaryAndWage(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      UUID salaryAndWagesID,
      SalaryAndWage salaryAndWage)
      throws IOException {
    try {
      TypeReference<SalaryAndWageObject> typeRef = new TypeReference<SalaryAndWageObject>() {};
      HttpResponse response =
          updateEmployeeSalaryAndWageForHttpResponse(
              accessToken, xeroTenantId, employeeId, salaryAndWagesID, salaryAndWage);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateEmployeeSalaryAndWage -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<SalaryAndWageObject> errorTypeRef =
            new TypeReference<SalaryAndWageObject>() {};
        SalaryAndWageObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "SalaryAndWageObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateEmployeeSalaryAndWageForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID employeeId,
      UUID salaryAndWagesID,
      SalaryAndWage salaryAndWage)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateEmployeeSalaryAndWage");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling updateEmployeeSalaryAndWage");
    } // verify the required parameter 'salaryAndWagesID' is set
    if (salaryAndWagesID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'salaryAndWagesID' when calling"
              + " updateEmployeeSalaryAndWage");
    } // verify the required parameter 'salaryAndWage' is set
    if (salaryAndWage == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'salaryAndWage' when calling"
              + " updateEmployeeSalaryAndWage");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateEmployeeSalaryAndWage");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);
    uriVariables.put("SalaryAndWagesID", salaryAndWagesID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Employees/{EmployeeId}/SalaryAndWages/{SalaryAndWagesID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(salaryAndWage);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * updates the tax records for an employee
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param employeeTax The employeeTax parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return EmployeeTaxObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public EmployeeTaxObject updateEmployeeTax(
      String accessToken, String xeroTenantId, UUID employeeId, EmployeeTax employeeTax)
      throws IOException {
    try {
      TypeReference<EmployeeTaxObject> typeRef = new TypeReference<EmployeeTaxObject>() {};
      HttpResponse response =
          updateEmployeeTaxForHttpResponse(accessToken, xeroTenantId, employeeId, employeeTax);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateEmployeeTax -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<EmployeeTaxObject> errorTypeRef = new TypeReference<EmployeeTaxObject>() {};
        EmployeeTaxObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "EmployeeTaxObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateEmployeeTaxForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, EmployeeTax employeeTax)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateEmployeeTax");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling updateEmployeeTax");
    } // verify the required parameter 'employeeTax' is set
    if (employeeTax == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeTax' when calling updateEmployeeTax");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateEmployeeTax");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}/Tax");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employeeTax);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * update a pay run
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payRunID Identifier for the pay run
   * @param payRun The payRun parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRunObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRunObject updatePayRun(
      String accessToken, String xeroTenantId, UUID payRunID, PayRun payRun) throws IOException {
    try {
      TypeReference<PayRunObject> typeRef = new TypeReference<PayRunObject>() {};
      HttpResponse response =
          updatePayRunForHttpResponse(accessToken, xeroTenantId, payRunID, payRun);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updatePayRun -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PayRunObject> errorTypeRef = new TypeReference<PayRunObject>() {};
        PayRunObject object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PayRunObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updatePayRunForHttpResponse(
      String accessToken, String xeroTenantId, UUID payRunID, PayRun payRun) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updatePayRun");
    } // verify the required parameter 'payRunID' is set
    if (payRunID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payRunID' when calling updatePayRun");
    } // verify the required parameter 'payRun' is set
    if (payRun == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payRun' when calling updatePayRun");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updatePayRun");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PayRunID", payRunID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayRuns/{PayRunID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(payRun);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * creates employee pay slip
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param paySlipID Identifier for the payslip
   * @param paySlip The paySlip parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return PaySlipObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PaySlipObject updatePaySlipLineItems(
      String accessToken, String xeroTenantId, UUID paySlipID, PaySlip paySlip) throws IOException {
    try {
      TypeReference<PaySlipObject> typeRef = new TypeReference<PaySlipObject>() {};
      HttpResponse response =
          updatePaySlipLineItemsForHttpResponse(accessToken, xeroTenantId, paySlipID, paySlip);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updatePaySlipLineItems -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PaySlipObject> errorTypeRef = new TypeReference<PaySlipObject>() {};
        PaySlipObject object = apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "PaySlipObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updatePaySlipLineItemsForHttpResponse(
      String accessToken, String xeroTenantId, UUID paySlipID, PaySlip paySlip) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updatePaySlipLineItems");
    } // verify the required parameter 'paySlipID' is set
    if (paySlipID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paySlipID' when calling updatePaySlipLineItems");
    } // verify the required parameter 'paySlip' is set
    if (paySlip == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paySlip' when calling updatePaySlipLineItems");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updatePaySlipLineItems");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PaySlipID", paySlipID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PaySlips/{PaySlipID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(paySlip);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * update a timesheet line
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheetID Identifier for the timesheet
   * @param timesheetLineID Identifier for the timesheet line
   * @param timesheetLine The timesheetLine parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return TimesheetLineObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimesheetLineObject updateTimesheetLine(
      String accessToken,
      String xeroTenantId,
      UUID timesheetID,
      UUID timesheetLineID,
      TimesheetLine timesheetLine)
      throws IOException {
    try {
      TypeReference<TimesheetLineObject> typeRef = new TypeReference<TimesheetLineObject>() {};
      HttpResponse response =
          updateTimesheetLineForHttpResponse(
              accessToken, xeroTenantId, timesheetID, timesheetLineID, timesheetLine);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateTimesheetLine -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<TimesheetLineObject> errorTypeRef =
            new TypeReference<TimesheetLineObject>() {};
        TimesheetLineObject object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError(e.getStatusCode(), "TimesheetLineObject", object.getProblem(), e);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateTimesheetLineForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID timesheetID,
      UUID timesheetLineID,
      TimesheetLine timesheetLine)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateTimesheetLine");
    } // verify the required parameter 'timesheetID' is set
    if (timesheetID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetID' when calling updateTimesheetLine");
    } // verify the required parameter 'timesheetLineID' is set
    if (timesheetLineID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetLineID' when calling updateTimesheetLine");
    } // verify the required parameter 'timesheetLine' is set
    if (timesheetLine == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetLine' when calling updateTimesheetLine");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateTimesheetLine");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TimesheetID", timesheetID);
    uriVariables.put("TimesheetLineID", timesheetLineID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Timesheets/{TimesheetID}/Lines/{TimesheetLineID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(timesheetLine);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  public ByteArrayInputStream convertInputToByteArray(InputStream is) throws IOException {
    byte[] bytes = IOUtils.toByteArray(is);
    try {
      // Process the input stream..
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
      return byteArrayInputStream;
    } finally {
      is.close();
    }
  }
}