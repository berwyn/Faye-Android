// @formatter:off
/******************************************************************************
 *
 *  Copyright 2011-2012 b3rwyn Mobile Solutions
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/
// @formatter:on

package com.b3rwynmobile.fayeclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Service class to run Faye. Provides a singleton method to get the running
 * instance.
 * 
 * @author Jamison Greeley (atomicrat2552@gmail.com)
 */
public class FayeService extends Service {

	// Debug tag
	protected final String			TAG				= "Faye Service";

	// String constants
	final protected static String	FAYE_HOST		= "YOUR_ADDRESS"; // ws://someurl.com
	final protected static String	FAYE_PORT		= "YOUR_PORT";// 80
	final protected static String	INITIAL_CHANNEL	= "YOUR_INITIAL_CHANNEL";// /push
	final protected static String	AUTH_TOKEN		= "SUPER SECRET TOKEN";

	// Data objects
	protected FayeClient			mFaye;
	protected FayeBinder			mFayeBinder;

	/**
	 * Default constructor
	 */
	public FayeService() {
		super();
	}

	/**
	 * Returns the Binder to interact with Faye. This is the prefered method to
	 * run the service, and starting from an Intent is not currently supported
	 */
	@Override
	public IBinder onBind(Intent intent) {
		setup();
		return this.mFayeBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		setup();
	}

	/**
	 * Stops Faye when the Service is being destroyed by the OS
	 */
	@Override
	public void onDestroy() {
		stopFaye();
		super.onDestroy();
	}

	protected void setup() {
		// Debug toast
		if (FayeClient.DEBUG) {
			Toast.makeText(getApplicationContext(), "Faye Service created",
					Toast.LENGTH_SHORT).show();
		}
		String fayeUrl = FayeService.FAYE_HOST + ":" + FayeService.FAYE_PORT
				+ FayeService.INITIAL_CHANNEL;

		// Create the client
		this.mFaye = new FayeClient(fayeUrl);

		// Create the binder
		this.mFayeBinder = new FayeBinder(this, this.mFaye);
	}

	/**
	 * Starts the Faye client
	 */
	public void startFaye() {
		if (FayeClient.DEBUG) {
			Toast.makeText(getApplicationContext(), "Faye Started",
					Toast.LENGTH_SHORT).show();
		}
		this.mFaye.connect();
	}

	/**
	 * Stops the Faye client
	 */
	public void stopFaye() {
		this.mFaye.disconnect();
	}

}
