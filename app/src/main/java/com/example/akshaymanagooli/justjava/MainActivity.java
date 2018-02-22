package com.example.akshaymanagooli.justjava;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    /**
     * Variable/widgets Declarations
     */
    TextView tvPrice, tvQuantity;
    EditText etName;
    CheckBox cb_whipped_cream, cb_chocolate;


    int quantity = 1;
    int itemPrice = 10;
    boolean hasWhippedCream, hasChocolate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    /**
     * This method is invoked when the layout is created,
     * here we have initialized our widgets to their respective id
     * and we have set the checked listener to those two check box
     */
    private void initView() {
        tvPrice = findViewById(R.id.tv_price);
        tvQuantity = findViewById(R.id.tv_quantity_value);
        etName = findViewById(R.id.et_name);
        cb_whipped_cream = findViewById(R.id.cb_whippedcream);
        cb_chocolate = findViewById(R.id.cb_chocolate);

        cb_whipped_cream.setOnCheckedChangeListener(this);
        cb_chocolate.setOnCheckedChangeListener(this);
    }


    /**
     * method is called when we click the buttons like (+),(-), Reset button
     * @param v is attached with the button properties which we press
     */
    public void Onclick(View v) {

        switch (v.getId()) {
            case R.id.bt_minus:
                if (quantity > 1) {
                    decrement();
                }

                break;

            case R.id.bt_plus:
                if (quantity < 100) {
                    increment();
                }

                break;

            case R.id.bt_order:
                placeorder();
                break;
        }

    }

    /**
     * Method used for quantity increment
     */
    private void increment() {
        quantity++;
        tvQuantity.setText(String.valueOf(quantity));
    }

    /**
     * Method used for quantity decrement
     */
    private void decrement() {
        quantity--;
        tvQuantity.setText(String.valueOf(quantity));
    }


    /**
     * This method calculates the price of order.
     * @param qty is the value of number of quantity
     * @return integer value which is the calculated price.
     */
    private int calculatePrice(int qty) {
        return qty * itemPrice;
    }

    /**
     * Method is used to place order
     */
    private void placeorder() {
        if (etName.getText().length() > 0) {
            String whippedCream = getString(R.string.add_whipped_cream) + hasWhippedCream;
            String chocolate = getString(R.string.add_chocolate_label) + hasChocolate;
            ConfirmDialog(String.format(getString(R.string.price), etName.getText().toString(), whippedCream, chocolate, quantity, calculatePrice(quantity)));

        } else {
            Toast.makeText(this, R.string.toastlabel_entername, Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * Method is used to confirm whether the order is correct.
     * An AlertDialog will be invoked.
     * @param s is the message that contains order summary
     */
    private void ConfirmDialog(final String s) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(R.string.labelOrdersummary);
        alertDialogBuilder.setMessage(s);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.label_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                emailIntent(s);
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }


    /**
     * Method is used to send email.
     * Invokes email app through Intent
     * @param s is the message that contains order summary
     */
    private void emailIntent(String s) {
        String subject = String.format(getString(R.string.emailsubject_label),etName.getText().toString());
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, s);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Saves the instance of app, for example when app enters into landscape mode or back to potrait mode
     * @param outState holds the properties of current state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("quantity", quantity);

    }

    /**
     * Restores the instance of app, for example when app enters into landscape mode or back to potrait mode
     * @param savedInstanceState holds the properties of saved instance state
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantity = savedInstanceState.getInt("quantity");
        tvQuantity.setText(String.valueOf(quantity));
        calculatePrice(quantity);
    }

    /**
     * This method is invoked when user checks any of toppings
     * @param compoundButton is attached with the checkbox properties which user checks
     * @param b boolean to identify if the checkbox is checked, true if checked, false if unchecked
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {
            case R.id.cb_whippedcream:
                if (b) {
                    hasWhippedCream = true;
                    ToppingPrice(true, 1);
                } else {
                    hasWhippedCream = false;
                    ToppingPrice(false, 1);
                }
                break;
            case R.id.cb_chocolate:
                if (b) {
                    hasChocolate = true;
                    ToppingPrice(true, 2);
                } else {
                    hasChocolate = false;
                    ToppingPrice(false, 2);
                }
                break;
        }


    }

    /**
     * This method is used to add topping prices to main price
     * @param b to identify if topping is checked or unchecked
     * @param i topping price to be added
     */
    private void ToppingPrice(boolean b, int i) {

        if (b) {
            itemPrice = itemPrice + i;
        } else {
            itemPrice = itemPrice - i;
        }

    }

}
