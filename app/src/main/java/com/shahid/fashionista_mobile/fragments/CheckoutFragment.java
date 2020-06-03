package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.gson.Gson;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shahid.fashionista_mobile.CustomNavigator;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.adapters.CartAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentCheckoutBinding;
import com.shahid.fashionista_mobile.dto.request.PurchaseRequest;
import com.shahid.fashionista_mobile.dto.response.CartItemResponse;
import com.shahid.fashionista_mobile.dto.response.CartResponse;
import com.shahid.fashionista_mobile.services.CartService;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class CheckoutFragment extends AuthFragment implements ServiceCallback {

    private FragmentCheckoutBinding binding;

    AwesomeValidation main, payment;

    EditText firstName, lastName, address, city, country;
    EditText cardNumber, cvv, expirationDate;
    Switch paymentMethod;

    CartResponse items;

    @Inject
    CartService service;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
        // Get cart from bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String json = getArguments().getString("CART_LIST");
            items = new Gson().fromJson(json, CartResponse.class);
        }

        // Initialize main validation
        main = new AwesomeValidation(UNDERLABEL);
        main.setContext(activity);

        // Initialize payment validation
        payment = new AwesomeValidation(UNDERLABEL);
        payment.setContext(activity);
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

        firstName = binding.firstname;
        lastName = binding.lastname;
        address = binding.address;
        city = binding.city;
        country = binding.country;

        cardNumber = binding.cardNumber;
        cvv = binding.cvv;
        expirationDate = binding.expirationDate;

        paymentMethod = binding.paymentMethod;

        binding.cart.setAdapter(new CartAdapter(items.getItems(), null));

        binding.paymentMethod.setOnCheckedChangeListener(this::onPaymentMethodChange);
        binding.checkoutButton.setOnClickListener(this::onCheckoutClick);

        main.addValidation(firstName, RegexTemplate.NOT_EMPTY, "First name is required");
        main.addValidation(lastName, RegexTemplate.NOT_EMPTY, "Last name is required");
        main.addValidation(address, RegexTemplate.NOT_EMPTY, "Address is required.");
        main.addValidation(city, RegexTemplate.NOT_EMPTY, "City is required");
        main.addValidation(country, RegexTemplate.NOT_EMPTY, "Country is required");

        payment.addValidation(cardNumber, RegexTemplate.NOT_EMPTY, "Please enter valid card number");
        payment.addValidation(cvv, RegexTemplate.NOT_EMPTY, "Please enter a valid CVV");
        payment.addValidation(expirationDate, RegexTemplate.NOT_EMPTY, "Please enter a valid expiration date");
    }

    private void onPaymentMethodChange(CompoundButton compoundButton, boolean b) {
        binding.setIsCardPayment(b);
    }

    private void onCheckoutClick(View view) {
        if (!main.validate() || !paymentMethod.isChecked() && !payment.validate()) {
            return;
        }
        binding.setLoading(true);

        List<String> cart = items
                .getItems()
                .stream()
                .map(CartItemResponse::getId)
                .collect(Collectors.toList());

        String firstName = this.firstName.getText().toString();
        String lastName = this.lastName.getText().toString();
        String address = this.address.getText().toString();
        String city = this.city.getText().toString();
        String country = this.country.getText().toString();

        String paymentMethod = this.paymentMethod.isChecked() ? "CARD_PAYMENT" : "CASH_PAYMENT";

        PurchaseRequest request = new PurchaseRequest(
                cart,
                firstName + " " + lastName,
                address,
                city,
                country,
                paymentMethod
        );

        if (authState == null) {
            return;
        }

        service.purchaseCart("Bearer " + authState.getToken(), request, this);
    }

    @Override
    public void onSuccess(Response mResponse) {
        DynamicToast.makeSuccess(activity, "Your purchase was successful!").show();
        CustomNavigator.goBack(rootNavController);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        DynamicToast.makeError(activity, mErrorMessage).show();
        binding.setLoading(false);
    }
}
