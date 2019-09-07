package android.carpoolrider;

import android.carpoolrider.Settings.Password.CurrentPasswordActivity;
import android.carpoolrider.Settings.ProfileActivity;
import android.carpoolrider.Settings.EmailActivity;
import android.carpoolrider.Settings.PhoneActivity;
import android.carpoolrider.StartFromLogIn.LogInActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    private View moreView;
    FirebaseAuth mAuth;

    private DatabaseReference RootRef;
    String currentUID;
    private TextView firstName, lastName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        moreView = inflater.inflate(R.layout.fragment_settings, container, false);
        mAuth = FirebaseAuth.getInstance();



        firstName = moreView.findViewById(R.id.firstnameofuser);
        lastName = moreView.findViewById(R.id.lastnameofuser);
        currentUID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.child("Users").child(currentUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                firstName.setText(dataSnapshot.child("firstname").getValue().toString());
                lastName.setText(dataSnapshot.child("lastname").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        initProfile();
        initEmail();
        initPhone();
        initPassword();
        signout();

        return moreView;
    }

    private void signout() {
        RelativeLayout profile = (RelativeLayout) moreView.findViewById(R.id.sign_out_rider);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                // EFFECTS: Animation from SettingsActivity to EditProfileActivity.
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
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
                Intent intent = new Intent(getActivity(), EmailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initPhone() {
        RelativeLayout phone = (RelativeLayout) moreView.findViewById(R.id.settings_phone);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhoneActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initPassword() {
        RelativeLayout password = (RelativeLayout) moreView.findViewById(R.id.settings_password);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CurrentPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
