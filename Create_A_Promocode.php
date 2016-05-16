<?php
/*
{"applicationId": "ANDSH1001","userId": "SP1001","shopId":"SP1001", "productSKUDId":"SKUID1001", "promocode":"VALANTINE", "discountType":"Flat", "eligibilityCriteria":"New Users", "promoCodeUseFrequency":"1", "validFrom":"25-Feb-2016", "validTo":"25-Mar-2016", "maximumDiscount":"500"}
ShopTheFortune/Create_A_Promocode.php?input={"applicationId": "ANDSH1001","userId": "SP1001","shopId":"SP1001", "productSKUDId":"SKUID1001", "promocode":"VALANTINE", "discountType":"Flat", "eligibilityCriteria":"New Users", "promoCodeUseFrequency":"1", "validFrom":"25-Feb-2016", "validTo":"25-Mar-2016", "maximumDiscount":"500"}
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
$discountType = mysqli_real_escape_string($link,$jsonRequest['discountType']);
$eligibilityCriteria = mysqli_real_escape_string($link,$jsonRequest['eligibilityCriteria']);
$promoCodeUseFrequency = mysqli_real_escape_string($link,$jsonRequest['promoCodeUseFrequency']);
$validFrom = mysqli_real_escape_string($link,$jsonRequest['validFrom']);
$validTo = mysqli_real_escape_string($link,$jsonRequest['validTo']);
$maximumDiscount = mysqli_real_escape_string($link,$jsonRequest['maximumDiscount']);

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

if($discountType=="")
throw new Exception($discountType_MISSING);

if($eligibilityCriteria=="")
throw new Exception($eligibilityCriteria_MISSING);

if($promoCodeUseFrequency=="")
throw new Exception($promoCodeUseFrequency_MISSING);

if($validFrom=="")
throw new Exception($validFrom_MISSING);

if($validTo=="")
throw new Exception($validTo_MISSING);

if($maximumDiscount=="")
throw new Exception($maximumDiscount_MISSING);

$sql = "INSERT INTO `PromoCode` (`ShopId`, `ProductSKUDId`, `Promocode`, `DiscountType`, `EligibilityCriteria`, `PromoCodeUseFrequency`, `ValidFrom`, `ValidTo`, `MaximumDiscount`, `CreatedBy`) VALUES ('".$shopId."', '".$productSKUDId."', '".$promocode."', '".$discountType."', '".$eligibilityCriteria."', '".$promoCodeUseFrequency."', '".$validFrom."', '".$validTo."', '".$maximumDiscount."', '".$userId."');";
//echo $sql;
$result=mysqli_query($link,$sql);
	if(mysqli_affected_rows($link)==1){
		$id = mysqli_insert_id($link);
		$SKUId = 'SKUID'.$id;
		$sql = "UPDATE `Product` SET ProductSKUID = '".$SKUId."',ProductImageName='".$SKUId.$imageExtension."'  WHERE ID = ".$id."";
		$result=mysqli_query($link,$sql);
		if(mysqli_affected_rows($link)==1){
			
			$dataResult = array();
			$dataResult['status']=1;
			$dataResult['response']['SKUID']=$SKUId;
			$dataResult['response']['successMessage']=$PROMOCODE_CREATION_SUCCESS;
			}
		else{
		throw new Exception($PROMOCODE_CREATION_ERROR);
		}
	}
	else{
	throw new Exception($PROMOCODE_CREATION_ERROR);
	}
	echo processResponse($jsonRequest,json_encode($dataResult)); 
	
}
//catch exception
catch(Exception $e) {
	$data = array();
	$data['status']=0;
	$data['response']['errorCode']=$CREATE_A_PROMOCODE_SERVICE_ERROR_CODE;
	$data['response']['errorMessage']=$e->getMessage();
	$data['response']['supportContactNumber']=fetchSupportContactNumber($link,$applicationId);
	logAnError($link,'Create_A_Promocode', $_POST, $userId, json_encode($data), $e);
    echo json_encode($data); 
}

finally {
	mysqli_close($link);
}

?>