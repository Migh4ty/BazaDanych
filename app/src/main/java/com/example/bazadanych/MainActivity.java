package com.example.bazadanych;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
    public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText dlugoscHasla;
    private CheckBox duzeliteryCheckbox, cyfryCheckbox, specjalneznakiCheckbox;
        private String password;
    private String danePracownikow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stanowisko, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        View imiePracownika = findViewById(R.id.editTextText);
        View nazwiskoPracownika = findViewById(R.id.editTextText2);
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
            Toast.makeText(MainActivity.this, "Imię: " + imiePracownika.toString() + "Nazwisko: " + nazwiskoPracownika.toString(), Toast.LENGTH_SHORT).show();
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

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }