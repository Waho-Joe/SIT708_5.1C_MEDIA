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


public class LoginFragment extends Fragment {

    private EditText editTextLoginUserName, editTextLoginUserPassword;
    private Button btnLogin, btnSignUp;
    private AppDatabase database;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextLoginUserName = view.findViewById(R.id.editTextLoginUserName);
        editTextLoginUserPassword = view.findViewById(R.id.editTextLoginUserPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnSignUp = view.findViewById(R.id.btnSignUp);

        database = AppDatabase.getInstance(requireContext());

        btnLogin.setOnClickListener(v -> loginUser(view));

        btnSignUp.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment_to_signUpFragment);
        });

        return view;
    }
    private void loginUser(View view) {
        String username = editTextLoginUserName.getText().toString().trim();
        String password = editTextLoginUserPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = database.userDao().login(username, password);

        if (user != null) {

            Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("currentUsername", username);

            Navigation.findNavController(view)
                    .navigate(R.id.streamHomeFragment, bundle);


        } else {
            Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}