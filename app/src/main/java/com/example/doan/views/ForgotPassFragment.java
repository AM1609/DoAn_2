package com.example.doan.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.R;
import com.example.doan.viewmodel.AuthViewModel;


public class ForgotPassFragment extends Fragment {

    EditText editEmailFor;
    Button btnSendmail;
    TextView txtDNQMK;
    private NavController navController;
    private AuthViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_pass, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        editEmailFor = view.findViewById(R.id.editEmailFor);
        btnSendmail = view.findViewById(R.id.btnSendmail);
        txtDNQMK = view.findViewById(R.id.txtDNQMK);
        navController = Navigation.findNavController(view);
        txtDNQMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_forgotPassFragment_to_signInFragment);
            }
        });
        btnSendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmailFor.getText().toString();
                if (!email.isEmpty()){
                    viewModel.ForgetPass(email);
                    editEmailFor.setText("");
                }
                else {
                    Toast.makeText(getContext(), "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}