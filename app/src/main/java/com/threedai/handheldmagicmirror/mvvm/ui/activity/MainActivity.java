package com.threedai.handheldmagicmirror.mvvm.ui.activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;
import com.threedai.handheldmagicmirror.R;
import com.threedai.handheldmagicmirror.base.BaseActivity;
import com.threedai.handheldmagicmirror.databinding.ActivityMainBinding;
import com.threedai.handheldmagicmirror.mvvm.ui.fragment.TestFragment;
import com.threedai.handheldmagicmirror.mvvm.viewmodel.TestViewModel;

public class MainActivity extends BaseActivity<TestViewModel, ActivityMainBinding>{

    @Override
    protected TestViewModel createViewModel() {
        return new ViewModelProvider(this).get(TestViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mViewDataBinding.tvRquest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getNewsList(1);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                // 加载当前显示的Fragment
                transaction.replace(R.id.big_fragment, new TestFragment());
                transaction.commit(); // 提交创建Fragment请求
            }
        });
    }

    @Override
    protected void initViewObservable() {

    }

}
