package com.test;

import android.content.ContentValues;
import android.database.Cursor;
import com.squareup.sqldelight.ColumnAdapter;
import java.lang.String;

public interface UserModel {
  String TABLE_NAME = "user";

  String BALANCE = "balance";

  String CREATE_TABLE = ""
      + "CREATE TABLE user (\n"
      + "    balance BLOB NOT NULL\n"
      + ")";

  User.Money balance();

  final class Mapper<T extends UserModel> {
    private final Creator<T> creator;

    private final ColumnAdapter<User.Money> balanceAdapter;

    protected Mapper(Creator<T> creator, ColumnAdapter<User.Money> balanceAdapter) {
      this.creator = creator;
      this.balanceAdapter = balanceAdapter;
    }

    public T map(Cursor cursor) {
      return creator.create(
          balanceAdapter.map(cursor, cursor.getColumnIndex(BALANCE))
      );
    }

    public interface Creator<R extends UserModel> {
      R create(User.Money balance);
    }
  }

  class UserMarshal<T extends UserMarshal<T>> {
    protected ContentValues contentValues = new ContentValues();

    private final ColumnAdapter<User.Money> balanceAdapter;

    public UserMarshal(ColumnAdapter<User.Money> balanceAdapter) {
      this.balanceAdapter = balanceAdapter;
    }

    public UserMarshal(UserModel copy, ColumnAdapter<User.Money> balanceAdapter) {
      this.balanceAdapter = balanceAdapter;
      this.balance(copy.balance());
    }

    public final ContentValues asContentValues() {
      return contentValues;
    }

    public T balance(User.Money balance) {
      balanceAdapter.marshal(contentValues, BALANCE, balance);
      return (T) this;
    }
  }
}
