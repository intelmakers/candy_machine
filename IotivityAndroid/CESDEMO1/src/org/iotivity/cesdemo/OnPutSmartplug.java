package org.iotivity.cesdemo;

import android.util.Log;

import org.iotivity.base.AbstractPutCallback;
import org.iotivity.base.OCHeaderOption;
import org.iotivity.base.OCRepresentation;
import org.iotivity.base.OCStackResult;

public class OnPutSmartplug extends AbstractPutCallback{
    final private static String TAG = "OnPut";

    public void Callback(OCHeaderOption[] options, OCRepresentation rep, int eCode) {
        if(eCode == OCStackResult.OC_STACK_OK || eCode == OCStackResult.OC_STACK_RESOURCE_CREATED) {
                Log.i(TAG, "PUT request was successful");

                LightThemeActivity.mtksmartplug.m_state = rep.getValueString("state");
                Log.i(TAG, "state : " + LightThemeActivity.mtksmartplug.m_state);

                /*
                Log.e(TAG, "updating display from thread");
                LightThemeActivity.mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LightThemeActivity.updateSmartplugStatus();
                    }
                });
                */
        }
        else {
            Log.e(TAG, "onPut Response error : " + eCode);
        }

    }
}
