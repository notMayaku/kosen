<?php
   function DBsearch($searchWord){
      try{
         $server = "jocalc1";
         $DB = "r01_j4_exp";
         $portNumber = "5432";
         $userID = "td16131";
         $userPass = "td16131";

         $dbh = new PDO("pgsql:host=$server; dbname=$DB; port=$portNumber; user=$userID; password=$userPass");
         $sql = "SELECT * FROM company_21 WHERE name LIKE '?'";
         $stmt = $dbh->prepare($sql);
         $stmt->bindValue(1, $searchWord, PDO::PARAM_STR);
         $stmt->execute();
         $result = $stmt->fetch(PDO::FETCH_ASSOC);
         $dbh = null;
      }
      catch(Exception $e){
         return null;
      }
      return $result;
   }
?>

<!DOCTYPE html>
<html lang="ja">
<head>
   <meta charset="UTF-8">
   <title>検索・表示画面</title>
</head>
<body>
   <h1>企業一覧表</h1><br>
   <form method="post" action="searchDB.php">
      企業名部分一致検索：<input type="text" name="searchWord">
      <input type="submit" value="検索">
   </form>
   <br>
   <?php
      $searchWord = htmlspecialchars($_POST['searchWord'], ENT_QUOTES, 'UTF-8');
      if($searchWord = ""){
         echo "empty error";
      }
      else{
         require_once("Company.php");
         $result = DBsearch($searchWord);
         $company = [];

         foreach($result as $row){
            $company[] = new Company($row['code'], $row['name'], $row['address'], $row['phone'], $row['labors']);
         }

         echo "企業数：" . htmlspecialchars(count($company), ENT_QUOTES, 'UTF-8') . "件\n<br>\n";
         echo "<table>\n";
         echo "<tr>\n";
         echo "<th>企業コード</th><th>企業名</th><th>所在地</th><th>電話番号</th><th>従業員数</th>\n";
         echo "<tr>\n";
         foreach($company as $row){
            echo "<tr>\n";
            echo "<td>" . htmlspecialchars($row->getCode(), ENT_QUOTES, 'UTF-8') . "</td>\n";
            echo "<td>" . htmlspecialchars($row->getName(), ENT_QUOTES, 'UTF-8') . "</td>\n";
            echo "<td>" . htmlspecialchars($row->getAddress(), ENT_QUOTES, 'UTF-8') . "</td>\n";
            echo "<td>" . htmlspecialchars($row->getPhone(), ENT_QUOTES, 'UTF-8') . "</td>\n";
            echo "<td>" . htmlspecialchars($row->getLabors(), ENT_QUOTES, 'UTF-8') . "</td>\n";
            echo "</tr>\n";
         }
         echo "</table>\n";
      }
   ?>
</body>
</html>