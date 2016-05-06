<?php
/*
{"applicationId": "ANDSH1001","userId": "9503157441"}
ShopTheFortune/Fetch_Associated_DeliveryPersons.php?input={"applicationId": "ANDSH1001","userId": "9503157441"}
*/
include 'database.php';
header('Content-type: x-www-form-urlencoded');
$jsonRequest = json_decode( processRequest($_POST['input']), true);
$applicationId = mysqli_real_escape_string($link,$jsonRequest['applicationId']);
$userId = mysqli_real_escape_string($link,$jsonRequest['userId']); 

try {
if($applicationId=="")
throw new Exception($applicationId_MISSING);

if($userId=="")
throw new Exception($userId_MISSING);


		$query = "SELECT CustomerMobileNumber,CustomerName, PreferredTime FROM `CallRequest`,Customer WHERE (CallRequest.CustomerMobileNumber = Customer.CustomerMobileNumber)";
	
	$result=mysqli_query($link,$query);
	$dataResult = array();
	$dataResult['status']=1;
	$i = 0;
	if(mysqli_num_rows($result) >= 1){
	while($row=mysqli_fetch_array($result)){
		
			$dataRow = array();
			$dataRow['customerMobileNumber']=$row['CustomerMobileNumber']." ".$row['CustomerName'];
			$dataRow['preferredTime']=$row['PreferredTime'];
			$dataResult['response']['callRequest'][$i++]=$dataRow;
		}
	}
else if(mysqli_num_rows($result) == 0){

	$dataResult = array();
	$dataRow = array();
	$dataResult['status']=1;
	$dataResult['response']['callRequest']=$dataRow;
}
else{
throw new Exception($NO_RECORD_FOUND);
}
	
	echo str_replace('\\', '', processResponse($jsonRequest,json_encode($dataResult))); 
}

//catch exception
catch(Exception $e) {
	$data = array();
	$data['status']=0;
	$data['response']['errorCode']=$FETCH_CALL_REQUESTS_SERVICE_ERROR_CODE;
	$data['response']['errorMessage']=$e->getMessage();
	$data['response']['supportContactNumber']=fetchSupportContactNumber($link,$applicationId);
	logAnError($link,'Fetch_Call_Requests', $_POST, $userId, json_encode($data), $e);
    echo json_encode($data); 
}

finally {
	mysqli_close($link);
}

?>
