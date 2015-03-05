package ru.sergeykarleev.useuconverter;

import ru.sergeykarleev.useuconverter.R.string;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,
		OnKeyListener {

	final static int DIALOG_DATE = 1;
	final static String LOG_TAG = "myLogs";

	TextView tvUSDValue;
	TextView tvEURValue;

	Button btnDate;
	Button btnRequest;

	EditText etConvUSD;
	EditText etConvEUR;

	int mYear = 2015;
	int mMonth = 0;
	int mDay = 1;

	static float multiplicator_USD_EUR = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnDate = (Button) findViewById(R.id.btnDate);
		btnDate.setOnClickListener(this);

		btnRequest = (Button) findViewById(R.id.btnRequest);
		btnRequest.setOnClickListener(this);

		etConvUSD = (EditText) findViewById(R.id.etConvUSD);
		etConvUSD.setOnKeyListener(this);
		etConvEUR = (EditText) findViewById(R.id.etConvEUR);
		etConvEUR.setOnKeyListener(this);

		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabCreator(tabHost);

	}

	private void tabCreator(TabHost tabHost) {
		tabHost.setup();

		TabHost.TabSpec tabSpec;

		String tag_1 = getResources().getString(R.string.tab_1);
		String tag_2 = getResources().getString(R.string.tab_2);

		tabSpec = tabHost.newTabSpec(tag_1);
		tabSpec.setIndicator(tag_1);
		tabSpec.setContent(R.id.tab1);
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec(tag_2);
		tabSpec.setIndicator(tag_2);
		tabSpec.setContent(R.id.tab2);
		tabHost.addTab(tabSpec);

		tabHost.setCurrentTabByTag(tag_1);
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {

		if (id == DIALOG_DATE) {
			DatePickerDialog dpd = new DatePickerDialog(this, myCallBack,
					mYear, mMonth, mDay);
			return dpd;
		}
		return super.onCreateDialog(id);
	}

	OnDateSetListener myCallBack = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear + 1;
			mDay = dayOfMonth;
			btnDate.setText(mDay + "/" + mMonth + "/" + mYear);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDate:
			Toast.makeText(this, "зададим дату", Toast.LENGTH_SHORT).show();
			showDialog(DIALOG_DATE);
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		
		float USD = 0;
		float EUR = 0;
		
		if (event.getAction() != KeyEvent.ACTION_DOWN
				|| (keyCode != KeyEvent.KEYCODE_ENTER))
			return false;

		switch (v.getId()) {
		case R.id.etConvUSD:		
				etConvEUR.setText(String.valueOf(Float.valueOf(etConvUSD.getText().toString())*multiplicator_USD_EUR));
			break;
		case R.id.etConvEUR:
			if (multiplicator_USD_EUR==0)
				return false;
			etConvUSD.setText(String.valueOf(Float.valueOf(etConvEUR.getText().toString())/multiplicator_USD_EUR));
			break;
		default:
			break;
		}
		return false;
	}

}
