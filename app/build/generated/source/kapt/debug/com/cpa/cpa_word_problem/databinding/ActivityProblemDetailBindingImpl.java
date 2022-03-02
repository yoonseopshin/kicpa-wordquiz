package com.cpa.cpa_word_problem.databinding;
import com.cpa.cpa_word_problem.R;
import com.cpa.cpa_word_problem.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityProblemDetailBindingImpl extends ActivityProblemDetailBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(6);
        sIncludes.setIncludes(1, 
            new String[] {"layout_problem_detail"},
            new int[] {5},
            new int[] {com.cpa.cpa_word_problem.R.layout.layout_problem_detail});
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityProblemDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private ActivityProblemDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 8
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[4]
            , (com.cpa.cpa_word_problem.databinding.LayoutProblemDetailBinding) bindings[5]
            , (android.widget.ScrollView) bindings[0]
            , (androidx.appcompat.widget.Toolbar) bindings[2]
            , (android.widget.TextView) bindings[3]
            );
        this.fabNext.setTag(null);
        setContainedBinding(this.layProblemDetail);
        this.mboundView1 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.scrollView.setTag(null);
        this.toolbar.setTag(null);
        this.tvElapsedTime.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x200L;
        }
        layProblemDetail.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (layProblemDetail.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x100L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        layProblemDetail.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelTimeMillis((kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long>) object, fieldId);
            case 1 :
                return onChangeViewModelMode((kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode>) object, fieldId);
            case 2 :
                return onChangeViewModelTotalProblemNumbers((kotlinx.coroutines.flow.StateFlow<java.lang.Integer>) object, fieldId);
            case 3 :
                return onChangeViewModelUseTimer((kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean>) object, fieldId);
            case 4 :
                return onChangeLayProblemDetail((com.cpa.cpa_word_problem.databinding.LayoutProblemDetailBinding) object, fieldId);
            case 5 :
                return onChangeViewModelProblem((kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>) object, fieldId);
            case 6 :
                return onChangeViewModelLastLapTime((kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long>) object, fieldId);
            case 7 :
                return onChangeViewModelSolvedProblemNumbers((kotlinx.coroutines.flow.StateFlow<java.lang.Integer>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelTimeMillis(kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> ViewModelTimeMillis, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelMode(kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode> ViewModelMode, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelTotalProblemNumbers(kotlinx.coroutines.flow.StateFlow<java.lang.Integer> ViewModelTotalProblemNumbers, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelUseTimer(kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> ViewModelUseTimer, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLayProblemDetail(com.cpa.cpa_word_problem.databinding.LayoutProblemDetailBinding LayProblemDetail, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelProblem(kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> ViewModelProblem, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelLastLapTime(kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> ViewModelLastLapTime, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelSolvedProblemNumbers(kotlinx.coroutines.flow.StateFlow<java.lang.Integer> ViewModelSolvedProblemNumbers, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
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
        kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> viewModelTimeMillis = null;
        java.lang.Integer viewModelSolvedProblemNumbersGetValue = null;
        kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode> viewModelMode = null;
        com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode viewModelModeGetValue = null;
        int viewModelUseTimerViewVISIBLEViewGONE = 0;
        java.lang.Boolean viewModelUseTimerGetValue = null;
        kotlinx.coroutines.flow.StateFlow<java.lang.Integer> viewModelTotalProblemNumbers = null;
        kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> viewModelUseTimer = null;
        java.lang.Long viewModelTimeMillisGetValue = null;
        java.lang.Long viewModelLastLapTimeGetValue = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelUseTimerGetValue = false;
        java.lang.Integer viewModelTotalProblemNumbersGetValue = null;
        kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> viewModelProblem = null;
        kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> viewModelLastLapTime = null;
        kotlinx.coroutines.flow.StateFlow<java.lang.Integer> viewModelSolvedProblemNumbers = null;
        com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem viewModelProblemGetValue = null;
        com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x3efL) != 0) {


            if ((dirtyFlags & 0x341L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.timeMillis
                        viewModelTimeMillis = viewModel.getTimeMillis();
                        // read viewModel.lastLapTime
                        viewModelLastLapTime = viewModel.getLastLapTime();
                    }
                    androidx.databinding.ViewDataBindingKtx.updateStateFlowRegistration(this, 0, viewModelTimeMillis);
                    androidx.databinding.ViewDataBindingKtx.updateStateFlowRegistration(this, 6, viewModelLastLapTime);


                    if (viewModelTimeMillis != null) {
                        // read viewModel.timeMillis.getValue()
                        viewModelTimeMillisGetValue = viewModelTimeMillis.getValue();
                    }
                    if (viewModelLastLapTime != null) {
                        // read viewModel.lastLapTime.getValue()
                        viewModelLastLapTimeGetValue = viewModelLastLapTime.getValue();
                    }
            }
            if ((dirtyFlags & 0x302L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.mode
                        viewModelMode = viewModel.getMode();
                    }
                    androidx.databinding.ViewDataBindingKtx.updateStateFlowRegistration(this, 1, viewModelMode);


                    if (viewModelMode != null) {
                        // read viewModel.mode.getValue()
                        viewModelModeGetValue = viewModelMode.getValue();
                    }
            }
            if ((dirtyFlags & 0x384L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.totalProblemNumbers
                        viewModelTotalProblemNumbers = viewModel.getTotalProblemNumbers();
                        // read viewModel.solvedProblemNumbers
                        viewModelSolvedProblemNumbers = viewModel.getSolvedProblemNumbers();
                    }
                    androidx.databinding.ViewDataBindingKtx.updateStateFlowRegistration(this, 2, viewModelTotalProblemNumbers);
                    androidx.databinding.ViewDataBindingKtx.updateStateFlowRegistration(this, 7, viewModelSolvedProblemNumbers);


                    if (viewModelTotalProblemNumbers != null) {
                        // read viewModel.totalProblemNumbers.getValue()
                        viewModelTotalProblemNumbersGetValue = viewModelTotalProblemNumbers.getValue();
                    }
                    if (viewModelSolvedProblemNumbers != null) {
                        // read viewModel.solvedProblemNumbers.getValue()
                        viewModelSolvedProblemNumbersGetValue = viewModelSolvedProblemNumbers.getValue();
                    }
            }
            if ((dirtyFlags & 0x308L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.useTimer
                        viewModelUseTimer = viewModel.getUseTimer();
                    }
                    androidx.databinding.ViewDataBindingKtx.updateStateFlowRegistration(this, 3, viewModelUseTimer);


                    if (viewModelUseTimer != null) {
                        // read viewModel.useTimer.getValue()
                        viewModelUseTimerGetValue = viewModelUseTimer.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.useTimer.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelUseTimerGetValue = androidx.databinding.ViewDataBinding.safeUnbox(viewModelUseTimerGetValue);
                if((dirtyFlags & 0x308L) != 0) {
                    if(androidxDatabindingViewDataBindingSafeUnboxViewModelUseTimerGetValue) {
                            dirtyFlags |= 0x800L;
                    }
                    else {
                            dirtyFlags |= 0x400L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.useTimer.getValue()) ? View.VISIBLE : View.GONE
                    viewModelUseTimerViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelUseTimerGetValue) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x320L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.problem
                        viewModelProblem = viewModel.getProblem();
                    }
                    androidx.databinding.ViewDataBindingKtx.updateStateFlowRegistration(this, 5, viewModelProblem);


                    if (viewModelProblem != null) {
                        // read viewModel.problem.getValue()
                        viewModelProblemGetValue = viewModelProblem.getValue();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x302L) != 0) {
            // api target 1

            com.cpa.cpa_word_problem.feature.quiz.presentation.util.AppBindingAdapterKt.bindProblemDetailMode(this.fabNext, viewModelModeGetValue);
            this.layProblemDetail.setMode(viewModelModeGetValue);
        }
        if ((dirtyFlags & 0x320L) != 0) {
            // api target 1

            this.layProblemDetail.setProblem(viewModelProblemGetValue);
        }
        if ((dirtyFlags & 0x384L) != 0) {
            // api target 1

            com.cpa.cpa_word_problem.feature.quiz.presentation.util.AppBindingAdapterKt.bindProblems(this.toolbar, viewModelSolvedProblemNumbersGetValue, viewModelTotalProblemNumbersGetValue);
        }
        if ((dirtyFlags & 0x308L) != 0) {
            // api target 1

            this.tvElapsedTime.setVisibility(viewModelUseTimerViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x341L) != 0) {
            // api target 1

            com.cpa.cpa_word_problem.feature.quiz.presentation.util.AppBindingAdapterKt.bindTimer(this.tvElapsedTime, viewModelTimeMillisGetValue, viewModelLastLapTimeGetValue);
        }
        executeBindingsOn(layProblemDetail);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.timeMillis
        flag 1 (0x2L): viewModel.mode
        flag 2 (0x3L): viewModel.totalProblemNumbers
        flag 3 (0x4L): viewModel.useTimer
        flag 4 (0x5L): layProblemDetail
        flag 5 (0x6L): viewModel.problem
        flag 6 (0x7L): viewModel.lastLapTime
        flag 7 (0x8L): viewModel.solvedProblemNumbers
        flag 8 (0x9L): viewModel
        flag 9 (0xaL): null
        flag 10 (0xbL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.useTimer.getValue()) ? View.VISIBLE : View.GONE
        flag 11 (0xcL): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.useTimer.getValue()) ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}