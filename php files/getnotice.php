<?php
 require_once 'DB_Connect.php';
            // connecting to database

            $db = new DB_Connect();
            $conn = $db->connect();
try{
    // json response array
    $response = array();
    $sql = "SELECT * FROM noticedata";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
    // output data of each row
        // $response["error"] = FALSE;
        while($row = $result->fetch_assoc()) {
            $response [] = $row;
            // $response ["notice"]["uid"] = $row["uniqueid"];
            // $response["notice"]["title"] = $row["title"];
            // $response["notice"]["description"] = $row["description"];
            // $response["notice"]["created_at"] = $row["createdat"];
            // $response["notice"]["updated_at"] = $row["updatedat"];
            // echo json_encode($response);
        }
        $response2 = array();
        $response2 ["notice"] = $response;
        echo json_encode(array("notice" => $response));
    } 
    else {
        $response["error"] = TRUE;
        $response["error_msg"] = "error occured while getting data";
        echo json_encode($response);
    }
 
}catch(Exception $e){
    $response["error"] = TRUE;
    $response["error_msg"] = var_dump($e->getMessage());
    echo json_encode($response);
}
?>