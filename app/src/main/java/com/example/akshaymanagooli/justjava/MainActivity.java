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

    private void initView() {
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvQuantity = (TextView) findViewById(R.id.tv_quantity_value);
        etName = (EditText) findViewById(R.id.et_name);
        cb_whipped_cream = findViewById(R.id.cb_whippedcream);
        cb_chocolate = findViewById(R.id.cb_chocolate);

        cb_whipped_cream.setOnCheckedChangeListener(this);
        cb_chocolate.setOnCheckedChangeListener(this);
    }


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

    private void increment() {
        quantity++;
        tvQuantity.setText(String.valueOf(quantity));
    }

    private void decrement() {
        quantity--;
        tvQuantity.setText(String.valueOf(quantity));
    }

    private int calculatePrice(int qty) {
        return qty * itemPrice;
    }

    private void placeorder() {
        if (etName.getText().length() > 0) {
            String whippedCream = "Add whipped cream ? " + hasWhippedCream;
            String chocolate = "Add chocolate ? " + hasChocolate;
            ConfirmDialog(String.format(getString(R.string.price), etName.getText().toString(), whippedCream, chocolate, quantity, calculatePrice(quantity)));

        } else {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();

        }

    }

    private void ConfirmDialog(final String s) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Order Summary");
        alertDialogBuilder.setMessage(s);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                emailIntent(s);
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }



    private void emailIntent(String s) {
        String subject = String.format("JustJava order for %s",etName.getText().toString());
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, s);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("quantity", quantity);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantity = savedInstanceState.getInt("quantity");
        tvQuantity.setText(String.valueOf(quantity));
        calculatePrice(quantity);
    }

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

    private void ToppingPrice(boolean b, int i) {

        if (b) {
            itemPrice = itemPrice + i;
        } else {
            itemPrice = itemPrice - i;
        }

    }

}
