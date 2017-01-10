<?php
 
require_once 'DB_Functions.php';
$db = new DB_Functions();

 try {
            
    // json response array
    $response = array("error" => FALSE);
     
    if (isset($_POST['title']) && isset($_POST['description'])) {
     
        // receiving the post params
        $title = $_POST['title'];
        $description = $_POST['description'];
     
        // check if user is already existed with the same email
       
            // create a new user
            $user = $db->addnotice($title, $description);
            if ($user) {
                // user stored successfully
                $db->getToken($title, $description);
                $response["error"] = FALSE;
                $response["error_msg"] = "notice saved";
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Unknown error occurred while adding notice";
                echo json_encode($response);
            }
        
    }
     else {
        $response["error"] = TRUE;
        $response["error_msg"] = "Required parameters (title, description) is missing!";
        echo json_encode($response);
    }
}catch(Exception $e){
    $response["error"] = TRUE;
    $response["error_msg"] = var_dump($e->getMessage());
    echo json_encode($response);
}
?>