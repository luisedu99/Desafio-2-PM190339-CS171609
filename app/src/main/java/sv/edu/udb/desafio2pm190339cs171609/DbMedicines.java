package sv.edu.udb.desafio2pm190339cs171609;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbMedicines extends  DbHelper {
    public DbMedicines(@Nullable Context context) {
        super(context);
    }

    public Cursor showMedicines(){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor rows = db.rawQuery("SELECT * FROM  " + TABLE_MEDICINES + " ORDER BY name ASC ", null);
            if(rows.moveToFirst()){
                return rows;
            }else{
                return null;
            }
        }catch(Exception ex){
            return null;
        }
    }
}
