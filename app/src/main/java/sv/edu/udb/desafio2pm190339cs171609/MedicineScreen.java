package sv.edu.udb.desafio2pm190339cs171609;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MedicineScreen extends AppCompatActivity {

    EditText txtClientname, txtClientDate;
    Button btnAddOrder, btnAddMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_screen);

        txtClientname = findViewById(R.id.txtClientName);
        txtClientDate = findViewById(R.id.txtDateClient);
        btnAddOrder = findViewById(R.id.btnAddOrder);

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbBills dbBills = new DbBills(MedicineScreen.this);
                long id = dbBills.insertBills(txtClientname.getText().toString(), txtClientDate.getText().toString(),0.0, 0);

                if(id > 0){
                    Log.d("Mensaje: ", "Orden de compra agregada");
                    Toast.makeText(MedicineScreen.this, "ORDEN DE COMPRA AGREGADA CON EXITO", Toast.LENGTH_LONG).show();
                }else{
                    Log.d("Mensaje: ", "ERROR al agregar orden de compra");
                    Toast.makeText(MedicineScreen.this, "ERROR AL GUARDAR ORDEN ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void volverMenu(View v){
        Intent intent = new Intent( MedicineScreen.this, MenuScreen.class);
        startActivity(intent);
        finish();
    }
}