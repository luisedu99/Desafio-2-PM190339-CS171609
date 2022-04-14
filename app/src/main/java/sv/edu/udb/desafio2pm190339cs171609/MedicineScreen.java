package sv.edu.udb.desafio2pm190339cs171609;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MedicineScreen extends AppCompatActivity{

    EditText txtClientname, txtClientDate, txtQuantity;
    Button btnAddOrder, btnAddMedicine;
    Spinner spnMedicines;
    TextView txtvMedicineName, txtvMedicinePrice, txtvBillID,txtvMedicineID;
    ArrayList<Bills> billList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_screen);

        txtClientname = findViewById(R.id.txtClientName);
        txtClientDate = findViewById(R.id.txtDateClient);
        btnAddOrder = findViewById(R.id.btnAddOrder);
        btnAddMedicine = findViewById(R.id.btnAddMedicine);
        spnMedicines = findViewById(R.id.spnMedicines);
        txtvMedicineName = findViewById(R.id.txtvMedicineName);
        txtvMedicinePrice = findViewById(R.id.txtvMedicinePrice);
        txtvBillID = findViewById(R.id.txtvBillID);
        txtvMedicineID = findViewById(R.id.txtvMedicineID);
        txtQuantity = findViewById(R.id.txtMedicineQuantity);

        List<Medicines> medicinesList = fillMedicinesSpinner();
        ArrayAdapter<Medicines> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, medicinesList);
        spnMedicines.setAdapter(arrayAdapter);

        spnMedicines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int idMed = ((Medicines) adapterView.getSelectedItem()).getId_medicine();
                String nameMed = ((Medicines) adapterView.getSelectedItem()).getName();
                double priceMed = ((Medicines) adapterView.getSelectedItem()).getPrice();

                txtvMedicineID.setText(String.valueOf(idMed));
                txtvMedicineName.setText(nameMed);
                txtvMedicinePrice.setText(String.valueOf(priceMed));

                Log.d("ID: ", String.valueOf(idMed));
                Log.d("NAME: ", String.valueOf(nameMed));
                Log.d("PRICE: ", String.valueOf(priceMed));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

        btnAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getBillID();
                if(txtvBillID.getText() != "TextView"){
                    Log.d("Mensaje: ", "ES DIFERENTE DE TextView");
                    DbDetails dbDetails = new DbDetails(MedicineScreen.this);

                    int billId = Integer.parseInt(txtvBillID.getText().toString());
                    int medicineId = Integer.parseInt(txtvMedicineID.getText().toString());
                    int quantity = Integer.parseInt(txtQuantity.getText().toString());
                    double price = Double.parseDouble(txtvMedicinePrice.getText().toString());
                    double total = quantity * price;
                    Log.d("BILL ID: ", String.valueOf(billId));
                    Log.d("MEDICINE ID: ", String.valueOf(medicineId));
                    Log.d("QUANTITY: ", String.valueOf(quantity));
                    Log.d("PRICE: ", String.valueOf(price));
                    Log.d("TOTAL: ", String.valueOf(total));

                    long id = dbDetails.insertDetails(billId,medicineId,quantity,price, total);
                    if(id > 0){
                        Log.d("Mensaje: ", "Medicamento agregado correctamente");
                        Toast.makeText(MedicineScreen.this, "MEDICAMENTO AGREGADO CON EXITO", Toast.LENGTH_LONG).show();
                        txtQuantity.setText("");
                        txtvMedicineName.setText("Nombre del medicamento.");
                        txtvMedicinePrice.setText("Precio del medicamento.");
                    }else{
                        Log.d("Mensaje: ", "ERROR al agregar medicamento");
                        Toast.makeText(MedicineScreen.this, "ERROR AGREGAR MEDICAMENTO", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Log.d("Mensaje: ", "NO ES DIFERENTE DE TextView");
                }
            }
        });

    }

    private List<Medicines> fillMedicinesSpinner(){
        List<Medicines> Medlist = new ArrayList<>();
        DbMedicines dbMedicines = new DbMedicines(MedicineScreen.this);
        Cursor cursor = dbMedicines.showMedicines();

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Medicines med = new Medicines();
                    med.setId_medicine(cursor.getInt(cursor.getColumnIndexOrThrow("id_medicine")));
                    med.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    med.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
                    //med.setId_medicine(cursor.getInt(cursor.getColumnIndex("id_medicine")));
                    //med.setName(cursor.getString(cursor.getColumnIndex("name")));
                    //med.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                    Medlist.add(med);
                }while(cursor.moveToNext());
            }
        }
        dbMedicines.close();

        return Medlist;
    }

    private void getBillID(){
        try {
           DbHelper dbHelper = new DbHelper(this);
           SQLiteDatabase db = dbHelper.getReadableDatabase();
           Bills bill = null;
           String nameClient = txtClientname.getText().toString();
            Log.d("NAMECLIENT: ", nameClient);
           billList = new ArrayList<Bills>();
           Cursor cursor = db.rawQuery("SELECT * FROM t_bills where client = '" + nameClient + "'", null);

            while (cursor.moveToNext()){
                bill = new Bills();
                bill.setId_bill(cursor.getInt(0));
                billList.add(bill);
                txtvBillID.setText(String.valueOf(bill.getId_bill()));
                Log.d("CURSOR: ", String.valueOf(bill.getId_bill()));
            }

        }catch (Exception ex){

        }
    }

    public void volverMenu(View v){
        Intent intent = new Intent( MedicineScreen.this, MenuScreen.class);
        startActivity(intent);
        finish();
    }
}