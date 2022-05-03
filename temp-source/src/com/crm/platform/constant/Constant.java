package com.crm.platform.constant;

/**
 * 
 *
 */
public interface Constant {

    // Login
    public static final String LOGIN_ERROR_CODE_100001 = "100001";
    public static final String LOGIN_ERROR_MESSAGE_USERERROR = "LOGIN_ERROR_MESSAGE_USERERROR";
    public static final String LOGIN_ERROR_CODE_100002 = "100002";
    public static final String LOGIN_ERROR_MESSAGE_SYSTEMERROR = "LOGIN_ERROR_MESSAGE_SYSTEMERROR";
    public static final String LOGIN_ERROR_CODE_100003 = "100003";
    public static final String LOGIN_ERROR_MESSAGE_MAXERROR = "LOGIN_ERROR_MESSAGE_MAXERROR";
    public static final String LOGIN_ERROR_CODE_100005 = "100005";
    public static final String LOGIN_ERROR_MESSAGE_UNREVIEW = "LOGIN_ERROR_MESSAGE_UNREVIEW";
    public static final String LOGIN_ERROR_CODE_100006 = "100006";
    public static final String LOGIN_ERROR_MESSAGE_REVIEW_FAIL = "LOGIN_ERROR_MESSAGE_REVIEW_FAIL";

    // Registered
    public static final String REGISTERED_SUCCESS_MESSAGE = "REGISTERED_SUCCESS_MESSAGE";
    public static final String REGISTERED_ERROR_MESSAGE = "REGISTERED_ERROR_MESSAGE";
    public static final String REGISTERED_ERROR_MESSAGE_ACCOUNT = "REGISTERED_ERROR_MESSAGE_ACCOUNT";
    public static final String REGISTERED_ERROR_MESSAGE_UPLOAD = "REGISTERED_ERROR_MESSAGE_UPLOAD";
    public static final String REGISTERED_ERROR_MESSAGE_TRACTYPE = "REGISTERED_ERROR_MESSAGE_TRACTYPE";

    // User review
    public static final String TRANSACTION_TYPE_ERROR = "TRANSACTION_TYPE_ERROR";
    public static final String LEVEL_MORE = "LEVEL_MORE";
    public static final String LEVEL_LESS = "LEVEL_LESS";
    public static final String MT4_REGISTERED_FAIL = "MT4_REGISTERED_FAIL";
    public static final String MT4_REGISTERED_FAIL_SEVEN = "MT4_REGISTERED_FAIL_SEVEN";
    public static final String MT4_REGISTERED_FAIL_EIGHT = "MT4_REGISTERED_FAIL_EIGHT";
    public static final String MT4_REGISTERED_FAIL_TEN = "MT4_REGISTERED_FAIL_TEN";

    // Deposit & Withdrawal
    public static final String IN_GOLD_FAIL = "IN_GOLD_FAIL";
    public static final String IN_GOLD_REVIEWED = "IN_GOLD_REVIEWED";
    public static final String OUT_GOLD_FAIL = "OUT_GOLD_FAIL";
    public static final String IN_GOLD = "Deposit";
    public static final String OUT_GOLD = "Withdrawal";
    public static final String MANUAL_REBATE = "manual";
    public static final String FINISH_CARD_INFO = "FINISH_CARD_INFO";
    public static final String WITHDRAW_MORE_THAN_BALANCE = "WITHDRAW_MORE_THAN_BALANCE";

    // Profile
    public static final String FILE_SMALL = "FILE_SMALL";
    public static final String UPLOAD_SUCCESS = "UPLOAD_SUCCESS";

    // Common
    public static final String SUCCESS_SAVE = "SUCCESS_SAVE";
    public static final String SUCCESS_DELETE = "SUCCESS_DELETE";
    public static final String SYSTEM_EXCEPTION = "SYSTEM_EXCEPTION";

    // Rebate
    public static final String VIOLATION_OF_REBATE_RULES = "VIOLATION_OF_REBATE_RULES";
    public static final String VALUE_EXCEEDS_THE_LIMIT = "VALUE_EXCEEDS_THE_LIMIT";
    public static final String VALUE_LESS_THE_LIMIT = "VALUE_LESS_THE_LIMIT";
    public static final String PLEASE_CHOOSE_UNDERLING = "PLEASE_CHOOSE_UNDERLING";
    public static final String VIOLATION_OF_UNDERLING_REBATE = "VIOLATION_OF_UNDERLING_REBATE";
    public static final String INSUFFICIENT_BALANCE = "INSUFFICIENT_BALANCE";
    public static final String REBATE_LOGIN_NULL = "REBATE_LOGIN_NULL";

    // Reset pass
    public static final String ACCOUNT_EMPTY = "ACCOUNT_EMPTY";
    public static final String ACCOUNT_NOT_EXIST = "ACCOUNT_NOT_EXIST";
    public static final String PASSWORD_EMPTY = "PASSWORD_EMPTY";
    public static final String LINK_ADDR_INCORRECT = "LINK_ADDR_INCORRECT";
    public static final String LINK_OUTTIME = "LINK_OUTTIME";
    public static final String RESET_SUCCESS = "RESET_SUCCESS";

    public static final String RESET_PASS_URL = "http://mt5.mmigasia.com/crmf/reset/page";
    // public static final String RESET_PASS_URL =
    // "http://47.52.36.159:8080/crmf/reset/page";
    public static final String EMAIL_HAS_BEEN_SENT = "EMAIL_HAS_BEEN_SENT";

    // system Setting Error
    public static final String SYSTEM_NOT_SETTING = "SYSTEM_NOT_SETTING";
    public static final String INFO_INCOMPLETE = "INFO_INCOMPLETE";

    public static final String[] DATE_PATTERNS = { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd",
            "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

}
