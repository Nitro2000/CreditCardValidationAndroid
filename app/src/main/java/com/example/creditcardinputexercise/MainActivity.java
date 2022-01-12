package com.example.creditcardinputexercise;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    // Declaration of variables
    TextInputLayout tilCardNum, tilMMYY, tilSec, tilFirst, tilLast;
    TextInputEditText tietCardNum, tietMMYY, tietSec, tietFirst, tietLast;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intialise();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkCardNum(tietCardNum.getText())) {
                    tilCardNum.setError("Invalid card number");
                    tietCardNum.setText("");
                } else if (!checkExpiry(tietMMYY.getText())) {
                    tilMMYY.setError("Invalid or Already Expired");
                    tietMMYY.setText("");
                } else if (!checkSecCode(tietSec.getText())) {
                    tilSec.setError("Invalid security code");
                    tietSec.setText("");
                } else if (!checkName(tietFirst.getText())) {
                    tilFirst.setError("letter & space only");
                    tietFirst.setText("");
                } else if (!checkName(tietLast.getText())) {
                    tilLast.setError("letter & space only");
                    tietLast.setText("");
                } else {
                    tilCardNum.setErrorEnabled(false);
                    tilMMYY.setErrorEnabled(false);
                    tilSec.setErrorEnabled(false);
                    tilFirst.setErrorEnabled(false);
                    tilLast.setErrorEnabled(false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Payment Successful").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create().show();
                }

            }
        });
    }

    private boolean checkName(Editable text) {
        String str = text.toString();
        if (!str.matches("[a-zA-Z][a-zA-Z ]+")) {
            return false;
        }
        return true;
    }

    private boolean checkSecCode(Editable tietSec) {
        String str = tietSec.toString();
        if (!str.matches("[0-9]{3,5}")) return false;
        return true;
    }

    private boolean checkExpiry(Editable text) {
        String s = text.toString();
        if (s.matches("[0][1-9]|[1][12]/[0-9]{2}")) {
            String[] str = s.split("/");
            int mon = Integer.parseInt(str[0]);
            int year = Integer.parseInt(str[1]);
            LocalDate ld = LocalDate.now();
            if (ld.getYear() % 100 > year) return false;
            else if (ld.getYear() % 100 == year) {
                if (mon < ld.getMonthValue()) return false;
            }
            return true;
        }

        return false;
    }

    private boolean checkCardNum(Editable text) {
        String str= text.toString();
        if (!(str.length() == 16)) return false;
        else {
            long num = Long.parseLong(str);
            int last = (int) (num % 10);
            num = num / 10;
            int sum = 0;
            int count = 1;
            while (num != 0) {
                if (count % 2 != 0) {
                    sum += (num % 10) * 2;
                } else sum += num % 10;
                count++;
                num = num / 10;
            }
            return 10 - (sum % 10) == last;
        }
    }

    private void intialise() {
        // Buttons
        btnSubmit = findViewById(R.id.button);

        // TextInputLayouts
        tilCardNum = findViewById(R.id.tilCardNum);
        tilMMYY = findViewById(R.id.tilmmyy);
        tilSec = findViewById(R.id.tilSec);
        tilFirst = findViewById(R.id.tilFirst);
        tilLast = findViewById(R.id.tilLast);

        // TextInputEditText
        tietCardNum = findViewById(R.id.tietCardNum);
        tietMMYY = findViewById(R.id.tietmmyy);
        tietSec = findViewById(R.id.tietsec);
        tietFirst = findViewById(R.id.tietFirst);
        tietLast = findViewById(R.id.tietLast);

    }
}