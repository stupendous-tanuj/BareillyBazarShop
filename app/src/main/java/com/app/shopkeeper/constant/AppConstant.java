package com.app.shopkeeper.constant;

/**
 * Created by sonia on 18/8/15.
 */
public class AppConstant {


    public static final String APPLICATION_ID = "ANDSH1001";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String GCM_ID = "389845974615";


    public interface BUNDLE_KEY {
        String IS_ADD_ASSOCIATE_PRODUCT_HOME = "IS_ADD_ASSOCIATE_PRODUCT_HOME";
        String SKU_ID = "skuid";
        String ENGLISH_NAME = "english_name";
        String HINDI_NAME = "HINDI_NAME";
        String DES = "DES";
        String UNIT = "UNIT";
        String PRICE = "price";
        String OFFER_PRICE = "OFFER_PRICE";
        String IMAGE = "image";
        String ORDER_ID = "ORDER_ID";
        String ORDER_STATUS = "ORDER_STATUS";
    }

    public interface PreferenceKeeperNames {
        String LOGIN = "user_login";
        String FLAT_NUMBER = "flat_number";
        String PINCODE = "pincode";
        String STATE = "state";
        String CITY = "city";
        String LOCALITY = "locality";
        String AREA = "area";

    }

    public interface RequestCodes {
        int UPDATE_LOCATION = 1;
    }

    public interface ResponseExtra {

    }
}
