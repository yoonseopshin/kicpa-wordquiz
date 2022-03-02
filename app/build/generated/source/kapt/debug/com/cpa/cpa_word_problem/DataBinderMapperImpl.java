package com.cpa.cpa_word_problem;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.cpa.cpa_word_problem.databinding.ActivityProblemDetailBindingImpl;
import com.cpa.cpa_word_problem.databinding.ActivityQuizStatisticsBindingImpl;
import com.cpa.cpa_word_problem.databinding.FragmentHomeBindingImpl;
import com.cpa.cpa_word_problem.databinding.FragmentNoteBindingImpl;
import com.cpa.cpa_word_problem.databinding.LayoutBottomSheetSearchBindingImpl;
import com.cpa.cpa_word_problem.databinding.LayoutCommonNoteHeaderBindingImpl;
import com.cpa.cpa_word_problem.databinding.LayoutNoteResultHeaderBindingImpl;
import com.cpa.cpa_word_problem.databinding.LayoutProblemDetailBindingImpl;
import com.cpa.cpa_word_problem.databinding.LayoutScrollToTopBindingImpl;
import com.cpa.cpa_word_problem.databinding.ListItemProblemBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYPROBLEMDETAIL = 1;

  private static final int LAYOUT_ACTIVITYQUIZSTATISTICS = 2;

  private static final int LAYOUT_FRAGMENTHOME = 3;

  private static final int LAYOUT_FRAGMENTNOTE = 4;

  private static final int LAYOUT_LAYOUTBOTTOMSHEETSEARCH = 5;

  private static final int LAYOUT_LAYOUTCOMMONNOTEHEADER = 6;

  private static final int LAYOUT_LAYOUTNOTERESULTHEADER = 7;

  private static final int LAYOUT_LAYOUTPROBLEMDETAIL = 8;

  private static final int LAYOUT_LAYOUTSCROLLTOTOP = 9;

  private static final int LAYOUT_LISTITEMPROBLEM = 10;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(10);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.cpa.cpa_word_problem.R.layout.activity_problem_detail, LAYOUT_ACTIVITYPROBLEMDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.cpa.cpa_word_problem.R.layout.activity_quiz_statistics, LAYOUT_ACTIVITYQUIZSTATISTICS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.cpa.cpa_word_problem.R.layout.fragment_home, LAYOUT_FRAGMENTHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.cpa.cpa_word_problem.R.layout.fragment_note, LAYOUT_FRAGMENTNOTE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.cpa.cpa_word_problem.R.layout.layout_bottom_sheet_search, LAYOUT_LAYOUTBOTTOMSHEETSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.cpa.cpa_word_problem.R.layout.layout_common_note_header, LAYOUT_LAYOUTCOMMONNOTEHEADER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.cpa.cpa_word_problem.R.layout.layout_note_result_header, LAYOUT_LAYOUTNOTERESULTHEADER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.cpa.cpa_word_problem.R.layout.layout_problem_detail, LAYOUT_LAYOUTPROBLEMDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.cpa.cpa_word_problem.R.layout.layout_scroll_to_top, LAYOUT_LAYOUTSCROLLTOTOP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.cpa.cpa_word_problem.R.layout.list_item_problem, LAYOUT_LISTITEMPROBLEM);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYPROBLEMDETAIL: {
          if ("layout/activity_problem_detail_0".equals(tag)) {
            return new ActivityProblemDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_problem_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYQUIZSTATISTICS: {
          if ("layout/activity_quiz_statistics_0".equals(tag)) {
            return new ActivityQuizStatisticsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_quiz_statistics is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTHOME: {
          if ("layout/fragment_home_0".equals(tag)) {
            return new FragmentHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_home is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTNOTE: {
          if ("layout/fragment_note_0".equals(tag)) {
            return new FragmentNoteBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_note is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTBOTTOMSHEETSEARCH: {
          if ("layout/layout_bottom_sheet_search_0".equals(tag)) {
            return new LayoutBottomSheetSearchBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_bottom_sheet_search is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTCOMMONNOTEHEADER: {
          if ("layout/layout_common_note_header_0".equals(tag)) {
            return new LayoutCommonNoteHeaderBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_common_note_header is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTNOTERESULTHEADER: {
          if ("layout/layout_note_result_header_0".equals(tag)) {
            return new LayoutNoteResultHeaderBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_note_result_header is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTPROBLEMDETAIL: {
          if ("layout/layout_problem_detail_0".equals(tag)) {
            return new LayoutProblemDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_problem_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTSCROLLTOTOP: {
          if ("layout/layout_scroll_to_top_0".equals(tag)) {
            return new LayoutScrollToTopBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_scroll_to_top is invalid. Received: " + tag);
        }
        case  LAYOUT_LISTITEMPROBLEM: {
          if ("layout/list_item_problem_0".equals(tag)) {
            return new ListItemProblemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for list_item_problem is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(9);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "elapsedTime");
      sKeys.put(2, "headerTitle");
      sKeys.put(3, "mode");
      sKeys.put(4, "onClickListener");
      sKeys.put(5, "onLongClickListener");
      sKeys.put(6, "problem");
      sKeys.put(7, "userSelectedIndex");
      sKeys.put(8, "viewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(10);

    static {
      sKeys.put("layout/activity_problem_detail_0", com.cpa.cpa_word_problem.R.layout.activity_problem_detail);
      sKeys.put("layout/activity_quiz_statistics_0", com.cpa.cpa_word_problem.R.layout.activity_quiz_statistics);
      sKeys.put("layout/fragment_home_0", com.cpa.cpa_word_problem.R.layout.fragment_home);
      sKeys.put("layout/fragment_note_0", com.cpa.cpa_word_problem.R.layout.fragment_note);
      sKeys.put("layout/layout_bottom_sheet_search_0", com.cpa.cpa_word_problem.R.layout.layout_bottom_sheet_search);
      sKeys.put("layout/layout_common_note_header_0", com.cpa.cpa_word_problem.R.layout.layout_common_note_header);
      sKeys.put("layout/layout_note_result_header_0", com.cpa.cpa_word_problem.R.layout.layout_note_result_header);
      sKeys.put("layout/layout_problem_detail_0", com.cpa.cpa_word_problem.R.layout.layout_problem_detail);
      sKeys.put("layout/layout_scroll_to_top_0", com.cpa.cpa_word_problem.R.layout.layout_scroll_to_top);
      sKeys.put("layout/list_item_problem_0", com.cpa.cpa_word_problem.R.layout.list_item_problem);
    }
  }
}
