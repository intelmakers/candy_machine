package org.iotivity.cesdemo;

import org.iotivity.cesdemo.R;
import org.iotivity.cesdemo.numberpicker.*;

import android.app.Activity;
import android.os.Bundle;

public class DarkThemeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dark);

        NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker);
        np.setMaxValue(20);
        np.setMinValue(0);
        np.setFocusable(true);
        np.setFocusableInTouchMode(true);
    }
}
