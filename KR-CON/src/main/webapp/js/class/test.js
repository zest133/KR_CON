(function(){
  
  //angular module
  var myApp = angular.module('myApp', ['angularTreeview']);

  //test controller
  myApp.controller('myController', function($scope){

  	
      
    //roleList1 to treeview
    $scope.roleList = [
        {
            "type": "genre",
            "id": "C00001",
            "key": "childrens",
            "title": "Children's",
            "narrower": [
                {
                    "type": "genre",
                    "id": "C00002",
                    "key": "activities",
                    "title": "Activities",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00004",
                    "key": "drama",
                    "title": "Drama",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00003",
                    "key": "entertainmentandcomedy",
                    "title": "Entertainment & Comedy",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00005",
                    "key": "factual",
                    "title": "Factual",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00006",
                    "key": "music",
                    "title": "Music",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00007",
                    "key": "news",
                    "title": "News",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00008",
                    "key": "sport",
                    "title": "Sport",
                    "has_topic_page": false,
                    "sameAs": null
                }
            ],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        },
        {
            "type": "genre",
            "id": "C00193",
            "key": "comedy",
            "title": "Comedy",
            "narrower": [
                {
                    "type": "genre",
                    "id": "C00202",
                    "key": "character",
                    "title": "Character",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00194",
                    "key": "impressionists",
                    "title": "Impressionists",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00203",
                    "key": "music",
                    "title": "Music",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00195",
                    "key": "satire",
                    "title": "Satire",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00196",
                    "key": "sitcoms",
                    "title": "Sitcoms",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00197",
                    "key": "sketch",
                    "title": "Sketch",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00198",
                    "key": "spoof",
                    "title": "Spoof",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00199",
                    "key": "standup",
                    "title": "Standup",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00204",
                    "key": "stunt",
                    "title": "Stunt",
                    "has_topic_page": false,
                    "sameAs": null
                }
            ],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        },
        {
            "type": "genre",
            "id": "C00017",
            "key": "drama",
            "title": "Drama",
            "narrower": [
                {
                    "type": "genre",
                    "id": "C00018",
                    "key": "actionandadventure",
                    "title": "Action & Adventure",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00019",
                    "key": "biographical",
                    "title": "Biographical",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00020",
                    "key": "classicandperiod",
                    "title": "Classic & Period",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00021",
                    "key": "crime",
                    "title": "Crime",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00024",
                    "key": "historical",
                    "title": "Historical",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00025",
                    "key": "horrorandsupernatural",
                    "title": "Horror & Supernatural",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00026",
                    "key": "legalandcourtroom",
                    "title": "Legal & Courtroom",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00028",
                    "key": "medical",
                    "title": "Medical",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00029",
                    "key": "musical",
                    "title": "Musical",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00031",
                    "key": "political",
                    "title": "Political",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00032",
                    "key": "psychological",
                    "title": "Psychological",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00034",
                    "key": "relationshipsandromance",
                    "title": "Relationships & Romance",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00035",
                    "key": "scifiandfantasy",
                    "title": "SciFi & Fantasy",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00036",
                    "key": "soaps",
                    "title": "Soaps",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00033",
                    "key": "spiritual",
                    "title": "Spiritual",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00037",
                    "key": "thriller",
                    "title": "Thriller",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00038",
                    "key": "waranddisaster",
                    "title": "War & Disaster",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00039",
                    "key": "western",
                    "title": "Western",
                    "has_topic_page": false,
                    "sameAs": null
                }
            ],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        },
        {
            "type": "genre",
            "id": "C00200",
            "key": "entertainment",
            "title": "Entertainment",
            "narrower": [
                {
                    "type": "genre",
                    "id": "C00201",
                    "key": "varietyshows",
                    "title": "Variety Shows",
                    "has_topic_page": false,
                    "sameAs": null
                }
            ],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        },
        {
            "type": "genre",
            "id": "C00045",
            "key": "factual",
            "title": "Factual",
            "narrower": [
                {
                    "type": "genre",
                    "id": "C00048",
                    "key": "antiques",
                    "title": "Antiques",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00046",
                    "key": "artscultureandthemedia",
                    "title": "Arts, Culture & the Media",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00049",
                    "key": "beautyandstyle",
                    "title": "Beauty & Style",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00052",
                    "key": "carsandmotors",
                    "title": "Cars & Motors",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00054",
                    "key": "consumer",
                    "title": "Consumer",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00062",
                    "key": "crimeandjustice",
                    "title": "Crime & Justice",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00124",
                    "key": "disability",
                    "title": "Disability",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00056",
                    "key": "familiesandrelationships",
                    "title": "Families & Relationships",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00057",
                    "key": "foodanddrink",
                    "title": "Food & Drink",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00059",
                    "key": "healthandwellbeing",
                    "title": "Health & Wellbeing",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00060",
                    "key": "history",
                    "title": "History",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00061",
                    "key": "homesandgardens",
                    "title": "Homes & Gardens",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00050",
                    "key": "lifestories",
                    "title": "Life Stories",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00051",
                    "key": "money",
                    "title": "Money",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00047",
                    "key": "petsandanimals",
                    "title": "Pets & Animals",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00063",
                    "key": "politics",
                    "title": "Politics",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00064",
                    "key": "scienceandnature",
                    "title": "Science & Nature",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00065",
                    "key": "travel",
                    "title": "Travel",
                    "has_topic_page": false,
                    "sameAs": null
                }
            ],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        },
        {
            "type": "genre",
            "id": "C00040",
            "key": "learning",
            "title": "Learning",
            "narrower": [
                {
                    "type": "genre",
                    "id": "C00044",
                    "key": "adults",
                    "title": "Adults",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00041",
                    "key": "preschool",
                    "title": "Pre-School",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00042",
                    "key": "primary",
                    "title": "Primary",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00043",
                    "key": "secondary",
                    "title": "Secondary",
                    "has_topic_page": false,
                    "sameAs": null
                }
            ],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        },
        {
            "type": "genre",
            "id": "C00066",
            "key": "music",
            "title": "Music",
            "narrower": [
                {
                    "type": "genre",
                    "id": "C00068",
                    "key": "classicpopandrock",
                    "title": "Classic Pop & Rock",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00069",
                    "key": "classical",
                    "title": "Classical",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00129",
                    "key": "country",
                    "title": "Country",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00070",
                    "key": "danceandelectronica",
                    "title": "Dance & Electronica",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00071",
                    "key": "easylisteningsoundtracksandmusicals",
                    "title": "Easy Listening, Soundtracks & Musicals",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00136",
                    "key": "folk",
                    "title": "Folk",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00077",
                    "key": "hiphoprnbanddancehall",
                    "title": "Hip Hop, RnB & Dancehall",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00138",
                    "key": "jazzandblues",
                    "title": "Jazz & Blues",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00075",
                    "key": "popandchart",
                    "title": "Pop & Chart",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00076",
                    "key": "rockandindie",
                    "title": "Rock & Indie",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00142",
                    "key": "soulandreggae",
                    "title": "Soul & Reggae",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00078",
                    "key": "world",
                    "title": "World",
                    "has_topic_page": false,
                    "sameAs": null
                }
            ],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        },
        {
            "type": "genre",
            "id": "C00079",
            "key": "news",
            "title": "News",
            "narrower": [],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        },
        {
            "type": "genre",
            "id": "C00080",
            "key": "religionandethics",
            "title": "Religion & Ethics",
            "narrower": [],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        },
        {
            "type": "genre",
            "id": "C00081",
            "key": "sport",
            "title": "Sport",
            "narrower": [
                {
                    "type": "genre",
                    "id": "C00206",
                    "key": "alpineskiing",
                    "title": "Alpine Skiing",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00205",
                    "key": "americanfootball",
                    "title": "American Football",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00175",
                    "key": "archery",
                    "title": "Archery",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00094",
                    "key": "athletics",
                    "title": "Athletics",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00098",
                    "key": "badminton",
                    "title": "Badminton",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00176",
                    "key": "baseball",
                    "title": "Baseball",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00099",
                    "key": "basketball",
                    "title": "Basketball",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00207",
                    "key": "biathlon",
                    "title": "Biathlon",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00208",
                    "key": "bobsleigh",
                    "title": "Bobsleigh",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00100",
                    "key": "bowls",
                    "title": "Bowls",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00093",
                    "key": "boxing",
                    "title": "Boxing",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00177",
                    "key": "canoeing",
                    "title": "Canoeing",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00109",
                    "key": "commonwealthgames",
                    "title": "Commonwealth Games",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00085",
                    "key": "cricket",
                    "title": "Cricket",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00209",
                    "key": "crosscountryskiing",
                    "title": "Cross Country Skiing",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00210",
                    "key": "curling",
                    "title": "Curling",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00097",
                    "key": "cycling",
                    "title": "Cycling",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00101",
                    "key": "darts",
                    "title": "Darts",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00114",
                    "key": "disabilitysport",
                    "title": "Disability Sport",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00178",
                    "key": "diving",
                    "title": "Diving",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00102",
                    "key": "equestrian",
                    "title": "Equestrian",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00179",
                    "key": "fencing",
                    "title": "Fencing",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00211",
                    "key": "figureskating",
                    "title": "Figure Skating",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00082",
                    "key": "football",
                    "title": "Football",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00192",
                    "key": "formulaone",
                    "title": "Formula One",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00212",
                    "key": "freestyleskiing",
                    "title": "Freestyle Skiing",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00115",
                    "key": "gaelicgames",
                    "title": "Gaelic Games",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00091",
                    "key": "golf",
                    "title": "Golf",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00116",
                    "key": "gymnastics",
                    "title": "Gymnastics",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00180",
                    "key": "handball",
                    "title": "Handball",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00103",
                    "key": "hockey",
                    "title": "Hockey",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00096",
                    "key": "horseracing",
                    "title": "Horse Racing",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00104",
                    "key": "icehockey",
                    "title": "Ice Hockey",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00117",
                    "key": "judo",
                    "title": "Judo",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00213",
                    "key": "luge",
                    "title": "Luge",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00181",
                    "key": "modernpentathlon",
                    "title": "Modern Pentathlon",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00092",
                    "key": "motorsport",
                    "title": "Motorsport",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00214",
                    "key": "nordiccombined",
                    "title": "Nordic Combined",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00111",
                    "key": "olympics",
                    "title": "Olympics",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00105",
                    "key": "rowing",
                    "title": "Rowing",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00089",
                    "key": "rugbyleague",
                    "title": "Rugby League",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00087",
                    "key": "rugbyunion",
                    "title": "Rugby Union",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00106",
                    "key": "sailing",
                    "title": "Sailing",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00112",
                    "key": "shinty",
                    "title": "Shinty",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00119",
                    "key": "shooting",
                    "title": "Shooting",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00215",
                    "key": "shorttrackskating",
                    "title": "Short Track Skating",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00216",
                    "key": "skeleton",
                    "title": "Skeleton",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00217",
                    "key": "skijumping",
                    "title": "Ski Jumping",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00095",
                    "key": "snooker",
                    "title": "Snooker",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00219",
                    "key": "snowboarding",
                    "title": "Snowboarding",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00182",
                    "key": "softball",
                    "title": "Softball",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00218",
                    "key": "speedskating",
                    "title": "Speed Skating",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00108",
                    "key": "swimming",
                    "title": "Swimming",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00120",
                    "key": "tabletennis",
                    "title": "Table Tennis",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00183",
                    "key": "taekwondo",
                    "title": "Taekwondo",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00090",
                    "key": "tennis",
                    "title": "Tennis",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00121",
                    "key": "triathlon",
                    "title": "Triathlon",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00184",
                    "key": "volleyball",
                    "title": "Volleyball",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00185",
                    "key": "waterpolo",
                    "title": "Water Polo",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00122",
                    "key": "weightlifting",
                    "title": "Weightlifting",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00110",
                    "key": "winterolympics",
                    "title": "Winter Olympics",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00123",
                    "key": "wintersports",
                    "title": "Winter Sports",
                    "has_topic_page": false,
                    "sameAs": null
                },
                {
                    "type": "genre",
                    "id": "C00186",
                    "key": "wrestling",
                    "title": "Wrestling",
                    "has_topic_page": false,
                    "sameAs": null
                }
            ],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        },
        {
            "type": "genre",
            "id": "C00113",
            "key": "weather",
            "title": "Weather",
            "narrower": [],
            "broader": {},
            "has_topic_page": false,
            "sameAs": null
        }
    ];
  
  });
  
})();


/*
	@license Angular Treeview version 0.1.4
	ⓒ 2013 AHN JAE-HA http://github.com/eu81273/angular.treeview
	License: MIT
*/

(function(e){e.module("angularTreeview",[]).directive("treeModel",function($compile){return{restrict:"A",link:function(a,g,b){var f=b.treeModel,d=b.nodeLabel||"label",c=b.nodeChildren||"children",d='<ul><li data-ng-repeat="node in '+f+'"><i class="collapsed" data-ng-show="node.'+c+'.length && node.collapsed" data-ng-click="selectNodeHead(node)"></i><i class="expanded" data-ng-show="node.'+c+'.length && !node.collapsed" data-ng-click="selectNodeHead(node)"></i><i class="normal" data-ng-hide="node.'+c+'.length"></i> <span data-ng-class="node.selected" data-ng-click="selectNodeLabel(node)">{{node.'+
d+'}}</span><div data-ng-hide="node.collapsed" data-tree-model="node.'+c+'" data-node-id='+(b.nodeId||"id")+" data-node-label="+d+" data-node-children="+c+"></div></li></ul>";f&&f.length&&(b.angularTreeview&&(a.selectNodeHead=a.selectNodeHead||function(a){a.collapsed=!a.collapsed},a.selectNodeLabel=a.selectNodeLabel||function(b){a.currentNode&&a.currentNode.selected&&(a.currentNode.selected=void 0);b.selected="selected";a.currentNode=b}),g.html(null).append($compile(d)(a)))}}})})(angular);