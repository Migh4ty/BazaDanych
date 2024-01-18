package com.example.bazadanych;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
    public class MainActivity extends AppCompatActivity {

    private EditText dlugoscHasla;
    private CheckBox duzeliteryCheckbox, cyfryCheckbox, specjalneznakiCheckbox;
        private String password;
    private String danePracownikow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dlugoscHasla = findViewById(R.id.editTextText3);
        duzeliteryCheckbox = findViewById(R.id.checkBox);
        cyfryCheckbox = findViewById(R.id.checkBox2);
        specjalneznakiCheckbox = findViewById(R.id.checkBox3);
        Button generujhasloButton = findViewById(R.id.passbutton);
        Button zatwierdzButton = findViewById(R.id.subbutton);
        generujhasloButton.setOnClickListener(view -> {
            generatePassword();
            Toast.makeText(MainActivity.this, "Hasło: " + password, Toast.LENGTH_SHORT).show();
        });

        zatwierdzButton.setOnClickListener(view -> {
            saveEmployeeData();
            resetFields();
            Toast.makeText(MainActivity.this, danePracownikow, Toast.LENGTH_SHORT).show();
        });
    }

    private void generatePassword() {
        int length = Integer.parseInt(dlugoscHasla.getText().toString());
        StringBuilder generatedPassword = new StringBuilder();

        String litery = "abcdefghijklmnopqrstuvwxyz";
        String dlitery = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String cyfry = "0123456789";
        String znakiSpecjalne = "!@#$%^&*()_+-=";

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char lznaki = litery.charAt(random.nextInt(litery.length()));

            if (duzeliteryCheckbox.isChecked() && i == 0)
                lznaki = dlitery.charAt(random.nextInt(dlitery.length()));
            else if (cyfryCheckbox.isChecked() && (i == 1)) {
                lznaki = cyfry.charAt(random.nextInt(cyfry.length()));
            } else if (specjalneznakiCheckbox.isChecked() && i == 2) {
                lznaki = znakiSpecjalne.charAt(random.nextInt(znakiSpecjalne.length()));
            }

            generatedPassword.append(lznaki);
        }

        password = generatedPassword.toString();
    }

    private void saveEmployeeData() {
        danePracownikow = "Dane pracownika: ";
    }

    private void resetFields() {
        dlugoscHasla.setText("");
        duzeliteryCheckbox.setChecked(false);
        cyfryCheckbox.setChecked(false);
        specjalneznakiCheckbox.setChecked(false);
        password = "";
        danePracownikow = "";
    }
}