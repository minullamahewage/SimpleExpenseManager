package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.TableDetails;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO, Serializable {

    private transient SQLiteDatabase database;

    public PersistentTransactionDAO(SQLiteDatabase database){
        this.database = database;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        String sql = "INSERT INTO " + TableDetails.Transactions.TABLE_NAME + " ( " +
                TableDetails.Transactions.ACCOUNT_NO + " , " +
                TableDetails.Transactions.DATE + " , " +
                TableDetails.Transactions.EXPENSE_TYPE + " , " +
                TableDetails.Transactions.AMOUNT + " ) VALUES (?,?,?,?); ";
        try {
            SQLiteStatement stmt = database.compileStatement(sql);
            stmt.bindString(1,accountNo);
            stmt.bindString(2, date.toString());
            stmt.bindString(3,expenseType.name());
            stmt.bindDouble(4, amount );
            stmt.executeInsert();

        } catch (SQLiteException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<Transaction> getAllTransactionLogs() throws ParseException {

        String sql = "SELECT * FROM " + TableDetails.Transactions.TABLE_NAME ;

        List<Transaction> transactions = new ArrayList<>();

        Cursor cursor = database.rawQuery(sql,null);
        while(cursor.moveToNext()){
            String accountNo = cursor.getString(cursor.getColumnIndex(TableDetails.Transactions.ACCOUNT_NO));
            String dateString = cursor.getString(cursor.getColumnIndex(TableDetails.Transactions.DATE));
            String expenseType = cursor.getString(cursor.getColumnIndex(TableDetails.Transactions.EXPENSE_TYPE));
            Log.d("STATE", dateString);
            DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
            Date date = df.parse(dateString);
            Double amount = cursor.getDouble(cursor.getColumnIndex(TableDetails.Transactions.AMOUNT));

            Transaction transaction = new Transaction(date, accountNo, ExpenseType.valueOf(expenseType), amount);

            transactions.add(transaction);
        }

        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) throws ParseException {

        String sql = "SELECT * FROM " + TableDetails.Transactions.TABLE_NAME +  " LIMIT " +
                limit + " ;" ;

        List<Transaction> transactions = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql,null);

        while(cursor.moveToNext()){
            String accountNo = cursor.getString(cursor.getColumnIndex(TableDetails.Transactions.ACCOUNT_NO));
            String dateString = cursor.getString(cursor.getColumnIndex(TableDetails.Transactions.DATE));
            String expenseType = cursor.getString(cursor.getColumnIndex(TableDetails.Transactions.EXPENSE_TYPE));
//            DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
//            Date date = df.parse(dateString);
            Double amount = cursor.getDouble(cursor.getColumnIndex(TableDetails.Transactions.AMOUNT));

            Transaction transaction = new Transaction(new Date(dateString), accountNo, ExpenseType.valueOf(expenseType), amount);

            transactions.add(transaction);
        }

        return transactions;
    }
}
