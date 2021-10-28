package com.example.hiddengems.account;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Person;
import com.example.hiddengems.databinding.FragmentLoginBinding;
import com.example.hiddengems.databinding.FragmentProfileBinding;

import com.example.hiddengems.dataModels.Person.*;
import com.example.hiddengems.profile.ProfileFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private final String TAG = "TAG";
    FragmentLoginBinding binding;
    String username;
    String password;
    Users user;
    ArrayList<Users> users;
    String type;

    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = binding.usernameEdit.getText().toString();
                password = binding.passwordEdit.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    type = "Users";
                    users = Person.getUsers(type);
                    for (int i = 0; i < users.size(); i++) {
                        user = users.get(i);
                        Log.d(TAG,user.getEmail());
                        Log.d(TAG,username);
                        if (user.getEmail().equals(username)) {
                            Log.d(TAG,"Passed Username check");
                            if (user.getPassword().equals(password)) {
                                Log.d(TAG,"Passed password check");
                                action.login(user);
                                return;
                            } else {
                                badPassword(getActivity());
                            }
                        }
                    }
                    badUsername(getActivity());
                }
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof login){
            action = (login) context;
        }
    }

    public static login action;

    public interface login{
        void login(Users user);
    }

    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.loginMissing),Toast.LENGTH_SHORT).show();
    }
    public void badPassword(Context context){
        Toast.makeText(context, getString(R.string.badPassword),Toast.LENGTH_SHORT).show();
    }
    public void badUsername(Context context){
        Toast.makeText(context, getString(R.string.badUsername),Toast.LENGTH_SHORT).show();
    }
}