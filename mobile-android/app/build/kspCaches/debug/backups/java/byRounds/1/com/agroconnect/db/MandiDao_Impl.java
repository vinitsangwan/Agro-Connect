package com.agroconnect.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MandiDao_Impl implements MandiDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CachedMandi> __insertionAdapterOfCachedMandi;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public MandiDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCachedMandi = new EntityInsertionAdapter<CachedMandi>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cached_mandis` (`mandiId`,`mandiName`,`stateCode`,`districtName`,`latitude`,`longitude`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CachedMandi entity) {
        statement.bindLong(1, entity.getMandiId());
        statement.bindString(2, entity.getMandiName());
        statement.bindString(3, entity.getStateCode());
        if (entity.getDistrictName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDistrictName());
        }
        statement.bindDouble(5, entity.getLatitude());
        statement.bindDouble(6, entity.getLongitude());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cached_mandis";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<CachedMandi> mandis,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCachedMandi.insert(mandis);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CachedMandi>> getAll() {
    final String _sql = "SELECT * FROM cached_mandis ORDER BY mandiName";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"cached_mandis"}, new Callable<List<CachedMandi>>() {
      @Override
      @NonNull
      public List<CachedMandi> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMandiId = CursorUtil.getColumnIndexOrThrow(_cursor, "mandiId");
          final int _cursorIndexOfMandiName = CursorUtil.getColumnIndexOrThrow(_cursor, "mandiName");
          final int _cursorIndexOfStateCode = CursorUtil.getColumnIndexOrThrow(_cursor, "stateCode");
          final int _cursorIndexOfDistrictName = CursorUtil.getColumnIndexOrThrow(_cursor, "districtName");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final List<CachedMandi> _result = new ArrayList<CachedMandi>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CachedMandi _item;
            final int _tmpMandiId;
            _tmpMandiId = _cursor.getInt(_cursorIndexOfMandiId);
            final String _tmpMandiName;
            _tmpMandiName = _cursor.getString(_cursorIndexOfMandiName);
            final String _tmpStateCode;
            _tmpStateCode = _cursor.getString(_cursorIndexOfStateCode);
            final String _tmpDistrictName;
            if (_cursor.isNull(_cursorIndexOfDistrictName)) {
              _tmpDistrictName = null;
            } else {
              _tmpDistrictName = _cursor.getString(_cursorIndexOfDistrictName);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item = new CachedMandi(_tmpMandiId,_tmpMandiName,_tmpStateCode,_tmpDistrictName,_tmpLatitude,_tmpLongitude);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<CachedMandi>> getByState(final String state) {
    final String _sql = "SELECT * FROM cached_mandis WHERE stateCode = ? ORDER BY mandiName";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, state);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"cached_mandis"}, new Callable<List<CachedMandi>>() {
      @Override
      @NonNull
      public List<CachedMandi> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMandiId = CursorUtil.getColumnIndexOrThrow(_cursor, "mandiId");
          final int _cursorIndexOfMandiName = CursorUtil.getColumnIndexOrThrow(_cursor, "mandiName");
          final int _cursorIndexOfStateCode = CursorUtil.getColumnIndexOrThrow(_cursor, "stateCode");
          final int _cursorIndexOfDistrictName = CursorUtil.getColumnIndexOrThrow(_cursor, "districtName");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final List<CachedMandi> _result = new ArrayList<CachedMandi>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CachedMandi _item;
            final int _tmpMandiId;
            _tmpMandiId = _cursor.getInt(_cursorIndexOfMandiId);
            final String _tmpMandiName;
            _tmpMandiName = _cursor.getString(_cursorIndexOfMandiName);
            final String _tmpStateCode;
            _tmpStateCode = _cursor.getString(_cursorIndexOfStateCode);
            final String _tmpDistrictName;
            if (_cursor.isNull(_cursorIndexOfDistrictName)) {
              _tmpDistrictName = null;
            } else {
              _tmpDistrictName = _cursor.getString(_cursorIndexOfDistrictName);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item = new CachedMandi(_tmpMandiId,_tmpMandiName,_tmpStateCode,_tmpDistrictName,_tmpLatitude,_tmpLongitude);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
