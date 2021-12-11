package com.example.hiddengems;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hiddengems.account.LoginFragment;
import com.example.hiddengems.account.RegisterFragment;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.databinding.ActivityMainBinding;
import com.example.hiddengems.map.MapFragment;
import com.example.hiddengems.profile.*;

import com.example.hiddengems.home.*;
import com.example.hiddengems.search.LocationFragment;
import com.example.hiddengems.search.SearchResultsFragment;
import com.example.hiddengems.search.SearchScreenFragment;
import com.example.hiddengems.search.editLocationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.hiddengems.dataModels.Person.*;
import com.example.hiddengems.dataModels.Locations.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProfileFragment.profile,
        RegisterFragment.register, AddScreenFragment.add, SearchResultsFragment.location,
        EditProfileFragment.eprofile, LoginFragment.login,
        BottomNavigationView.OnNavigationItemSelectedListener,SearchScreenFragment.results,
        MyGemsFragment.locationGem, OurPicksFragment.ourPicks, HomeFragment.goPicks, LocationFragment.location,
        editLocationFragment.editLocation, WhatsNewFragment.ourPick{

    ActivityMainBinding binding;
    HomeFragment homeFragment = new HomeFragment();
    SearchScreenFragment searchFragment = new SearchScreenFragment();
    AddScreenFragment addFragment = new AddScreenFragment();
    Locations test = new Locations();
    private final String TAG = "TAG";
    FirebaseAuth mAuth;
    MapFragment mapFragment = new MapFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null){
            navView.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new LoginFragment())
                    .commit();
        } else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if(document.getBoolean("banned").equals(true)) {
                                Toast.makeText(getBaseContext(),document.getString("ban_reason"),Toast.LENGTH_SHORT).show();
                                logout();
                                Log.d(TAG, "banned");
                            }
                        } else {

                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });


            navView.setVisibility(View.VISIBLE);
            navView.setOnNavigationItemSelectedListener(this);
            navView.setSelectedItemId(R.id.navigation_home);
        }


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, homeFragment).commit();
                return true;
            case R.id.navigation_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, searchFragment).commit();
                return true;
            case R.id.navigation_add:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, addFragment).commit();
                return true;
            case R.id.navigation_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new MapFragment()).commit();
                return true;
            case R.id.navigation_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new ProfileFragment()).commit();
                return true;
        }
        return false;
    }

    @Override
    public void tagRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, RequestTagsFragment.newInstance())
                .addToBackStack("TagRequest")
                .commit();
    }

    @Override
    public void gems() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, MyGemsFragment.newInstance())
                .addToBackStack("gems")
                .commit();
    }

    @Override
    public void operatingHoursRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, HoursChangeFragment.newInstance())
                .addToBackStack("operatingHoursRequest")
                .commit();
    }

    @Override
    public void contactUs() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, ContactUsFragment.newInstance())
                .addToBackStack("contactUs")
                .commit();
    }

    @Override
    public void locationRemovalRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationRemovalFragment.newInstance())
                .addToBackStack("locationRemovalRequest")
                .commit();
    }

   @Override
    public void requestVerification() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, RequestVerificationFragment.newInstance())
                .addToBackStack("requestVerification")
                .commit();
    }

    @Override
    public void editProfile() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new EditProfileFragment())
                .addToBackStack("editProfile")
                .commit();
    }

    @Override
    public void editLocation(DocumentReference docRef) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new editLocationFragment(docRef))
                .addToBackStack("editLocation")
                .commit();
    }

    @Override
    public void report(DocumentReference docRef) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new ReportPageFragment(docRef))
                .addToBackStack("report")
                .commit();
    }

    @Override
    public void goProfile(String id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, ProfileFragment.newInstance(id))
                .addToBackStack("Profile")
                .commit();
    }

    @Override
    public void likedLocationsRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LikedLocationsFragment.newInstance())
                .addToBackStack("likedLocationsRequest")
                .commit();
    }

    @Override
    public void logout() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setVisibility(View.GONE);
        FirebaseAuth.getInstance().signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment.newInstance())
                .addToBackStack("Login")
                .commit();
    }

    @Override
    public void login(FirebaseUser user) {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setVisibility(View.VISIBLE);
        navView.setOnNavigationItemSelectedListener(this);
        navView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void register() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, RegisterFragment.newInstance())
                .addToBackStack("Register")
                .commit();
    }

    @Override
    public void profile() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new ProfileFragment())
                .addToBackStack("Profile")
                .commit();
    }

    @Override
    public void searchResults(ArrayList<Location> locations) {
        Log.d(TAG,locations.get(0).toString());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, SearchResultsFragment.newInstance(locations))
                .addToBackStack("SearchResults")
                .commit();
    }

    @Override
    public void showLocation(String id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationFragment.newInstance(id))
                .addToBackStack("ShowLocation")
                .commit();
    }

    @Override
    public void addLocation(String id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationFragment.newInstance(id))
                .addToBackStack("addPage")
                .commit();
    }

    @Override
    public void signUp(FirebaseUser user) {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setVisibility(View.VISIBLE);
        navView.setOnNavigationItemSelectedListener(this);
        navView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void gemShow(String id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationFragment.newInstance(id))
                .addToBackStack("ShowLocation")
                .commit();
    }

    @Override
    public void pickShow(String id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationFragment.newInstance(id))
                .addToBackStack("ShowLocation")
                .commit();
    }

    @Override
    public void showPick() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new OurPicksFragment())
                .addToBackStack("OurPicks")
                .commit();
    }

    @Override
    public void whatsNew() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new WhatsNewFragment())
                .addToBackStack("WhatsNew")
                .commit();
    }



    /*
    @Override
    public void reportPage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, ReportPageFragment.newInstance())
                .addToBackStack("reportPage")
                .commit();
    }*/

    public void banLog(Context context,String banReason){
        Toast.makeText(context,banReason,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void returnLocation(String id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationFragment.newInstance(id))
                .addToBackStack("ShowLocation")
                .commit();
    }

    @Override
    public void pickShow2(String id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationFragment.newInstance(id))
                .addToBackStack("ShowLocation")
                .commit();
    }
}