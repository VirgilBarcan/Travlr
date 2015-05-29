var geocoder;
var map;
var infowindow = new google.maps.InfoWindow();
var marker;
function initialize() {
geocoder = new google.maps.Geocoder();
var latlng = new google.maps.LatLng(40.730885,-73.997383);
var mapOptions = {
zoom: 8,
center: latlng,
mapTypeId: 'roadmap'
}
map = new google.maps.Map(document.getElementsByClass('map-canvas'), mapOptions);
google.maps.event.addListener(map, "click", function(event) {
var lat = event.latLng.lat();
var lng = event.latLng.lng();
var latlng = new google.maps.LatLng(lat, lng);
alert("Lat=" + lat + "; Lng=" + lng);
});
}

google.maps.event.addDomListener(window, 'load', initialize);
