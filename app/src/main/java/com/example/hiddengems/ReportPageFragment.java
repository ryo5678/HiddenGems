package com.example.hiddengems;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiddengems.databinding.FragmentReportPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class ReportPageFragment extends Fragment {

    FragmentReportPageBinding binding;
    String id;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseAuth mAuth;
    FirebaseUser user;
    DocumentReference docRef;

    public ReportPageFragment(DocumentReference documentReference) {
        this.docRef = documentReference;
        // Required empty public constructor
    }

    public static ReportPageFragment newInstance() {
        ReportPageFragment fragment = new ReportPageFragment(newInstance().docRef);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportPageBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Report Page");

        binding.reportSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reportDescription =  binding.reportDescription.getText().toString();

                if(reportDescription.isEmpty()) {
                    missingInput(getActivity());
                }
                else {
                    reportPage(reportDescription);
                }
            }
        });
    }

    public void reportPage(String reportDescription){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String,Object> reportLocation = new HashMap<>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        reportLocation.put("user", user.getUid());
        reportLocation.put("reason", reportDescription);
        reportLocation.put("locationID", docRef);

        db.collection("reportLocation")
                .add(reportLocation);

        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        fm.popBackStack("report", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }
}