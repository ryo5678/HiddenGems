package com.example.hiddengems.profile;

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

import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentHoursChangeBinding;
import com.example.hiddengems.databinding.FragmentRequestVerificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;


public class RequestVerificationFragment extends Fragment {

    FragmentRequestVerificationBinding binding;
    String reasoning;
    String id;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseAuth mAuth;
    FirebaseUser user;

    public RequestVerificationFragment() {
        // Required empty public constructor
    }


    public static RequestVerificationFragment newInstance() {
        RequestVerificationFragment fragment = new RequestVerificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRequestVerificationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Request Operating Hours");
        binding.submitVerific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reasoning = binding.verificMessage.getText().toString();
                if (reasoning.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    submitRequest(reasoning);
                }
            }
        });
    }

    public void submitRequest(String reasoning){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String,Object> requestVerification = new HashMap<>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        requestVerification.put("user", user.getUid());
        requestVerification.put("reason", reasoning);

        db.collection("verifiedRequests")
                .add(requestVerification);

        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        fm.popBackStack("requestVerification", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                /*}
            }
        });*/
    }
    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }


}