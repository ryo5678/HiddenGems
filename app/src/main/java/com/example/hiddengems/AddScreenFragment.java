package com.example.hiddengems;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hiddengems.dataModels.Locations.Location;
import com.example.hiddengems.databinding.FragmentAddScreenBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AddScreenFragment extends Fragment {

    FragmentAddScreenBinding binding;
    TimePickerDialog picker;
    TextView spinner;
    EditText startTime, endTime;
    Location locations;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<Integer> tagSelect = new ArrayList<>();
    String[] tagArray;
    List<String> tagData = new ArrayList<>();
    String id;
    Geocoder geocoder;


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
        geocoder = new Geocoder(getActivity());
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

        spinner = binding.spinnerAddTags;

        getTagList(spinner);

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

        // Generate id first
        id = db.collection("locations").document().getId();
        DocumentReference reference = db.collection("locations").document(id);

        String creator = user.getUid();

        location.put("Address", address);
        location.put("Category", category);
        location.put("Creator", creator);
        location.put("Description", "");
        location.put("Image", new ArrayList<String>());
        location.put("Name", name);
        location.put("Season", "");
        location.put("Tags", tags);
        location.put("Time", time);
        location.put("Verified", false);
        location.put("Views", 0);
        location.put("is_Event", false);
        location.put("is_HiddenGem", false);
        location.put("ratings", new ArrayList<String>());

        try {
            List<Address> addressList = geocoder.getFromLocationName(address,1);
            Address address1 = addressList.get(0);
            location.put("Coordinates",new GeoPoint(address1.getLatitude(),address1.getLongitude()));
        } catch (IOException e) {
            location.put("Coordinates", new GeoPoint(35.312636212037155, -80.74201626366117));
            e.printStackTrace();
        }


        reference.set(location).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });

        binding.editAddName.getText().clear();
        binding.editAddName.getText().clear();
        binding.editAddAddress.getText().clear();
        binding.editAddStartTime.getText().clear();
        binding.editAddEndTime.getText().clear();
        binding.spinnerAddTags.setText(null);

        action.addLocation(id);
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

    public void getTagList(TextView spinner) {
        // Clearing so when you switch pages through nav bar it doesn't save tags selected
        tagSelect.clear();

        boolean[] selectedTags = new boolean[tagArray.length];

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Select Tags");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(tagArray, selectedTags, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b) {
                            tagSelect.add(i);
                            tagData.add(tagArray[i]);
                            Collections.sort(tagSelect);
                        }
                        else {
                            tagData.remove(tagArray[i]);
                            for(int j=0; j < tagSelect.size(); j++){
                                if(tagSelect.get(j) == i){
                                    tagSelect.remove(j);
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
                        for(int j = 0; j < tagSelect.size(); j++) {
                            stringBuilder.append(tagArray[tagSelect.get(j)]);
                            if(j != tagSelect.size() - 1) {
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
                            tagSelect.clear();
                            spinner.setText("");
                        }
                    }
                });

                builder.show();
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
        void addLocation(String id);
    }
}