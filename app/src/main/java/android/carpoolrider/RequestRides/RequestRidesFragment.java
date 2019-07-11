package android.carpoolrider.RequestRides;

import android.carpoolrider.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RequestRidesFragment extends Fragment {

    View requestRidesView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requestRidesView = inflater.inflate(R.layout.fragment_request_rides, container, false);
        return requestRidesView;
    }
}
