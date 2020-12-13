package com.example.project.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.MainActivity;
import com.example.project.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;

public class HomeFragment extends Fragment {

    SignInButton signInButton;
    Button signOutButton;
    MainActivity main;
    GoogleSignInAccount account;

    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        main = (MainActivity) getActivity();
        signInButton = root.findViewById(R.id.login);
        signOutButton = root.findViewById(R.id.signout);
        signOutButton.setVisibility(View.INVISIBLE);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.signIn();
                GoogleSignInAccount account = main.account;
                if(account != null)
                {
                    String name = account.getDisplayName();
                    Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                    signOutButton.setVisibility(View.VISIBLE);
                    signInButton.setVisibility(View.INVISIBLE);
                }

            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.mGoogleSignInClient.signOut();
                signOutButton.setVisibility(View.INVISIBLE);
                signInButton.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }
}