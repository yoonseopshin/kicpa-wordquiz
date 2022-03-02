package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ProblemDao _problemDao;

  private volatile WrongProblemDao _wrongProblemDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `problems` (`pid` INTEGER NOT NULL, `year` INTEGER NOT NULL, `description` TEXT NOT NULL, `sub_description` TEXT NOT NULL, `questions` TEXT NOT NULL, `answer` INTEGER NOT NULL, `type` TEXT NOT NULL, PRIMARY KEY(`pid`, `year`, `type`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `wrong_problems` (`pid` INTEGER NOT NULL, `year` INTEGER NOT NULL, `type` TEXT NOT NULL, `created_at` INTEGER NOT NULL, PRIMARY KEY(`pid`, `year`, `type`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '15a094d8dd9dfd9a09fd4c4eb0a5786e')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `problems`");
        _db.execSQL("DROP TABLE IF EXISTS `wrong_problems`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsProblems = new HashMap<String, TableInfo.Column>(7);
        _columnsProblems.put("pid", new TableInfo.Column("pid", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProblems.put("year", new TableInfo.Column("year", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProblems.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProblems.put("sub_description", new TableInfo.Column("sub_description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProblems.put("questions", new TableInfo.Column("questions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProblems.put("answer", new TableInfo.Column("answer", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProblems.put("type", new TableInfo.Column("type", "TEXT", true, 3, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProblems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProblems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProblems = new TableInfo("problems", _columnsProblems, _foreignKeysProblems, _indicesProblems);
        final TableInfo _existingProblems = TableInfo.read(_db, "problems");
        if (! _infoProblems.equals(_existingProblems)) {
          return new RoomOpenHelper.ValidationResult(false, "problems(com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemEntity).\n"
                  + " Expected:\n" + _infoProblems + "\n"
                  + " Found:\n" + _existingProblems);
        }
        final HashMap<String, TableInfo.Column> _columnsWrongProblems = new HashMap<String, TableInfo.Column>(4);
        _columnsWrongProblems.put("pid", new TableInfo.Column("pid", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWrongProblems.put("year", new TableInfo.Column("year", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWrongProblems.put("type", new TableInfo.Column("type", "TEXT", true, 3, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWrongProblems.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWrongProblems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWrongProblems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWrongProblems = new TableInfo("wrong_problems", _columnsWrongProblems, _foreignKeysWrongProblems, _indicesWrongProblems);
        final TableInfo _existingWrongProblems = TableInfo.read(_db, "wrong_problems");
        if (! _infoWrongProblems.equals(_existingWrongProblems)) {
          return new RoomOpenHelper.ValidationResult(false, "wrong_problems(com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemEntity).\n"
                  + " Expected:\n" + _infoWrongProblems + "\n"
                  + " Found:\n" + _existingWrongProblems);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "15a094d8dd9dfd9a09fd4c4eb0a5786e", "46843b52d90d5f0b5d868b9ae5da9e65");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "problems","wrong_problems");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `problems`");
      _db.execSQL("DELETE FROM `wrong_problems`");
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
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ProblemDao.class, ProblemDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WrongProblemDao.class, WrongProblemDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public ProblemDao problemDao() {
    if (_problemDao != null) {
      return _problemDao;
    } else {
      synchronized(this) {
        if(_problemDao == null) {
          _problemDao = new ProblemDao_Impl(this);
        }
        return _problemDao;
      }
    }
  }

  @Override
  public WrongProblemDao wrongProblemDao() {
    if (_wrongProblemDao != null) {
      return _wrongProblemDao;
    } else {
      synchronized(this) {
        if(_wrongProblemDao == null) {
          _wrongProblemDao = new WrongProblemDao_Impl(this);
        }
        return _wrongProblemDao;
      }
    }
  }
}
