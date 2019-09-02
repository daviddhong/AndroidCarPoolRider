package android.carpoolrider.Settings;

import android.carpoolrider.R;
import android.carpoolrider.Settings.Password.SettingsCurrentPasswordActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private View moreView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        moreView = inflater.inflate(R.layout.fragment_settings, container, false);

        initProfile();
        initEmail();
        initPhone();
        initPassword();

        return moreView;
    }

    private void initProfile() {
        RelativeLayout profile = (RelativeLayout) moreView.findViewById(R.id.settings_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initEmail() {
        RelativeLayout email = (RelativeLayout) moreView.findViewById(R.id.settings_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsEmailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initPhone() {
        RelativeLayout phone = (RelativeLayout) moreView.findViewById(R.id.settings_phone);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsPhoneActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initPassword() {
        RelativeLayout password = (RelativeLayout) moreView.findViewById(R.id.settings_password);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsCurrentPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
