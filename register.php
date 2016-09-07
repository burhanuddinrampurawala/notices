<?php
 
require_once 'DB_Functions.php';
$db = new DB_Functions();
 try {
    // json response array
    $response = array("error" => FALSE);
     
    if (isset($_POST['name']) && isset($_POST['year']) && isset($_POST['branch']) && isset($_POST['rollno']) && isset($_POST['email']) && isset($_POST['password'])) {
     
        // receiving the post params
        $name = $_POST['name'];
        $year = $_POST['year'];
        $branch = $_POST['branch'];
        $rollno = $_POST['rollno'];
        $email = $_POST['email'];
        $password = $_POST['password'];

     
        // check if user is already existed with the same email
        if ($db->isUserExisted($email)) {
            // user already existed
            $response["error"] = TRUE;
            $response["error_msg"] = "User already existed with " . $email;
            echo json_encode($response);
        } else {
            // create a new user
            $user = $db->storeUser($name, $year, $branch, $rollno, $email, $password);
            if ($user) {
                // user stored successfully
                $response["error"] = FALSE;
                $response["error_msg"] = "User saved";
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Unknown error occurred in registration!";
                echo json_encode($response);
            }
        }
    } else {
        $response["error"] = TRUE;
        $response["error_msg"] = "Required parameters (name, year, branch, rollno, email or password) is missing!";
        echo json_encode($response);
    }
}catch(Exception $e){
    $response["error"] = TRUE;
    $response["error_msg"] = var_dump($e->getMessage());

    echo json_encode($response);
}
?>