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

import com.example.hiddengems.databinding.FragmentReportPageBinding;

public class ReportPageFragment extends Fragment {

    FragmentReportPageBinding binding;

    public ReportPageFragment() {
        // Required empty public constructor
    }

    public static ReportPageFragment newInstance(String param1, String param2) {
        ReportPageFragment fragment = new ReportPageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

                reportPage(reportDescription);
            }
        });
    }

    public void reportPage(String reportDescription){
        // Send report and return to previous page

        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        fm.popBackStack("locationRemovalRequest", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}