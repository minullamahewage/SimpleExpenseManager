package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;

public final class TableDetails {

    public static class AccountsTable{
        public static final String TABLE_NAME = "accounts";
        public static final String ACCOUNT_NO = "accountNo";
        public static final String BANK_NAME = "bankName";
        public static final String ACCOUNT_HOLDER_NAME = "accountHolderName";
        public static final String BALANCE  = "balance";

    }

    public static class Transactions{
        public static final String TABLE_NAME = "transactions";
        public static final String DATE = "date";
        public static final String ACCOUNT_NO = "accountNo";
        public static final String EXPENSE_TYPE = "expenseType";
        public static final String AMOUNT = "amount";


    }
}
