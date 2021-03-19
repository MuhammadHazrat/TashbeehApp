package com.example.tashbeehapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button plusBtn;
    Button minusBtn;
    TextView tvCount;
    EditText target, editTextName, editTextTarget;
    ArrayList<Tasbeeh> favTasbeeh;
    int favId =0;
    private Vibrator vibrator;
    private Notification notification;
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        tvCount = findViewById(R.id.textView);
        target = findViewById(R.id.target);

        readData(true);

        Intent intent = new Intent(this, CounterService.class);
//        startService(intent);


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        saveData();
        super.onSaveInstanceState(outState);
    }

    private int readData(Boolean wantText) {
        String defaultValue = "0";
        SharedPreferences sharedPreferences = getSharedPreferences("counterFile",MODE_PRIVATE);
        String text = sharedPreferences.getString("name", defaultValue);
        if (wantText)
            tvCount.setText(text);

        return Integer.parseInt(text);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        long counter = Integer.parseInt(tvCount.getText().toString());


        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_UP) {
                    plusBtnClicked(null);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    minusButtonClicked(null);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    private void saveData() {

        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("counterFile",MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
        long counter = Integer.parseInt(tvCount.getText().toString());
        myEdit.putString("name",String.valueOf(counter));

        // Once the changes have been made,
        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.apply();
    }

    public void minusButtonClicked(View view) {
        long counter = Integer.parseInt(tvCount.getText().toString());

        if (counter > 0) {
            counter--;
            tvCount.setText(String.valueOf(counter));
        }

    }

    public void plusBtnClicked(View view) {
        int targetInt = 0;
        long counter = Integer.parseInt(tvCount.getText().toString());
        String target = this.target.getText().toString();

        if (target.equals("")) {
            counter++;
            tvCount.setText(String.valueOf(counter));
            return;
        }


        targetInt = Integer.parseInt(target);

        if (counter == targetInt-1)
            vibrate();
            counter++;

        tvCount.setText(String.valueOf(counter));
    }

    private void vibrate() {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(1000);
        }
    }

    public void resetBtnClicked(View view) {
        tvCount.setText("0");
    }


    private void dialogbox() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_target_dialog, null);

        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "btn2", Toast.LENGTH_SHORT).show();
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextName = dialogView.findViewById(R.id.etName);
                editTextTarget = dialogView.findViewById(R.id.etTarget);
                saveFavourite(editTextName.getText().toString(), editTextTarget.getText().toString());
                Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void saveFavourite(String name, String target) {// Storing data into SharedPreferences
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child(name).setValue(target);




    }

    public void addTashbeehClicked(View view) {
        dialogbox();
    }


    public void viewTasbeeh(View view) {

        Intent intent = new Intent(this, FavouritesActivity.class);
        intent.putExtra("favTasbeeh", favTasbeeh);
        startActivity(intent);
    }

}