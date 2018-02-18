package com.example.akshaymanagooli.justjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvPrice, tvQuantity;
    EditText etName;

    int quantity = 0;
    int itemPrice = 10;

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
    }


    public void Onclick(View v) {

        switch (v.getId()) {
            case R.id.bt_minus:
                if (quantity > 0) {
                    decrement();
                }

                break;

            case R.id.bt_plus:
                increment();
                break;

            case R.id.bt_order:
                placeorder();
                break;
        }

    }

    private void increment() {
        quantity++;
        tvQuantity.setText(String.valueOf(quantity));
        calculatePrice(quantity);
    }

    private void decrement() {
        quantity--;
        tvQuantity.setText(String.valueOf(quantity));
        calculatePrice(quantity);
    }

    private void calculatePrice(int qty) {

        String totalPrice = String.valueOf(qty * itemPrice);
        tvPrice.setText("₹ "+totalPrice);

    }

    private void placeorder() {

        if (etName.getText().length()>0){
            String name = etName.getText().toString();
            Toast.makeText(this, String.format(getString(R.string.thankyou_toast), name), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, R.string.error_toast, Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("quantity",quantity);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantity = savedInstanceState.getInt("quantity");
        tvQuantity.setText(String.valueOf(quantity));
        calculatePrice(quantity);
    }
}
