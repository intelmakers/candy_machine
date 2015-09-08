package org.iotivity.cesdemo;

import android.util.Log;

import org.iotivity.base.AbstractPutCallback;
import org.iotivity.base.OCHeaderOption;
import org.iotivity.base.OCRepresentation;
import org.iotivity.base.OCStackResult;

public class OnPutEdisonLight extends AbstractPutCallback {
    final private static String TAG = "OnPut";

    public void Callback(OCHeaderOption[] options, OCRepresentation rep, int eCode) {
        if(eCode == OCStackResult.OC_STACK_OK || eCode == OCStackResult.OC_STACK_RESOURCE_CREATED) {
                Log.i(TAG, "PUT request was successful");

            if(rep.getUri().equals("/a/led")) {
                LightThemeActivity.mEdisonLight.m_state = rep.getValueString("state");
                Log.i(TAG, "power : " + LightThemeActivity.mEdisonLight.m_state);
            }
        }
        else {
            Log.e(TAG, "onPut Response error : " + eCode);
        }

    }
}
