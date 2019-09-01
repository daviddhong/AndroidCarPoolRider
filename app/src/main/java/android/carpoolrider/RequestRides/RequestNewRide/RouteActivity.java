package android.carpoolrider.RequestRides.RequestNewRide;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.carpoolrider.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_AUTOCOMPLETE_ORIGIN = 1;
    private static final int REQUEST_CODE_AUTOCOMPLETE_DESTINATION = 2;
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID_ORIGIN = "route-source-id-origin";
    private static final String ROUTE_SOURCE_ID_DESTINATION = "route-source-id-destination";
    private static final String ICON_LAYER_ID_ORIGIN = "icon-layer-id-origin";
    private static final String ICON_LAYER_ID_DESTINATION = "icon-layer-id-destination";
    private static final String ICON_ID_ORIGIN = "icon-id-origin";
    private static final String ICON_ID_DESTINATION = "icon-id-destination";

    private static final String ORIGIN_LATITUDE = "origin-latitude";
    private static final String ORIGIN_LONGITUDE = "origin-longitude";
    private static final String DESTINATION_LATITUDE = "destination-latitude";
    private static final String DESTINATION_LONGITUDE = "destination-longitude";

    private MapView mapView;
    private MapboxMap mapboxMap;
    Point originPoint;
    Point destinationPoint;
    Double originLatitude;
    Double originLongitude;
    Double destinationLatitude;
    Double destinationLongitude;
    private DirectionsRoute currentRoute;
    private MapboxDirections client;
    TextView textViewOrigin;
    TextView textViewDestination;
    TextView textViewCost;
    String costOfCarpool;
    int distance;
    Double costFormula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.API_KEY));
        setContentView(R.layout.activity_request_new_ride_route);

        // MODIFIES: this
        // EFFECTS: Generate map from Mapbox API.
        mapView = findViewById(R.id.request_carpool_map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        initBack();
        initNextActivity();
    }

    private void initBack() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.close_button_request_new_carpool_one);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/ekyho/cjzj77q4n4oq21coclc7pb3qj"),
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        initSearchOrigin();

                        initSearchDestination();

                        initSourceOrigin(style);

                        initLayerOrigin(style);

                        initSourceDestination(style);

                        initLayerDestination(style);
                    }
                });
    }

    // MODIFIES: this
    // EFFECTS: Helper function for onStyleLoaded. Provides the origin and destination's latitude
    // and longitude. The data is provided using SharedPreferences API.
    private void sharedPreferences() {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);

        originLatitude = Double.longBitsToDouble(sharedPreferences.getLong(ORIGIN_LATITUDE,
                0));

        originLongitude = Double.longBitsToDouble(sharedPreferences.getLong(ORIGIN_LONGITUDE,
                0));

        destinationLatitude = Double.longBitsToDouble(sharedPreferences
                .getLong(DESTINATION_LATITUDE, 0));

        destinationLongitude = Double.longBitsToDouble(sharedPreferences
                .getLong(DESTINATION_LONGITUDE, 0));
    }

    // MODIFIES: this
    // EFFECTS: generate the search function for origin and destination search bar.
    private void initSearchOrigin() {
        RelativeLayout originRelativeLayout = (RelativeLayout) findViewById(R.id
                .origin_request_new_carpool_one);

        originRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(getString(R.string.API_KEY))
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .build(PlaceOptions.MODE_CARDS))
                        .build(RouteActivity.this);

                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE_ORIGIN);
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: generate the search function for origin and destination search bar.
    private void initSearchDestination() {
        RelativeLayout destinationRelativeLayout = (RelativeLayout) findViewById(R.id
                .destination_request_new_carpool_one);

        destinationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(getString(R.string.API_KEY))
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .build(PlaceOptions.MODE_CARDS))
                        .build(RouteActivity.this);

                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE_DESTINATION);
            }
        });
    }

    // EFFECTS: The source for getRoute() data.
    private void initSourceRoute(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID,
                FeatureCollection.fromFeatures(new Feature[]{})));
    }

    // EFFECTS: The layer for the getRoute() data.
    private void initLayerRoute(@NonNull Style loadedMapStyle) {
        LineLayer routeLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);

        routeLayer.setProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(5f),
                lineColor(Color.parseColor("#0A1C2A"))
        );
        loadedMapStyle.addLayer(routeLayer);
    }

    // EFFECTS: The source for the origin data.
    private void initSourceOrigin(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID_ORIGIN));
    }

    // EFFECTS: The layer data for the origin source data.
    private void initLayerOrigin(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage(ICON_ID_ORIGIN, BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.ic_car)));

        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID_ORIGIN,
                ROUTE_SOURCE_ID_ORIGIN).withProperties(
                iconImage(ICON_ID_ORIGIN),
                iconOffset(new Float[]{0f, -8f})
        ));
    }

    // EFFECTS: The source for the destination data.
    private void initSourceDestination(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID_DESTINATION));
    }

    // EFFECTS: The layer data for the destination source data.
    private void initLayerDestination(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage(ICON_ID_DESTINATION, BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.ic_car)));

        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID_DESTINATION,
                ROUTE_SOURCE_ID_DESTINATION).withProperties(
                iconImage(ICON_ID_DESTINATION),
                iconOffset(new Float[]{0f, -8f})
        ));
    }

    // REQUIRES: requestCode, resultCode, and data.
    // MODIFIES: this
    // EFFECTS: Provides the functions and data for after the search activity of the origin and
    // destination takes place.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            CarmenFeature carmenFeatureOrigin = PlaceAutocomplete.getPlace(data);

            CarmenFeature carmenFeatureDestination = PlaceAutocomplete.getPlace(data);

            // EFFECTS: Search for origin.
            if (requestCode == REQUEST_CODE_AUTOCOMPLETE_ORIGIN) {
                if (mapboxMap != null) {
                    Style style = mapboxMap.getStyle();

                    if (style != null) {
                        // EFFECTS: Call the source previously created for origin.
                        GeoJsonSource geoJsonSource = style.getSourceAs(ROUTE_SOURCE_ID_ORIGIN);

                        if (geoJsonSource != null) {
                            geoJsonSource.setGeoJson(FeatureCollection.fromFeatures(
                                    new Feature[]{Feature.fromJson(carmenFeatureOrigin.toJson())}
                            ));
                            // EFFECTS: Create a origin point using carmenFeatureOrigin of where the
                            // search activity locates to. The point is created using LngLat format,
                            // which is important for storing data and retrieving latitude and
                            // longitude values separately.
                            originPoint = Point.fromLngLat(((Point) carmenFeatureOrigin.geometry())
                                    .latitude(), ((Point) carmenFeatureOrigin.geometry())
                                    .longitude());

                            // REQUIRES: Latitude and Longitude values != null.
                            // MODIFIES: this
                            // EFFECTS: Retrieves the latitude and longitude values from originPoint.
                            // Stores the latitude and longitude values into a SharedPreferences Editor.
                            // This can be retrieved later to generate a route.
                            SharedPreferences sharedPrefOrigin = this.getPreferences(Context
                                    .MODE_PRIVATE);
                            SharedPreferences.Editor editorOrigin = sharedPrefOrigin.edit();
                            editorOrigin.putLong(ORIGIN_LATITUDE, Double
                                    .doubleToLongBits(originPoint.latitude()));
                            editorOrigin.putLong(ORIGIN_LONGITUDE, Double
                                    .doubleToLongBits(originPoint.longitude()));
                            editorOrigin.apply();
                        }
                        // EFFECTS: Moves the camera to the new searched location.
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                        .target(new LatLng(((Point) carmenFeatureOrigin.geometry())
                                                .latitude(), ((Point) carmenFeatureOrigin
                                                .geometry()).longitude()))
                                        .zoom(14)
                                        .build()), 4000);
                    }

                    // EFFECTS: The searched location's name will be shown in the origin text view.
                    textViewOrigin = (TextView) findViewById(R.id.second_activity_origin);
                    textViewOrigin.setText(carmenFeatureOrigin.text());

                    // EFFECTS: Remove the previous ROUTE_LAYER_ID and ROUTE_SOURCE_ID to generate
                    // a new ROUTE_LAYER_ID and ROUTE_SOURCE_ID.
                    style.removeLayer(ROUTE_LAYER_ID);
                    style.removeSource(ROUTE_SOURCE_ID);

                    // EFFECTS: Retrieved stored data from another method.
                    sharedPreferences();

                    // EFFECTS: Initialize the new source and route.
                    initSourceRoute(style);
                    initLayerRoute(style);

                    // EFFECTS: Create two points to generate a route.
                    Point origin = Point.fromLngLat(originLatitude, originLongitude);
                    Point destination = Point.fromLngLat(destinationLatitude, destinationLongitude);

                    // EFFECTS: Generate a route with a polyline.
                    getRoute(style, origin, destination);
                }
            }

            if (requestCode == REQUEST_CODE_AUTOCOMPLETE_DESTINATION) {
                if (mapboxMap != null) {
                    Style style = mapboxMap.getStyle();

                    if (style != null) {
                        // EFFECTS: Call the source previous create for destination.
                        GeoJsonSource geoJsonSource = style.getSourceAs(ROUTE_SOURCE_ID_DESTINATION);

                        if (geoJsonSource != null) {
                            geoJsonSource.setGeoJson(FeatureCollection.fromFeatures(
                                    new Feature[]{Feature.fromJson(carmenFeatureDestination.toJson())}
                            ));

                            // EFFECTS: Create a destination point using carmenFeatureOrigin of where the
                            // search activity locates to. The point is created using LngLat format,
                            // which is important for storing data and retrieving latitude and
                            // longitude values separately.
                            destinationPoint = Point.fromLngLat(((Point) carmenFeatureDestination
                                    .geometry()).latitude(), ((Point)
                                    carmenFeatureDestination.geometry()).longitude());

                            // REQUIRES: Latitude and Longitude values != null.
                            // MODIFIES: this
                            // EFFECTS: Retrieves the latitude and longitude values from destinationPoint.
                            // Stores the latitude and longitude values into a SharedPreferences Editor.
                            // This can be retrieved later to generate a route.
                            SharedPreferences sharedPrefDestination = this.getPreferences(Context
                                    .MODE_PRIVATE);
                            SharedPreferences.Editor editorDestination = sharedPrefDestination.edit();
                            editorDestination.putLong(DESTINATION_LATITUDE, Double
                                    .doubleToLongBits(destinationPoint.latitude()));
                            editorDestination.putLong(DESTINATION_LONGITUDE, Double
                                    .doubleToLongBits(destinationPoint.longitude()));
                            editorDestination.apply();
                        }
                        // EFFECTS: Moves the camera to the new searched location.
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                        .target(new LatLng(((Point) carmenFeatureDestination
                                                .geometry()).latitude(), ((Point)
                                                carmenFeatureDestination.geometry()).longitude()))
                                        .zoom(14)
                                        .build()), 4000);
                    }
                    // EFFECTS: The searched location's name will be shown in the destination text view.
                    textViewDestination = (TextView) findViewById(R.id.second_activity_destination);
                    textViewDestination.setText(carmenFeatureDestination.text());

                    // EFFECTS: Remove the previous ROUTE_LAYER_ID and ROUTE_SOURCE_ID to generate
                    // a new ROUTE_LAYER_ID and ROUTE_SOURCE_ID.
                    style.removeLayer(ROUTE_LAYER_ID);
                    style.removeSource(ROUTE_SOURCE_ID);

                    // EFFECTS: Retrieved stored data from another method.
                    sharedPreferences();

                    // EFFECTS: Initialize the new source and route.
                    initSourceRoute(style);
                    initLayerRoute(style);

                    // EFFECTS: Create two points to generate a route.
                    Point origin = Point.fromLngLat(originLatitude, originLongitude);
                    Point destination = Point.fromLngLat(destinationLatitude, destinationLongitude);

                    // EFFECTS: Generate a route with a polyline.
                    getRoute(style, origin, destination);
                }
            }
        }
    }

    // REQUIRES: style, origin, destination.
    // EFFECTS: Creates a route using the layer and source provided.
    private void getRoute(@NonNull final Style style, Point origin, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(getString(R.string.API_KEY))
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                System.out.println(call.request().url().toString());
                Timber.d("Response code:" + response.code());
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");

                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");

                    return;
                }
                // EFFECTS: Returns the most optimal route.
                currentRoute = response.body().routes().get(0);

                // EFFECTS: Provide the earnings from the formula provided. (10 cents per km,
                // 30 cents, and 25% of the price)
                if (currentRoute != null) {
                    distance = currentRoute.distance().intValue();
                    costFormula = ((distance / 1000) * 0.1) + 0.3 + (((distance / 1000) * 0.1)
                            + 0.3) * 0.25;
                    costOfCarpool = costFormula.toString();

                    // EFFECTS: Provide the cost in text.
                    textViewCost = (TextView) findViewById(R.id.text_view_cost);
                    textViewCost.setText(costOfCarpool);
                }

                if (style.isFullyLoaded()) {
                    GeoJsonSource source = style.getSourceAs(ROUTE_SOURCE_ID);

                    if (source != null) {
                        Timber.d("onResponse: source != null");

                        source.setGeoJson(FeatureCollection.fromFeature(
                                Feature.fromGeometry(LineString.fromPolyline(
                                        currentRoute.geometry(), PRECISION_6))));
                    }
                }

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // EFFECTS: Cancel the Directions API request
        if (client != null) {
            client.cancelCall();
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    // EFFECTS: Initialize the next activity and sends data from this activity.
    private void initNextActivity() {
        SharedPreferences sharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
        Double originLatitudeData = Double.longBitsToDouble(sharedPrefs.getLong(ORIGIN_LATITUDE,
                0));
        Double originLongitudeData = Double.longBitsToDouble(sharedPrefs.getLong(ORIGIN_LONGITUDE,
                0));
        Double destinationLatitudeData = Double.longBitsToDouble(sharedPrefs
                .getLong(DESTINATION_LATITUDE, 0));
        Double destinationLongitudeData = Double.longBitsToDouble(sharedPrefs
                .getLong(DESTINATION_LONGITUDE, 0));

        RelativeLayout nextRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_next);
        nextRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RouteActivity.this,
                        PassengerNumberActivity.class);

                intent.putExtra("ORIGIN_LOCATION_STRING_KEY", textViewOrigin.getText());
                intent.putExtra("ORIGIN_LATITUDE_KEY", originLatitudeData);
                intent.putExtra("ORIGIN_LONGITUDE_KEY", originLongitudeData);

                intent.putExtra("DESTINATION_LOCATION_STRING_KEY", textViewDestination.getText());
                intent.putExtra("DESTINATION_LATITUDE_KEY", destinationLatitudeData);
                intent.putExtra("DESTINATION_LONGITUDE_KEY", destinationLongitudeData);

                intent.putExtra("EARNINGS_STRING_KEY", textViewCost.getText());

                startActivity(intent);

                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}