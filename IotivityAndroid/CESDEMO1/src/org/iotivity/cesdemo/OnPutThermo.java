package org.iotivity.cesdemo;

import android.util.Log;

import org.iotivity.base.AbstractPutCallback;
import org.iotivity.base.OCHeaderOption;
import org.iotivity.base.OCRepresentation;
import org.iotivity.base.OCStackResult;

public class OnPutThermo extends AbstractPutCallback {
    final private static String TAG = "OnPut";

    public void Callback(OCHeaderOption[] options, OCRepresentation rep, int eCode) {
        if(eCode == OCStackResult.OC_STACK_OK || eCode == OCStackResult.OC_STACK_RESOURCE_CREATED) {
                Log.i(TAG, "PUT request was successful");

                LightThemeActivity.mtkthermo.m_temp = rep.getValueString("temp");
                Log.i(TAG, "temp  : " + LightThemeActivity.mtkthermo.m_temp);
                LightThemeActivity.mtkthermo.m_tmode = rep.getValueInt("tmode");
                Log.i(TAG, "tmode : " + LightThemeActivity.mtkthermo.m_tmode);
        }
        else {
            Log.e(TAG, "onPut Response error : " + eCode);
        }

    }
}
