package org.iotivity.cesdemo;

import org.iotivity.base.AbstractFindCallback;
import org.iotivity.base.OCResource;

import android.util.Log;

public class FoundResource extends AbstractFindCallback {
     final private static String TAG = "FoundResource";


    public void Callback(OCResource resource) {

    	/*
        if(LightThemeActivity.smartplugResource != null) {
            Log.e(TAG, "Found another resource, ignoring");
        }
        */

        String resourceURI;
        String hostAddress;

        if(resource != null) {
            Log.i(TAG, "DISCOVERED Resource");

            resourceURI = resource.uri();
            Log.i(TAG, "URI of the resource: " + resourceURI);

            hostAddress = resource.host();
            Log.i(TAG, "Host address of the resource: " + hostAddress);

            if(resourceURI.equals("/a/smartplug")) {
                LightThemeActivity.mtkSmartplugResource = resource;
                OnGetSmartplug onGet = new OnGetSmartplug();
                resource.get(onGet);
            }
            else if(resourceURI.equals("/a/dimmer/0")) {
                LightThemeActivity.mtkDimmer1Resource = resource;
                OnGetDimmer onGet = new OnGetDimmer();
                resource.get(onGet);
            }
            else if(resourceURI.equals("/a/dimmer/1")) {
                LightThemeActivity.mtkDimmer2Resource = resource;
                OnGetDimmer onGet = new OnGetDimmer();
                resource.get(onGet);
            }
            else if(resourceURI.equals("/a/led")) {
            	 Log.i(TAG, "### Led ");
	                LightThemeActivity.mEdisonLightResource = resource;
	                OnGetEdisonLight onGet = new OnGetEdisonLight();
	                LightThemeActivity.mEdisonLightResource.get(onGet);
            }
            else if(resourceURI.equals("/frontdoor/lock")) {
            		 Log.i(TAG, "### Lock ");
	                LightThemeActivity.mEdisonLockResource = resource;
	                OnGetEdisonLock onGet = new OnGetEdisonLock();
	                LightThemeActivity.mEdisonLockResource.get(onGet);
            }
            else if(resourceURI.equals("/a/radio_thermostat")) {
                LightThemeActivity.mtkThermoResource = resource;
                OnGetThermo onGet = new OnGetThermo();
                resource.get(onGet);
            }
        }
        else {
            Log.e(TAG, "Resource is invalid");
        }
    }
}
