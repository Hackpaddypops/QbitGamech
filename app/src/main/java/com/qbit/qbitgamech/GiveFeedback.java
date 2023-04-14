package com.qbit.qbitgamech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GiveFeedback extends AppCompatActivity {

    private EditText feedbackEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback);

        feedbackEditText = findViewById(R.id.feedback_edittext);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(v -> {
            String feedback = feedbackEditText.getText().toString().trim();

            // Check if feedback is empty
            if (feedback.isEmpty()) {
                Toast.makeText(GiveFeedback.this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
            } else {
                // Process the feedback here
                // You can send the feedback to a server, store it locally, etc.
                Toast.makeText(GiveFeedback.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}