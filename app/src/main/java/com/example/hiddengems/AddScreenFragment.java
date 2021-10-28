package com.example.hiddengems;

import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hiddengems.dataModels.location;
import com.example.hiddengems.databinding.FragmentAddScreenBinding;
import com.example.hiddengems.databinding.FragmentReportPageBinding;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddScreenFragment extends Fragment {

    FragmentAddScreenBinding binding;
    TimePickerDialog picker;
    EditText startTime, endTime;
    location locations;

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
            //locations = (location) getArguments().getSerializable("");
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

        startTime = binding.editAddStartTime;
        startTime.setInputType(InputType.TYPE_NULL);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                startTime.setText((sHour + ":" + String.format(Locale.getDefault(),"%02d", sMinute)).toString());
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        endTime = binding.editAddEndTime;
        endTime.setInputType(InputType.TYPE_NULL);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int eHour, int eMinute) {
                                endTime.setText((eHour + ":" + String.format(Locale.getDefault(),"%02d", eMinute)).toString());
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

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