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
              return true;
          }
          else {
            return false;
        }
    }
     function editnotice($uid, $title, $description)
    {
     $time = date("Y-m-d H:i:s");
     $qury = "UPDATE noticedata SET title= '$title',description='$description',updatedat =NOW() WHERE uniqueid='$uid'";
     echo $qury;
     $stmt = $this->conn->query($qury);

     if ($stmt) {
      return true;
    }
    else {
        echo  $this->conn->error ;
    }
    }
     function deletenotice($uid)
    {

        $sql = "DELETE FROM noticedata WHERE uniqueid='$uid'";

        if ($this->conn->query($sql) === TRUE) {
        return true;     
     } 
     else {
        return false;
    }

    $this->conn->close();
    }
}
?>