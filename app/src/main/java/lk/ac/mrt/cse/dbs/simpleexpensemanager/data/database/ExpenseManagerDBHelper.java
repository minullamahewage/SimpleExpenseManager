package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ExpenseManagerDBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1 ;
    public static final String DATABASE_NAME = "170342N.db";

    public ExpenseManagerDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static final String CREATE_ACCOUNT_TABLE =
            "CREATE TABLE " + TableDetails.AccountsTable.TABLE_NAME + " ( " +
                    TableDetails.AccountsTable.ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                    TableDetails.AccountsTable.ACCOUNT_NO + " TEXT , " +
                    TableDetails.AccountsTable.ACCOUNT_HOLDER_NAME + " VARCHAR(50), " +
                    TableDetails.AccountsTable.BANK_NAME + " VARCHAR(50) , "+
                    TableDetails.AccountsTable.BALANCE + " DOUBLE) ";

    public static final String CREATE_TRANSACTION_TABLE =
            "CREATE TABLE " + TableDetails.Transactions.TABLE_NAME + " ( " +
                    TableDetails.Transactions.TRANSACTION_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TableDetails.Transactions.ACCOUNT_NO + " VARCHAR(20), " +
                    TableDetails.Transactions.AMOUNT + " DOUBLE, " +
                    TableDetails.Transactions.DATE + " VARCHAR(20), " +
                    TableDetails.Transactions.EXPENSE_TYPE + " VARCHAR(20) ," +
                    "FOREIGN KEY (" + TableDetails.Transactions.ACCOUNT_NO + ") REFERENCES " + TableDetails.AccountsTable.TABLE_NAME + " ( "+
                    TableDetails.AccountsTable.ACCOUNT_NO + " ))";


}
