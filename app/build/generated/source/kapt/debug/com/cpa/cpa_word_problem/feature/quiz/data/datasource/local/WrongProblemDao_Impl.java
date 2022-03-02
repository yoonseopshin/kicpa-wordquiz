package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
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
public final class WrongProblemDao_Impl implements WrongProblemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WrongProblemEntity> __insertionAdapterOfWrongProblemEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public WrongProblemDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWrongProblemEntity = new EntityInsertionAdapter<WrongProblemEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `wrong_problems` (`pid`,`year`,`type`,`created_at`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WrongProblemEntity value) {
        stmt.bindLong(1, value.getPid());
        stmt.bindLong(2, value.getYear());
        if (value.getType() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, __QuizType_enumToString(value.getType()));
        }
        stmt.bindLong(4, value.getCreatedAt());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "\n"
                + "        DELETE FROM\n"
                + "        wrong_problems \n"
                + "        WHERE year = ?\n"
                + "        AND pid = ?\n"
                + "        AND type = ?\n"
                + "        ";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM wrong_problems";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final List<WrongProblemEntity> problems,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWrongProblemEntity.insert(problems);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object delete(final int year, final int pid, final QuizType type,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, year);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, pid);
        _argIndex = 3;
        if (type == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, __QuizType_enumToString(type));
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<WrongProblemEntity>> getAll() {
    final String _sql = "SELECT * FROM wrong_problems";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"wrong_problems"}, new Callable<List<WrongProblemEntity>>() {
      @Override
      public List<WrongProblemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPid = CursorUtil.getColumnIndexOrThrow(_cursor, "pid");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<WrongProblemEntity> _result = new ArrayList<WrongProblemEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WrongProblemEntity _item;
            final int _tmpPid;
            _tmpPid = _cursor.getInt(_cursorIndexOfPid);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final QuizType _tmpType;
            _tmpType = __QuizType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new WrongProblemEntity(_tmpPid,_tmpYear,_tmpType,_tmpCreatedAt);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __QuizType_enumToString(final QuizType _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case Accounting: return "Accounting";
      case Business: return "Business";
      case None: return "None";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private QuizType __QuizType_stringToEnum(final String _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case "Accounting": return QuizType.Accounting;
      case "Business": return QuizType.Business;
      case "None": return QuizType.None;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
