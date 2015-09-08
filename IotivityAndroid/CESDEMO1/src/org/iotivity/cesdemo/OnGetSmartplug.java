package org.iotivity.cesdemo;

import android.util.Log;

import org.iotivity.base.AbstractGetCallback;
import org.iotivity.base.OCHeaderOption;
import org.iotivity.base.OCRepresentation;
import org.iotivity.base.OCStackResult;

public class OnGetSmartplug extends AbstractGetCallback {
    final private static String TAG = "OnGet";
    public void Callback(OCHeaderOption[] options, OCRepresentation rep, int eCode) {
        if(eCode == OCStackResult.OC_STACK_OK) {
            Log.i(TAG, "GET request was successful");
            Log.i(TAG, "Resource URI : " + rep.getUri());

//            LightThemeActivity.myplug.m_state = rep.getValueBool("state");
            LightThemeActivity.mtksmartplug.m_state = rep.getValueString("state");
            Log.i(TAG, "state : " + LightThemeActivity.mtksmartplug.m_state);

            Log.e(TAG, "updating display from thread");
            LightThemeActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LightThemeActivity.updateSmartplugStatus();
                }
            });
        }
        else {
           Log.e(TAG, "onGet Response error : " + eCode);
        }
    }
}
