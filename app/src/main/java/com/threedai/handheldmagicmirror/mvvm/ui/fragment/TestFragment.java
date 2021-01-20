package com.threedai.handheldmagicmirror.mvvm.ui.fragment;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import com.threedai.handheldmagicmirror.R;
import com.threedai.handheldmagicmirror.base.BaseFragment;
import com.threedai.handheldmagicmirror.databinding.FragmentTestBinding;
import com.threedai.handheldmagicmirror.mvvm.viewmodel.TestViewModel;

public class TestFragment extends BaseFragment<TestViewModel, FragmentTestBinding>  {

    @Override
    protected TestViewModel createViewModel() {
        return  new ViewModelProvider(requireActivity()).get(TestViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initViewObservable() {

    }


}