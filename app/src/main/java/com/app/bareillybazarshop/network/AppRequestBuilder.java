package com.app.bareillybazarshop.network;

import com.app.bareillybazarshop.activity.HomeActivity;
import com.app.bareillybazarshop.api.output.AssociatedProductResponse;
import com.app.bareillybazarshop.api.output.AssociatedShopIdResponse;
import com.app.bareillybazarshop.api.output.AssociatedShopsResponse;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.DeliveryPersonResponse;
import com.app.bareillybazarshop.api.output.LoginResponse;
import com.app.bareillybazarshop.api.output.MyOrderDetailResponse;
import com.app.bareillybazarshop.api.output.OrderUnitResponse;
import com.app.bareillybazarshop.api.output.ProductCategoryResponse;
import com.app.bareillybazarshop.api.output.ProductResponse;
import com.app.bareillybazarshop.api.output.RecentOrderResponse;
import com.app.bareillybazarshop.api.output.SellerHubProfileResponse;
import com.app.bareillybazarshop.api.output.ShopCategoryResponse;
import com.app.bareillybazarshop.api.output.ShopProfile;
import com.app.bareillybazarshop.api.output.ShopProfileResponse;
import com.app.bareillybazarshop.api.output.ShopReferenceDataResponse;
import com.app.bareillybazarshop.api.output.SupportedIdTypeResponse;
import com.app.bareillybazarshop.api.output.ViewAvailableProductResponse;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.utils.Logger;
import com.app.bareillybazarshop.utils.PreferenceKeeper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * AppRequestBuilder : All api access code
 */
public class AppRequestBuilder {

    public static String TAG = HomeActivity.class.getSimpleName();
    private static final String BASE_URL = "http://shopthefortune.com/shopthefortune/api/bareillybazar/test";
    //private static final String BASE_URL = "http://stupendoustanuj.co.nf/ShopTheFortune";
    private static final String ROOT_URL = "http://stupendoustanuj.co.nf/Dabbawala";
    public static String USER_TYPE = "";
    public static String USER_ID = "";

    // http://stupendoustanuj.co.nf/ShopTheFortune/ Verify_Mobile_Number.php

    static{
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        USER_ID = PreferenceKeeper.getInstance().getUserId();
    }

    private static void setUserHeader(Map<String, String> map) {
        map.put("applicationId", AppConstant.APPLICATION_ID);
        USER_ID = PreferenceKeeper.getInstance().getUserId();
        if((USER_ID.equals("")) || (USER_ID == null) || (USER_ID.equals("Admin"))) {
            map.put("userId", AppConstant.UserType.ADMIN_TYPE);
        }
        else {
            map.put("userId", USER_ID);
        }
        map.put("locale", PreferenceKeeper.getInstance().getLocale());
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
    }

    private static String setRequestBody(Map<String, String> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map, LinkedHashMap.class);
        Logger.INFO("REQUEST", "JSON : " + json);
        return json;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/User_Login.php\
    public static AppHttpRequest loginAPI(String userId, String userType, String password, AppResponseListener<LoginResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/User_Login.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", userType);
        map.put("userId", userId);
        map.put("password", password);
        //TODO IP Address to be put here
        map.put("IPAddress", "12.33.534.45");
        //TODO Device ID to be put here
        //map.put("deviceId", PreferenceKeeper.getInstance().getDeviceInfo());
        map.put("deviceId", "Test");

        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest addAShopAPI(ShopProfile shop, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Add_A_Shop.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopName", shop.getShopName());
        map.put("shopRegistrationStatus", shop.getShopRegistrationStatus());
        map.put("shopOwnerName", shop.getShopOwnerName());
        map.put("shopDescription", shop.getShopDescription());
        map.put("shopAddress", shop.getShopAddress());
        map.put("shopAddressAreaSector", shop.getShopAddressAreaSector());
        map.put("shopAddressCity", shop.getShopAddressCity());
        map.put("shopAddressPincode", shop.getShopAddressPincode());
        map.put("shopAddressState", shop.getShopAddressState());
        map.put("shopAddressCountry", shop.getShopAddressCountry());
        map.put("shopAddressLandmark", shop.getShopAddressLandmark());
        map.put("shopOwnerContactNumber", shop.getShopOwnerContactNumber());
        map.put("shopSupportContactNumber", shop.getShopSupportContactNumber());
        map.put("shopOrderProcessingContactNumber", shop.getShopOrderProcessingContactNumber());
        map.put("shopEmailId", shop.getShopEmailId());
        map.put("shopMinimumAcceptedOrder", shop.getShopMinimumAcceptedOrder());
        map.put("shopDeliveryCharges", shop.getShopDeliveryCharges());
        map.put("shopOwnerIDType", shop.getShopOwnerIDType());
        map.put("shopOwnerIDNumber", shop.getShopOwnerIDNumber());
        map.put("shopDeliveryTypeSupported", shop.getShopDeliveryTypeSupported());
        map.put("shopPaymentMethodSupported", shop.getShopPaymentMethodSupported());
        request.addParam("input", setRequestBody(map));
        return request;
    }


    public static AppHttpRequest changePasswordAPI(String oldPassword,String newPassword, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Change_Password.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", USER_TYPE);
        map.put("oldPassword", oldPassword);
        map.put("newPassword", newPassword);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest VerifyApplicationIDAPI(AppResponseListener<CommonResponse> appResponseListener) {

        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Verify_ApplicationID.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", USER_TYPE);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest updateSellerHubProfileAPI(String mobileNumber, String supportNumber,
                                                           String emailId, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_SellerHub_Profile.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("sellerHubMobileNumber", mobileNumber);
        map.put("sellerHubSupportContactNumber", supportNumber);
        map.put("sellerHubEmailID", emailId);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest associatedShopsAPI(String onlyShopId,AppResponseListener<AssociatedShopsResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Associated_Shops.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopCategory", "ALL");
        map.put("productCategory", "ALL");
        map.put("deliveryLocation", "ALL");
        map.put("onlyShopId", onlyShopId);
        map.put("userType", USER_TYPE);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest shopReferenceDataAPI(AppResponseListener<ShopReferenceDataResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Shop_Reference_Data.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest fetchDeliveryPersonProfileAPI(String deliveryPersonMobileNumber,AppResponseListener<DeliveryPersonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_DeliveryPerson_Profile.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("deliveryPersonMobileNumber", deliveryPersonMobileNumber);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_All_Orders.php

    public static AppHttpRequest fetchMyOrderDetailAPI(String fromDate, String toDate, AppResponseListener<MyOrderDetailResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_All_Orders.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", USER_TYPE);
        map.put("fromDate", fromDate);
        map.put("toDate", toDate);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest associatedShopId(AppResponseListener<AssociatedShopIdResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Associated_Shops.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopCategory", "ALL");
        map.put("productCategory", "ALL");
        map.put("deliveryLocation", "ALL");
        map.put("onlyShopId", "Y");
        map.put("userType", USER_TYPE);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/ShopTheFortune/ Fetch_Order_Details.php

    public static AppHttpRequest fetchRecentTracOrderAPI(String orderId, String orderStatus, AppResponseListener<RecentOrderResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Order_Details.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("orderId", orderId);
        map.put("orderStatus", orderStatus);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Shop_Profile.php
    public static AppHttpRequest fetchShopKeeperProfileApi(String shopId, AppResponseListener<ShopProfileResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Shop_Profile.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest resetPasswordAPI(String emailId,String mobileNumber, String userId, String userType, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Forget_Password.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("applicationId", AppConstant.APPLICATION_ID);
        map.put("userId", userId);
        map.put("userType", userType);
        map.put("emailId", emailId);
        map.put("mobileNumber", mobileNumber);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    //http://stupendoustanuj.co.nf/ShopTheFortune/Update_Shop_Profile.php
    public static AppHttpRequest updateShopKeeperProfileApi(String shopId, String shopName, String shopAddress,
                                                            String city, String pincode, String state,
                                                            String landmark, String closingTime, String openingTime, String ownerNumber,
                                                            String supportNumber, String procesingNumber,
                                                            String emailId, String minimumAccOrder, String deliveryCharge,
                                                            String deliveryType, String deliveryMethod, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_Shop_Profile.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("shopName", shopName);
        map.put("shopAddress", shopAddress);
        map.put("ShopAddressCity", city);
        map.put("shopAddressPincode", pincode);
        map.put("shopAddressState", state);
        map.put("shopAddressCountry", "India");
        map.put("shopAddressLandmark", landmark);
        map.put("shopClosingTime", closingTime);
        map.put("shopOpeningTime", openingTime);
        map.put("shopOwnerContactNumber", ownerNumber);
        map.put("shopSupportContactNumber", supportNumber);
        map.put("shopOrderProcessingContactNumber", procesingNumber);
        map.put("shopEmailId", emailId);
        map.put("shopMinimumAcceptedOrder", minimumAccOrder);
        map.put("shopDeliveryCharges", deliveryCharge);
        map.put("shopDeliveryTypeSupported", deliveryType);
        map.put("shopPaymentMethodSupported", deliveryMethod);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_SellerHub_Profile.php

    public static AppHttpRequest fetchSellerHubProfileAPI(AppResponseListener<SellerHubProfileResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_SellerHub_Profile.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    /// http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Associated_Products.php

    public static AppHttpRequest fetchAssociatedProductListAPI(String shopId, AppResponseListener<AssociatedProductResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Associated_Products.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Deassociate_A_Product.php
    public static AppHttpRequest deassociatedProductAPI(String shopId, String productSKUID, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Deassociate_A_Product.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("productSKUID", productSKUID);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    ///Fetch_All_Shop_Categories
    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_All_Shop_Categories.php

    public static AppHttpRequest fetchAllShopCategoryApi(AppResponseListener<ShopCategoryResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_All_Shop_Categories.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_All_Product_Categories.php
    public static AppHttpRequest fetchAllProductCategoryApi(String shopcategoryName, AppResponseListener<ProductCategoryResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_All_Product_Categories.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopCategoryName", shopcategoryName);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest fetchAvailableProductApi(String shopcategoryName, String productCategoryName, AppResponseListener<ProductResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Available_Products.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", "SP1001");
        map.put("shopCategoryName", shopcategoryName);
        map.put("productCategoryName", productCategoryName);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    /// http://stupendoustanuj.co.nf/ShopTheFortune/Associate_A_Product.php

    public static AppHttpRequest addAssociatedProductAPI(String shopId, String productSKUID, String productAvailability, String price, String offerPrce, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Associate_A_Product.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("productSKUID", productSKUID);
        map.put("productAvailability", productAvailability);
        map.put("productPrice", price);
        map.put("productOfferedPrice", offerPrce);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    /// http://stupendoustanuj.co.nf/ShopTheFortune/Associate_A_Product.php

    public static AppHttpRequest updateAssociatedProductAPI(String shopId, String productSKUID, String productAvailability, String price, String offerPrce, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Associate_A_Product.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("productSKUID", productSKUID);
        map.put("productAvailability", productAvailability);
        map.put("productPrice", price);
        map.put("productOfferedPrice", offerPrce);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Available_Products.php
    public static AppHttpRequest viewAvailableProductAPI(String shopId, String shopCategoryName, String productCategoryName, AppResponseListener<ViewAvailableProductResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Available_Products.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("shopCategoryName", shopCategoryName);
        map.put("productCategoryName", productCategoryName);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Add_A_Product.php
    public static AppHttpRequest addAProductAPI(String productNameEnglish, String productNameHindi, String productDescription,
                                                String productCategoryName, String shopCategoryName, String productOrderUnit, String productPriceForUnits,
                                                AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Add_A_Product.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("productNameEnglish", productNameEnglish);
        map.put("productNameHindi", productNameHindi);
        map.put("productDescription", productDescription);
        map.put("productCategoryName", productCategoryName);
        map.put("shopCategoryName", shopCategoryName);
        map.put("productOrderUnit", productOrderUnit);
        map.put("productPriceForUnits", productPriceForUnits);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Associated_DeliveryPersons.php
    public static AppHttpRequest associateDeliveryPersonAPI(String shopIdORSellerHubId,
                                                            AppResponseListener<DeliveryPersonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Associated_DeliveryPersons.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopIdORSellerHubId", shopIdORSellerHubId);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Deassociate_A_DeliveryPerson.php
    public static AppHttpRequest deAssociateDeliveryPersonAPI(String shopIdORSellerHubId, String deliveryPersonId,
                                                              AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Deassociate_A_DeliveryPerson.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopIdORSellerHubId", shopIdORSellerHubId);
        map.put("deliveryPersonId", deliveryPersonId);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    //  http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_SupportedID_Types.php

    public static AppHttpRequest supportedPersonIdTypeApi(AppResponseListener<SupportedIdTypeResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_SupportedID_Types.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/ShopTheFortune/Add_A_DeliveryPerson.php
    public static AppHttpRequest addAssociateDeliveryPersonAPI(String deliveryPersonName,
                                                               String deliveryPersonMobileNumber,
                                                               String deliveryPersonResidentialAddress,
                                                               String deliveryPersonIDType,
                                                               String deliveryPersonIDNumber,
                                                               AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Add_A_DeliveryPerson.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("deliveryPersonName", deliveryPersonName);
        map.put("deliveryPersonMobileNumber", deliveryPersonMobileNumber);
        map.put("deliveryPersonResidentialAddress", deliveryPersonResidentialAddress);
        map.put("deliveryPersonIDType", deliveryPersonIDType);
        map.put("deliveryPersonIDNumber", deliveryPersonIDNumber);
        map.put("shopIdORSellerHubId", "SP1001");
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Contact_us.php
    public static AppHttpRequest contactUsSendMessageApi(String message, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Contact_us.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("message", message);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Update_Shop_Timings.php
    public static AppHttpRequest addShopTimeAPI(String shopId, String closingDate, String shopOpeningTime,
                                                String shopClosingTime,
                                                AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_Shop_Timings.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("closingDate", closingDate);
        map.put("shopOpeningTime", shopOpeningTime);
        map.put("shopClosingTime", shopClosingTime);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest getLocationsBasedOnString(String loc, LocationResponseListener<ArrayList> appResponseListener) {
        return AppHttpRequest.getGetRequest("https://maps.googleapis.com/maps/api/place/autocomplete/" + loc, appResponseListener);
    }

    // http://stupendoustanuj.co.nf/ShopTheFortune/Fetch_Order_Units.php
    public static AppHttpRequest fetchOrderUnitApi(AppResponseListener<OrderUnitResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Order_Units.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

}
