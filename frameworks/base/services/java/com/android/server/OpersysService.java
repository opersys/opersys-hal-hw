/*
 * Copyright (C) 2012 Opersys inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server;

import android.content.Context;
import android.os.Handler;
import android.os.IOpersysService;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

public class OpersysService extends IOpersysService.Stub {
    private static final String TAG = "opersys";
    private Context mContext;
    private int mNativePointer;

    public OpersysService(Context context) {
	super();
	mContext = context;
	Log.i(TAG, "Opersys Service started");

        mNativePointer = init_native();
	
	Log.i(TAG, "test() returns " + test_native(mNativePointer, 20));
    }

    protected void finalize() throws Throwable {
        finalize_native(mNativePointer);
        super.finalize();
    }

    public String read(int maxLength)
    {
	int length;
	byte[] buffer = new byte[maxLength];

	length = read_native(mNativePointer, buffer);
	return new String(buffer, 0, length);
    }

    public int write(String mString)
    {
	byte[] buffer = mString.getBytes();

	return write_native(mNativePointer, buffer);
    }

    private static native int init_native();
    private static native void finalize_native(int ptr);
    private static native int read_native(int ptr, byte[] buffer);
    private static native int write_native(int ptr, byte[] buffer);
    private static native int test_native(int ptr, int value);
}
