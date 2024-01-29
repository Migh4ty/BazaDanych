package com.example.bazadanych;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

        private String imie, nazwisko, stanowisko;
        private DBHelper dbHelper;

        @SuppressLint("CutPasteId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Spinner spinner = findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stanowisko, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
            imie = ((EditText) findViewById(R.id.editTextText)).getText().toString();
            nazwisko = ((EditText) findViewById(R.id.editTextText2)).getText().toString();
            stanowisko = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
            dlugoscHasla = findViewById(R.id.editTextText3);
            duzeliteryCheckbox = findViewById(R.id.checkBox);
            cyfryCheckbox = findViewById(R.id.checkBox2);
            specjalneznakiCheckbox = findViewById(R.id.checkBox3);
            Button generujhasloButton = findViewById(R.id.passbutton);
            Button zatwierdzButton = findViewById(R.id.subbutton);
            dbHelper = new DBHelper(this);
            generujhasloButton.setOnClickListener(view -> {
                generatePassword();
                Toast.makeText(MainActivity.this, "Hasło: " + password, Toast.LENGTH_SHORT).show();
            });

            zatwierdzButton.setOnClickListener(view -> {
                resetowanie();
                imie = ((EditText) findViewById(R.id.editTextText)).getText().toString();
                nazwisko = ((EditText) findViewById(R.id.editTextText2)).getText().toString();
                stanowisko = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
                if (imie.isEmpty() || nazwisko.isEmpty()) {
                    Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "Dodano pracownika!\nImię: " + imie + "\nNazwisko: " + nazwisko + "\nStanowisko: " + stanowisko + "\nHasło: " + password, Toast.LENGTH_LONG).show();
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

        private void resetowanie() {
            dlugoscHasla.setText("");
            duzeliteryCheckbox.setChecked(false);
            cyfryCheckbox.setChecked(false);
            specjalneznakiCheckbox.setChecked(false);
            password = "";
            ((EditText) findViewById(R.id.editTextText)).getText().clear();
            ((EditText) findViewById(R.id.editTextText2)).getText().clear();
        }

        public class DBHelper extends SQLiteOpenHelper {
            private static final String DATABASE_NAME = "PracownicyDB";
            private static final int DATABASE_VERSION = 1;

            public static final String TABLE_NAME = "Pracownicy";
            public static final String COLUMN_ID = "id";
            public static final String COLUMN_IMIE = "imie";
            public static final String COLUMN_NAZWISKO = "nazwisko";
            public static final String COLUMN_STANOWISKO = "stanowisko";
            public static final String COLUMN_HASLO = "haslo";

            public DBHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_IMIE + " TEXT, " +
                        COLUMN_NAZWISKO + " TEXT, " +
                        COLUMN_STANOWISKO + " TEXT, " +
                        COLUMN_HASLO + " TEXT)";
                db.execSQL(createTableQuery);
            }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Aktualizacja bazy danych, jeśli to konieczne
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public void dodajRekordDoBazyDanych() {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_IMIE, imie);
            values.put(DBHelper.COLUMN_NAZWISKO, nazwisko);
            values.put(DBHelper.COLUMN_STANOWISKO, stanowisko);
            values.put(DBHelper.COLUMN_HASLO, password);

            db.insert(DBHelper.TABLE_NAME, null, values);
            db.close();
        }

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