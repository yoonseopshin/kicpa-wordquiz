package com.cpa.cpa_word_problem.databinding;
import com.cpa.cpa_word_problem.R;
import com.cpa.cpa_word_problem.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ListItemProblemBindingImpl extends ListItemProblemBinding  {

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

    public ListItemProblemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private ListItemProblemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.chip.Chip) bindings[2]
            , (com.google.android.material.chip.Chip) bindings[3]
            , (com.google.android.material.chip.Chip) bindings[1]
            , (android.widget.TextView) bindings[4]
            );
        this.chElapsedTime.setTag(null);
        this.chType.setTag(null);
        this.chYearPid.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvDescription.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x20L;
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
        else if (BR.onLongClickListener == variableId) {
            setOnLongClickListener((android.view.View.OnLongClickListener) variable);
        }
        else if (BR.elapsedTime == variableId) {
            setElapsedTime((int) variable);
        }
        else if (BR.onClickListener == variableId) {
            setOnClickListener((android.view.View.OnClickListener) variable);
        }
        else if (BR.userSelectedIndex == variableId) {
            setUserSelectedIndex((int) variable);
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
    public void setOnLongClickListener(@Nullable android.view.View.OnLongClickListener OnLongClickListener) {
        this.mOnLongClickListener = OnLongClickListener;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.onLongClickListener);
        super.requestRebind();
    }
    public void setElapsedTime(int ElapsedTime) {
        this.mElapsedTime = ElapsedTime;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.elapsedTime);
        super.requestRebind();
    }
    public void setOnClickListener(@Nullable android.view.View.OnClickListener OnClickListener) {
        this.mOnClickListener = OnClickListener;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.onClickListener);
        super.requestRebind();
    }
    public void setUserSelectedIndex(int UserSelectedIndex) {
        this.mUserSelectedIndex = UserSelectedIndex;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.userSelectedIndex);
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
        int problemPid = 0;
        com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem problem = mProblem;
        boolean elapsedTimeInt0 = false;
        boolean problemAnswerUserSelectedIndex = false;
        int userSelectedIndexInt0ProblemAnswerUserSelectedIndexMboundView0AndroidColorColorOnCorrectBgMboundView0AndroidColorColorOnIncorrectBgMboundView0AndroidColorThemeColor = 0;
        android.view.View.OnLongClickListener onLongClickListener = mOnLongClickListener;
        com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType problemType = null;
        java.lang.String timeFormatterINSTANCEFormatKoreanElapsedTime = null;
        int elapsedTime = mElapsedTime;
        android.view.View.OnClickListener onClickListener = mOnClickListener;
        int userSelectedIndex = mUserSelectedIndex;
        java.lang.String problemDescription = null;
        boolean userSelectedIndexInt0 = false;
        int problemAnswer = 0;
        int elapsedTimeInt0ViewVISIBLEViewGONE = 0;
        int problemYear = 0;
        int problemAnswerUserSelectedIndexMboundView0AndroidColorColorOnCorrectBgMboundView0AndroidColorColorOnIncorrectBg = 0;

        if ((dirtyFlags & 0x21L) != 0) {



                if (problem != null) {
                    // read problem.pid
                    problemPid = problem.getPid();
                    // read problem.type
                    problemType = problem.getType();
                    // read problem.description
                    problemDescription = problem.getDescription();
                    // read problem.year
                    problemYear = problem.getYear();
                }
        }
        if ((dirtyFlags & 0x22L) != 0) {
        }
        if ((dirtyFlags & 0x24L) != 0) {



                // read elapsedTime > 0
                elapsedTimeInt0 = (elapsedTime) > (0);
                // read TimeFormatter.INSTANCE.formatKorean(elapsedTime)
                timeFormatterINSTANCEFormatKoreanElapsedTime = com.cpa.cpa_word_problem.utils.TimeFormatter.INSTANCE.formatKorean(elapsedTime);
            if((dirtyFlags & 0x24L) != 0) {
                if(elapsedTimeInt0) {
                        dirtyFlags |= 0x200L;
                }
                else {
                        dirtyFlags |= 0x100L;
                }
            }


                // read elapsedTime > 0 ? View.VISIBLE : View.GONE
                elapsedTimeInt0ViewVISIBLEViewGONE = ((elapsedTimeInt0) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
        }
        if ((dirtyFlags & 0x28L) != 0) {
        }
        if ((dirtyFlags & 0x31L) != 0) {



                // read userSelectedIndex >= 0
                userSelectedIndexInt0 = (userSelectedIndex) >= (0);
            if((dirtyFlags & 0x31L) != 0) {
                if(userSelectedIndexInt0) {
                        dirtyFlags |= 0x80L;
                }
                else {
                        dirtyFlags |= 0x40L;
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x80L) != 0) {



                if (problem != null) {
                    // read problem.answer
                    problemAnswer = problem.getAnswer();
                }


                // read problem.answer == userSelectedIndex
                problemAnswerUserSelectedIndex = (problemAnswer) == (userSelectedIndex);
            if((dirtyFlags & 0x80L) != 0) {
                if(problemAnswerUserSelectedIndex) {
                        dirtyFlags |= 0x800L;
                }
                else {
                        dirtyFlags |= 0x400L;
                }
            }


                // read problem.answer == userSelectedIndex ? @android:color/color_on_correct_bg : @android:color/color_on_incorrect_bg
                problemAnswerUserSelectedIndexMboundView0AndroidColorColorOnCorrectBgMboundView0AndroidColorColorOnIncorrectBg = ((problemAnswerUserSelectedIndex) ? (getColorFromResource(mboundView0, R.color.color_on_correct_bg)) : (getColorFromResource(mboundView0, R.color.color_on_incorrect_bg)));
        }

        if ((dirtyFlags & 0x31L) != 0) {

                // read userSelectedIndex >= 0 ? problem.answer == userSelectedIndex ? @android:color/color_on_correct_bg : @android:color/color_on_incorrect_bg : @android:color/theme_color
                userSelectedIndexInt0ProblemAnswerUserSelectedIndexMboundView0AndroidColorColorOnCorrectBgMboundView0AndroidColorColorOnIncorrectBgMboundView0AndroidColorThemeColor = ((userSelectedIndexInt0) ? (problemAnswerUserSelectedIndexMboundView0AndroidColorColorOnCorrectBgMboundView0AndroidColorColorOnIncorrectBg) : (getColorFromResource(mboundView0, R.color.theme_color)));
        }
        // batch finished
        if ((dirtyFlags & 0x24L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.chElapsedTime, timeFormatterINSTANCEFormatKoreanElapsedTime);
            this.chElapsedTime.setVisibility(elapsedTimeInt0ViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x21L) != 0) {
            // api target 1

            com.cpa.cpa_word_problem.feature.quiz.presentation.util.AppBindingAdapterKt.bindByType(this.chType, problemType);
            com.cpa.cpa_word_problem.feature.quiz.presentation.util.AppBindingAdapterKt.bindYearAndPid(this.chYearPid, problemYear, problemPid);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvDescription, problemDescription);
        }
        if ((dirtyFlags & 0x31L) != 0) {
            // api target 1

            androidx.databinding.adapters.ViewBindingAdapter.setBackground(this.mboundView0, androidx.databinding.adapters.Converters.convertColorToDrawable(userSelectedIndexInt0ProblemAnswerUserSelectedIndexMboundView0AndroidColorColorOnCorrectBgMboundView0AndroidColorColorOnIncorrectBgMboundView0AndroidColorThemeColor));
        }
        if ((dirtyFlags & 0x28L) != 0) {
            // api target 1

            this.mboundView0.setOnClickListener(onClickListener);
        }
        if ((dirtyFlags & 0x22L) != 0) {
            // api target 1

            this.mboundView0.setOnLongClickListener(onLongClickListener);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): problem
        flag 1 (0x2L): onLongClickListener
        flag 2 (0x3L): elapsedTime
        flag 3 (0x4L): onClickListener
        flag 4 (0x5L): userSelectedIndex
        flag 5 (0x6L): null
        flag 6 (0x7L): userSelectedIndex >= 0 ? problem.answer == userSelectedIndex ? @android:color/color_on_correct_bg : @android:color/color_on_incorrect_bg : @android:color/theme_color
        flag 7 (0x8L): userSelectedIndex >= 0 ? problem.answer == userSelectedIndex ? @android:color/color_on_correct_bg : @android:color/color_on_incorrect_bg : @android:color/theme_color
        flag 8 (0x9L): elapsedTime > 0 ? View.VISIBLE : View.GONE
        flag 9 (0xaL): elapsedTime > 0 ? View.VISIBLE : View.GONE
        flag 10 (0xbL): problem.answer == userSelectedIndex ? @android:color/color_on_correct_bg : @android:color/color_on_incorrect_bg
        flag 11 (0xcL): problem.answer == userSelectedIndex ? @android:color/color_on_correct_bg : @android:color/color_on_incorrect_bg
    flag mapping end*/
    //end
}