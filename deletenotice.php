<?php
require_once 'DB_Functions.php';
$db = new DB_Functions();
try{
    // json response array
    $response = array("error" => FALSE);
     
   if (isset($_POST['uid']))  {
     
        // receiving the post params
        $uid = $_POST['uid'];
    
        // delete notice 
         $user = $db->deletenotice($uid);
        if ($user != false) {
            // use is found
            $response["error"] = FALSE;
                $response["error_msg"] = "notice deleted";
            echo json_encode($response);
        } 
            else {
            // user is not found with the credentials
            echo $user;
            $response["error"] = TRUE;
            $response["error_msg"] = "unknown error occurred";
            echo json_encode($response);
        }
    } else {
        // required post params is missing
        $response["error"] = TRUE;
        $response["error_msg"] = "try again";
        echo json_encode($response);
    }
}catch(Exception $e){
    $response["error"] = TRUE;
    $response["error_msg"] = var_dump($e->getMessage());
    echo json_encode($response);
}
?>