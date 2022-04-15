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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BillScreen extends AppCompatActivity {

    Spinner spnClients;
    TextView txtvOrdernum, txtvClient, txtvDate, txtvAmount;
    Button btnUpdateAmount, btnPayBill;
    ArrayList<Details> detailList2;
    boolean result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_screen);

        spnClients = findViewById(R.id.spnClients);

        txtvOrdernum = findViewById(R.id.txtvOrderNum);
        txtvClient = findViewById(R.id.txtvClientName);
        txtvDate = findViewById(R.id.txtvDate);
        txtvAmount = findViewById(R.id.txtvAmount);
        btnUpdateAmount = findViewById(R.id.btnUpdateAmount);
        btnPayBill = findViewById(R.id.btnPay);

        List<Bills> clientsList = fillClientsSpinner();
        ArrayAdapter<Bills> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, clientsList);
        spnClients.setAdapter(arrayAdapter);

        spnClients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int idBill = ((Bills) adapterView.getSelectedItem()).getId_bill();
                String client = ((Bills) adapterView.getSelectedItem()).getName();
                String date = ((Bills) adapterView.getSelectedItem()).getDate();
                double amount = ((Bills) adapterView.getSelectedItem()).getAmount();

                txtvOrdernum.setText(String.valueOf(idBill));
                txtvClient.setText(client);
                txtvDate.setText(date);
                txtvAmount.setText(String.valueOf(amount));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnUpdateAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumPrices();
            }
        });

        //Pagar orden de compra, actualizar monto de la factura y cambiar estatus de la compra
        btnPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Double.parseDouble(txtvAmount.getText().toString()) > 0.00 && txtvOrdernum.getText().toString() != ""){
                    Log.d("UPDATE STATUS BILLS:  ", "El monto es mayor a 0 y no esta vacio el id de orden ");
                    int id_order = Integer.parseInt(txtvOrdernum.getText().toString());
                    double order_amount = Double.parseDouble(txtvAmount.getText().toString());

                    Log.d("VALOR DE ID:  ", String.valueOf(id_order));
                    Log.d("VALOR DE MONTO:  ", String.valueOf(order_amount));

                    DbBills dbBills = new DbBills(BillScreen.this);
                    result = dbBills.updateStatusBills(id_order, order_amount);

                    if(result){
                        Toast.makeText(BillScreen.this, "ORDEN DE COMPRA PAGADA CON EXITO", Toast.LENGTH_LONG).show();
                        Log.d("RESULT DE METODO DE LA CLASE:  ", "ORDEN DE COMPRA PAGADA CON EXITO");
                    }else{
                        Toast.makeText(BillScreen.this, "ERROR AL REALIZAR EL PAGO", Toast.LENGTH_LONG).show();
                        Log.d("RESULT DE METODO DE LA CLASE:  ", "ERROR AL REALIZAR EL PAGO");
                    }

                }else{
                    Toast.makeText(BillScreen.this, "ERROR: El monto es menor o igual a 0 o esta vacio el id de orden", Toast.LENGTH_LONG).show();
                    Log.d("UPDATE STATUS BILLS:  ", "El monto es menor o igual a 0 o esta vacio el id de orden");
                }
            }
        });
    }

    private List<Bills> fillClientsSpinner(){
        List<Bills> Clientlist = new ArrayList<>();
        DbBills dbBills = new DbBills(BillScreen.this);
        Cursor cursor = dbBills.showBills();

        if(cursor != null){
           if(cursor.moveToFirst()){
               do{
                   Bills bills = new Bills();
                   bills.setId_bill(cursor.getInt(cursor.getColumnIndexOrThrow("id_bill")));
                   bills.setName(cursor.getString(cursor.getColumnIndexOrThrow("client")));
                   bills.setDate(cursor.getString(cursor.getColumnIndexOrThrow("bill_date")));
                   bills.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("total_amount")));
                   bills.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status_bill")));
                   Clientlist.add(bills);
               }while(cursor.moveToNext());
           }
        }
        dbBills.close();
        return  Clientlist;
    }

    private void sumPrices(){
        int result = 0;
        try {
            DbHelper dbHelper = new DbHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            int idClient = Integer.parseInt(txtvOrdernum.getText().toString());
            Log.d("NAMECLIENT: ", String.valueOf(idClient));

            Cursor cursor = db.rawQuery("SELECT sum(total) FROM t_detail where id_billFk = '" + idClient + "'", null);

            if(cursor.moveToFirst()){
                result = cursor.getInt(0);
                Log.d("CURSOR SUMPRICES: ", String.valueOf(result));
                txtvAmount.setText(String.valueOf(result));
            }
            cursor.close();
            db.close();
            Log.d("CURSOR SUMPRICES after close db: ", String.valueOf(result));

        }catch (Exception ex){

        }
    }

    public void volverMenu(View v){
        Intent intent = new Intent( BillScreen.this, MenuScreen.class);
        startActivity(intent);
        finish();
    }
}