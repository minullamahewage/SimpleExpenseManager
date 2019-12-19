package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;



import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.TableDetails;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO, Serializable {

    private transient SQLiteDatabase database;

    public PersistentAccountDAO(SQLiteDatabase database){
        this.database = database;
    }

    @Override
    public List<String> getAccountNumbersList() {

        String sql = "SELECT " + TableDetails.AccountsTable.ACCOUNT_NO + " FROM " + TableDetails.AccountsTable.TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);
        List<String> accountNumbersList = new ArrayList<>();

        while(cursor.moveToNext()){
            String accountNo = cursor.getString(cursor.getColumnIndex(TableDetails.AccountsTable.ACCOUNT_NO));

            accountNumbersList.add(accountNo);
        }
        return accountNumbersList;
    }

    @Override
    public List<Account> getAccountsList() {

        String sql = "SELECT * FROM " + TableDetails.AccountsTable.TABLE_NAME;
        Cursor cursor = database.rawQuery(sql,null);
        List<Account> accountList = new ArrayList<>();
        while(cursor.moveToNext()){
            String accountNo = cursor.getString(cursor.getColumnIndex(TableDetails.AccountsTable.ACCOUNT_NO));
            String bankName = cursor.getString(cursor.getColumnIndex(TableDetails.AccountsTable.BANK_NAME));
            String accountHolderName = cursor.getString(cursor.getColumnIndex(TableDetails.AccountsTable.ACCOUNT_HOLDER_NAME));
            double balance = cursor.getDouble(cursor.getColumnIndex(TableDetails.AccountsTable.BALANCE));

            Account account = new Account(accountNo, bankName, accountHolderName, balance);
            accountList.add(account);
        }
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        String sql = "SELECT * FROM " + TableDetails.AccountsTable.TABLE_NAME +" WHERE " + TableDetails.AccountsTable.ACCOUNT_NO + " = " + accountNo + " ;";
        Cursor cursor = database.rawQuery(sql,null);
        if(cursor.getCount() == 0){
            String message = "Account No is invalid";
            throw new InvalidAccountException(message);
        }
        else{
            cursor.moveToFirst();
            String bankName = cursor.getString(cursor.getColumnIndex(TableDetails.AccountsTable.BANK_NAME));
            String accountHolderName = cursor.getString(cursor.getColumnIndex(TableDetails.AccountsTable.ACCOUNT_HOLDER_NAME));
            double balance = cursor.getDouble(cursor.getColumnIndex(TableDetails.AccountsTable.BALANCE));
            Account account = new Account(accountNo, bankName, accountHolderName, balance);
            return account;
        }
    }

    @Override
    public void addAccount(Account account) {
        String sql = "INSERT INTO " + TableDetails.AccountsTable.TABLE_NAME + " ( " +
                TableDetails.AccountsTable.ACCOUNT_NO + " , " +
                TableDetails.AccountsTable.ACCOUNT_HOLDER_NAME + " , " +
                TableDetails.AccountsTable.BANK_NAME + " , " +
                TableDetails.AccountsTable.BALANCE + " ) " +
                " VALUES (?,?,?,?); ";
        try{
            SQLiteStatement stmt = database.compileStatement(sql);
            stmt.bindString(1,account.getAccountNo());
            stmt.bindString(2, account.getAccountHolderName());
            stmt.bindString(3,account.getBankName());
            stmt.bindDouble(4,account.getBalance());
            stmt.executeInsert();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String sql = "DELETE FROM " + TableDetails.AccountsTable.TABLE_NAME + " WHERE " + TableDetails.AccountsTable.ACCOUNT_NO + " = ? ;";
        try{
            SQLiteStatement stmt = database.compileStatement(sql);
            stmt.bindString(1,accountNo);
            stmt.executeUpdateDelete();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        String sql = "UPDATE " + TableDetails.AccountsTable.TABLE_NAME + " SET " + TableDetails.AccountsTable.BALANCE + " = ?" + " WHERE " + TableDetails.AccountsTable.ACCOUNT_NO + " ? ;";
        try{
            SQLiteStatement stmt

        }catch{

        }

    }
}
