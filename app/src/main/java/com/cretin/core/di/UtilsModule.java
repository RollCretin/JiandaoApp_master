package com.cretin.core.di;

import android.content.Context;

import com.cretin.core.di.qualifier.ApplicationScope;
import com.cretin.core.di.qualifier.ForApplication;
import com.cretin.util.ToastHelper;

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
