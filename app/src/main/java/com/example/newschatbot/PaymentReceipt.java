package com.example.newschatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.PaymentData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PaymentReceipt extends PaymentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_receipt);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        String paymentId = getIntent().getStringExtra("paymentId");
        String orderId = getIntent().getStringExtra("orderId");
        String name = getIntent().getStringExtra("name");

        String email = getIntent().getStringExtra("email");
        // Find views by their IDs
        final Button btnSaveReceipt = findViewById(R.id.btnSaveReceipt);
        final LinearLayout layout = findViewById(R.id.receiptSuccess);
        // Set click listener for the "Save Receipt as Image" button
        btnSaveReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReceiptAsImage(layout);
            }
        });



        // Display the payment details in the UI

        TextView orderIdTextView = findViewById(R.id.order_id_text_view);
        TextView nameTextView = findViewById(R.id.name_text_view);

        TextView emailTextView = findViewById(R.id.email_text_view);




        orderIdTextView.setText("Order ID: " + orderId);
        nameTextView.setText("Contact: " + name);

        emailTextView.setText("Email:"+email);







    }
    // Method to save the receipt layout as an image
    private void saveReceiptAsImage(View view) {
        Bitmap bitmap = getBitmapFromView(view);
        if (bitmap != null) {
            try {
                // Save bitmap to storage
                File file = createImageFile();
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                Toast.makeText(this, "Receipt saved successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to create a Bitmap from a View
    private Bitmap getBitmapFromView(View view) {

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }
    // Method to create an image file
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }


}