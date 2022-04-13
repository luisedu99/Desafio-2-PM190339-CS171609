package sv.edu.udb.desafio2pm190339cs171609;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbBills extends  DbHelper {

    Context context;

    public DbBills(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertBills(String client, String billsDate, double total_amount, int status_bill){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("client", client);
            values.put("bill_date", billsDate);
            values.put("total_amount", total_amount);
            values.put("status_bill", status_bill);

            id = db.insert(TABLE_BILLS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }
}
