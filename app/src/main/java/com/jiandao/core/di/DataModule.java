package com.jiandao.core.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiandao.core.EventBus;
import com.jiandao.core.di.qualifier.ApplicationScope;
import com.jiandao.core.di.qualifier.ForApplication;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grubber on 2017/1/6.
 */
@Module
public class DataModule {
    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreferences(@ForApplication Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    @Provides
    @ApplicationScope
    Bus provideEventBus() {
        return EventBus.getInstance();
    }
}
