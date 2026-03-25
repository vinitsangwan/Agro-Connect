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
public final class AdvisoryDao_Impl implements AdvisoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CachedAdvisory> __insertionAdapterOfCachedAdvisory;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public AdvisoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCachedAdvisory = new EntityInsertionAdapter<CachedAdvisory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cached_advisories` (`advisoryId`,`advisoryType`,`titleEn`,`contentEn`,`urgency`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CachedAdvisory entity) {
        statement.bindLong(1, entity.getAdvisoryId());
        statement.bindString(2, entity.getAdvisoryType());
        statement.bindString(3, entity.getTitleEn());
        statement.bindString(4, entity.getContentEn());
        statement.bindString(5, entity.getUrgency());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cached_advisories";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<CachedAdvisory> advisories,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCachedAdvisory.insert(advisories);
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
  public Flow<List<CachedAdvisory>> getAll() {
    final String _sql = "SELECT * FROM cached_advisories ORDER BY urgency";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"cached_advisories"}, new Callable<List<CachedAdvisory>>() {
      @Override
      @NonNull
      public List<CachedAdvisory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAdvisoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "advisoryId");
          final int _cursorIndexOfAdvisoryType = CursorUtil.getColumnIndexOrThrow(_cursor, "advisoryType");
          final int _cursorIndexOfTitleEn = CursorUtil.getColumnIndexOrThrow(_cursor, "titleEn");
          final int _cursorIndexOfContentEn = CursorUtil.getColumnIndexOrThrow(_cursor, "contentEn");
          final int _cursorIndexOfUrgency = CursorUtil.getColumnIndexOrThrow(_cursor, "urgency");
          final List<CachedAdvisory> _result = new ArrayList<CachedAdvisory>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CachedAdvisory _item;
            final int _tmpAdvisoryId;
            _tmpAdvisoryId = _cursor.getInt(_cursorIndexOfAdvisoryId);
            final String _tmpAdvisoryType;
            _tmpAdvisoryType = _cursor.getString(_cursorIndexOfAdvisoryType);
            final String _tmpTitleEn;
            _tmpTitleEn = _cursor.getString(_cursorIndexOfTitleEn);
            final String _tmpContentEn;
            _tmpContentEn = _cursor.getString(_cursorIndexOfContentEn);
            final String _tmpUrgency;
            _tmpUrgency = _cursor.getString(_cursorIndexOfUrgency);
            _item = new CachedAdvisory(_tmpAdvisoryId,_tmpAdvisoryType,_tmpTitleEn,_tmpContentEn,_tmpUrgency);
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
