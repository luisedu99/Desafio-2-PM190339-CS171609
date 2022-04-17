package sv.edu.udb.desafio2pm190339cs171609;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReportScreen extends AppCompatActivity {

    TextView txtHistorialName, txtHistorialDate, txtHistorialTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_screen);

        txtHistorialName = findViewById(R.id.txtHistorialName);
        txtHistorialDate = findViewById(R.id.txtHistorialDate);
        txtHistorialTotal = findViewById(R.id.txtHistorialTotal);

        List<Bills> ordersList  = getOrders();


    }

    public void volverMenu(View v){
        Intent intent = new Intent( ReportScreen.this, MenuScreen.class);
        startActivity(intent);
        finish();
    }

    public List<Bills> getOrders() {
        List<Bills> OrderList = new ArrayList<>();

        DbBills dbBills = new DbBills(ReportScreen.this);
        Cursor cursor = dbBills.showBills2();


        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Bills bills = new Bills();
                    bills.setId_bill(cursor.getInt(cursor.getColumnIndexOrThrow("id_bill")));
                    bills.setName(cursor.getString(cursor.getColumnIndexOrThrow("client")));
                    bills.setDate(cursor.getString(cursor.getColumnIndexOrThrow("bill_date")));
                    bills.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("total_amount")));
                    bills.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status_bill")));
                    OrderList.add(bills);

                    StringBuilder editClient = new StringBuilder();
                    editClient.append(bills.getName()).append("\n");

                    StringBuilder editDate = new StringBuilder();
                    editDate.append(bills.getDate()).append("\n");

                    StringBuilder editAmount = new StringBuilder();
                    editAmount.append("$"+ String.valueOf(bills.getAmount())).append("\n");

                    txtHistorialName.append(editClient);
                    txtHistorialDate.append(editDate);
                    txtHistorialTotal.append(editAmount);

                }while(cursor.moveToNext());
            }
        }
        dbBills.close();

        return OrderList;
    }
}