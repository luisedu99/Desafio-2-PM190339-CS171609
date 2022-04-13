package sv.edu.udb.desafio2pm190339cs171609;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MenuScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
    }

    public void escogerMedicinas(View v){
        DbHelper dbHelper = new DbHelper(MenuScreen.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db != null){
            Log.d("MENSAJE: ", "Base de datos creada con exito");
        } else {
            Log.d("MENSAJE: ", "Error al crear la base de datos");
        }

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