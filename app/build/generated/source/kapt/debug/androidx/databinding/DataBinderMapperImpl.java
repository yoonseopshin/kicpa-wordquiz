package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.cpa.cpa_word_problem.DataBinderMapperImpl());
  }
}
