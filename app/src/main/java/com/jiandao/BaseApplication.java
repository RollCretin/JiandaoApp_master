package com.jiandao;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.jiandao.core.di.ApiModule;
import com.jiandao.core.di.ApplicationComponent;
import com.jiandao.core.di.DaggerApplicationComponent;
import com.jiandao.core.di.DataModule;
import com.jiandao.core.di.HasComponent;
import com.jiandao.core.di.NetworkModule;
import com.jiandao.core.di.UtilsModule;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import timber.log.Timber;

/**
 * Created by grubber on 2017/1/6.
 */
public class BaseApplication extends Application implements HasComponent<ApplicationComponent> {
    private ApplicationComponent _component;
    private static BaseApplication application;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        handler = new Handler();

        _buildObjectGraph();
        _setupAnalytics();

        //初始化Hawk
        initHawk();
    }

    //手动配置Hawk
    private void initHawk() {
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();
    }

    public static BaseApplication getApp() {
        return application;
    }

    private void _setupAnalytics() {
        if ( BuildConfig.DEBUG ) {
            Timber.plant(new Timber.DebugTree());
            AndroidDevMetrics.initWith(this);
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }
    }

    private void _buildObjectGraph() {
        _component = DaggerApplicationComponent.builder()
                .androidModule(new com.github.grubber.tiangou.core.di.AndroidModule(this))
                .dataModule(new DataModule())
                .networkModule(new NetworkModule())
                .apiModule(new ApiModule())
                .utilsModule(new UtilsModule())
                .build();
    }

    @Override
    public ApplicationComponent getComponent() {
        return _component;
    }

    public static BaseApplication from(Context context) {
        return ( BaseApplication ) context.getApplicationContext();
    }

    public static Handler getHandler() {
        return handler;
    }
}
