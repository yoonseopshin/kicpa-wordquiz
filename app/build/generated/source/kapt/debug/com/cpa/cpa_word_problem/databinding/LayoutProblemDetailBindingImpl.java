package com.cpa.cpa_word_problem.databinding;
import com.cpa.cpa_word_problem.R;
import com.cpa.cpa_word_problem.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutProblemDetailBindingImpl extends LayoutProblemDetailBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutProblemDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private LayoutProblemDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.chip.Chip) bindings[2]
            , (com.google.android.material.chip.Chip) bindings[1]
            , (android.widget.RadioButton) bindings[6]
            , (android.widget.RadioButton) bindings[7]
            , (android.widget.RadioButton) bindings[8]
            , (android.widget.RadioButton) bindings[9]
            , (android.widget.RadioButton) bindings[10]
            , (android.widget.RadioGroup) bindings[5]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            );
        this.chType.setTag(null);
        this.chYearPid.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rb0.setTag(null);
        this.rb1.setTag(null);
        this.rb2.setTag(null);
        this.rb3.setTag(null);
        this.rb4.setTag(null);
        this.rgQuestions.setTag(null);
        this.tvDescription.setTag(null);
        this.tvSubDescription.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.problem == variableId) {
            setProblem((com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem) variable);
        }
        else if (BR.mode == variableId) {
            setMode((com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setProblem(@Nullable com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem Problem) {
        this.mProblem = Problem;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.problem);
        super.requestRebind();
    }
    public void setMode(@Nullable com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode Mode) {
        this.mMode = Mode;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.mode);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        boolean problemAnswerInt1 = false;
        com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem problem = mProblem;
        java.lang.String problemQuestionsGetInt0 = null;
        java.util.List<java.lang.String> problemQuestions = null;
        java.lang.String problemQuestionsEmptyProblemQuestionsGetInt0JavaLangObjectNull = null;
        boolean modeProblemDetailModeDetail = false;
        java.lang.String problemQuestionsEmptyProblemQuestionsGetInt3JavaLangObjectNull = null;
        boolean modeProblemDetailModeDetailProblemAnswerInt1BooleanFalse = false;
        boolean problemQuestionsEmpty = false;
        boolean problemAnswerInt4 = false;
        boolean problemAnswerInt0 = false;
        java.lang.String problemDescription = null;
        int problemAnswer = 0;
        int problemYear = 0;
        boolean ProblemQuestionsEmpty1 = false;
        boolean modeProblemDetailModeDetailProblemAnswerInt2BooleanFalse = false;
        int problemPid = 0;
        java.lang.String problemQuestionsEmptyProblemQuestionsGetInt2JavaLangObjectNull = null;
        java.lang.String problemQuestionsGetInt4 = null;
        com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode mode = mMode;
        com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType problemType = null;
        boolean problemAnswerInt3 = false;
        java.lang.String problemQuestionsGetInt3 = null;
        boolean modeProblemDetailModeDetailProblemAnswerInt3BooleanFalse = false;
        boolean modeProblemDetailModeDetailProblemAnswerInt4BooleanFalse = false;
        java.util.List<java.lang.String> problemSubDescriptions = null;
        java.lang.String problemQuestionsGetInt2 = null;
        java.lang.String problemQuestionsEmptyProblemQuestionsGetInt1JavaLangObjectNull = null;
        boolean problemAnswerInt2 = false;
        java.lang.String problemQuestionsEmptyProblemQuestionsGetInt4JavaLangObjectNull = null;
        java.lang.String problemQuestionsGetInt1 = null;
        boolean modeProblemDetailModeDetailProblemAnswerInt0BooleanFalse = false;

        if ((dirtyFlags & 0x5L) != 0) {



                if (problem != null) {
                    // read problem.questions
                    problemQuestions = problem.getQuestions();
                    // read problem.description
                    problemDescription = problem.getDescription();
                    // read problem.year
                    problemYear = problem.getYear();
                    // read problem.pid
                    problemPid = problem.getPid();
                    // read problem.type
                    problemType = problem.getType();
                    // read problem.subDescriptions
                    problemSubDescriptions = problem.getSubDescriptions();
                }


                if (problemQuestions != null) {
                    // read problem.questions.empty
                    ProblemQuestionsEmpty1 = problemQuestions.isEmpty();
                }


                // read !problem.questions.empty
                problemQuestionsEmpty = !ProblemQuestionsEmpty1;
            if((dirtyFlags & 0x5L) != 0) {
                if(problemQuestionsEmpty) {
                        dirtyFlags |= 0x10L;
                        dirtyFlags |= 0x40L;
                        dirtyFlags |= 0x1000L;
                        dirtyFlags |= 0x40000L;
                        dirtyFlags |= 0x100000L;
                }
                else {
                        dirtyFlags |= 0x8L;
                        dirtyFlags |= 0x20L;
                        dirtyFlags |= 0x800L;
                        dirtyFlags |= 0x20000L;
                        dirtyFlags |= 0x80000L;
                }
            }
        }
        if ((dirtyFlags & 0x7L) != 0) {



                // read mode == ProblemDetailMode.Detail
                modeProblemDetailModeDetail = (mode) == (com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode.Detail);
            if((dirtyFlags & 0x7L) != 0) {
                if(modeProblemDetailModeDetail) {
                        dirtyFlags |= 0x100L;
                        dirtyFlags |= 0x400L;
                        dirtyFlags |= 0x4000L;
                        dirtyFlags |= 0x10000L;
                        dirtyFlags |= 0x400000L;
                }
                else {
                        dirtyFlags |= 0x80L;
                        dirtyFlags |= 0x200L;
                        dirtyFlags |= 0x2000L;
                        dirtyFlags |= 0x8000L;
                        dirtyFlags |= 0x200000L;
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x414500L) != 0) {



                if (problem != null) {
                    // read problem.answer
                    problemAnswer = problem.getAnswer();
                }

            if ((dirtyFlags & 0x100L) != 0) {

                    // read problem.answer == 1
                    problemAnswerInt1 = (problemAnswer) == (1);
            }
            if ((dirtyFlags & 0x10000L) != 0) {

                    // read problem.answer == 4
                    problemAnswerInt4 = (problemAnswer) == (4);
            }
            if ((dirtyFlags & 0x400000L) != 0) {

                    // read problem.answer == 0
                    problemAnswerInt0 = (problemAnswer) == (0);
            }
            if ((dirtyFlags & 0x4000L) != 0) {

                    // read problem.answer == 3
                    problemAnswerInt3 = (problemAnswer) == (3);
            }
            if ((dirtyFlags & 0x400L) != 0) {

                    // read problem.answer == 2
                    problemAnswerInt2 = (problemAnswer) == (2);
            }
        }
        if ((dirtyFlags & 0x10L) != 0) {

                if (problemQuestions != null) {
                    // read problem.questions.get(0)
                    problemQuestionsGetInt0 = problemQuestions.get(0);
                }
        }
        if ((dirtyFlags & 0x100000L) != 0) {

                if (problemQuestions != null) {
                    // read problem.questions.get(4)
                    problemQuestionsGetInt4 = problemQuestions.get(4);
                }
        }
        if ((dirtyFlags & 0x40L) != 0) {

                if (problemQuestions != null) {
                    // read problem.questions.get(3)
                    problemQuestionsGetInt3 = problemQuestions.get(3);
                }
        }
        if ((dirtyFlags & 0x1000L) != 0) {

                if (problemQuestions != null) {
                    // read problem.questions.get(2)
                    problemQuestionsGetInt2 = problemQuestions.get(2);
                }
        }
        if ((dirtyFlags & 0x40000L) != 0) {

                if (problemQuestions != null) {
                    // read problem.questions.get(1)
                    problemQuestionsGetInt1 = problemQuestions.get(1);
                }
        }

        if ((dirtyFlags & 0x5L) != 0) {

                // read !problem.questions.empty ? problem.questions.get(0) : null
                problemQuestionsEmptyProblemQuestionsGetInt0JavaLangObjectNull = ((problemQuestionsEmpty) ? (problemQuestionsGetInt0) : (null));
                // read !problem.questions.empty ? problem.questions.get(3) : null
                problemQuestionsEmptyProblemQuestionsGetInt3JavaLangObjectNull = ((problemQuestionsEmpty) ? (problemQuestionsGetInt3) : (null));
                // read !problem.questions.empty ? problem.questions.get(2) : null
                problemQuestionsEmptyProblemQuestionsGetInt2JavaLangObjectNull = ((problemQuestionsEmpty) ? (problemQuestionsGetInt2) : (null));
                // read !problem.questions.empty ? problem.questions.get(1) : null
                problemQuestionsEmptyProblemQuestionsGetInt1JavaLangObjectNull = ((problemQuestionsEmpty) ? (problemQuestionsGetInt1) : (null));
                // read !problem.questions.empty ? problem.questions.get(4) : null
                problemQuestionsEmptyProblemQuestionsGetInt4JavaLangObjectNull = ((problemQuestionsEmpty) ? (problemQuestionsGetInt4) : (null));
        }
        if ((dirtyFlags & 0x7L) != 0) {

                // read mode == ProblemDetailMode.Detail ? problem.answer == 1 : false
                modeProblemDetailModeDetailProblemAnswerInt1BooleanFalse = ((modeProblemDetailModeDetail) ? (problemAnswerInt1) : (false));
                // read mode == ProblemDetailMode.Detail ? problem.answer == 2 : false
                modeProblemDetailModeDetailProblemAnswerInt2BooleanFalse = ((modeProblemDetailModeDetail) ? (problemAnswerInt2) : (false));
                // read mode == ProblemDetailMode.Detail ? problem.answer == 3 : false
                modeProblemDetailModeDetailProblemAnswerInt3BooleanFalse = ((modeProblemDetailModeDetail) ? (problemAnswerInt3) : (false));
                // read mode == ProblemDetailMode.Detail ? problem.answer == 4 : false
                modeProblemDetailModeDetailProblemAnswerInt4BooleanFalse = ((modeProblemDetailModeDetail) ? (problemAnswerInt4) : (false));
                // read mode == ProblemDetailMode.Detail ? problem.answer == 0 : false
                modeProblemDetailModeDetailProblemAnswerInt0BooleanFalse = ((modeProblemDetailModeDetail) ? (problemAnswerInt0) : (false));
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            com.cpa.cpa_word_problem.feature.quiz.presentation.util.AppBindingAdapterKt.bindByType(this.chType, problemType);
            com.cpa.cpa_word_problem.feature.quiz.presentation.util.AppBindingAdapterKt.bindYearAndPid(this.chYearPid, problemYear, problemPid);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.rb0, problemQuestionsEmptyProblemQuestionsGetInt0JavaLangObjectNull);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.rb1, problemQuestionsEmptyProblemQuestionsGetInt1JavaLangObjectNull);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.rb2, problemQuestionsEmptyProblemQuestionsGetInt2JavaLangObjectNull);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.rb3, problemQuestionsEmptyProblemQuestionsGetInt3JavaLangObjectNull);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.rb4, problemQuestionsEmptyProblemQuestionsGetInt4JavaLangObjectNull);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvDescription, problemDescription);
            com.cpa.cpa_word_problem.feature.quiz.presentation.util.AppBindingAdapterKt.bindSubDescription(this.tvSubDescription, problemSubDescriptions);
        }
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.rb0, modeProblemDetailModeDetailProblemAnswerInt0BooleanFalse);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.rb1, modeProblemDetailModeDetailProblemAnswerInt1BooleanFalse);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.rb2, modeProblemDetailModeDetailProblemAnswerInt2BooleanFalse);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.rb3, modeProblemDetailModeDetailProblemAnswerInt3BooleanFalse);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.rb4, modeProblemDetailModeDetailProblemAnswerInt4BooleanFalse);
        }
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            com.cpa.cpa_word_problem.feature.quiz.presentation.util.AppBindingAdapterKt.bindProblemDetailMode(this.rgQuestions, mode);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): problem
        flag 1 (0x2L): mode
        flag 2 (0x3L): null
        flag 3 (0x4L): !problem.questions.empty ? problem.questions.get(0) : null
        flag 4 (0x5L): !problem.questions.empty ? problem.questions.get(0) : null
        flag 5 (0x6L): !problem.questions.empty ? problem.questions.get(3) : null
        flag 6 (0x7L): !problem.questions.empty ? problem.questions.get(3) : null
        flag 7 (0x8L): mode == ProblemDetailMode.Detail ? problem.answer == 1 : false
        flag 8 (0x9L): mode == ProblemDetailMode.Detail ? problem.answer == 1 : false
        flag 9 (0xaL): mode == ProblemDetailMode.Detail ? problem.answer == 2 : false
        flag 10 (0xbL): mode == ProblemDetailMode.Detail ? problem.answer == 2 : false
        flag 11 (0xcL): !problem.questions.empty ? problem.questions.get(2) : null
        flag 12 (0xdL): !problem.questions.empty ? problem.questions.get(2) : null
        flag 13 (0xeL): mode == ProblemDetailMode.Detail ? problem.answer == 3 : false
        flag 14 (0xfL): mode == ProblemDetailMode.Detail ? problem.answer == 3 : false
        flag 15 (0x10L): mode == ProblemDetailMode.Detail ? problem.answer == 4 : false
        flag 16 (0x11L): mode == ProblemDetailMode.Detail ? problem.answer == 4 : false
        flag 17 (0x12L): !problem.questions.empty ? problem.questions.get(1) : null
        flag 18 (0x13L): !problem.questions.empty ? problem.questions.get(1) : null
        flag 19 (0x14L): !problem.questions.empty ? problem.questions.get(4) : null
        flag 20 (0x15L): !problem.questions.empty ? problem.questions.get(4) : null
        flag 21 (0x16L): mode == ProblemDetailMode.Detail ? problem.answer == 0 : false
        flag 22 (0x17L): mode == ProblemDetailMode.Detail ? problem.answer == 0 : false
    flag mapping end*/
    //end
}