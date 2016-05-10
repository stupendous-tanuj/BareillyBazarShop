Check the validity of the Promo Code.
Evaluate Eligibility Criteria.
Evaluate Code Use Frequency.
Evaluate Minimum order value.
Calculate Discount value.
in case of Flat Discount
In case of Percent Discount.
Normalize the discount according to maximum discount value.


<?php
/*
request parameters
{"applicationId": "ANDSH1001","userId": "9503157441","message": "message","userType": "9503157441"}
ShopTheFortune/Contact_us.php?input={"applicationId": "ANDSH1001","userId": "9503157441","message": "message","userType": "9503157441"}
*/
include 'database.php';
header('Content-type: x-www-form-urlencoded');
$jsonRequest = json_decode( processRequest($_POST['input']), true);
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
$row=mysqli_fetch_array($result);

  if(mysqli_num_rows($link)==1){
    
    //Evaluate Eligibility Criteria.
    if($row['EligibilityCriteria'] == "Both")
  	
  }
  else{
  throw new Exception($INVALID_PROMOCODE_ERROR);
  }
  echo processResponse($jsonRequest,json_encode($dataResult)); 
  
}
//catch exception
catch(Exception $e) {
	$data = array();
	$data['status']=0;
	$data['response']['errorCode']=$CUSTOMER_FAVOURITE_SHOP_SERVICE_ERROR_CODE;
	$data['response']['errorMessage']=$e->getMessage();
	$data['response']['supportContactNumber']=fetchSupportContactNumber($link,$applicationId);
	logAnError($link,'Contact_us', $_POST, $userId, json_encode($data), $e);
    echo json_encode($data); 
}
finally {
	mysqli_close($link);
}
?>
