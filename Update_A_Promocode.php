<?php
/*
request parameters
{"applicationId": "ANDSH1001","userId": "SP1001","shopId":"SP1001", "productSKUDId":"SKUID1001", "promocode":"VALANTINE", "discountType":"Flat", "eligibilityCriteria":"New Users", "promoCodeUseFrequency":"1", "validFrom":"25-Feb-2016", "validTo":"25-Mar-2016", "maximumDiscount":"500"}
ShopTheFortune/Update_A_Promocode.php?input={"applicationId": "ANDSH1001","userId": "SP1001","shopId":"SP1001", "productSKUDId":"SKUID1001", "promocode":"VALANTINE", "discountType":"Flat", "eligibilityCriteria":"New Users", "promoCodeUseFrequency":"1", "validFrom":"25-Feb-2016", "validTo":"25-Mar-2016", "maximumDiscount":"500"}
*/
include 'database.php';
header('Content-type: x-www-form-urlencoded');
$jsonRequest = json_decode(processRequest($_POST['input']), true);
$applicationId = mysqli_real_escape_string($link,$jsonRequest['applicationId']);
$userId = mysqli_real_escape_string($link,$jsonRequest['userId']); 
$locale = mysqli_real_escape_string($link,$jsonRequest['locale']); 
$shopId = mysqli_real_escape_string($link,$jsonRequest['shopId']); 
$productSKUDId = mysqli_real_escape_string($link,$jsonRequest['productSKUDId']); 
$promocode = mysqli_real_escape_string($link,$jsonRequest['promocode']); 

try {
if($applicationId=="")
throw new Exception($applicationId_MISSING);

if($userId=="")
throw new Exception($userId_MISSING);

$sql = "UPDATE `Promocode` SET ";

if(array_key_exists('eligibilityCriteria', $jsonRequest))
{
$sql = $sql."`EligibilityCriteria`='".$jsonRequest['eligibilityCriteria']."',";
if($jsonRequest['eligibilityCriteria']=="")
throw new Exception($eligibilityCriteria_MISSING);
}
if(array_key_exists('promoCodeUseFrequency', $jsonRequest))
{
$sql = $sql."`PromoCodeUseFrequency`='".$jsonRequest['promoCodeUseFrequency']."',";
if($jsonRequest['promoCodeUseFrequency']=="")
throw new Exception($promoCodeUseFrequency_MISSING);
}
if(array_key_exists('validFrom', $jsonRequest))
{
$sql = $sql."`ValidFrom`='".$jsonRequest['validFrom']."',";
if($jsonRequest['validFrom']=="")
throw new Exception($validFrom_MISSING);
}
if(array_key_exists('validTo', $jsonRequest))
{
$sql = $sql."`ValidTo`='".$jsonRequest['validTo']."',";
if($jsonRequest['validTo']=="")
throw new Exception($validTo_MISSING);
}
if(array_key_exists('maximumDiscount', $jsonRequest))
{
$sql = $sql."`MaximumDiscount`='".$jsonRequest['maximumDiscount']."',";
if($jsonRequest['maximumDiscount']=="")
throw new Exception($maximumDiscount_MISSING);
}

$sql = substr($sql,0,strlen($sql)-1);
$sql = $sql." WHERE (`ShopId` = '".$shopId."') AND (`ProductSKUDId` = '".$productSKUDId."') AND (`Promocode` = '".$promocode."');";

$result=mysqli_query($link,$sql);
	if(mysqli_affected_rows($link)>=0){
		$dataResult = array();
		$dataResult['status']=1;
		$dataResult['response']['successMessage']=$UPDATE_PROMOCODE_SUCCESS;
	}
	else{
	throw new Exception($UPDATE_PROMOCODE_ERROR);
	}
	echo processResponse($jsonRequest,json_encode($dataResult)); 

}
//catch exception
catch(Exception $e) {
	$data = array();
	$data['status']=0;
	$data['response']['errorCode']=$UPDATE_PROMOCODE_SERVICE_ERROR_CODE;
	$data['response']['errorMessage']=$e->getMessage();
	$data['response']['supportContactNumber']=fetchSupportContactNumber($link,$applicationId);
	logAnError($link,'Update_A_Promocode', $_POST, $userId, json_encode($data), $e);
    echo json_encode($data); 
}

finally {
	mysqli_close($link);
}

?>