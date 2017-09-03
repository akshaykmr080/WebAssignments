                window.fbAsyncInit = function() {
                        FB.init({
                        appId      : '131941503994532',
                        xfbml      : true,
                        version    : 'v2.1'
                });
            };

                (function(d, s, id){
                    var js, fjs = d.getElementsByTagName(s)[0];
                    if (d.getElementById(id)) {return;}
                    js = d.createElement(s); js.id = id;
                    js.src = "//connect.facebook.net/en_US/sdk.js";
                    fjs.parentNode.insertBefore(js, fjs);
                }(document, 'script', 'facebook-jssdk'));


                var locationData = "";

                function clearPage(formId){
                    var d = document.getElementById(formId);
                    document.body.innerHTML="";
                    document.body.appendChild(d);
                }

            function getCurrentLocation(){
                var options = {
                    enableHighAccuracy: true,
                    timeout: 5000,
                    maximumAge: 0
                    };

                function success(pos) {
                var crd = pos.coords;


                    locationData = crd;
            };

                function error(err) {
                console.warn(`ERROR(${err.code}): ${err.message}`);
            };

            navigator.geolocation.getCurrentPosition(success, error, options)
        }
            // define angular module/app
            var formApp = angular.module('formApp', ['angularMoment']);
            //$scope.globalFavouriteData = [];

            // create angular controller and pass in $scope and $http
            function formController($scope, $http) {

              var vm = this;

              // create a new time variable with the current date
              vm.time = new Date();
                // create a blank object to hold our form information
                // $scope will allow this to pass between controller and view
                $scope.formData = {};

               //a $scope.data = false;
                $scope.loading = false;
                $scope.pager = false;
                $scope.tableHeader = false;
                $scope.previousUser = false;
                $scope.nextUser = false;

                $scope.isBooked = function(id){
                    var newData = JSON.parse(localStorage.getItem("FavouritesData"));
                    if(newData != null)
                        return newData.some(function(el) {

                            return el["id"] == id;
                    });

                }

                // process the form
                $scope.processForm = function() {
                   // console.log($scope.formData);
                    $scope.loading = true;
                    $scope.userData = "";
                $scope.pagesData = "";
                $scope.eventsData = "";
                $scope.placesData = "";
                $scope.groupsData = "";
                $scope.usersTab = false;
                $scope.pagesTab = false;
                $scope.placesTab = false;
                $scope.eventsTab = false;
                $scope.groupsTab = false;
                $scope.albumsAndPosts = false;

                $('#myCarousel').carousel(0);
                    navigator.geolocation.getCurrentPosition(function success(pos) {
                    $('#myCarousel').carousel(0);
                    var crd = pos.coords;

                    //console.log("locationData in html "+crd.latitude);
                    $http({
                        method  : 'GET',
                        url     : './index.php?searchValue='+$scope.formData.search+'&latitude='+crd.latitude+'&longitude='+crd.longitude,
                          // pass in data as strings
                        headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  // set the headers so angular passing info as form data (not request payload)
                    })
                        .success(function(jsonData) {
                        $scope.loading = false;
                        $scope.pager = true;
                        $scope.tableHeader = true;
                        $scope.userData = jsonData.users.data;
                        $scope.pagesData = jsonData.pages.data;
                        $scope.eventsData = jsonData.events.data;
                        $scope.placesData = jsonData.places.data;
                        $scope.groupsData = jsonData.groups.data;
                        $scope.usersTab = true;
                        $scope.pagesTab = true;
                        $scope.placesTab = true;
                        $scope.eventsTab = true;
                        $scope.groupsTab = true;
                        $scope.albumsAndPosts = true;
                        window.localStorage.clear();
                        $scope.favouritesData = localStorage.getItem("FavouritesData");
                        try{
                        if("next" in jsonData.users.paging.paging){
                            $scope.nextUser = true;
                            $scope.nextUserLink = jsonData.users.paging.paging.next;
                        }
                      } catch(e){

                      }
                      try{
                        if("previous" in jsonData.users.paging.paging){
                            $scope.previousUser = true;
                            $scope.previousUserLink = jsonData.users.paging.paging.previous;
                        }
                      } catch(e){

                      }
                      try{
                        if("next" in jsonData.pages.paging.paging){
                            $scope.nextPage = true;
                            $scope.nextPageLink = jsonData.pages.paging.paging.next;
                        }
                      } catch(e){

                      }
                      try{
                        if("previous" in jsonData.pages.paging.paging){
                            $scope.previousPage = true;
                            $scope.previousPageLink = jsonData.pages.paging.paging.previous;
                        }
                      } catch(e){

                      }
                      try{
                        if("next" in jsonData.events.paging.paging){
                            $scope.nextEvent = true;
                            $scope.nextEventLink = jsonData.events.paging.paging.next;
                        }
                      } catch(e){

                      }
                      try{
                        if("previous" in jsonData.events.paging.paging){
                            $scope.previousEvent = true;
                            $scope.previousEventLink = jsonData.events.paging.paging.previous;
                        }
                      } catch(e){

                      }
                      try{
                        if("next" in jsonData.groups.paging.paging){
                            $scope.nextGroup = true;
                            $scope.nextGroupLink = jsonData.groups.paging.paging.next;
                        }
                      } catch(e){

                      }
                      try{
                        if("previous" in jsonData.groups.paging.paging){
                            $scope.previousGroup = true;
                            $scope.previousGrouprLink = jsonData.groups.paging.paging.previous;
                        }
                      } catch(e){

                      }
                      try{
                        if("next" in jsonData.places.paging.paging){
                            $scope.nextPlace = true;
                            $scope.nextPlaceLink = jsonData.places.paging.paging.next;
                        }
                      } catch(e){

                      }
                      try{
                        if("previous" in jsonData.places.paging.paging){
                            $scope.previousPlace = true;
                            $scope.previousPlaceLink = jsonData.places.paging.paging.previous;
                        }
                      } catch(e){

                      }


          });

        });
        };

        function toggleBackGround(id){
           // console.log("in toggle background :"+id);
            var background = document.getElementById(id).style.backgroundColor;
            if (background == "rgb(244, 215, 66)") {
                   document.getElementById(id).style.background = "rgb(255,255,255)";
                    if(document.getElementsByName(id).length >0)
                        document.getElementsByName(id)[0].style.background = "rgb(255,255,255)";

            } else {
                    document.getElementById(id).style.background = "rgb(244, 215, 66)";
                    if(document.getElementsByName(id).length>0)
                        document.getElementsByName(id)[0].style.background = "rgb(244, 215, 66)";
    }
        }

        $scope.getDetails = function(row,type) {
                        $scope.albumsData = [];
                        $scope.posts = [];
                        $scope.albumBody = false;
                        $scope.postBody = false;
                        $scope.detailsloading = true;
                        $scope.entireRow = row;
                        $scope.rowType = type;
                        $http({
                        method  : 'POST',
                        url     : './index.php',
                        data    : $.param({detailsId:row.id}),  // pass in data as strings
                        headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  // set the headers so angular passing info as form data (not request payload)
                    })
                        .success(function(jsonData) {

                        $scope.detailsloading = false;
                        albumsArray = [];
                            postArray = [];
                            $scope.albumsData = [];
                            $scope.userName = '';
                            $scope.imageAddress = '';
                            $scope.posts = [];
                            $scope.albumBody = true;
                            $scope.postBody = true;
                            try{
                        if("albums" in jsonData){
                            console.log("albums found");
                            albumsArray = jsonData["albums"];
                            $scope.noalbumsData = false;
                          //  $scope.albumPanalBody = true;
                            if(albumsArray.length > 0){
                                $scope.albumsData = albumsArray;
                            } else {
                                $scope.noalbumsData = true;
                            }
                        }  else {
                            $scope.noalbumsData = true;
                        }
                      } catch(e){
                        $scope.albumsData = [];
                        $scope.userName = '';

                        $scope.detailsloading = false;
                        $scope.noalbumsData = true;
                        //$scope.albumPanalBody = true;
                      }
                          try{
                            if("posts" in jsonData){
                                $scope.userName = jsonData.name;
                                $scope.imageAddress = jsonData.picture.url;
                                postArray = jsonData["posts"];
                                if(postArray.length >0){
                                    $scope.posts = postArray;
                                    $scope.noposts = false;
                                    console.log("postArray found");
                                }else {
                                    console.log("no postArray found");
                                    $scope.noposts = true;
                                }
                            } else {
                                $scope.noposts = true;
                            }
                          } catch(e){
                            $scope.userName = '';
                            $scope.imageAddress = '';
                            $scope.posts = [];
                            $scope.detailsloading = false;
                            $scope.noposts = true;
                            $scope.noalbumsData = true;
                          //  $scope.albumPanalBody = true;
                          }

          }).error(function(err){
                            $scope.albumsData = [];
                            $scope.userName = '';
                            $scope.imageAddress = '';
                            $scope.posts = [];
                            $scope.detailsloading = false;
                            $scope.noposts = true;
                            $scope.noalbumsData = true;
                            $scope.albumBody = true;
                            $scope.postBody = true;
                            console.log("all set");

          });
                }

            $scope.resetForm = function(){
                    document.getElementById('form').reset();
                    $scope.userData = "";
                $scope.pagesData = "";
                $scope.eventsData = "";
                $scope.placesData = "";
                $scope.groupsData = "";
                $scope.usersTab = false;
                $scope.pagesTab = false;
                $scope.placesTab = false;
                $scope.eventsTab = false;
                $scope.groupsTab = false;
                $scope.albumsAndPosts = false;
                $('#myCarousel').carousel(0);
                    //$scope.tableData = false;
                   // window.location = "index.html";
                }

            $scope.hidePanalData = function(){
              //  scope.albumPanalBody = false;

            }

                $scope.addToLocalStorage = function (x,type) {
                    x["typeData"] = type;
                    //console.log(x);
                    exists = false;

                   //toggleBackGround(x["id"]);

                   var old = localStorage.getItem("FavouritesData");
                    if(old === null)
                    {
                        old = [];
                        old.push(x);
                        localStorage.setItem("FavouritesData",JSON.stringify(old));
                    } else {
                        newData = JSON.parse(old);
                        console.log(newData);
                        removedData = newData.filter(function(el) {
                        //console.log("id from localstorage "+el["id"]);
                        return el["id"] !== x["id"];
                    });


                if(removedData.length == newData.length){
                    newData.push(x);

                    localStorage.setItem("FavouritesData",JSON.stringify(newData));
                } else {
                    localStorage.setItem("FavouritesData",JSON.stringify(removedData));
                }



            }
            //localStorage.setItem("FavouritesData", old+x);
                    //console.log("Data from local strage "+JSON.parse(JSON.stringify(localStorage.getItem("FavouritesData"))));
            $scope.favouritesData = JSON.parse(localStorage.getItem("FavouritesData"));
           // console.log("FavouritesData is :")
            //console.log(localStorage.getItem("FavouritesData"));
            };

             $scope.deleteFromLocalStorage = function(id,index){
                 //toggleBackGround(id);
                 data = JSON.parse(localStorage.getItem("FavouritesData"));
                 data.splice(index,1);
                 localStorage.setItem("FavouritesData",JSON.stringify(data));
                 $scope.favouritesData = JSON.parse(localStorage.getItem("FavouritesData"));
             }

             $scope.displayFBPopUp = function(image,userName){
                // console.log("imageURL: "+image);
                // console.log("username :"+userName);
                 FB.ui({
                    app_id: "131941503994532",
                    method: 'feed',
                    link: window.location.href,
                    picture: image,
                    name: userName,
                    caption: "FB Search From USC CSCI571",
                    }, function(response){
                        if (response && !response.error_message)
                            alert("Posted successfully");
                        else
                            alert("Failed to post");
                });

             }

             $scope.getNextOrPrevious = function(link,type){
                            $http({
                        method  : 'GET',
                        url     : link
                    })
                        .success(function(jsonData) {
                            if(type == 'users'){
                                $scope.userData = jsonData.data;
                                $scope.nextUser = false;
                                $scope.nextUserLink = '';
                                $scope.previousUser = false;
                                $scope.previousUserLink = '';
                                try{
                                if("next" in jsonData.paging){
                                    $scope.nextUser = true;
                                    $scope.nextUserLink = jsonData.paging.next;
                                }
                              } catch(e){

                              } try{
                                if("previous" in jsonData.paging){
                                    $scope.previousUser = true;
                                    $scope.previousUserLink = jsonData.paging.previous;
                                }
                              } catch(e){

                              }
                            } else if(type == 'pages'){
                                $scope.pagesData = jsonData.data;
                                $scope.nextPage = false;
                                $scope.nextPageLink = '';
                                $scope.previousPage = false;
                                $scope.previousPageLink = '';
                                try{
                                if("next" in jsonData.paging){
                                    $scope.nextPage = true;
                                    $scope.nextPageLink = jsonData.paging.next;
                                }
                              } catch(e){

                              }
                              try{
                                if("previous" in jsonData.paging){
                                    $scope.previousPage = true;
                                    $scope.previousPageLink = jsonData.paging.previous;
                                }
                              } catch(e){

                              }
                            } else if(type == 'events'){
                                $scope.eventsData = jsonData.data;
                                $scope.nextEvent = false;
                                $scope.nextEventLink = '';
                                $scope.previousEvent = false;
                                $scope.previousEventLink = '';
                                try{
                                if("next" in jsonData.paging){
                                    $scope.nextEvent = true;
                                    $scope.nextEventLink = jsonData.paging.next;
                                }
                              }catch(e) {

                              }
                              try{
                                if("previous" in jsonData.paging){
                                    $scope.previousEvent = true;
                                    $scope.previousEventLink = jsonData.paging.previous;
                                }
                              } catch(e){

                              }
                            } else if(type == 'places'){
                                $scope.placesData = jsonData.data;
                                $scope.nextPlace = false;
                                $scope.nextPlaceLink = '';
                                $scope.previousPlace = false;
                                $scope.previousPlaceLink = '';
                                try{
                                if("next" in jsonData.paging){
                                    $scope.nextPlace = true;
                                    $scope.nextPlaceLink = jsonData.paging.next;
                                }
                              } catch(e){

                              }
                              try{
                                if("previous" in jsonData.paging){
                                    $scope.previousPlace = true;
                                    $scope.previousPlaceLink = jsonData.paging.previous;
                                }
                              } catch(e){

                              }
                            } else if(type == 'groups'){
                                $scope.groupsData = jsonData.data;
                                $scope.nextGroup = false;
                                $scope.nextGroupLink= '';
                                $scope.previousGroup = false;
                                $scope.previousGrouprLink = '';
                                try{
                                if("next" in jsonData.paging){
                                    $scope.nextGroup = true;
                                    $scope.nextGroupLink = jsonData.paging.next;
                                }
                              } catch(e){

                              }
                              try{
                                if("previous" in jsonData.paging){
                                    $scope.previousGroup = true;
                                    $scope.previousGrouprLink = jsonData.paging.previous;
                                }
                              } catch(e){

                              }
                            }

                    }).error(function(err){
                            console.log(err);
                            $scope.albumsData = [];
                            $scope.userName = '';
                            $scope.imageAddress = '';
                            $scope.posts = [];

          });

             }

             $scope.changeCorousel = function() {
                // console.log("event caught in angular");
                    $('#myCarousel').carousel(0);
             }

        }

            $("#eventId").click(function(){
               $("#eventId").attr("href","#myCarousel");
            });

                $('a[data-toggle="tab"]').click(function(){
                   // console.log("event caught");
                    $('#myCarousel').carousel(0);

                });

                $('ul li').hover(function(){
                    console.log("hello123");
                    var color  = $(this).css("background-color");

                    $(this).css("background", "#386785");

                    $(this).bind("mouseout", function(){
                    $(this).css("background", color);
                })
            })
