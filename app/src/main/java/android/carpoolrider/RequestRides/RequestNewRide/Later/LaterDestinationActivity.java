package android.carpoolrider.RequestRides.RequestNewRide.Later;

import android.app.Activity;
import android.carpoolrider.R;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
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
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class LaterDestinationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_AUTOCOMPLETE_DESTINATION = 1;
    private static final String DESTINATION_PIN_ICON_ID = "destination-pin-icon-id";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private String geoJsonSourceLayerIDDestination = "geoJsonSourceLayerIDDestination";
    private Point destination;
    private String APIKEY;
    TextView textViewDestination;

    RelativeLayout nextActivityRelativeLayout;
    ImageView backRequestNewRideDestinationActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APIKEY  = getString(R.string.API_KEY);

        // EFFECTS: Mapbox access token is configured here. This needs to nbe called either in your
        // application object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, "" + APIKEY);
        setContentView(R.layout.activity_request_new_ride_destination_later);

        // EFFECTS: Initialize MapView.
        mapView = findViewById(R.id.map_view_destination);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        setBackRequestNewRideWhereActivity();

        setNextActivityRelativeLayout();
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                initSearchDestination();
                initSourceDestination(style);
                initLayerDestination(style);
            }
        });
    }

    private void initSearchDestination() {
        RelativeLayout destinationRelativeLayout = (RelativeLayout) findViewById(R.id.destination);
        destinationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken("" + APIKEY)
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .build(PlaceOptions.MODE_CARDS))
                        .build(LaterDestinationActivity.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE_DESTINATION);
            }
        });
    }

    private void initSourceDestination(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geoJsonSourceLayerIDDestination));
    }

    private void initLayerDestination(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage(DESTINATION_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.ic_location_marker)
        ));
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ORIGIN_ID",
                geoJsonSourceLayerIDDestination).withProperties(
                iconImage(DESTINATION_PIN_ICON_ID),
                iconOffset(new Float[]{0f, -8f})
        ));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        CarmenFeature featureDestination = PlaceAutocomplete.getPlace(data);
        textViewDestination = (TextView) findViewById(R.id.text_view_destination);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE_DESTINATION) {
            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource geoJsonSource = style.getSourceAs(geoJsonSourceLayerIDDestination);
                    if (geoJsonSource != null) {
                        geoJsonSource.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(featureDestination.toJson())}
                        ));
                    }

                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) featureDestination.geometry()).latitude(),
                                            ((Point) featureDestination.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);
                }
            }

            destination = Point.fromLngLat(((Point) featureDestination.geometry()).latitude(),
                    ((Point) featureDestination.geometry()).longitude());

            Toast.makeText(this, featureDestination.text(), Toast.LENGTH_LONG).show();
        }

        textViewDestination.setText(featureDestination.text());

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    // EFFECTS: Set OnClickActivity for backActivity.
    private void setBackRequestNewRideWhereActivity() {
        backRequestNewRideDestinationActivity = (ImageView) findViewById(R.id.ic_back_activity_destination);
        backRequestNewRideDestinationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    // EFFECTS: Set OnClickActivity for nextActivity
    private void setNextActivityRelativeLayout() {
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_where_next_destination);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {

            Bundle bundle = getIntent().getExtras();
            String origin = bundle.getString("ORIGIN");

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterDestinationActivity.this,
                        LaterWhenActivity.class);
                intent.putExtra("DESTINATION", textViewDestination.getText().toString());
                intent.putExtra("ORIGIN", origin);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
