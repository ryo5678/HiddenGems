package com.example.hiddengems;

import android.app.TimePickerDialog;
import android.content.Context;
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

import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.databinding.FragmentAddScreenBinding;

import java.util.Calendar;
import java.util.Locale;

public class AddScreenFragment extends Fragment {

    FragmentAddScreenBinding binding;
    TimePickerDialog picker;
    EditText startTime, endTime;
    Locations locations;

    public AddScreenFragment() {
        // Required empty public constructor
    }

    public static AddScreenFragment newInstance() {
        AddScreenFragment fragment = new AddScreenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //locations = (location) getArguments().getSerializable("location");
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
        getTime(startTime);

        endTime = binding.editAddEndTime;
        getTime(endTime);

        binding.addSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editAddName.getText().toString();
                String address = binding.editAddAddress.getText().toString();
                String category = binding.spinnerAddCategory.getSelectedItem().toString();
                String tags = binding.spinnerAddTags.getSelectedItem().toString();
                String startTime = binding.editAddStartTime.getText().toString();
                String endTime = binding.editAddEndTime.getText().toString();

                String[] sarr = startTime.split(":");
                int sHour = Integer.parseInt(sarr[0]);
                int sMinute = Integer.parseInt(sarr[1]);
                int startHour = (sHour * 60) + sMinute;

                String[] earr = startTime.split(":");
                int eHour = Integer.parseInt(earr[0]);
                int eMinute = Integer.parseInt(earr[1]);
                int endHour = (eHour * 60) + eMinute;

                String[] strArr = new String[] {tags};

                if(name.isEmpty()  || address.isEmpty() || category.isEmpty() || tags.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                    missingInput(getActivity());
                }
                else {
                    addPage(name, address, category, strArr, startHour, endHour);
                }
            }
        });
    }

    public void addPage(String name, String address, String category, String[] tags, int startTime, int endTime) {
        locations.setName(name);
        locations.setAddress(address);
        locations.setCategory(category);
        locations.setTags(tags);
        locations.setStartTime(startTime);
        locations.setEndTime(endTime);

        action.addLocation(locations);
    }

    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }

    public void getTime(EditText time) {
        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int eHour, int eMinute) {
                                time.setText((eHour + ":" + String.format(Locale.getDefault(),"%02d", eMinute)).toString());
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof add){
            action = (add) context;
        }
    }

    public static add action;

    public interface add{
        void addLocation(location locations);
    }
}