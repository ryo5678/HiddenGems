package com.example.hiddengems;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiddengems.databinding.FragmentAddScreenBinding;
import com.example.hiddengems.databinding.FragmentReportPageBinding;

import java.text.DateFormat;

public class AddScreenFragment extends Fragment {

    FragmentAddScreenBinding binding;

    public AddScreenFragment() {
        // Required empty public constructor
    }

    public static AddScreenFragment newInstance(String param1, String param2) {
        AddScreenFragment fragment = new AddScreenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddScreenBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Location");

        binding.addSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editAddName.getText().toString();
                String address = binding.editAddAddress.getText().toString();
                String category = binding.spinnerAddCategory.getSelectedItem().toString();
                String tags = binding.spinnerAddTags.getSelectedItem().toString();
                String startTime = binding.editAddStartTime.getText().toString();
                String endTime = binding.editAddEndTime.getText().toString();

                if(name.isEmpty()  || address.isEmpty() || category.isEmpty() || tags.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                    missingInput(getActivity());
                }
                else {
                    addPage(name, address, category, tags, startTime, endTime);
                }
            }
        });
    }

    public void addPage(String name, String address, String category, String tags, String startTime, String endTime) {
        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        fm.popBackStack("addPage", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }
}