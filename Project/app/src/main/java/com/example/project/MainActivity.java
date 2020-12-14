package com.example.project;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.ui.search.SearchFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private static String TAG = "MainActivity";
    private static int RC_SIGN_IN = 1;
    public GoogleSignInClient mGoogleSignInClient;
    public GoogleSignInAccount account;
    String ACCESS_TOKEN = "ma4Uvju8RyzHlMDuVvqdxooupxz7_IkhbKpEek9HAzcGvu868nQhbkkDi_kk-FUb9_aWodrCEUJg-XiAPsTRYGTbmug2yhI0f0bBHwX7FnFBOoldEukhvNR75QzUX3Yx";
    Company company = new Company();
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_CODE_PERMISSION = 2;
    GPSTracker gps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // ...
        // Initialize Firebase Auth

    }
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignResult(task);
        }
    }

    private void handleSignResult(Task<GoogleSignInAccount> task)
    {
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(MainActivity.this, "Signed in Successfully", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Log.d(TAG, "Google sign in failed", e);
            Toast.makeText(MainActivity.this, "Signed in Failed", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(null);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(MainActivity.this, "Signed in with credential Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Signed in with credential Failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user)
    {
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null)
        {
            String name = account.getDisplayName();
            //mDatabase.child().child("favorite").setValue("you suck");
            Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
        }
    }

    public void search(String location, final VolleyCallBack callBack)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "https://api.yelp.com/v3/businesses/search?location="+location + "+term=";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                company.add(response.toString());
                callBack.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Failed");
                System.out.println(error);
            }
        }) {
            //This is for Headers If You Needed
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","bearer " + ACCESS_TOKEN);
                return params;
            }
        };
        queue.add(request);
    }

    public void GPS(final VolleyCallBack callBack)
    {
        // Checks to see if user gives permission to use their location
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Initialize the GPS
        gps = new GPSTracker(MainActivity.this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Using GPS coordinates for OpenWeather API
        String url = "https://api.yelp.com/v3/businesses/search?location=" + Double.toString(latitude) + "," + Double.toString(longitude);
        Log.d(TAG, url);
        // Request a JSONObject response from the provided URL.
        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        company.add(response.toString());
                        callBack.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Display error
                Toast.makeText(getApplicationContext(), "That didn't work", Toast.LENGTH_LONG).show();
            }
        })
        {
            //This is for Headers If You Needed
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","bearer " + ACCESS_TOKEN);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(JSONRequest);
    }

    public Company getCompany()
    {
        return company;
    }
}