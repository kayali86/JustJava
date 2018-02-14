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


 //This app displays an order form to order coffee.
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //This method is called when the + button is clicked.
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.increment_toast_message), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }


    //This method is called when the - button is clicked.
    public void decrement(View view) {
        if (quantity ==1) {
            Toast.makeText(this, getString(R.string.decrement_toast_message), Toast.LENGTH_SHORT) .show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }


    //This method is called when the order button is clicked.
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
      intent.setData(Uri.parse("mailto:")); // only email apps should handle this
      intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) + name);
      intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
      if (intent.resolveActivity(getPackageManager()) != null) {
    startActivity(intent);
  }
    }


    /**
     * This method Calculates the price of the order.
     *
     *@param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants chocolate topping
     * @return Total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        // price of one cup of coffee
        int basePrice = 5;

        // add 1$ if the user wants whipped cream
        if (hasWhippedCream) {
            basePrice += 1;
        }

        //add 2$ if the user wants chocolate
        if (hasChocolate) {
            basePrice += 2;
        }

        // calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }


    /**
     * Creat summary of the order:
     *
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @return text summary
     * @param name of the customer
     */
    public String createOrderSummary (int price, boolean addWhippedCream, boolean addChocolate, String name){
        String priceMessage = getString(R.string.order_name_summary, name);
        priceMessage += "\n" + getString(R.string.hasWhippedCream_summary, addWhippedCream);
        priceMessage += "\n" + getString(R.string.hasChocolate_summary, addChocolate);
        priceMessage += "\n" + getString(R.string.Quantity_summary, quantity);
        priceMessage += "\n" + getString(R.string.Total_summary, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thankYou_summary);
        return priceMessage;
    }


    //This method displays the given quantity value on the screen.
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}