package com.cpa.cpa_word_problem.databinding;
import com.cpa.cpa_word_problem.R;
import com.cpa.cpa_word_problem.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutBottomSheetSearchBindingImpl extends LayoutBottomSheetSearchBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etSearchandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.userInputText.getValue()
            //         is viewModel.userInputText.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etSearch);
            // localize variables for thread safety
            // viewModel.userInputText != null
            boolean viewModelUserInputTextJavaLangObjectNull = false;
            // viewModel.userInputText.getValue()
            java.lang.String viewModelUserInputTextGetValue = null;
            // viewModel.userInputText
            kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> viewModelUserInputText = null;
            // viewModel
            com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note.NoteViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelUserInputText = viewModel.getUserInputText();

                viewModelUserInputTextJavaLangObjectNull = (viewModelUserInputText) != (null);
                if (viewModelUserInputTextJavaLangObjectNull) {




                    viewModelUserInputText.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public LayoutBottomSheetSearchBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds));
    }
    private LayoutBottomSheetSearchBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.EditText) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            );
        this.etSearch.setTag(null);
        this.layout.setTag(null);
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
            setViewModel((com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note.NoteViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note.NoteViewModel ViewModel) {
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
                return onChangeViewModelUserInputText((kotlinx.coroutines.flow.MutableStateFlow<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelUserInputText(kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> ViewModelUserInputText, int fieldId) {
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
        java.lang.String viewModelUserInputTextGetValue = null;
        kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> viewModelUserInputText = null;
        com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note.NoteViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x7L) != 0) {



                if (viewModel != null) {
                    // read viewModel.userInputText
                    viewModelUserInputText = viewModel.getUserInputText();
                }
                androidx.databinding.ViewDataBindingKtx.updateStateFlowRegistration(this, 0, viewModelUserInputText);


                if (viewModelUserInputText != null) {
                    // read viewModel.userInputText.getValue()
                    viewModelUserInputTextGetValue = viewModelUserInputText.getValue();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etSearch, viewModelUserInputTextGetValue);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etSearch, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etSearchandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.userInputText
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}