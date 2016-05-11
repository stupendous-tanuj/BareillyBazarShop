<?php
/*
request parameters
{"applicationId": "ANDSH1001","userId": "9503157441","message": "message","userType": "9503157441"}
shopthefortune.com/api/bareillybazar/test/Apply_Promocode.php?input={"applicationId": "ANDSH1002","userId": "9503157441","message": "message","locale": "en","shopId": "SP1001","promoCode": "FLAT50","orderValue": "200"}
*/
include 'database.php';
header('Content-type: x-www-form-urlencoded');
$jsonRequest = json_decode( processRequest($_GET['input']), true);
$applicationId = mysqli_real_escape_string($link,$jsonRequest['applicationId']);
$userId = mysqli_real_escape_string($link,$jsonRequest['userId']); 
$locale = mysqli_real_escape_string($link,$jsonRequest['locale']); 
$shopId = mysqli_real_escape_string($link,$jsonRequest['shopId']); 
$promoCode = mysqli_real_escape_string($link,$jsonRequest['promoCode']);
$orderValue = mysqli_real_escape_string($link,$jsonRequest['orderValue']);

try {
if($applicationId=="")
throw new Exception($applicationId_MISSING);
if($userId=="")
throw new Exception($userId_MISSING);
if($shopId=="")
throw new Exception($shopId_MISSING);
if($promoCode=="")
throw new Exception($promoCode_MISSING);
if($orderValue=="")
throw new Exception($orderValue_MISSING);
//Check the validity of the Promo Code.

$sql = "SELECT DiscountType,EligibilityCriteria,PromoCodeUseFrequency,MaximumDiscount,FlatDiscount,PercentDiscount,MinimumOrderValue From PromoCode WHERE ShopId = '".$shopId."' AND Promocode = '".$promoCode."' AND date('Y-m-d') BETWEEN ValidFrom AND ValidTo";

$result=mysqli_query($link,$sql);
$validityRow=mysqli_fetch_array($result);

if(mysqli_num_rows($link)==1)
$validPromocode = true;
else{
$validPromocode = false;
throw new Exception($INVALID_PROMOCODE_ERROR);
}

//Evaluate Eligibility Criteria.
if($validityRow['EligibilityCriteria'] == "Both"){
$eligibilityCriteria = true;
}
else if($validityRow['EligibilityCriteria'] == "New Customer"){
	$query = "SELECT NewCustomer FROM Customer WHERE CustomerMobileNumber = '".$userId."'";
	$result=mysqli_query($link,$query);
	$row=mysqli_fetch_array($result);
	if($row['NewCustomer'] == 1)
	$eligibilityCriteria = true;
	else{
	$eligibilityCriteria = false;
	throw new Exception($NOT_ELIGIBLE_PROMOCODE_ERROR);
	}
}
else if($validityRow['EligibilityCriteria'] == "Existing Customer"){
	$query = "SELECT NewCustomer FROM Customer WHERE CustomerMobileNumber = '".$userId."'";
	$result=mysqli_query($link,$query);
	$row=mysqli_fetch_array($result);
	if($row['NewCustomer'] == 0)
	$eligibilityCriteria = true;
	else{
	$eligibilityCriteria = false;
	throw new Exception($NOT_ELIGIBLE_PROMOCODE_ERROR);
	}
}

//Evaluate Code Use Frequency.
$query = "SELECT PromoCode FROM PromoCodeUsageHistory WHERE CustomerMobileNumber = '".$userId."' AND ShopId = '".$shopId."' AND PromoCode = '".$promoCode."'";
$result=mysqli_query($link,$query);
$row=mysqli_fetch_array($result);

if(mysqli_num_rows($link)>=$validityRow['PromoCodeUseFrequency']){
$promoCodeUseFrequency = false;
throw new Exception($PROMOCODE_ALREADY_USED_ERROR);
}
else
$promoCodeUseFrequency = true;

//Evaluate Minimum order value.
if($validityRow['MinimumOrderValue']>$orderValue)
$minimumOrderValue = false;
else{
$minimumOrderValue = true;
throw new Exception("Minimum Order valud should be ".$validityRow['MinimumOrderValue']);
}

//Calculate Discount value.
//in case of Flat Discount
if($validityRow['DiscountType']=='Flat')
$discountValue = $validityRow['FlatDiscount'];

//In case of Percent Discount.
if($validityRow['DiscountType']=='Percent')
$discountValue = ($orderValue%$validityRow['PercentDiscount'])/100;

//Normalize the discount according to maximum discount value.
if($discountValue>$validityRow['MaximumDiscount'])
$discountValue = $validityRow['MaximumDiscount'];

if(($validPromocode == true)&&($eligibilityCriteria == true)&&($promoCodeUseFrequency == true)&&($minimumOrderValue == true))
{
	$dataResult = array();
	$dataResult['status']=1;
	$dataResult['response']['discountValue']=$discountValue;
	$dataResult['response']['successMessage']=$APPLY_PROMOCODE_SUCCESS;
}
else
{
	throw new Exception($SOME_ERROR_OCURRED);
}
  
  echo processResponse($jsonRequest,json_encode($dataResult)); 
  
}
//catch exception
catch(Exception $e) {
	$data = array();
	$data['status']=0;
	$data['response']['errorCode']=$APPLY_PROMOCODE_SERVICE_ERROR_CODE;
	$data['response']['errorMessage']=$e->getMessage();
	$data['response']['supportContactNumber']=fetchSupportContactNumber($link,$applicationId);
	logAnError($link,'Contact_us', $_POST, $userId, json_encode($data), $e);
    echo json_encode($data); 
}
finally {
	mysqli_close($link);
}
?>

$orderValue_MISSING = 'orderValue is missing';
$INVALID_PROMOCODE_ERROR = 'Invalid Promo Code';
$NOT_ELIGIBLE_PROMOCODE_ERROR = 'You are not eligible for this promocode.';
$PROMOCODE_ALREADY_USED_ERROR = 'This Promo code has already been used.';
$MINIMUM_ORDER_VALUE_ERROR = '';
$APPLY_PROMOCODE_SUCCESS = 'Promocode applied successfully.';
