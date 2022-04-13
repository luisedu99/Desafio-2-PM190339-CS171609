package sv.edu.udb.desafio2pm190339cs171609;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
    }

    public void escogerMedicinas(View v){
        Intent intent =new Intent(MenuScreen.this,MedicineScreen.class);
        startActivity(intent);
    }

    public void pagarMedicinas(View v){
        Intent intent =new Intent(MenuScreen.this,BillScreen.class);
        startActivity(intent);
    }

    public void historialCompras(View v){
        Intent intent =new Intent(MenuScreen.this,ReportScreen.class);
        startActivity(intent);
    }

}