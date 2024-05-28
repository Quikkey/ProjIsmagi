package ma.ismagi.android.projismagi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BuyMedicinDetailsActivity extends AppCompatActivity {


    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button btnback,btnAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy_medicin_details);

        tvPackageName = findViewById(R.id.textViewLDPackageName);
        edDetails = findViewById(R.id.ediTextMultiline);
        edDetails.setKeyListener(null);
        tvTotalCost = findViewById(R.id.textViewTotalCost);
        btnback = findViewById(R.id.buttonBack);
        btnAddToCart = findViewById(R.id.buttonAddToCart);

        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost :"+intent.getStringExtra("text3")+"/-");


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(BuyMedicinDetailsActivity.this , BuyMedicineActivity.class));
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat(intent.getStringExtra("text3").toString());

                Database db = new Database(getApplicationContext(), "healthcare", null, 1);


                if (db.checkCart(username, product) == 1) {
                    Toast.makeText(getApplicationContext(), "Product Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    db.addCart(username, product, price, "medicine");
                    Toast.makeText(getApplicationContext(), "Record Inserted To Cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BuyMedicinDetailsActivity.this, BuyMedicineActivity.class));
                }
            }
        });
    }
}