package com.zms.ignorekey;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btnRegister, btnUnRegister;

	private IgnoreKeyReceiver ignoreKeyReceiver;
	private IntentFilter ignoreKeyFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialLayout();

		ignoreKeyReceiver = new IgnoreKeyReceiver();
		ignoreKeyFilter = new IntentFilter();
		ignoreKeyFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

	}

	private void initialLayout() {
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnUnRegister = (Button) findViewById(R.id.btnUnRegister);

		btnRegister.setOnClickListener(new MyOnClickListener());
		btnUnRegister.setOnClickListener(new MyOnClickListener());

	}

	private class MyOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnRegister:
				registerReceiver(ignoreKeyReceiver, ignoreKeyFilter);

				break;

			case R.id.btnUnRegister:
				unregisterReceiver(ignoreKeyReceiver);
				break;

			default:
				break;
			}
		}
	}

	private class IgnoreKeyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
			String reason = intent.getStringExtra("reason");
			if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {

				if (reason.equalsIgnoreCase("globalactions")) {
					// 电源键长按
					Log.v("ZMS", "IgnoreKeyReceiver:globalactions");

				} else if (reason.equalsIgnoreCase("homekey")) {

					// Home键
					Log.v("ZMS", "IgnoreKeyReceiver:homekey");

				} else if (reason.equalsIgnoreCase("recentapps")) {

					// Home键长按
					Log.v("ZMS", "IgnoreKeyReceiver:recentapps");
				}
			}

		}

	}
}
