package com.opersys.hellointernal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.os.ServiceManager;   // Will only work in AOSP
import android.os.IOpersysService;  // Interface "hidden" in SDK

public class HelloOpersysInternalActivity extends Activity {
    private static final String DTAG = "HelloOpersysInternal";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        IOpersysService om = IOpersysService.Stub.asInterface(ServiceManager.getService("opersys"));
        try {
        	Log.d(DTAG, "Going to write to the \"opersys\" service");
        	om.write("Hello Opersys");
        	Log.d(DTAG, "Service returned: " + om.read(20));
        }
        catch (Exception e) {
        	Log.d(DTAG, "FAILED to call service");
        	e.printStackTrace();
        }
    }
}