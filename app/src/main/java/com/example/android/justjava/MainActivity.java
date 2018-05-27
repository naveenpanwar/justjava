/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    public int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity > 9) {
            Toast.makeText(this, "You can't have more than 100 cups.", Toast.LENGTH_SHORT).show();
        } else {
            displayQuantity(++quantity);
        }
    }

    public void decrement(View view) {
        if (quantity < 2) {
            Toast.makeText(this, "You can't have less than 1 cup.", Toast.LENGTH_SHORT).show();
        } else {
            displayQuantity(--quantity);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText customerNameTextField = findViewById(R.id.customer_name_text_field);
        String customerName = customerNameTextField.getText().toString();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chotolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chotolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String orderSummary = createOrderSummary(price, hasWhippedCream, hasChocolate, customerName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, ( customerName + "\'s Order Summary" ));
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String message = getString(R.string.order_summary_name, name)+ "\n";
        message += getString(R.string.order_summary_add_whipped_cream, addWhippedCream) + "\n";
        message += getString(R.string.order_summary_add_chocolate, addChocolate) + "\n";
        message += getString(R.string.order_summary_quantity, quantity) + "\n";
        message += getString(R.string.order_summary_total, NumberFormat.getCurrencyInstance().format(price)) + "\n";
        message += getString(R.string.order_summary_thank_you);
        return message;
    }

    public int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream) {
            basePrice += 1;
        }
        if (addChocolate) {
            basePrice += 2;
        }
        return basePrice * quantity;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(getString(R.string.quantity_text, number));
    }
}