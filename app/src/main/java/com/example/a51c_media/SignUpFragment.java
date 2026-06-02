package com.example.a51c_media;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUpFragment extends Fragment {

    private EditText editTextRegFullname, editTextRegUserName, editTextRegPassword, editTextConfirmPassword;
    private Button btnCreateAccount;
    private AppDatabase database;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        editTextRegFullname = view.findViewById(R.id.editTextRegFullname);
        editTextRegUserName = view.findViewById(R.id.editTextRegUserName);
        editTextRegPassword = view.findViewById(R.id.editTextRegPassword);
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);
        btnCreateAccount = view.findViewById(R.id.btnCreateAccount);

        database = AppDatabase.getInstance(requireContext());

        btnCreateAccount.setOnClickListener(v -> registerUser(view));

        return view;
    }
    private void registerUser(View view) {
        String fullName = editTextRegFullname.getText().toString().trim();
        String username = editTextRegUserName.getText().toString().trim();
        String password = editTextRegPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        User existingUser = database.userDao().getUserByUsername(username);

        if (existingUser != null) {
            Toast.makeText(requireContext(), "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(fullName, username, password);
        database.userDao().insertUser(newUser);

        Toast.makeText(requireContext(), "Account created successfully", Toast.LENGTH_SHORT).show();

        Navigation.findNavController(view)
                .navigate(R.id.action_signUpFragment_to_loginFragment);
    }
}