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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    private View moreView;
    private TextView signout, firstN, lastN, emaiL;
    private String currentUID, retrievefirstname, retrievelastname, retrieveemail;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        moreView = inflater.inflate(R.layout.fragment_settings, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUID = mAuth.getCurrentUser().getUid();
        firstN = moreView.findViewById(R.id.text_view_profile_name_more_first);
        lastN = moreView.findViewById(R.id.text_view_profile_name_more_last);
        emaiL = moreView.findViewById(R.id.User_email);

        signout = moreView.findViewById(R.id.SignOutOfApp);

        signout.setOnClickListener(new View.OnClickListener() {
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

        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.child("Users").child(currentUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                retrievefirstname = dataSnapshot.child("firstname").getValue().toString();
                retrievelastname = dataSnapshot.child("lastname").getValue().toString();
                retrieveemail = dataSnapshot.child("email").getValue().toString();
                firstN.setText(retrievefirstname);
                lastN.setText(retrievelastname);
                emaiL.setText(retrieveemail);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return moreView;
    }
}
