    <?php
        require_once __DIR__ . '/php-graph-sdk-5.0.0/src/Facebook/autoload.php';
        header("Access-Control-Allow-Origin: *");
        header("Access-Control-Allow-Headers: Content-type");
        header("Access-Control-Allow-Methods: ['OPTIONS','GET','POST']");
       header('Content-Type: application/json');

define("ACCESS_TOKEN","EAAB4AAZA6CqQBAE7ia3jlYmhWdwEKFB9vXGImbZCfdDEiZBCAAW0LO0rvBXTiJZBhZBpbn0uC0epNpBHDZBfpabUNVEEKaWOYuHhrUWEmZAHtOMTDZAnx3cPsvjYZBqX1RZBVUZCKO1nl65theGGrhIZAZBCzZBzz01EM1TW4ZD");
          define("APP_ID","131941503994532");
          define("APP_SECRET","4aa883608a99e2616d7f8acf0aa0cbed");
          define("GOOGLE_KEY","AIzaSyAlBfe7wlIDM2gYVQaMEd4Dma3qbecOu60");

function getGoogleResponse($location){

    try{
        if($location != ""){
            $googlemaps_url = "https://maps.googleapis.com/maps/api/geocode/json?address=".$location."&key=".GOOGLE_KEY;

            $result = file_get_contents($googlemaps_url);
            $result = json_decode($result);
        } else {
        return;
        }
    } catch(Exception $e){
        echo "error in google query. Additional Info: ".$e->getMessage();
    }
    $current = "";
    if("OK" == $result->status){
        $current = $result->results[0]->geometry->location->lat.",".$result->results[0]->geometry->location->lng;
    }
    return $current;
}


function getFacebookData($fb,$url,$q,$type,$fields,$center=0,$distance=0){
  $urlData = $url."?q=".$q."&type=".$type."&fields=".$fields."&access_token=".ACCESS_TOKEN;

  if($center != 0){
    $urlData .= "&center=".$center;
  }
  if($distance != 0){
    $urlData .= "&distance=".$distance;
  }
  try {
    $response = $fb->get($urlData);
    $userNode = $response->getGraphEdge();
    $pagingData = $response->getGraphEdge()->getMetaData();
     // var_dump($pagingData);
      $returnData = array(
        "data" => json_decode($userNode),
          "paging" => $pagingData
      );
      //$userPaging = $response->getMetadata()->getMetaData();
     // print $userNode;
  } catch(Facebook\Exceptions\FacebookResponseException $e) {
    // When Graph returns an error
    header("HTTP/1.1 404 Internal Server Error");
    echo '{"data": "Exception occurred: '.$e->getMessage().'"}';
    exit;
  } catch(Facebook\Exceptions\FacebookSDKException $e) {
    // When validation fails or other local issues
    header("HTTP/1.1 404 Internal Server Error");
    echo '{"data": "Exception occurred: '.$e->getMessage().'"}';
    exit;
  }
 // return $userNode.",".$pagingData;
    return $returnData;
}

function highResolutionPicCall($fb,$id){
  $urlData = "/".$id."/picture?redirect=false";
  try {
    $response = $fb->get($urlData);
    $userNode = $response->getGraphNode();
    $json = json_decode($userNode);
    return $json->url;
  } catch(Exception $e){
    echo 'Graph returned an error: ' . $e->getMessage();
    exit;
  }
}


function makeDetailsFacebookCall($fb,$id){
  $urlData = "/".$id."?fields=id,name,picture.width(700).height(700),albums.limit(5){name,photos.limit(2){name, picture}},posts.limit(5)&access_token=".ACCESS_TOKEN;
  try {
    $response = $fb->get($urlData);
    $json = $response->getGraphNode();
  } catch(Facebook\Exceptions\FacebookResponseException $e) {
    header("HTTP/1.1 404 Internal Server Error");
    echo '{"data": "Exception occurred: '.$e->getMessage().'"}';
    exit;
  } catch(Facebook\Exceptions\FacebookSDKException $e) {
    header("HTTP/1.1 404 Internal Server Error");
    echo '{"data": "Exception occurred: '.$e->getMessage().'"}';
    exit;
  }
  return $json;
}

    date_default_timezone_set("America/Los_Angeles");

    $fb = new Facebook\Facebook([
      'app_id' => APP_ID,
      'app_secret' => APP_SECRET,
      'default_graph_version' => 'v2.5',
    ]);
   // $fb->setDefaultAccessToken(ACCESS_TOKEN);
if(isset($_POST['detailsId'])){
    $jsonDetailsData = makeDetailsFacebookCall($fb,$_POST['detailsId']);
    echo $jsonDetailsData;
    flush();
}
if(isset($_GET['searchValue'])){
    $center = $_GET['latitude'].",".$_GET['longitude'];
    //$center = getGoogleResponse($_POST['location']);
    $users = getFacebookData($fb,"/search",$_GET['searchValue'],"user",'id,name,picture.width(700).height(700)');
    $pages = getFacebookData($fb,"/search",$_GET['searchValue'],"page",'id,name,picture.width(700).height(700)');
    $event = getFacebookData($fb,"/search",$_GET['searchValue'],"event",'id,name,picture.width(700).height(700)');
    $place = getFacebookData($fb,"/search",$_GET['searchValue'],"place",' id,name,picture.width(700).height(700)',$center);
    $group = getFacebookData($fb,"/search",$_GET['searchValue'],"group",'id,name,picture.width(700).height(700)');


    $mergedData = array("users" => $users,"pages" => $pages,"events" => $event,"places"=>$place,"groups" =>$group);
    echo json_encode($mergedData);
    flush();
}

?>
