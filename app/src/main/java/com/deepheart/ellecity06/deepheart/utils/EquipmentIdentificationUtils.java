package com.deepheart.ellecity06.deepheart.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @author ellecity06
 * @time 2018/5/9 10:00
 * @des TODO
 */

public class EquipmentIdentificationUtils {
    /**
     * 唯一的设备ID：
     * GSM手机的 IMEI、CDMA手机的 MEID和设备序列号
     */
    public static String getEquipmentID(Context context) {
        String equipmentID = "equipmentID is null";
        // GSM手机的 IMEI 和 CDMA手机的 MEID.
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        // 设备序列号
        String SerialNumber = android.os.Build.SERIAL;

        if(SerialNumber != null) {
            return SerialNumber;
        } else if(deviceId != null) {
            return deviceId;
        } else {
            return equipmentID;
        }
    }
}
