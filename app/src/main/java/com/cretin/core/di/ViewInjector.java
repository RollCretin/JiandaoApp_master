package com.cretin.core.di;


import com.cretin.ui.activity.MainActivity;
import com.cretin.ui.fragment.InfoFragment;
import com.cretin.ui.fragment.home.CommonHomeFragment;
import com.cretin.ui.fragment.home.HomeFragment;
import com.cretin.ui.fragment.user.CurrentFragment;
import com.cretin.ui.fragment.user.LoginFragment;
import com.cretin.ui.fragment.user.RegisterFragment;

/**
 * Created by grubber on 2017/1/6.
 */
public interface ViewInjector {
    void inject(InfoFragment infoFragment);

    void inject(CurrentFragment currentFragment);

    void inject(LoginFragment loginFragment);

    void inject(RegisterFragment registerFragment);

    void inject(MainActivity mainActivity);

    void inject(HomeFragment homeFragment);

    void inject(CommonHomeFragment commonHomeFragment);
}
