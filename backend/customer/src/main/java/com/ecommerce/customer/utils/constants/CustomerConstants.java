package com.ecommerce.customer.utils.constants;

public class CustomerConstants {
  public static class Service {
    public static final String CUSTOMER_NOT_FOUND = "Customer %s not found!";
    public static final String CUSTOMER_EMAIL_ALREADY_EXIST = "The email %s already exists!";
    public static final String CUSTOMER_USERNAME_ALREADY_EXIST = "The username %s already exists!";
    public static final String LOG_ERROR_INSERT_DUPLICATED_EMAIL = "Insert customer: Email %s duplicated";
    public static final String LOG_ERROR_UPDATE_DUPLICATED_EMAIL = "Update customer: Email %s duplicated";
    public static final String LOG_ERROR_INSERT_DUPLICATED_USERNAME = "Insert customer: Username %s duplicated";
    public static final String LOG_ERROR_UPDATE_DUPLICATED_USERNAME = "Update customer: Username %s duplicated";
    public static final String NO_DATA_CHANGES_FOUND = "No data changes found";
  }
}
