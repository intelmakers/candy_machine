package org.iotivity.cesdemo;

import android.util.Log;

import org.iotivity.base.AbstractGetCallback;
import org.iotivity.base.OCHeaderOption;
import org.iotivity.base.OCRepresentation;
import org.iotivity.base.OCStackResult;

public class OnGetDimmer extends AbstractGetCallback {
    final private static String TAG = "OnGet";
    public void Callback(OCHeaderOption[] options, OCRepresentation rep, int eCode) {
        if(eCode == OCStackResult.OC_STACK_OK) {
            Log.i(TAG, "GET request was successful");
            Log.i(TAG, "Resource URI : " + rep.getUri());

            //FIXME : 
            if(rep.getUri().equals("/a/dimmer/0")) {
                    LightThemeActivity.mtkdimmer1.m_power = rep.getValueInt("power");
                    Log.i(TAG, "power : " + LightThemeActivity.mtkdimmer1.m_power);
            }
            else {
                    LightThemeActivity.mtkdimmer2.m_power = rep.getValueInt("power");
                    Log.i(TAG, "power : " + LightThemeActivity.mtkdimmer2.m_power);
            }

            Log.e(TAG, "updating display from thread");
            LightThemeActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LightThemeActivity.updateDimmerDisplay();
                }
            });
        }
        else {
           Log.e(TAG, "onGet Response error : " + eCode);
        }
    }
}
