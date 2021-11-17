package com.example.hiddengems;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hiddengems.dataModels.Locations.Location;
import com.example.hiddengems.dataModels.Person.*;
import com.example.hiddengems.databinding.FragmentAddScreenBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddScreenFragment extends Fragment {

    FragmentAddScreenBinding binding;
    TimePickerDialog picker;
    EditText startTime, endTime;
    Location locations;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<Integer> tagList = new ArrayList<>();
    String[] tagArray;
    List<String> tagData = new ArrayList<>();


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
        tagArray = getResources().getStringArray(R.array.filter_tags);
        if (getArguments() != null) {
            locations = (Location) getArguments().getSerializable("Location");
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

        TextView spinner = binding.spinnerAddTags;

        boolean[] selectedTags = new boolean[tagArray.length];

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Select Tags");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(tagArray, selectedTags, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b) {
                            tagList.add(i);
                            tagData.add(tagArray[i]);
                            Collections.sort(tagList);
                        }
                        else {
                            tagData.remove(tagArray[i]);
                            for(int j=0; j<tagList.size(); j++){
                                if(tagList.get(j) == i){
                                    tagList.remove(j);
                                    break;
                                }
                            }
                        }
                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int j = 0; j < tagList.size(); j++) {
                            stringBuilder.append(tagArray[tagList.get(j)]);
                            if(j != tagList.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        spinner.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j = 0; j < selectedTags.length; j++) {
                            selectedTags[j] = false;
                            tagList.clear();
                            spinner.setText("");
                        }
                    }
                });

                builder.show();
            }
        });

        startTime = binding.editAddStartTime;
        getTime(startTime);

        endTime = binding.editAddEndTime;
        getTime(endTime);

        binding.addSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sHour, sMinute, startHour, eHour, eMinute, endHour;

                String name = binding.editAddName.getText().toString();
                String address = binding.editAddAddress.getText().toString();
                String category = binding.spinnerAddCategory.getSelectedItem().toString();
                String startTime = binding.editAddStartTime.getText().toString();
                String endTime = binding.editAddEndTime.getText().toString();
                String time = startTime + " - " + endTime;

                boolean allInput = name.isEmpty()  || address.isEmpty() || category.isEmpty() || tagData.isEmpty() || startTime.isEmpty() || endTime.isEmpty();
                boolean minInput = name.isEmpty()  || address.isEmpty() || category.isEmpty();

                if(minInput) {
                    missingInput(getActivity());
                }
                else if(!allInput){
                    addPage(name, address, category, tagData, time);
                }
            }
        });
    }

    public void addPage(String name, String address, String category, List<String> tags, String time) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String,Object> location = new HashMap<>();

        String creator = user.getUid();

        location.put("Creator", creator);
        location.put("Name", name);
        location.put("Address", address);
        location.put("Category", category);
        location.put("Tags", tags);
        location.put("Time", time);

        db.collection("locations")
                .add(location)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        HashMap<String, Object> comment = new HashMap<>();
                        comment.put("creator", creator);
                        comment.put("timeCreated", Timestamp.now());

                        documentReference.collection("Comments")
                                .add(comment)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                    }
                                });
                    }
                });

        action.addLocation(db);
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
                            public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
                                String am_pm = "";

                                Calendar datetime = Calendar.getInstance();
                                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                datetime.set(Calendar.MINUTE, minute);

                                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                                    am_pm = "AM";
                                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                                    am_pm = "PM";

                                String text = datetime.get(Calendar.HOUR) + ":" + String.format(Locale.getDefault(),"%02d", datetime.get(Calendar.MINUTE))  + " " + am_pm;

                                time.setText(text);
                            }
                        }, hour, minutes, false);
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
        void addLocation(FirebaseFirestore locations);
    }
}