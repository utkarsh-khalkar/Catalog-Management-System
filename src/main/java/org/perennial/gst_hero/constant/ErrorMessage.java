package org.perennial.gst_hero.constant;

/**
 * Author: Utkarsh Khalkar
 * Title: ErrorMessage - Interface defining constant error messages and error codes.
 * Date: 02-04-2025
 * Time: 08:53 AM
 */
public interface ErrorMessage {
    String CATEGORY_NOT_FOUND = "Category Not Found";
    String CATEGORY_ALREADY_EXIST = "Category Already Exist";
    String USER_ALREADY_EXIST = "User Already Exist";
    String USER_NOT_FOUND = "User Not Found";
    String SALES_REPORT_ALREADY_GENERATED = "Sales Report Already Generated";
    String PURCHASE_REPORT_ALREADY_GENERATED = "Purchase Report Already Generated";
    String PAST_FINANCIAL_REQUEST = "Past Financial Year Records cannot be Updated";
    String SALES_RECORD_NOT_FOUND = "Sales Record Not Found";
    int ERROR_STATUS = 0;
    String PURCHASE_REQUEST_NOT_FOUND = "Purchase Record Not Found";

}
