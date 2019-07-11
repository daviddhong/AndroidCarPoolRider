package android.carpoolrider.AvaliableRides;

import android.carpoolrider.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AvailableRidesFragment extends Fragment {

    View availableRidesView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        availableRidesView = inflater.inflate(R.layout.fragment_available_rides, container, false);
        return availableRidesView;
    }
}
