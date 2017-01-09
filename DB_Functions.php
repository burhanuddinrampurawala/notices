    <?php


    class DB_Functions {

        private $conn;

        // constructor
        function __construct() {
            require_once 'DB_Connect.php';
            // connecting to database
            $db = new DB_Connect();
            $this->conn = $db->connect();
        }

        // destructor
        function __destruct() {

        }

        /**
         * Storing new user
         * returns user details
         */
        function storeStudent($name, $year, $branch, $rollno, $email, $password) {
            $uuid = uniqid('', true);
            $hash = $this->hashSSHA($password);
            $encrypted_password = $hash["encrypted"]; // encrypted password
            $salt = $hash["salt"]; // salt
            
            $stmt = $this->conn->prepare("INSERT INTO studentdata(uniqueid, name, year, branch, rollno,  email, password, salt, createdat) VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW())");
            $stmt->bind_param("ssssssss", $uuid, $name, $year, $branch, $rollno, $email, $encrypted_password, $salt);
            $result = $stmt->execute();
            $stmt->close();
            // check for successful store
            if ($result) {
    // get user details
              $stmt = $this->conn->prepare("SELECT * FROM studentdata WHERE email = ?");
              $stmt->bind_param("s", $email);
              $stmt->execute();
              $user = $stmt->get_result()->fetch_assoc();
              $stmt->close();
              return true;
            }
          else {
            return false;
        }
    }


        /**
         * Get user by email and password
         */
         function getStudent($email, $password) {

            $stmt = $this->conn->prepare("SELECT * FROM studentdata WHERE email = ?");

            $stmt->bind_param("s", $email);

            if ($stmt->execute()) {
                $user = $stmt->get_result()->fetch_assoc();
                $stmt->close();

                // verifying user password
                $salt = $user['salt'];
                $encrypted_password = $user['password'];
                $hash = $this->checkhashSSHA($salt, $password);
                // check for password equality
                if ($encrypted_password == $hash) {
                    // user authentication details are correct
                    return $user;
                }
            } else {
                return NULL;
            }
        }

         function getAdmin($email, $password) {

            $stmt = $this->conn->prepare("SELECT * FROM admindata WHERE email = ?");

            $stmt->bind_param("s", $email);

            if ($stmt->execute()) {
                $user = $stmt->get_result()->fetch_assoc();
                $stmt->close();

                // verifying user password
                $salt = $user['salt'];
                $encrypted_password = $user['password'];
                $hash = $this->checkhashSSHA($salt, $password);
                // check for password equality
                if ($encrypted_password == $hash) {
                    // user authentication details are correct
                    return $user;
                }
            } else {
                return NULL;
            }
        }

        /**
         * Check user is existed or not
         */
         function isStudentExisted($email) {
            $stmt = $this->conn->prepare("SELECT email from studentdata WHERE email = ?");

            $stmt->bind_param("s", $email);

            $stmt->execute();

            $stmt->store_result();

            if ($stmt->num_rows > 0) {
                // user existed 
                $stmt->close();
                return true;
            } else {
                // user not existed
                $stmt->close();
                return false;
            }
        }

        /**
         * Encrypting password
         * @param password
         * returns salt and encrypted password
         */
         function hashSSHA($password) {

            $salt = sha1(rand());
            $salt = substr($salt, 0, 10);
            $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
            $hash = array("salt" => $salt, "encrypted" => $encrypted);
            return $hash;
        }

        /**
         * Decrypting password
         * @param salt, password
         * returns hash string
         */
         function checkhashSSHA($salt, $password) {

            $hash = base64_encode(sha1($password . $salt, true) . $salt);

            return $hash;
        }
         function addnotice($title, $description) {

            $uuid = uniqid('', true);
            $stmt = $this->conn->prepare("INSERT INTO noticedata(uniqueid, title, description, createdat) VALUES(?, ?, ?, NOW())");
            $stmt->bind_param("sss", $uuid, $title, $description);
            $result = $stmt->execute();
            $stmt->close();
            // check for successful store
            if ($result) {
    // get user details
              $stmt = $this->conn->prepare("SELECT * FROM noticedata WHERE title = ?");
              $stmt->bind_param("s", $title);
              $stmt->execute();
              $user = $stmt->get_result()->fetch_assoc();
              $stmt->close();
              getToken($title);
              return true;
          }
          else {
            return false;
        }
    }
     function editnotice($uid, $title, $description)
    {
       $qury = "UPDATE noticedata SET title= '$title',description='$description',updatedat =NOW() WHERE uniqueid='$uid'";
       if ($this->conn->query($qury)) {
        getToken($title);
        return true;
      }
      else {
          return false ;
      }
    }
     function deletenotice($uid)
    {
        $sql = "DELETE FROM noticedata WHERE uniqueid='$uid'";
        if ($this->conn->query($sql)) {
            return true;     
     } 
     else {
        return false;
    }

    $this->conn->close();
    }
    function getToken($title)
    {
      $sql = " Select token From tokentable";
      $result = mysqli_query($conn,$sql);
      $tokens = array();
      if(mysqli_num_rows($result) > 0 )
      {
        while ($row = mysqli_fetch_assoc($result)) 
        {
          $tokens[] = $row["Token"];
        }
      }
      mysqli_close($conn);
      $messgae = array("message" => '$title');
      notification($tokens, $message);
    }
    function notification($tokens,$message)
    {

    $url = 'https://fcm.googleapis.com/fcm/send';
    $fields = array(
       'registration_ids' => $tokens,
       'data' => $message
      );
    $headers = array(
      'Authorization:key =  AIzaSyDpUjPAoujRbiuyA0tC4z1TPNRGli9ZsBg ',
      'Content-Type: application/json'
      );
     $ch = curl_init();
       curl_setopt($ch, CURLOPT_URL, $url);
       curl_setopt($ch, CURLOPT_POST, true);
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);  
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
       curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
       $result = curl_exec($ch);           
       if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch));
       }
       curl_close($ch); 
    }
}

?>