package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
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
public final class ProblemDao_Impl implements ProblemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProblemEntity> __insertionAdapterOfProblemEntity;

  private final Converters __converters = new Converters();

  public ProblemDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProblemEntity = new EntityInsertionAdapter<ProblemEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `problems` (`pid`,`year`,`description`,`sub_description`,`questions`,`answer`,`type`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ProblemEntity value) {
        stmt.bindLong(1, value.getPid());
        stmt.bindLong(2, value.getYear());
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        final String _tmp = __converters.fromList(value.getSubDescriptions());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        final String _tmp_1 = __converters.fromList(value.getQuestions());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp_1);
        }
        stmt.bindLong(6, value.getAnswer());
        if (value.getType() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, __QuizType_enumToString(value.getType()));
        }
      }
    };
  }

  @Override
  public Object insert(final List<ProblemEntity> problems,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProblemEntity.insert(problems);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<ProblemEntity>> getAll() {
    final String _sql = "SELECT * FROM problems";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"problems"}, new Callable<List<ProblemEntity>>() {
      @Override
      public List<ProblemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPid = CursorUtil.getColumnIndexOrThrow(_cursor, "pid");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubDescriptions = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_description");
          final int _cursorIndexOfQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "questions");
          final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<ProblemEntity> _result = new ArrayList<ProblemEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ProblemEntity _item;
            final int _tmpPid;
            _tmpPid = _cursor.getInt(_cursorIndexOfPid);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final List<String> _tmpSubDescriptions;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSubDescriptions)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSubDescriptions);
            }
            _tmpSubDescriptions = __converters.toList(_tmp);
            final List<String> _tmpQuestions;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfQuestions)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfQuestions);
            }
            _tmpQuestions = __converters.toList(_tmp_1);
            final int _tmpAnswer;
            _tmpAnswer = _cursor.getInt(_cursorIndexOfAnswer);
            final QuizType _tmpType;
            _tmpType = __QuizType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            _item = new ProblemEntity(_tmpPid,_tmpYear,_tmpDescription,_tmpSubDescriptions,_tmpQuestions,_tmpAnswer,_tmpType);
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
  public ProblemEntity get(final QuizType type) {
    final String _sql = "\n"
            + "        SELECT * \n"
            + "        FROM problems \n"
            + "        WHERE type = ? \n"
            + "        ORDER BY RANDOM() \n"
            + "        LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, __QuizType_enumToString(type));
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfPid = CursorUtil.getColumnIndexOrThrow(_cursor, "pid");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfSubDescriptions = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_description");
      final int _cursorIndexOfQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "questions");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final ProblemEntity _result;
      if(_cursor.moveToFirst()) {
        final int _tmpPid;
        _tmpPid = _cursor.getInt(_cursorIndexOfPid);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final List<String> _tmpSubDescriptions;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfSubDescriptions)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfSubDescriptions);
        }
        _tmpSubDescriptions = __converters.toList(_tmp);
        final List<String> _tmpQuestions;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfQuestions)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfQuestions);
        }
        _tmpQuestions = __converters.toList(_tmp_1);
        final int _tmpAnswer;
        _tmpAnswer = _cursor.getInt(_cursorIndexOfAnswer);
        final QuizType _tmpType;
        _tmpType = __QuizType_stringToEnum(_cursor.getString(_cursorIndexOfType));
        _result = new ProblemEntity(_tmpPid,_tmpYear,_tmpDescription,_tmpSubDescriptions,_tmpQuestions,_tmpAnswer,_tmpType);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ProblemEntity get(final int year, final int pid, final QuizType type) {
    final String _sql = "\n"
            + "            SELECT *\n"
            + "            FROM problems\n"
            + "            WHERE type = ?\n"
            + "            AND year= ?\n"
            + "            AND pid= ?\n"
            + "            LIMIT 1\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, __QuizType_enumToString(type));
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, year);
    _argIndex = 3;
    _statement.bindLong(_argIndex, pid);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfPid = CursorUtil.getColumnIndexOrThrow(_cursor, "pid");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfSubDescriptions = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_description");
      final int _cursorIndexOfQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "questions");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final ProblemEntity _result;
      if(_cursor.moveToFirst()) {
        final int _tmpPid;
        _tmpPid = _cursor.getInt(_cursorIndexOfPid);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final List<String> _tmpSubDescriptions;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfSubDescriptions)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfSubDescriptions);
        }
        _tmpSubDescriptions = __converters.toList(_tmp);
        final List<String> _tmpQuestions;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfQuestions)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfQuestions);
        }
        _tmpQuestions = __converters.toList(_tmp_1);
        final int _tmpAnswer;
        _tmpAnswer = _cursor.getInt(_cursorIndexOfAnswer);
        final QuizType _tmpType;
        _tmpType = __QuizType_stringToEnum(_cursor.getString(_cursorIndexOfType));
        _result = new ProblemEntity(_tmpPid,_tmpYear,_tmpDescription,_tmpSubDescriptions,_tmpQuestions,_tmpAnswer,_tmpType);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Object search(final String text,
      final Continuation<? super List<ProblemEntity>> continuation) {
    final String _sql = "\n"
            + "        SELECT *\n"
            + "        FROM problems\n"
            + "        WHERE description LIKE '%' || ? || '%'\n"
            + "        OR sub_description LIKE '%' || ? || '%'\n"
            + "        OR questions LIKE '%' || ? || '%'\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (text == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, text);
    }
    _argIndex = 2;
    if (text == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, text);
    }
    _argIndex = 3;
    if (text == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, text);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ProblemEntity>>() {
      @Override
      public List<ProblemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPid = CursorUtil.getColumnIndexOrThrow(_cursor, "pid");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubDescriptions = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_description");
          final int _cursorIndexOfQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "questions");
          final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<ProblemEntity> _result = new ArrayList<ProblemEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ProblemEntity _item;
            final int _tmpPid;
            _tmpPid = _cursor.getInt(_cursorIndexOfPid);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final List<String> _tmpSubDescriptions;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSubDescriptions)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSubDescriptions);
            }
            _tmpSubDescriptions = __converters.toList(_tmp);
            final List<String> _tmpQuestions;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfQuestions)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfQuestions);
            }
            _tmpQuestions = __converters.toList(_tmp_1);
            final int _tmpAnswer;
            _tmpAnswer = _cursor.getInt(_cursorIndexOfAnswer);
            final QuizType _tmpType;
            _tmpType = __QuizType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            _item = new ProblemEntity(_tmpPid,_tmpYear,_tmpDescription,_tmpSubDescriptions,_tmpQuestions,_tmpAnswer,_tmpType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
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
