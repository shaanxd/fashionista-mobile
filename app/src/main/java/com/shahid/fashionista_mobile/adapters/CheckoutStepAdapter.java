package com.shahid.fashionista_mobile.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.shahid.fashionista_mobile.fragments.ConfirmStepFragment;
import com.shahid.fashionista_mobile.fragments.ContactStepFragment;
import com.shahid.fashionista_mobile.fragments.PaymentStepFragment;
import com.shahid.fashionista_mobile.fragments.ShippingStepFragment;
import com.shahid.fashionista_mobile.fragments.StepFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;

public class CheckoutStepAdapter extends AbstractFragmentStepAdapter {
    public CheckoutStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        StepFragment step = null;

        switch (position) {
            case 0: {
                step = new ContactStepFragment();
                break;
            }
            case 1: {
                step = new ShippingStepFragment();
                break;
            }
            case 2: {
                step = new PaymentStepFragment();
                break;
            }
            default: {
                step = new ConfirmStepFragment();
            }
        }

        Bundle bundle = new Bundle();
        bundle.putInt("messageResourceId", position);
        step.setArguments(bundle);

        return step;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
