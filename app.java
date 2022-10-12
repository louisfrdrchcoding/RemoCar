package com.example.androidcar;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private StorageReference mStorageReference;

    public void Send(String msg) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/car/message");       // Funktion für Senden der Nachricht
        myRef.setValue(msg);
    }

    public void Get() {
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database2.getReference("/car/entfernung");

        ValueEventListener postListener = new ValueEventListener() {
            @Override                                                               // Funktion, um Nachricht zu erhalten
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer text2 = dataSnapshot.getValue(Integer.class);       // Schreibe Nachricht in variable "text2"
                Log.d(TAG, String.valueOf(text2));

                final TextView text = findViewById(R.id.textView);
                text.setText(String.valueOf("Abstand: " + text2 + " cm"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        myRef2.addValueEventListener(postListener);
    }

    public void fotoAnzeigen() {
        mStorageReference = FirebaseStorage.getInstance().getReference().child("image.jpeg");
        try {
            final File localfile = File.createTempFile("image", "jpeg");
            mStorageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override

                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());                     // Funktion für Anzeigen von Fotos
                    ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override

                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Fail");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Get();

        final Button button_vor = findViewById(R.id.button2);
        button_vor.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "Vor");
                        Send("Vor");        // Schreibe "Vor" in die Datenbank (DB) solange Button gedrückt wird
                        return true;

                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "Stehen");
                        Send("Stehen");     // Schreibe "Stehen" in die DB wenn losgelassen
                        return true;
                }
                return false;
            }
        });

        final Button button_zuruck = findViewById(R.id.button4);
        button_zuruck.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "Zuruck");   // Schreibe "Zurück" in die DB
                        Send("Zuruck");
                        return true;

                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "Stehen");
                        Send("Stehen");
                        return true;
                }
                return false;
            }
        });

        final Button button_links = findViewById(R.id.button);
        button_links.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "Links");        // Schreibe "Links" in die DB
                        Send("Links");
                        return true;

                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "Stehen");
                        Send("Stehen");
                        return true;
                }
                return false;
            }
        });

        final Button button_rechts = findViewById(R.id.button3);
        button_rechts.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "Rechts");       // Schreibe "Rechts" in die DB
                        Send("Rechts");
                        return true;

                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "Stehen");
                        Send("Stehen");
                    return true;
                }
                return false;
            }
        });

        final Button button_foto = findViewById(R.id.button5);
        button_foto.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Log.d(TAG, "Foto");                 // Schreibe "Foto" in die DB
                Send("Foto");
            }
        });

        final Button button_kreis_drehen = findViewById(R.id.button6);
        button_kreis_drehen.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Send("Kreis_drehen");           // Schreibe "Kreis_drehen" in die DB
            }
        });

        final Button button_autonom = findViewById(R.id.button8);
        button_autonom.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Send("autonom");                // Schreibe "autonom" in die DB
            }
        });
    }
}
