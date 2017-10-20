package com.cretin.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * Created by Cretin on 2016/6/7.
 */
public class CommonUtil {
    private static TextView tv;

//    public static String getIMIE(Context context) {
//        try {
//            android.telephony.TelephonyManager tm = ( android.telephony.TelephonyManager ) context
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            String device_id = null;
//            if ( !lacksPermission(Manifest.permission.READ_PHONE_STATE, context) ) {
//                device_id = tm.getDeviceId();
//            }
//            android.net.wifi.WifiManager wifi = ( android.net.wifi.WifiManager ) context
//                    .getSystemService(Context.WIFI_SERVICE);
//            String mac = wifi.getConnectionInfo().getMacAddress();
//            if ( TextUtils.isEmpty(device_id) ) {
//                device_id = mac;
//            }
//            if ( TextUtils.isEmpty(device_id) ) {
//                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
//                        android.provider.Settings.Secure.ANDROID_ID);
//            }
//            return device_id;
//        } catch ( Exception e ) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    // 判断是否缺少权限
    public static boolean lacksPermission(String permission, Context context) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_DENIED;
    }


    /**
     * 获取屏幕的缩放级别
     * @param context
     * @return
     */
    public static float getScreenScale(Context context) {
        if ( tv == null ) {
            tv = new TextView(context);
            tv.setTextSize(1);
        }
        return tv.getTextSize();
    }

}
