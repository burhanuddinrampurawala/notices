<?php
	require_once 'DB_Connect.php';
	$db = new DB_Connect();
	$conn = $db->connect();
	if(isset($_POST["token"])){
		$token = $_POST["token"];
		$query = mysqli_query($conn,"SELECT * FROM tokentable WHERE token = '$token'");
			if(!(mysqli_num_rows($query) > 0)){

				$sql = mysqli_query($conn,"INSERT INTO tokentable(token) 
											VALUES('$token')");
	             if (!$sql){
	                $response = array("error" => True);
					echo json_encode($response);
	            }
	            else{
	            	$response = array("error" => FALSE);
					echo json_encode($response);
				}
        	}
		mysqli_close($conn);
	}
?>