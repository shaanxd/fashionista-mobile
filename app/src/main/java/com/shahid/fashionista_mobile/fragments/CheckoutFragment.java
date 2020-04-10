package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.adapters.CheckoutStepAdapter;
import com.shahid.fashionista_mobile.databinding.FragmentCheckoutBinding;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class CheckoutFragment extends AuthFragment implements StepperLayout.StepperListener {

    private FragmentCheckoutBinding binding;
    private StepperLayout stepper;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stepper = binding.stepper;
        stepper.setAdapter(new CheckoutStepAdapter(getParentFragmentManager(), activity));
    }

    @Override
    public void onCompleted(View completeButton) {
        Toast.makeText(activity, "Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(activity, "Error occurred", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {
        Toast.makeText(activity, "End of stepper", Toast.LENGTH_SHORT).show();
    }
}
