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

try {
if($applicationId=="")
throw new Exception($applicationId_MISSING);

if($userId=="")
throw new Exception($userId_MISSING);

if($shopId=="")
throw new Exception($shopId_MISSING);

$sql = "INSERT INTO `CustomerFavroiteShop` (`CustomerMobileNumber`, `ShopId`) VALUES ('".$userId."', '".$shopId."')";


//echo $sql;
$result=mysqli_query($link,$sql);
	if(mysqli_affected_rows($link)==1){
		$dataResult = array();
		$dataResult['status']=1;
		$dataResult['response']['successMessage']=$SHOP_FAVOURITE_SUCCESS;
	}
	else{
	throw new Exception($SHOP_FAVOURITE_ERROR);
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
