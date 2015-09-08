package org.iotivity.cesdemo;

import android.util.Log;

import org.iotivity.base.AbstractGetCallback;
import org.iotivity.base.OCHeaderOption;
import org.iotivity.base.OCRepresentation;
import org.iotivity.base.OCStackResult;

/**
 * Created by vchen on 11/12/14.
 */
public class OnGetThermo extends AbstractGetCallback {
    final private static String TAG = "OnGet";
    public void Callback(OCHeaderOption[] options, OCRepresentation rep, int eCode) {
        if(eCode == OCStackResult.OC_STACK_OK) {
            Log.i(TAG, "GET request was successful");
            Log.i(TAG, "Resource URI : " + rep.getUri());

//            LightThemeActivity.mtkthermo.m_temp = rep.getValueInt("temp");
            LightThemeActivity.mtkthermo.m_current_temp = rep.getValueString("temp");
            Log.i(TAG, "temp  : " + LightThemeActivity.mtkthermo.m_current_temp);
            LightThemeActivity.mtkthermo.m_tmode = rep.getValueInt("tmode");
            Log.i(TAG, "tmode : " + LightThemeActivity.mtkthermo.m_tmode);

            Log.e(TAG, "updating display from thread");
            LightThemeActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LightThemeActivity.updateThermoStatus();
                }
            });
        }
        else {
           Log.e(TAG, "onGet Response error : " + eCode);
        }
    }
}
