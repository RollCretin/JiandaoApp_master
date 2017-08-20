package com.jiandao.core.di;

import android.content.Context;

import com.jiandao.core.di.qualifier.ApplicationScope;
import com.jiandao.core.di.qualifier.ForApplication;
import com.jiandao.util.ToastHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grubber on 2017/1/6.
 */
@Module
public class UtilsModule {
    @Provides
    @ApplicationScope
    ToastHelper provideToastHelper(@ForApplication Context context) {
        return new ToastHelper(context);
    }
}
