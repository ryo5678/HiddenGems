package com.example.hiddengems.profile;

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

import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentHoursChangeBinding;
import com.example.hiddengems.databinding.FragmentLikedLocationsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LikedLocationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikedLocationsFragment extends Fragment {

    FragmentLikedLocationsBinding binding;
    String text;

    public LikedLocationsFragment() {
        // Required empty public constructor
    }


    public static LikedLocationsFragment newInstance() {
        LikedLocationsFragment fragment = new LikedLocationsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLikedLocationsBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Liked Locations");
    }

}