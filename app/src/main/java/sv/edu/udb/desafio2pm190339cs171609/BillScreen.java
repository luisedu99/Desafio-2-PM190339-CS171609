package sv.edu.udb.desafio2pm190339cs171609;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BillScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_screen);
    }

    public void volverMenu(View v){
        Intent intent = new Intent( BillScreen.this, MenuScreen.class);
        startActivity(intent);
        finish();
    }
}