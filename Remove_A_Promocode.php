<?php
/*
request parameters
{"applicationId": "ANDSH1001","userId": "SP1001","shopId":"SP1001", "productSKUDId":"SKUID1001", "promocode":"VALANTINE")
ShopTheFortune/Remove_A_Promocode.php?input={"applicationId": "ANDSH1001","userId": "SP1001","shopId":"SP1001", "productSKUDId":"SKUID1001", "promocode":"VALANTINE")
*/
include 'database.php';
header('Content-type: x-www-form-urlencoded');
$jsonRequest = json_decode( processRequest($_POST['input']), true);
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

if($shopId=="")
throw new Exception($shopId_MISSING);

if($productSKUDId=="")
throw new Exception($productSKUDId_MISSING);

if($promocode=="")
throw new Exception($promocode_MISSING);

//$sql = "DELETE FROM `DeliveryAddresses` WHERE (CustomerId = '".$userId."') AND (AddressIdentifier = '".$addressIdentifier."')";
$sql = "UPDATE `Promocode` SET Deleted = 1 WHERE (ShopId = '".$shopId."') AND (ProductSKUDId = '".$productSKUDId."') AND (Promocode = '".$promocode."')";

//echo $sql; 
$result=mysqli_query($link,$sql);
	if(mysqli_affected_rows($link)){
	$dataResult = array();
	$dataResult['status']=1;
	$dataResult['response']['successMessage']=$REMOVE_PROMOCODE_SUCCESS;
	}
	else{
	throw new Exception($REMOVE_PROMOCODE_ERROR);
	}
	echo processResponse($jsonRequest,json_encode($dataResult)); 
}
//catch exception
catch(Exception $e) {
	$data = array();
	$data['status']=0;
	$data['response']['errorCode']=$REMOVE_A_PROMOCODE_SERVICE_ERROR_CODE;
	$data['response']['errorMessage']=$e->getMessage();
	$data['response']['supportContactNumber']=fetchSupportContactNumber($link,$applicationId);
	logAnError($link,'Remove_A_Promocode', $_POST, $userId, json_encode($data), $e);
    echo json_encode($data); 
}
/*
finally {
	mysqli_close($link);
}
*/
?>