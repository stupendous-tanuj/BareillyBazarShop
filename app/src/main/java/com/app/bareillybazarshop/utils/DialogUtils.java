package com.app.bareillybazarshop.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.activity.BaseActivity;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.listner.IDialogListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by umesh on 11/9/15.
 */
public class DialogUtils {

    public static void showDialog(Context context, String message) {
        final MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(context);
        materialDialog.title(context.getString(R.string.text));
        materialDialog.content(message).positiveColorRes(R.color.blue_button_background).negativeColorRes(R.color.blue_button_background)
                .positiveText(context.getString(R.string.ok));
        if (!((BaseActivity) context).isFinishing())
            materialDialog.show();
    }


    public static boolean isMobileVarification(BaseActivity activity, String mobileNumber) {
        boolean checkmobileNumber = TextUtils.isEmpty(mobileNumber);
        if (checkmobileNumber) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_mobile_number));
            return false;
        }
        return true;
    }


    public static boolean isChangePasswordVerification(BaseActivity activity, String oldPassword, String newPassword, String confNewPassword) {

        if(!checkForBlank(activity,activity.getString(R.string.label_Old_Password), oldPassword))
            return false;
        if(!checkForBlank(activity,activity.getString(R.string.label_New_Password), newPassword ))
            return false;
        if(!checkForBlank(activity,activity.getString(R.string.label_Confirm_New_Password), confNewPassword))
            return false;

        if(!newPassword.equals(confNewPassword)){
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_confNewPassword_not_match));
            return false;
        }
        return true;
    }

    public static void showDialogYesNo(Context context, final String msg, String yes, final String no, final IDialogListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yes_no);
        dialog.setCanceledOnTouchOutside(true);

        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setText(no);
        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
        tv_ok.setText(yes);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                listener.onCancel();
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    listener.onCancel();
                }
                return false;
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onCancel();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClickOk();
            }
        });
        dialog.show();
    }

    public static void showDialogNetwork(Context context, final String msg, final IDialogListener listener) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_network);
        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
        TextView msgg = (TextView) dialog.findViewById(R.id.view2);
        msgg.setText(msg);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClickOk();
            }
        });
        dialog.show();
    }

    public static boolean isSpinnerDefaultValue(BaseActivity activity, String spinnerValue, String labelName) {

        if(spinnerValue.equals(activity.getString(R.string.please_select))){
            DialogUtils.showDialog(activity, "Please select "+labelName);
            return false;
        }
        return true;
    }


    public static boolean isUpdateOrderDetailsVerification(BaseActivity activity, String toOrderStatusValue) {

        if(!checkForBlank(activity,activity.getString(R.string.label_To_Order_Status), toOrderStatusValue))
            return false;

        if (toOrderStatusValue.equals("NA")) {
            DialogUtils.showDialog(activity, activity.getString(R.string.NA_orderStatus));
            return false;
        }
        return true;
    }

    public static boolean checkForBlank(BaseActivity activity, String fieldName, String fieldValue)
    {
        boolean ret = TextUtils.isEmpty(fieldValue);
        if(ret) {
            DialogUtils.showDialog(activity, fieldName+" is Blank.");
            return false;
        }
        return true;

    }

    public static boolean isChecked(BaseActivity activity, String fieldName, boolean checked)
    {
        if(checked == false)
            DialogUtils.showDialog(activity, fieldName+" should be checked");

        return checked;
    }



    public static boolean isQuotedAmountVerification(BaseActivity activity, String quotedAmount, String invoiceAmount) {

        if(!checkForBlank(activity,activity.getString(R.string.label_Quoted_Amount), quotedAmount))
            return false;
        if(!checkForBlank(activity,activity.getString(R.string.label_Invoice_Amount), invoiceAmount))
            return false;
        if(!integerValidator(activity, activity.getString(R.string.label_Quoted_Amount), quotedAmount))
            return false;
        if(!integerValidator(activity, activity.getString(R.string.label_Invoice_Amount), invoiceAmount))
            return false;

        int quoted = Integer.parseInt(quotedAmount);
        int invoice = Integer.parseInt(invoiceAmount);

        if (quoted!=invoice) {
            DialogUtils.showDialog(activity, activity.getString(R.string.notEqual_quotedInvoice));
            return false;
        }
        return true;
    }

    public static boolean emailValidator(BaseActivity activity, String field,String email)
    {
        boolean ret = false;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        ret = matcher.matches();
        if(ret == false) {
            DialogUtils.showDialog(activity, "Invalid " + field + ".");
        }
        return ret;

    }

    public static boolean integerValidator(BaseActivity activity, String field, String input)
    {
        boolean ret = false;
        try
        {
            Integer.parseInt( input );
            ret = true;
        }
        catch(Exception e)
        {
            ret = false;
        }

        if(ret == false) {
            DialogUtils.showDialog(activity, "Invalid " + field + ".");
        }
        return ret;
    }

    public static boolean isResetPasswordVerification(BaseActivity activity, String emailId,String mobileNumber,String userId) {

        if(!checkForBlank(activity,activity.getString(R.string.label_Email_Id), emailId))
            return false;
        if(!checkForBlank(activity,activity.getString(R.string.label_Mobile_Number), mobileNumber))
            return false;
        if(!checkForBlank(activity, activity.getString(R.string.label_User_ID), userId))
            return false;

        return true;
    }


    public static boolean lengthValidator(BaseActivity activity, String field, String input, int length)
    {
        boolean ret = false;
        try
        {
            if(input.length() == length) {
                ret = true;
                return ret;
            }
            else if(input.length() > length) {
                ret = false;
                DialogUtils.showDialog(activity, "Length of " + field + " is greater than "+length+".");
                return ret;
            }
            else if(input.length() < length) {
                ret = false;
                DialogUtils.showDialog(activity, "Length of " + field + " is less than "+length+".");
                return ret;
            }
        }
        catch(Exception e)
        {
            ret = false;
        }

        return ret;
    }



    public static boolean rangeValidator(BaseActivity activity, String field,String input,int min,int max)
    {
        boolean ret = false;
        int intAge=0;
        intAge = Integer.parseInt(input);
        if(intAge>=min && intAge<=max)
            ret =  true;
        else
            ret =  false;

        if(ret == false) {
            DialogUtils.showDialog(activity, "Invalid " + field + ".");
        }
        return ret;
    }

    public static boolean mobileNumberValidator(BaseActivity activity, String field, String input)
    {
        boolean ret = false;
        final String NUMBER_PATTERN = "\\d{10}";
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(input);
        ret = matcher.matches();
        if(ret == false) {
            DialogUtils.showDialog(activity, "Invalid " + field + ".");
        }
        return ret;
    }

    public static void showDialogError(final Context context, final ErrorObject errors) {

        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.dialog_errors);
            TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_error_ok);
            TextView tv_error_code = (TextView) dialog.findViewById(R.id.tv_error_code);
            TextView tv_error_msg = (TextView) dialog.findViewById(R.id.tv_error_msg);
            TextView tv_support_number = (TextView) dialog.findViewById(R.id.tv_support_number);
            tv_error_code.setText(String.valueOf(errors.getErrorCode()));
            tv_error_msg.setText(errors.getErrorMessage());
            tv_support_number.setText(errors.getSupportContactNumber());

            dialog.findViewById(R.id.linear_support_number).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = errors.getSupportContactNumber();
                    if (number != null) {
                        try {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:" + number));
                            context.startActivity(callIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context, "Calling functinality is not founded", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Support number getting is null", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {

        }
    }

    public static boolean isLoginVerification(BaseActivity activity, String userId, String password, String userType) {

        boolean cuserId = TextUtils.isEmpty(userId);
        boolean cpassword = TextUtils.isEmpty(password);

        if (cuserId) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_userId));
            return false;
        }
        Log.i("DeliveryPerson", ((isMobileVarification(activity,userId)))+"");
        Log.i("DeliveryPerson", (userType.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE))+"");
        if(userType.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE))
        {
            if(!isMobileVarification(activity,userId)) {
                DialogUtils.showDialog(activity, activity.getString(R.string.enter_valid_userId));
                return false;
            }
        }

        else if(userType.equals(AppConstant.UserType.SELLER_HUB_TYPE))
        {
            if(!((userId.length()==6) && (userId.substring(0,2).equals("SH")))) {
                DialogUtils.showDialog(activity, activity.getString(R.string.enter_valid_userId));
                return false;
            }
        }

        else if(userType.equals(AppConstant.UserType.SHOP_TYPE))
        {
            if(!((userId.length()==6) && (userId.substring(0,2).equals("SP")))) {
                DialogUtils.showDialog(activity, activity.getString(R.string.enter_valid_userId));
                return false;
            }
        }
        if (cpassword) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_password));
            return false;
        }

        return true;
    }

    public static boolean showDialogProduct(BaseActivity activity, String price, String offerPrice) {
        boolean cname = TextUtils.isEmpty(price);
        boolean cEmail = TextUtils.isEmpty(offerPrice);
        if (cname) {
            DialogUtils.showDialog(activity, "Price is not empty");
            return false;
        }
        if (cEmail) {
            DialogUtils.showDialog(activity, "Offer price is not empty");
            return false;
        }
        double p = Double.parseDouble(price);
        double o = Double.parseDouble(offerPrice);
        if (o > p) {
            DialogUtils.showDialog(activity, "Offer Price cannot be greater than Product Price");
            return false;
        }
        return true;
    }

    public static boolean showDialogShopOpertion(BaseActivity activity, String closingDate, String openingTime, String closingTime) {
        boolean cname = TextUtils.isEmpty(closingDate);
        boolean copeningTime = TextUtils.isEmpty(openingTime);
        boolean cclosingTime = TextUtils.isEmpty(closingTime);

        if (cname) {
            DialogUtils.showDialog(activity, "Closing date is not empty");
            return false;
        }
        if (copeningTime) {
            DialogUtils.showDialog(activity, "Opening time is not empty");
            return false;
        }
        if (cclosingTime) {
            DialogUtils.showDialog(activity, "Closing time is not empty");
            return false;
        }
        int open = AppUtil.getTotlTime(openingTime);
        int close = AppUtil.getTotlTime(closingTime);
        if (open > close) {
            DialogUtils.showDialog(activity, "OpeningTime cannot be greater than Closing time");
            return false;
        }
        return true;
    }

    public static boolean showDialogddProduct(BaseActivity activity, String eName, String hName, String des, String noOfUnit) {
        boolean ceName = TextUtils.isEmpty(eName);
        boolean chName = TextUtils.isEmpty(hName);
        boolean cdes = TextUtils.isEmpty(des);
        boolean cnoOfUnit = TextUtils.isEmpty(noOfUnit);


        if (ceName && chName) {
            DialogUtils.showDialog(activity, "Product name is not empty");
            return false;
        }
        if (cdes) {
            DialogUtils.showDialog(activity, "Description is not empty");
            return false;
        }
        if (cnoOfUnit) {
            DialogUtils.showDialog(activity, "No. of unit is not empty");
            return false;
        }

        return true;

    }

    public static boolean showDialogDeliveryPerson(BaseActivity activity, String name, String mobileNumber, String address, String idType, String idNumber, String emailId) {

        if(!checkForBlank(activity,activity.getString(R.string.label_Name) ,name))
            return false;
        if(!checkForBlank(activity,activity.getString(R.string.label_Mobile_Number) ,mobileNumber))
            return false;
        if(!mobileNumberValidator(activity, activity.getString(R.string.label_Mobile_Number), mobileNumber))
            return false;
        if(!checkForBlank(activity, activity.getString(R.string.label_Email_Id), emailId))
            return false;
        if(!emailValidator(activity, activity.getString(R.string.label_Email_Id), emailId))
            return false;
        if(!checkForBlank(activity, activity.getString(R.string.label_Address), address))
            return false;
        if(!checkForBlank(activity, activity.getString(R.string.label_Owner_Id_Type), idType))
            return false;
        if(!checkForBlank(activity, activity.getString(R.string.label_Owner_Id_Number), idNumber))
            return false;

        return true;
    }
}
