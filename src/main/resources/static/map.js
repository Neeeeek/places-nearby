// Icons icons
var icons = {
    pizza: {
        icon: 'icons/pizza1.png'
    },
    kebab: {
        icon: 'icons/meat.png'
    },
    sushi: {
        icon: 'icons/sushi.png'
    },
    pub: {
        icon: 'icons/bar.png'
    },
    restaurant: {
        icon: 'icons/rest.png'
    },
    location: {
        icon: 'icons/location-pin.png'
    }

};
// POST method implementation:
async function postData(url = '', data) {
    // Default options are marked with *
    const response = await fetch(url, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            //'Content-Type': 'application/json'
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        //body: JSON.stringify(data) // body data type must match "Content-Type" header
        body: data
    });
    return response.json(); // parses JSON response into native JavaScript objects
}

//Type choice
function choose(choice){
    type = choice;
}

// Map initialization
function initMap() {

    map = new google.maps.Map(document.getElementById('map'), {
        mapId: "843975716d0ab4d2",
        center: {lat: 51.1517799, lng: 17.0262679},
        zoom: 13
    });

    infoWindow = new google.maps.InfoWindow;

    // Try HTML5 geolocation.
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };

            infoWindow.setPosition(pos);
            infoWindow.setContent('You are here!');
            infoWindow.open(map);
            map.setCenter(pos);
        }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
        });
    } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
    }


    map.addListener('click', function(mapsMouseEvent) {
            // if type undefined dont do anything
        if (typeof type !== 'undefined') {


            deleteMarkers();
            var myLatLng = mapsMouseEvent.latLng;
            addMarker(myLatLng, ' ', 'location');

            postData('/places', 'lat=' + myLatLng.lat() +'&lon=' +myLatLng.lng() + "&type="+type)
                .then(data => {
                    places = data;
                    places.forEach(value => {
                        addMarker({lat: value.lat, lng: value.lon}, value.name + "(" + value.rating + ")", type);
                    });

                });
        }


    });


}


// Adding marker
function addMarker(location, label, type) {
    var marker = new google.maps.Marker({
        position: location,
        label: {
          text: label,
          fontFamily: "Segoe UI"
        },
        icon: {
            url: icons[type].icon,
            scaledSize: new google.maps.Size(20, 24),
            labelOrigin: new google.maps.Point(10, -10)
        },

        map: map

    });
    markers.push(marker);
}

// Sets the map on all markers in the array.
function setMapOnAll(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

// Shows any markers currently in the array.
function showMarkers() {
    setMapOnAll(map);
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
    setMapOnAll(null);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
    clearMarkers();
    markers = [];
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
    infoWindow.open(map);
}