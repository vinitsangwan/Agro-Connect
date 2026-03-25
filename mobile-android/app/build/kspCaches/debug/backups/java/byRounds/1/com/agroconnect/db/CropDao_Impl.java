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
public final class CropDao_Impl implements CropDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CachedCrop> __insertionAdapterOfCachedCrop;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CropDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCachedCrop = new EntityInsertionAdapter<CachedCrop>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cached_crops` (`cropId`,`cropNameEn`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CachedCrop entity) {
        statement.bindLong(1, entity.getCropId());
        statement.bindString(2, entity.getCropNameEn());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cached_crops";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<CachedCrop> crops,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCachedCrop.insert(crops);
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
  public Flow<List<CachedCrop>> getAll() {
    final String _sql = "SELECT * FROM cached_crops ORDER BY cropNameEn";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"cached_crops"}, new Callable<List<CachedCrop>>() {
      @Override
      @NonNull
      public List<CachedCrop> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCropId = CursorUtil.getColumnIndexOrThrow(_cursor, "cropId");
          final int _cursorIndexOfCropNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "cropNameEn");
          final List<CachedCrop> _result = new ArrayList<CachedCrop>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CachedCrop _item;
            final int _tmpCropId;
            _tmpCropId = _cursor.getInt(_cursorIndexOfCropId);
            final String _tmpCropNameEn;
            _tmpCropNameEn = _cursor.getString(_cursorIndexOfCropNameEn);
            _item = new CachedCrop(_tmpCropId,_tmpCropNameEn);
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
