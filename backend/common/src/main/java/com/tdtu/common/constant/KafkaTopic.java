package com.tdtu.common.constant;

public class KafkaTopic {
    public static final String CREATE_ACCOUNT = "onboard-success";
    public static final String VERIFY_ACCOUNT = "verify-account";

    public static final String CREATE_ORDER = "create-order";
    public static final String UPDATE_ORDER = "update-order";

    public static final String CREATE_PAYMENT = "create-payment";
    public static final String UPDATE_PAYMENT = "update-payment";

    public static final String CREATE_SHIPMENT = "create-shipment";


    private KafkaTopic() {

    }
}