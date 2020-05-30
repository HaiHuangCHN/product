package com.product.costant;

public class Constants {

    public static final String SOURCE = "PD_APP";
    public static final String UNEXPECTED_ERROR = "unexpected error";
    public static final String OK = "All is well";
    public static final String HEADER_NAME = "header name";
    public static final String HEADER_DESC = "header desc";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String INVALID_RETENTION_PERIOD = "Invalid retention period, please check";
//    public static final String INVALID_RETENTION_PERIOD_FORMAT = "Invalid retention period format, should be like 1-2-3 and not null";
//    public static final String RETENTION_PERIOD_REGX = "^(\\d+)-(\\d+)-(\\d+)$";
    public static final String INVALID_RETENTION_PERIOD_VALUE = "Invalid retention period value. Should be number and not null, please check config";
    public static final String RETENTION_PERIOD_REGX = "^(\\d+)$";
    public static final String CONFIG = "config";

    private Constants() {
    }

}
