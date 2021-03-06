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
$preferredTime = mysqli_real_escape_string($link,$jsonRequest['preferredTime']); 
try {
if($applicationId=="")
throw new Exception($applicationId_MISSING);
if($userId=="")
throw new Exception($userId_MISSING);
if($preferredTime=="")
throw new Exception($preferredTime_MISSING);
$sql = "INSERT INTO `CallRequest` (`CustomerMobileNumber`, `PreferredTime`) VALUES ('".$userId."', '".$preferredTime."')";
//echo $sql;
$result=mysqli_query($link,$sql);
	if(mysqli_affected_rows($link)==1){
		$dataResult = array();
		$dataResult['status']=1;
		$dataResult['response']['successMessage']=$REQUEST_A_CALL_SUCCESS;
	}
	else{
	throw new Exception($REQUEST_A_CALL_ERROR);
	}
	echo processResponse($jsonRequest,json_encode($dataResult)); 
}
//catch exception
catch(Exception $e) {
	$data = array();
	$data['status']=0;
	$data['response']['errorCode']=$REQUEST_A_CALL_SERVICE_ERROR_CODE;
	$data['response']['errorMessage']=$e->getMessage();
	$data['response']['supportContactNumber']=fetchSupportContactNumber($link,$applicationId);
	logAnError($link,'Contact_us', $_POST, $userId, json_encode($data), $e);
    echo json_encode($data); 
}
finally {
	mysqli_close($link);
}
?>
