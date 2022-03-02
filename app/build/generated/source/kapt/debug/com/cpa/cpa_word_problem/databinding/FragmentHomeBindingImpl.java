package com.cpa.cpa_word_problem.databinding;
import com.cpa.cpa_word_problem.R;
import com.cpa.cpa_word_problem.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentHomeBindingImpl extends FragmentHomeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lay_accounting, 3);
        sViewsWithIds.put(R.id.lay_business, 4);
        sViewsWithIds.put(R.id.bs_quiz, 5);
        sViewsWithIds.put(R.id.fab_close_bs_quiz, 6);
    }
    // views
    @NonNull
    private final androidx.coordinatorlayout.widget.CoordinatorLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView2;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentHomeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private FragmentHomeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (bindings[5] != null) ? com.cpa.cpa_word_problem.databinding.LayoutBottomSheetQuizSettingsBinding.bind((android.view.View) bindings[5]) : null
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[6]
            , (bindings[3] != null) ? com.cpa.cpa_word_problem.databinding.LayoutHomeSubjectCardBinding.bind((android.view.View) bindings[3]) : null
            , (bindings[4] != null) ? com.cpa.cpa_word_problem.databinding.LayoutHomeSubjectCardBinding.bind((android.view.View) bindings[4]) : null
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            );
        this.mboundView0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (android.widget.LinearLayout) bindings[2];
        this.mboundView2.setTag(null);
        this.toolbar.setTag(null);
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
        if (BR.viewModel == variableId) {
            setViewModel((com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home.HomeViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home.HomeViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelNextExamDate((kotlinx.coroutines.flow.MutableStateFlow<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelNextExamDate(kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> ViewModelNextExamDate, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> viewModelNextExamDate = null;
        com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home.HomeViewModel viewModel = mViewModel;
        java.lang.String viewModelNextExamDateGetValue = null;

        if ((dirtyFlags & 0x7L) != 0) {



                if (viewModel != null) {
                    // read viewModel.nextExamDate
                    viewModelNextExamDate = viewModel.getNextExamDate();
                }
                androidx.databinding.ViewDataBindingKtx.updateStateFlowRegistration(this, 0, viewModelNextExamDate);


                if (viewModelNextExamDate != null) {
                    // read viewModel.nextExamDate.getValue()
                    viewModelNextExamDateGetValue = viewModelNextExamDate.getValue();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            com.cpa.cpa_word_problem.feature.quiz.presentation.util.AppBindingAdapterKt.bindDDay(this.toolbar, viewModelNextExamDateGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.nextExamDate
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}