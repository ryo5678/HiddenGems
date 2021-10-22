package com.example.hiddengems.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentHoursChangeBinding;
import com.example.hiddengems.databinding.FragmentRequestTagsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HoursChangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HoursChangeFragment extends Fragment {

    FragmentHoursChangeBinding binding;

    public HoursChangeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HoursChangeFragment newInstance() {
        HoursChangeFragment fragment = new HoursChangeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHoursChangeBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Request Operating Hours");
    }
}