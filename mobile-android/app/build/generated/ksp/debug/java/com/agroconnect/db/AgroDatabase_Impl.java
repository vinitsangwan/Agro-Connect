package com.agroconnect.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AgroDatabase_Impl extends AgroDatabase {
  private volatile CropDao _cropDao;

  private volatile MandiDao _mandiDao;

  private volatile PriceDao _priceDao;

  private volatile AdvisoryDao _advisoryDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `cached_crops` (`cropId` INTEGER NOT NULL, `cropNameEn` TEXT NOT NULL, PRIMARY KEY(`cropId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cached_mandis` (`mandiId` INTEGER NOT NULL, `mandiName` TEXT NOT NULL, `stateCode` TEXT NOT NULL, `districtName` TEXT, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, PRIMARY KEY(`mandiId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cached_prices` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cropId` INTEGER NOT NULL, `mandiId` INTEGER NOT NULL, `date` TEXT NOT NULL, `pricePerQuintal` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cached_advisories` (`advisoryId` INTEGER NOT NULL, `advisoryType` TEXT NOT NULL, `titleEn` TEXT NOT NULL, `contentEn` TEXT NOT NULL, `urgency` TEXT NOT NULL, PRIMARY KEY(`advisoryId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7b5d1b0069042b4752868e77aaea3070')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `cached_crops`");
        db.execSQL("DROP TABLE IF EXISTS `cached_mandis`");
        db.execSQL("DROP TABLE IF EXISTS `cached_prices`");
        db.execSQL("DROP TABLE IF EXISTS `cached_advisories`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCachedCrops = new HashMap<String, TableInfo.Column>(2);
        _columnsCachedCrops.put("cropId", new TableInfo.Column("cropId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedCrops.put("cropNameEn", new TableInfo.Column("cropNameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCachedCrops = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCachedCrops = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCachedCrops = new TableInfo("cached_crops", _columnsCachedCrops, _foreignKeysCachedCrops, _indicesCachedCrops);
        final TableInfo _existingCachedCrops = TableInfo.read(db, "cached_crops");
        if (!_infoCachedCrops.equals(_existingCachedCrops)) {
          return new RoomOpenHelper.ValidationResult(false, "cached_crops(com.agroconnect.db.CachedCrop).\n"
                  + " Expected:\n" + _infoCachedCrops + "\n"
                  + " Found:\n" + _existingCachedCrops);
        }
        final HashMap<String, TableInfo.Column> _columnsCachedMandis = new HashMap<String, TableInfo.Column>(6);
        _columnsCachedMandis.put("mandiId", new TableInfo.Column("mandiId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedMandis.put("mandiName", new TableInfo.Column("mandiName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedMandis.put("stateCode", new TableInfo.Column("stateCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedMandis.put("districtName", new TableInfo.Column("districtName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedMandis.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedMandis.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCachedMandis = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCachedMandis = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCachedMandis = new TableInfo("cached_mandis", _columnsCachedMandis, _foreignKeysCachedMandis, _indicesCachedMandis);
        final TableInfo _existingCachedMandis = TableInfo.read(db, "cached_mandis");
        if (!_infoCachedMandis.equals(_existingCachedMandis)) {
          return new RoomOpenHelper.ValidationResult(false, "cached_mandis(com.agroconnect.db.CachedMandi).\n"
                  + " Expected:\n" + _infoCachedMandis + "\n"
                  + " Found:\n" + _existingCachedMandis);
        }
        final HashMap<String, TableInfo.Column> _columnsCachedPrices = new HashMap<String, TableInfo.Column>(5);
        _columnsCachedPrices.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedPrices.put("cropId", new TableInfo.Column("cropId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedPrices.put("mandiId", new TableInfo.Column("mandiId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedPrices.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedPrices.put("pricePerQuintal", new TableInfo.Column("pricePerQuintal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCachedPrices = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCachedPrices = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCachedPrices = new TableInfo("cached_prices", _columnsCachedPrices, _foreignKeysCachedPrices, _indicesCachedPrices);
        final TableInfo _existingCachedPrices = TableInfo.read(db, "cached_prices");
        if (!_infoCachedPrices.equals(_existingCachedPrices)) {
          return new RoomOpenHelper.ValidationResult(false, "cached_prices(com.agroconnect.db.CachedPrice).\n"
                  + " Expected:\n" + _infoCachedPrices + "\n"
                  + " Found:\n" + _existingCachedPrices);
        }
        final HashMap<String, TableInfo.Column> _columnsCachedAdvisories = new HashMap<String, TableInfo.Column>(5);
        _columnsCachedAdvisories.put("advisoryId", new TableInfo.Column("advisoryId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAdvisories.put("advisoryType", new TableInfo.Column("advisoryType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAdvisories.put("titleEn", new TableInfo.Column("titleEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAdvisories.put("contentEn", new TableInfo.Column("contentEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAdvisories.put("urgency", new TableInfo.Column("urgency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCachedAdvisories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCachedAdvisories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCachedAdvisories = new TableInfo("cached_advisories", _columnsCachedAdvisories, _foreignKeysCachedAdvisories, _indicesCachedAdvisories);
        final TableInfo _existingCachedAdvisories = TableInfo.read(db, "cached_advisories");
        if (!_infoCachedAdvisories.equals(_existingCachedAdvisories)) {
          return new RoomOpenHelper.ValidationResult(false, "cached_advisories(com.agroconnect.db.CachedAdvisory).\n"
                  + " Expected:\n" + _infoCachedAdvisories + "\n"
                  + " Found:\n" + _existingCachedAdvisories);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7b5d1b0069042b4752868e77aaea3070", "cceec2b6336efd5be1380d0ea0e471b9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cached_crops","cached_mandis","cached_prices","cached_advisories");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `cached_crops`");
      _db.execSQL("DELETE FROM `cached_mandis`");
      _db.execSQL("DELETE FROM `cached_prices`");
      _db.execSQL("DELETE FROM `cached_advisories`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CropDao.class, CropDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MandiDao.class, MandiDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PriceDao.class, PriceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AdvisoryDao.class, AdvisoryDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CropDao cropDao() {
    if (_cropDao != null) {
      return _cropDao;
    } else {
      synchronized(this) {
        if(_cropDao == null) {
          _cropDao = new CropDao_Impl(this);
        }
        return _cropDao;
      }
    }
  }

  @Override
  public MandiDao mandiDao() {
    if (_mandiDao != null) {
      return _mandiDao;
    } else {
      synchronized(this) {
        if(_mandiDao == null) {
          _mandiDao = new MandiDao_Impl(this);
        }
        return _mandiDao;
      }
    }
  }

  @Override
  public PriceDao priceDao() {
    if (_priceDao != null) {
      return _priceDao;
    } else {
      synchronized(this) {
        if(_priceDao == null) {
          _priceDao = new PriceDao_Impl(this);
        }
        return _priceDao;
      }
    }
  }

  @Override
  public AdvisoryDao advisoryDao() {
    if (_advisoryDao != null) {
      return _advisoryDao;
    } else {
      synchronized(this) {
        if(_advisoryDao == null) {
          _advisoryDao = new AdvisoryDao_Impl(this);
        }
        return _advisoryDao;
      }
    }
  }
}
