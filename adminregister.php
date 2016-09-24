<?php
 
 $conn;
define("DB_HOST", "127.0.0.1");
define("DB_USER", "burhan");
define("DB_PASSWORD", "burhanuddin");
define("DB_DATABASE", "notices");
$conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

 try {
            
    // json response array
    $response = array("error" => FALSE);
     
    if (isset($_POST['name']) && isset($_POST['branch']) && isset($_POST['email']) && isset($_POST['password'])) 
    {
     
        // receiving the post params
        $name = $_POST['name'];
        $branch = $_POST['branch'];
        $email = $_POST['email'];
        $password = $_POST['password'];
        $stmt = $conn->prepare("SELECT email from admindata WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) 
        {
            // user existed 
            $stmt->close();
            $response["error"] = TRUE;
            $response["error_msg"] = "User already existed with " . $email;
            echo json_encode($response);
        }
            
         else 
         {
            // create a new user
                $uuid = uniqid('', true);
                $salt = sha1(rand());
                $salt = substr($salt, 0, 10);
                $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
                $hash = array("salt" => $salt, "encrypted" => $encrypted);
                $encrypted_password = $hash["encrypted"]; // encrypted password
                 $salt = $hash["salt"]; // salt
            
                $stmt = $conn->prepare("INSERT INTO admindata(uniqueid, name, branch, email, password, salt, createdat) VALUES( ?, ?, ?, ?, ?, ?, NOW())");
                $stmt->bind_param("ssssss", $uuid, $name, $branch, $email, $encrypted_password, $salt);
                $result = $stmt->execute();
                $stmt->close();
            // check for successful store
                if ($result) {
    // get user details
                    $stmt = $conn->prepare("SELECT * FROM admindata WHERE email = ?");
                    $stmt->bind_param("s", $email);
                    $stmt->execute();
                    $user = $stmt->get_result()->fetch_assoc();
                    $stmt->close();
                    $response["error"] = FALSE;
                    $response["error_msg"] = "User saved";
                    echo json_encode($response);
                
                }
                else {
                    $response["error"] = TRUE;
                    $response["error_msg"] = "Unknown error occurred in registration!";
                    echo json_encode($response);
                 }
        }
    } 
    else {
        $response["error"] = TRUE;
        $response["error_msg"] = "Required parameters (name, branch, email or password) is missing!";
        echo json_encode($response);
    }
}catch(Exception $e){
    $response["error"] = TRUE;
    $response["error_msg"] = var_dump($e->getMessage());
    echo json_encode($response);
}
?>