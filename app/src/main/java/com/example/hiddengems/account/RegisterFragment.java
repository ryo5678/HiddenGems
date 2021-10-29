package com.example.hiddengems.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentLoginBinding;
import com.example.hiddengems.databinding.FragmentRegisterBinding;

import com.example.hiddengems.dataModels.Person.*;


public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    Users user;
    String firstName;
    String lastName;
    String password;
    String confirm;
    String email;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Register For Hidden Gems");

        binding.registerButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = binding.registerEditFirst.getText().toString();
                lastName = binding.registerEditLast.getText().toString();
                email = binding.registerEditEmail.getText().toString();
                password = binding.registerEditPassword.getText().toString();
                confirm = binding.registerEditConfirm.getText().toString();
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||password.isEmpty() || confirm.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    if (password.equals(confirm)) {
                        user = new Users((firstName + lastName),(firstName + " " + lastName),email,password,"");
                        action.signUp(user);
                    } else {
                        badPassword(getActivity());
                    }
                }
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof register){
            action = (register) context;
        }
    }

    public static register action;

    public interface register{
        void signUp(Users user);
    }
    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.loginMissing),Toast.LENGTH_SHORT).show();
    }
    public void badPassword(Context context){
        Toast.makeText(context, "The passwords do not match. Please try again.",Toast.LENGTH_SHORT).show();
    }
}
