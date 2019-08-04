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
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class LaterOriginActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_AUTOCOMPLETE_ORIGIN = 1;
    private static final String ORIGIN_PIN_ICON_ID = "origin-pin-icon-id";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private String geoJsonSourceLayerIDOrigin = "geoJsonSourceLayerIDOrigin";
    private Point origin;
     private String APIKEY;


    ImageView backRequestNewRideOriginActivity;
    RelativeLayout nextActivityRelativeLayout;
    TextView textViewOrigin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         APIKEY = getString(R.string.API_KEY);

        // EFFECTS: Mapbox access token is configured here. This needs to nbe called either in your
        // application object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, "" + APIKEY);
        setContentView(R.layout.activity_request_new_ride_origin_later);

        // EFFECTS: Initialize MapView.
        mapView = findViewById(R.id.map_view_origin);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // EFFECTS: Call setBackRequestNewRideOriginActivity.
        setBackRequestNewRideWhereActivity();

        // EFFECTS: Call setNextActivity.
        setNextActivityRelativeLayout();
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                initSearchOrigin();
                initSourceOrigin(style);
                initLayerOrigin(style);
            }
        });
    }

    private void initSearchOrigin() {
        RelativeLayout originRelativeLayout = (RelativeLayout) findViewById(R.id.origin);
        originRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken("" + APIKEY)
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .build(PlaceOptions.MODE_CARDS))
                        .build(LaterOriginActivity.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE_ORIGIN);
            }
        });
    }

    private void initSourceOrigin(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geoJsonSourceLayerIDOrigin));
    }

    private void initLayerOrigin(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage(ORIGIN_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.ic_location_marker)
        ));
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ORIGIN_ID",
                geoJsonSourceLayerIDOrigin).withProperties(
                iconImage(ORIGIN_PIN_ICON_ID),
                iconOffset(new Float[]{0f, -8f})
        ));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        CarmenFeature featureOrigin = PlaceAutocomplete.getPlace(data);
        textViewOrigin = (TextView) findViewById(R.id.text_view_origin);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE_ORIGIN) {
            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource geoJsonSource = style.getSourceAs(geoJsonSourceLayerIDOrigin);
                    if (geoJsonSource != null) {
                        geoJsonSource.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(featureOrigin.toJson())}
                        ));
                    }
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) featureOrigin.geometry()).latitude(),
                                            ((Point) featureOrigin.geometry()).longitude()))
                            .zoom(14)
                            .build()), 4000);
                }
            }

            origin = Point.fromLngLat(((Point) featureOrigin.geometry()).latitude(),
                    ((Point) featureOrigin.geometry()).longitude());

            Toast.makeText(this, featureOrigin.text(), Toast.LENGTH_LONG).show();
        }
        textViewOrigin.setText(featureOrigin.text());
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
        backRequestNewRideOriginActivity = (ImageView) findViewById(R.id.ic_back_activity_request_new_ride_where_later);
        backRequestNewRideOriginActivity.setOnClickListener(new View.OnClickListener() {
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
        nextActivityRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_where_next);
        nextActivityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaterOriginActivity.this,
                        LaterDestinationActivity.class);
                intent.putExtra("ORIGIN", textViewOrigin.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                // EFFECTS: Animation from ()Activity to ()Activity.
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}