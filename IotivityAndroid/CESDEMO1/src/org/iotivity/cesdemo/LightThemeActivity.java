package org.iotivity.cesdemo;

import org.iotivity.base.OCPlatform;
import org.iotivity.base.OCRepresentation;
import org.iotivity.base.OCResource;
import org.iotivity.base.PlatformConfig;
import org.iotivity.cesdemo.numberpicker.NumberPicker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class LightThemeActivity extends Activity 
		implements 	OnClickListener, 
						Switch.OnCheckedChangeListener {
	
    final private static String TAG = "LightThemeActivity";
    
    class MtkSmartplug {
      public String m_state;
    }
    
    class mtkDimmer {
    	public int m_power;
    }
    class EdisonLight {
    	public String m_state;
    }
    
    class EdisonLock {
    	public String m_state;
    }
    
    class mtkThermo {
      public String m_temp;
      public String m_current_temp;
      public int m_tmode;
    }

    final private static int MTK_DIMMER_MIN_VALUE = 0;
    final private static int MTK_DIMMER_MAX_VALUE = 15;
    final private static int MTK_THERMO_MIN_VALUE = 60;
    final private static int MTK_THERMO_MAX_VALUE = 90;

    
    /*
	String[] np_d1 = new String[3];
	String[] np_d2 = new String[3];
	String[] np_t1 = new String[3];
	*/
	String[] np_t2 = new String[16];
	String[] np_h = new String[257];
	
	static Switch switch_smartplug;
	static Switch switch_edisonlight;
	static Switch switch_edisonlock;
	static EdisonLightSwitchChangeClicker mEdisonLightChangeListener;
    static EdisonLockSwitchChangeClicker mEdisonLockChangeListener;
    
	ToggleButton btn_hue;
	Button btn_d1on;
	Button btn_d1off;
	Button btn_d2on;
	Button btn_d2off;
	Button btn_set_therm;
	Button btn_noti;
	Button btn_group;
	
	static NumberPicker np_dim1;
	static NumberPicker np_dim2;
	NumberPicker np_therm1;
	NumberPicker np_therm2;
	NumberPicker np_hue_color;
	
    static MtkSmartplug mtksmartplug;
    static mtkDimmer mtkdimmer1;
    static mtkDimmer mtkdimmer2;
    static mtkThermo mtkthermo;
    static OCResource mtkSmartplugResource;
    static OCResource mtkDimmer1Resource;
    static OCResource mtkDimmer2Resource;
    static OCResource mtkThermoResource;
    static Activity mActivity;
    //////////////////////////
    static EdisonLight mEdisonLight;
    static OCResource mEdisonLightResource;
    static EdisonLock mEdisonLock;
    static OCResource mEdisonLockResource;
    
    static TextView smartplugStatus;
    static TextView dimmerStatus;
    static TextView thermoStatus;
    
    static TextView edisonLightStatus;
    static TextView edisonLockStatus;
    
    static TextView tempVal;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        
        mEdisonLightChangeListener = new EdisonLightSwitchChangeClicker();
       mEdisonLockChangeListener = new EdisonLockSwitchChangeClicker();
        mActivity = this;
        mtksmartplug = new MtkSmartplug();
        mtkdimmer1 = new mtkDimmer();
        mtkdimmer2 = new mtkDimmer();
        mtkthermo = new mtkThermo();
        mEdisonLight = new EdisonLight();
        mEdisonLight.m_state = "false";
        mEdisonLock = new EdisonLock();
        mEdisonLock.m_state = "false";

        PlatformConfig cfg = new PlatformConfig(PlatformConfig.ServiceType.INPROC,
                                        PlatformConfig.ModeType.CLIENT,
                                        "0.0.0.0",
                                        0,
                                        PlatformConfig.QualityOfService.LO_QOS);

        OCPlatform.configure(cfg);
        try {
            FoundResource foundResource = new FoundResource();
            OCPlatform.findResource("", "coap://224.0.1.187/oc/core", foundResource);
        }
        catch (Exception e) {
            Log.e(TAG, "Exception : " + e);
        }

        setContentView(R.layout.activity_light);

        smartplugStatus = (TextView)findViewById(R.id.textView_smartplug);
        smartplugStatus.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
        smartplugStatus.setTextColor(Color.parseColor("#ffffffff")); // WHITE
        
        dimmerStatus = (TextView)findViewById(R.id.textView_dimmer);
        dimmerStatus.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
        dimmerStatus.setTextColor(Color.parseColor("#ffffffff")); // WHITE

        edisonLightStatus = (TextView)findViewById(R.id.edison_light_status);
        edisonLightStatus.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
        edisonLightStatus.setTextColor(Color.parseColor("#ffffffff")); // WHITE
        
        edisonLockStatus = (TextView)findViewById(R.id.edison_lock_status);
        edisonLockStatus.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
        edisonLockStatus.setTextColor(Color.parseColor("#ffffffff")); // WHITE
        
        thermoStatus = (TextView)findViewById(R.id.textView_thermo);
        thermoStatus.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
        thermoStatus.setTextColor(Color.parseColor("#ffffffff")); // WHITE
        
        switch_smartplug = (Switch)findViewById(R.id.tswitch_smartplug);
        switch_smartplug.setTextOn("ON");
        switch_smartplug.setTextOff("OFF");
        switch_smartplug.setChecked(true);
        switch_smartplug.setOnCheckedChangeListener(this);
        
        // Edison Light changes
        switch_edisonlight = (Switch)findViewById(R.id.tedison_switch);
        switch_edisonlight.setTextOn("ON");
        switch_edisonlight.setTextOff("OFF");

        //Edison Lock changes
        switch_edisonlock = (Switch)findViewById(R.id.tedison_lock_switch);
        switch_edisonlock.setTextOn("ON");
        switch_edisonlock.setTextOff("OFF");

        

        btn_d1on = (Button)findViewById(R.id.btn_dimmer1On);
        btn_d1on.setOnClickListener(this);
        btn_d1off = (Button)findViewById(R.id.btn_dimmer1Off);
        btn_d1off.setOnClickListener(this);
        btn_d2on = (Button)findViewById(R.id.btn_dimmer2On);
        btn_d2on.setOnClickListener(this);
        btn_d2off = (Button)findViewById(R.id.btn_dimmer2Off);
        btn_d2off.setOnClickListener(this);

        btn_set_therm = (Button)findViewById(R.id.btn_set_therm);
        btn_set_therm.setOnClickListener(this);
        
        btn_hue = (ToggleButton)findViewById(R.id.tbtn_hue_power);
        btn_hue.setOnClickListener(this);
        btn_noti = (Button)findViewById(R.id.btn_noti);
        btn_noti.setOnClickListener(this);
        btn_group = (Button)findViewById(R.id.btn_bulb_all_off);
        btn_group.setOnClickListener(this);
        
		final String[] nums = new String[MTK_DIMMER_MAX_VALUE+1];
		for(int i=0; i<nums.length; i++) {
		   nums[i] = Integer.toString(i);
		}

        np_dim1 = (NumberPicker) findViewById(R.id.np_dimmer1);
        np_dim1.setMinValue(MTK_DIMMER_MIN_VALUE);
        np_dim1.setMaxValue(MTK_DIMMER_MAX_VALUE);
        np_dim1.setWrapSelectorWheel(false);
		np_dim1.setDisplayedValues(nums);
        np_dim1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        np_dim2 = (NumberPicker) findViewById(R.id.np_dimmer2);
        np_dim2.setMinValue(MTK_DIMMER_MIN_VALUE);
        np_dim2.setMaxValue(MTK_DIMMER_MAX_VALUE);
        np_dim2.setWrapSelectorWheel(false);
        np_dim2.setDisplayedValues(nums);
        np_dim2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        for(int i = 0; i < np_t2.length ; i++){
        	np_t2[i] = Integer.toString(i);
        }
        
        for(int i = 0; i < np_h.length ; i++){
        	np_h[i] = Integer.toString(i);
        }
        
        np_dim1.setOnScrollListener(new NumberPicker.OnScrollListener() {

            private int oldValue;  //You need to init this value.
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
            	if(mtkDimmer1Resource == null)
            		return;
            	
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    OCRepresentation rep = new OCRepresentation();
                    rep.setValueInt("power", numberPicker.getValue());
                    OnPutDimmer onPut = new OnPutDimmer();
                    mtkDimmer1Resource.put(rep, onPut);
                }
            }
        });
        
        np_dim2.setOnScrollListener(new NumberPicker.OnScrollListener() {

            private int oldValue;  //You need to init this value.
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
            	if(mtkDimmer2Resource == null)
            		return;
            	
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    OCRepresentation rep = new OCRepresentation();
                    rep.setValueInt("power", numberPicker.getValue());
                    OnPutDimmer onPut = new OnPutDimmer();
                    mtkDimmer2Resource.put(rep, onPut);
                }
            }
        });
        
        final String[] modes = new String[3];
        modes[0] = "OFF";
        modes[1] = "HEAT";
        modes[2] = "COOL";

        // Create the array of numbers that will populate the numberpicker
        final String[] thermo_nums = new String[MTK_THERMO_MAX_VALUE+1];
        for(int i=0; i<thermo_nums.length; i++) {
            thermo_nums[i] = Integer.toString(i+MTK_THERMO_MIN_VALUE);
        }

        np_therm1 = (NumberPicker) findViewById(R.id.np_therm1);
        np_therm1.setMinValue(0);
        np_therm1.setMaxValue(2);
        np_therm1.setWrapSelectorWheel(false);
        np_therm1.setDisplayedValues(modes);
        np_therm1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        np_therm2 = (NumberPicker) findViewById(R.id.np_therm2);
        np_therm2.setMinValue(MTK_THERMO_MIN_VALUE);
        np_therm2.setMaxValue(MTK_THERMO_MAX_VALUE);
        np_therm2.setWrapSelectorWheel(false);
        np_therm2.setDisplayedValues(nums);

        tempVal = (TextView) findViewById(R.id.textview_thermo_temp);
        tempVal.setEnabled(false);
        tempVal.setText("NIL");
        tempVal.setTextColor(Color.parseColor("#ffffffff")); // WHITE
        tempVal.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
        
        np_therm1.setOnScrollListener(new NumberPicker.OnScrollListener() {

            private int oldValue;  //You need to init this value.
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                	LightThemeActivity.mtkthermo.m_tmode = numberPicker.getValue();
                }
            }
        });
        
        np_therm2.setOnScrollListener(new NumberPicker.OnScrollListener() {

            private int oldValue;  //You need to init this value.
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                	LightThemeActivity.mtkthermo.m_temp = new Integer(numberPicker.getValue()).toString();

                }
            }
        });
        
        np_hue_color = (NumberPicker) findViewById(R.id.np_hue_color);
        np_hue_color.setMaxValue(256);
        np_hue_color.setMinValue(0);
        np_hue_color.setDisplayedValues(np_h);
        np_hue_color.setFocusable(true);
        np_hue_color.setFocusableInTouchMode(true);
        np_hue_color.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np_hue_color.setOnScrollListener(new NumberPicker.OnScrollListener() {

            private int oldValue;  //You need to init this value.
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    //We get the different between oldValue and the new value
                    int valueDiff = numberPicker.getValue() - oldValue;

                    //Update oldValue to the new value for the next scroll
                    oldValue = numberPicker.getValue();

                    //Do action with valueDiff
                    Toast.makeText(getApplicationContext(), np_t2[oldValue], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    @Override
    protected void onResume() {
        Log.e(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause()");
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy()");
        super.onDestroy();
    }

    static public void updateConnectStatus(String device, boolean status) {
    	if(device.equals("mtksmartplug")) {
            if(status) {
                smartplugStatus.setBackgroundColor(Color.parseColor("#ff00ff00")); // GREEN
            }
            else {
                smartplugStatus.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
            }
    	}
    	else if(device.equals("mtkdimmer")) {
            if(status) {
                dimmerStatus.setBackgroundColor(Color.parseColor("#ff00ff00")); // GREEN
            }
            else {
                dimmerStatus.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
            }
    	}
    	else if(device.equals("EdisonLight")) {
            if(status) {
                edisonLightStatus.setBackgroundColor(Color.parseColor("#ff00ff00")); // GREEN
            }
            else {
            	edisonLightStatus.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
            }
    	}
    	else if(device.equals("EdisonLock")) {
            if(status) {
                edisonLockStatus.setBackgroundColor(Color.parseColor("#ff00ff00")); // GREEN
            }
            else {
            	edisonLockStatus.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
            }
    	}
    	else if(device.equals("mtkthermo")) {
            if(status) {
                thermoStatus.setBackgroundColor(Color.parseColor("#ff00ff00")); // GREEN
            }
            else {
                thermoStatus.setBackgroundColor(Color.parseColor("#ff888888")); // GRAY
            }
    	}
    }

    static public void updateSmartplugStatus() {
        updateConnectStatus("mtksmartplug", true);

        if(mtksmartplug.m_state.equals("on"))
           switch_smartplug.setChecked(true);
       else
           switch_smartplug.setChecked(false);
    }
    
    static public void updateDimmerDisplay() {
        updateConnectStatus("mtkdimmer", true);
        np_dim1.setValue(mtkdimmer1.m_power);
        np_dim2.setValue(mtkdimmer2.m_power);
    }
    
    static public void updateEdisonLightDisplay() {
        updateConnectStatus("EdisonLight", true);  
        Log.i("", "avi "+mEdisonLight+"mEdisonLight.m_state "+mEdisonLight.m_state);
        if( mEdisonLight.m_state.equals("true"))
        switch_edisonlight.setChecked(true);
       else
    	 switch_edisonlight.setChecked(false);
        switch_edisonlight.setOnCheckedChangeListener(mEdisonLightChangeListener);
    }
    
    static public void updateEdisonLockDisplay() {
        updateConnectStatus("EdisonLock", true);  
        Log.i("", "avi "+mEdisonLock+"mEdisonLock.m_state "+mEdisonLock.m_state);
        if( mEdisonLock.m_state.equals("true"))
        switch_edisonlock.setChecked(true);
       else
    	 switch_edisonlock.setChecked(false);
        switch_edisonlock.setOnCheckedChangeListener(mEdisonLockChangeListener);
    }
    
    static public void updateThermoStatus() {
        updateConnectStatus("mtkthermo", true);
        tempVal.setText(mtkthermo.m_current_temp);
        tempVal.setBackgroundColor(Color.parseColor("#ff00ff00")); // GREEN
    }
    
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
      Log.e(TAG, "onCheckedChanged() " + isChecked);
      if(mtkSmartplugResource != null) {
          if(isChecked)
              mtksmartplug.m_state = "on";
          else
              mtksmartplug.m_state = "off";

          OCRepresentation rep = new OCRepresentation();

          Log.i(TAG, "setValueString : " + mtksmartplug.m_state);
          rep.setValueString("state", mtksmartplug.m_state);

          OnPutSmartplug onPut = new OnPutSmartplug();
          mtkSmartplugResource.put(rep, onPut);
      }
  }

	@Override
	public void onClick(View v) {
        OCRepresentation rep = new OCRepresentation();

		// TODO Auto-generated method stub
		if(v.getId() == R.id.tbtn_hue_power){
			ToggleButton t = (ToggleButton)v;
			if(t.isChecked()){
				// TODO Your Logic..
				Toast.makeText(getApplicationContext(), "Hue ON", Toast.LENGTH_SHORT).show();
			} else {
				// TODO Your Logic..
				Toast.makeText(getApplicationContext(), "Hue OFF", Toast.LENGTH_SHORT).show();
			}
		} else if(v.getId() == R.id.btn_dimmer1On){
            np_dim1.setValue(MTK_DIMMER_MAX_VALUE);

            if(mtkDimmer1Resource == null)
                return;

            rep.setValueInt("power", MTK_DIMMER_MAX_VALUE);
            OnPutDimmer onPut = new OnPutDimmer();
            mtkDimmer1Resource.put(rep, onPut);
		} else if(v.getId() == R.id.btn_dimmer1Off) {
            np_dim1.setValue(MTK_DIMMER_MIN_VALUE);

            if(mtkDimmer1Resource == null)
                return;

            rep.setValueInt("power", MTK_DIMMER_MIN_VALUE);
            OnPutDimmer onPut = new OnPutDimmer();
            mtkDimmer1Resource.put(rep, onPut);
		} else if(v.getId() == R.id.btn_dimmer2On){
            np_dim2.setValue(MTK_DIMMER_MAX_VALUE);

            if(mtkDimmer2Resource == null)
                return;

            rep.setValueInt("power", MTK_DIMMER_MAX_VALUE);
            OnPutDimmer onPut = new OnPutDimmer();
            mtkDimmer2Resource.put(rep, onPut);
		} else if(v.getId() == R.id.btn_dimmer2Off) {
            np_dim2.setValue(MTK_DIMMER_MIN_VALUE);

            if(mtkDimmer2Resource == null)
                return;

            rep.setValueInt("power", MTK_DIMMER_MIN_VALUE);
            OnPutDimmer onPut = new OnPutDimmer();
            mtkDimmer2Resource.put(rep, onPut);
		} else if(v.getId() == R.id.btn_set_therm) {
            if(mtkThermoResource == null)
                return;

            rep.setValueInt("temp", new Integer(LightThemeActivity.mtkthermo.m_temp));
            rep.setValueInt("tmode", LightThemeActivity.mtkthermo.m_tmode);
            OnPutThermo onPut = new OnPutThermo();
            mtkThermoResource.put(rep, onPut);
		} else if(v.getId() == R.id.btn_noti) {
			// TODO Your Logic..
			Toast.makeText(getApplicationContext(), "Send Noti. to Gear", Toast.LENGTH_SHORT).show();
		} else if(v.getId() == R.id.btn_bulb_all_off) {
			// TODO Your Logic..
			Toast.makeText(getApplicationContext(), "All bulb OFF", Toast.LENGTH_SHORT).show();
		}
	}
	/*
	@Override
	// Not using this method
	
	public void onScrollStateChange(NumberPicker view, int scrollState) {
		// TODO Auto-generated method stub
		if(view.getId() == R.id.np_dimmer1){
			// TODO Your Logic..
			Toast.makeText(getApplicationContext(), np_d1[view.getValue()], Toast.LENGTH_SHORT).show();
		} else if(view.getId() == R.id.np_dimmer2){
			// TODO Your Logic..
			Toast.makeText(getApplicationContext(), np_d2[view.getValue()], Toast.LENGTH_SHORT).show();
		} else if(view.getId() == R.id.np_therm1){
			// TODO Your Logic..
			Toast.makeText(getApplicationContext(), np_t1[view.getValue()], Toast.LENGTH_SHORT).show();
		} else if(view.getId() == R.id.np_therm2){
			// TODO Your Logic..
			Toast.makeText(getApplicationContext(), np_t2[view.getValue()], Toast.LENGTH_SHORT).show();
		} else if(view.getId() == R.id.np_hue_color){
			// TODO Your Logic..
			Toast.makeText(getApplicationContext(), np_h[view.getValue()], Toast.LENGTH_SHORT).show();
		} else {
			
		}
	}

	*/
	class EdisonLightSwitchChangeClicker implements Switch.OnCheckedChangeListener
    {

       @Override
       public void onCheckedChanged(CompoundButton buttonView,
               boolean isChecked) {
      
    	   if(buttonView == switch_edisonlight)
    	   {
	    	
	    	   if(mEdisonLightResource != null) 
	    	   {
	    	          if(isChecked)
	    	              mEdisonLight.m_state = "true";
	    	          else
	    	        	  mEdisonLight.m_state = "false";
	
	    	          OCRepresentation rep = new OCRepresentation();
	
	    	          Log.i(TAG, "setValueString of LED : " + mEdisonLight.m_state);
	    	          rep.setValueString("state", mEdisonLight.m_state);
	
	    	          OnPutEdisonLight onPut = new OnPutEdisonLight();
	    	          mEdisonLightResource.put(rep, onPut);
	          }
	    	   
    	   }/*else if(buttonView == switch_edisonlock)
        	   {
    	    	   if(mEdisonLockResource != null) {
    	    	          if(isChecked)
    	    	              mEdisonLock.m_state = "true";
    	    	          else
    	    	        	  mEdisonLock.m_state = "false";
    	
    	    	          OCRepresentation rep = new OCRepresentation();
    	
    	    	          Log.i(TAG, "setValueString of LOCK : " + mEdisonLock.m_state);
    	    	          rep.setValueString("state", mEdisonLock.m_state);
    	
    	    	          OnPutEdisonLock onPut = new OnPutEdisonLock();
    	    	          mEdisonLockResource.put(rep, onPut);
    	         }*/
    }
    }
	
	class EdisonLockSwitchChangeClicker implements Switch.OnCheckedChangeListener
    {

       @Override
       public void onCheckedChanged(CompoundButton buttonView,
               boolean isChecked) {
      

    	   if(buttonView == switch_edisonlock)
    	   {
	    	   if(mEdisonLockResource != null) {
	    	          if(isChecked)
	    	              mEdisonLock.m_state = "true";
	    	          else
	    	        	  mEdisonLock.m_state = "false";
	
	    	          OCRepresentation rep = new OCRepresentation();
	
	    	          Log.i(TAG, "setValueString : " + mEdisonLock.m_state);
	    	          rep.setValueString("state", mEdisonLock.m_state);
	
	    	          OnPutEdisonLock onPut = new OnPutEdisonLock();
	    	          mEdisonLockResource.put(rep, onPut);
	         }
    	   }
    }
     
    }
	
}
