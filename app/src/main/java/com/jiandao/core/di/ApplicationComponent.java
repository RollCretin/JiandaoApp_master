package com.jiandao.core.di;


import com.github.grubber.tiangou.core.di.AndroidModule;
import com.jiandao.core.di.qualifier.ApplicationScope;

import dagger.Component;

/**
 * Created by grubber on 2017/1/6.
 */
@ApplicationScope
@Component(modules = {AndroidModule.class, DataModule.class, NetworkModule.class, ApiModule.class,
        UtilsModule.class})
public interface ApplicationComponent extends ViewInjector {
}
