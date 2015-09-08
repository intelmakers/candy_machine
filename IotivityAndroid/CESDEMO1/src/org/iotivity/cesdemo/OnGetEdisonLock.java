package org.iotivity.cesdemo;

import android.util.Log;

import org.iotivity.base.AbstractGetCallback;
import org.iotivity.base.OCHeaderOption;
import org.iotivity.base.OCRepresentation;
import org.iotivity.base.OCStackResult;

public class OnGetEdisonLock extends AbstractGetCallback {
    final private static String TAG = "OnGet";
    public void Callback(OCHeaderOption[] options, OCRepresentation rep, int eCode) {
    	Log.i(TAG, "power -----"+"---"+rep.getUri());
        if(eCode == OCStackResult.OC_STACK_OK) {
            Log.i(TAG, "GET request was successful");
            Log.i(TAG, "Resource URI : " + rep.getUri());

            if(rep.getUri().equals("/frontdoor/lock")) {
            	Log.i(TAG, "power ----->" +rep.getValueString("state"));
                    LightThemeActivity.mEdisonLock.m_state = rep.getValueString("state");
                    Log.i(TAG, "power : " + LightThemeActivity.mEdisonLock.m_state);
            }
            

            Log.e(TAG, "updating display from thread");
            LightThemeActivity.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LightThemeActivity.updateEdisonLockDisplay();
                }
            });
        }
        else {
           Log.e(TAG, "onGet Response error : " + eCode);
        }
    }
}
