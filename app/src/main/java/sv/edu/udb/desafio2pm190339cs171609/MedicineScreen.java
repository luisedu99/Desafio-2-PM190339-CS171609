package sv.edu.udb.desafio2pm190339cs171609;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MedicineScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_screen);
    }

    public void volverMenu(View v){
        Intent intent = new Intent( MedicineScreen.this, MenuScreen.class);
        startActivity(intent);
        finish();
    }
}