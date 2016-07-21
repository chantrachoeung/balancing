package com.chantra.lampscrap.api.key;

/**
 * Created by phearom on 7/16/16.
 */
public interface K {
    String IS_EXPENSE = "is_expense";
    String TYPE_ID = "type_id";
    String TYPE_NAME = "type_name";

    enum FilterMode {
        DAY, WEEK, MONTH, YEAR
    }

    enum TType {
        INCOME, EXPENSE
    }
}
