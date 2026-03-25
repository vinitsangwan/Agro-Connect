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
public final class PriceDao_Impl implements PriceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CachedPrice> __insertionAdapterOfCachedPrice;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public PriceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCachedPrice = new EntityInsertionAdapter<CachedPrice>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cached_prices` (`id`,`cropId`,`mandiId`,`date`,`pricePerQuintal`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CachedPrice entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCropId());
        statement.bindLong(3, entity.getMandiId());
        statement.bindString(4, entity.getDate());
        statement.bindDouble(5, entity.getPricePerQuintal());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cached_prices";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<CachedPrice> prices,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCachedPrice.insert(prices);
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
  public Flow<List<CachedPrice>> getLatestPrices(final int cropId, final int limit) {
    final String _sql = "SELECT * FROM cached_prices WHERE cropId = ? ORDER BY date DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cropId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"cached_prices"}, new Callable<List<CachedPrice>>() {
      @Override
      @NonNull
      public List<CachedPrice> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCropId = CursorUtil.getColumnIndexOrThrow(_cursor, "cropId");
          final int _cursorIndexOfMandiId = CursorUtil.getColumnIndexOrThrow(_cursor, "mandiId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfPricePerQuintal = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerQuintal");
          final List<CachedPrice> _result = new ArrayList<CachedPrice>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CachedPrice _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpCropId;
            _tmpCropId = _cursor.getInt(_cursorIndexOfCropId);
            final int _tmpMandiId;
            _tmpMandiId = _cursor.getInt(_cursorIndexOfMandiId);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final double _tmpPricePerQuintal;
            _tmpPricePerQuintal = _cursor.getDouble(_cursorIndexOfPricePerQuintal);
            _item = new CachedPrice(_tmpId,_tmpCropId,_tmpMandiId,_tmpDate,_tmpPricePerQuintal);
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
