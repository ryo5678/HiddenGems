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

import com.example.hiddengems.MainActivity;
import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Person;
import com.example.hiddengems.databinding.FragmentLoginBinding;
import com.example.hiddengems.databinding.FragmentProfileBinding;

import com.example.hiddengems.dataModels.Person.*;
import com.example.hiddengems.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    FirebaseUser user;
    ArrayList<Users> users;
    String type;
    private FirebaseAuth mAuth;

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

        getActivity().setTitle("Login to Hidden Gems");

        mAuth = FirebaseAuth.getInstance();

        binding.registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.register();
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = binding.usernameEdit.getText().toString();
                password = binding.passwordEdit.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    mAuth.signInWithEmailAndPassword(username,password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        Log.d(TAG,"Login success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        action.login(user);
                                    } else {

                                    }
                                }
                            });
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
        void login(FirebaseUser user);
        void register();
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