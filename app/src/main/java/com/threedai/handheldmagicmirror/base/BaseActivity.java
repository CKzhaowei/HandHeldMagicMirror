package com.threedai.handheldmagicmirror.base;

import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.kongzue.dialog.v3.CustomDialog;
import org.simple.eventbus.EventBus;


/**
 * Activity的公共基类
 */
public abstract class BaseActivity<VM extends BaseViewModel,VDB extends ViewDataBinding> extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();
    protected VM mViewModel;
    protected VDB mViewDataBinding;
    private CustomDialog dialog;

    /**
     * 创建ViewModel,该ViewModel是绑定在view的生命周期上
     */
    protected abstract VM createViewModel();

    /**
     * 获取资源layout id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 是否使用EventBus
     * @return
     */
    protected abstract boolean useEventBus();

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);

    /**
     * 初始化用于ViewModel层转到View层的事件
     */
    protected abstract void initViewObservable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(useEventBus()){
            EventBus.getDefault().register(this);
        }
        //全屏设置
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).
                hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR).keyboardEnable(true).init();
        //设置viewDataBinding
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.setLifecycleOwner(this);
        //创建viewModel
        mViewModel = createViewModel();
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack();
        //初始化数据
        initData(savedInstanceState);
        //页面事件监听的方法
        initViewObservable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(useEventBus()){
            EventBus.getDefault().unregister(this);
        }
    }

    //注册ViewModel与View的契约UI回调事件
    protected void registerUIChangeLiveDataCallBack(){
        //加载对话框显示
        mViewModel.getUIChangeLiveData().getShowDialog().observe(this, v -> showDialog());
        //加载对话框消失
        mViewModel.getUIChangeLiveData().getHideDialog().observe(this, v -> hideDialog());
        //显示toast
        mViewModel.getUIChangeLiveData().getShowMsg().observe(this, ToastUtils::showShort);
    }

    /**
     * 显示进度
     */
    protected void showDialog() {
        if(dialog != null) {
            dialog.show();
        }
    }

    /**
     * 隐藏进度条
     */
    protected void hideDialog(){
        if(dialog != null) {
            dialog.doDismiss();
        }
    }


    /**
     * 适配屏幕
     * @return
     */
    @Override
    public Resources getResources() {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 375);
    }

}
