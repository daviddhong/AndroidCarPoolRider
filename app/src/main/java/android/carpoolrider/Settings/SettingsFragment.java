package android.carpoolrider.Settings;

import android.carpoolrider.R;
import android.carpoolrider.StartFromLogIn.LogInActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private View moreView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        moreView = inflater.inflate(R.layout.fragment_settings, container, false);

        return moreView;
    }
}
