package sv.edu.udb.desafio2pm190339cs171609;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbDetails extends  DbHelper{

    Context context;

    public DbDetails(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertDetails(int id_bill, int id_medicine, int quantity, double price, double total){
        long id = 0;
        Log.d("CLASE BILL ID: ", String.valueOf(id_bill));
        Log.d("CLASE MEDICINE ID: ", String.valueOf(id_medicine));
        Log.d("CLASE QUANTITY: ", String.valueOf(quantity));
        Log.d("CLASE PRICE: ", String.valueOf(price));
        Log.d("CLASE TOTAL: ", String.valueOf(total));
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("id_billFk", id_bill);
            values.put("id_medicineFk", id_medicine);
            values.put("quantity", quantity);
            values.put("price", price);
            values.put("total", total);

            id = db.insert(TABLE_DETAIL, null, values);
            Log.d("CLASE ID: ", String.valueOf(id));
        }catch (Exception ex) {
            ex.toString();
        }
        return id;
    }
}
