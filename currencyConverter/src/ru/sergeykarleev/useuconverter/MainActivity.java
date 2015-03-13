package ru.sergeykarleev.useuconverter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author SergeyKarleev
 * 
 */
public class MainActivity extends Activity implements OnKeyListener,
		LoaderCallbacks<String> {

	final static int DIALOG_DATE = 1;
	final static String LOG_TAG = "myLogs";

	final static int ID_LOADER = 1;

	final String URL_DAY = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
	// final String URL_MONTH =
	// "http://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=02/03/2001&date_req2=14/03/2001&VAL_NM_RQ=R01235";

	static TextView tvUSDValue;
	static TextView tvEURValue;

	Button btnDate;
	Context context = this;

	EditText etConvUSD;
	EditText etConvEUR;
	EditText etConvRURusd;
	EditText etConvRUReur;

	LinearLayout llGraph;

	int mYear = 2015;
	int mMonth = 0;
	int mDay = 1;

	MyGraphClass mGraphObject;

	private static double multiplicator_USD = 1;
	private static double multiplicator_EUR = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnDate = (Button) findViewById(R.id.btnDate);

		llGraph = (LinearLayout) findViewById(R.id.llGraph);

		etConvUSD = (EditText) findViewById(R.id.etConvUSD);
		etConvUSD.setOnKeyListener(this);
		etConvEUR = (EditText) findViewById(R.id.etConvEUR);
		etConvEUR.setOnKeyListener(this);

		etConvRURusd = (EditText) findViewById(R.id.etConvRURusd);
		etConvRURusd.setOnKeyListener(this);
		etConvRUReur = (EditText) findViewById(R.id.etConvRUReur);
		etConvRUReur.setOnKeyListener(this);
		
		tvUSDValue = (TextView) findViewById(R.id.tvUSDValue);
		tvEURValue = (TextView) findViewById(R.id.tvEURValue);
		
		
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
			Calendar c = Calendar.getInstance();
			c.set(year, monthOfYear, dayOfMonth);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date = sdf.format(c.getTime());

			btnDate.setText(date);
			createRequest(URL_DAY + date);
		}
	};

	public void onclick(View v) {
		// Toast.makeText(this, "onclick", Toast.LENGTH_SHORT).show();
		switch (v.getId()) {
		case R.id.btnDate:
			showDialog(DIALOG_DATE);
			break;
		case R.id.btnRequest:
			// TODO: ������� ��������� ��������� �� ����������� ����
			// TODO: ������� ����������� ����������� �����
			break;
		case R.id.btnGetGraph:
			Toast.makeText(this, "������ ������", Toast.LENGTH_SHORT).show();
			// TODO:������� ��������� ������� ��������� �� ��������� �����
			// TODO: ����� ������ MyGraphClass ���������� ������� �� ������
			// ����������� �������
			// ��������� (�.�. ������� �� ���������� �������)

			Double[] test = { 12.5, 11.3, 10.4 };
			createGraph(test);
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
			etConvRURusd.setText(roundUp(Double.valueOf(etConvUSD
					.getText().toString()) * multiplicator_USD));
			break;
		case R.id.etConvEUR:
			etConvRUReur.setText(roundUp(Double.valueOf(etConvEUR
					.getText().toString()) * multiplicator_EUR));
			break;
		case R.id.etConvRURusd:
			if (multiplicator_USD == 0)
				break;
			etConvUSD.setText(roundUp(Double.valueOf(etConvRURusd
					.getText().toString()) / multiplicator_USD * 1.0));
			break;
		case R.id.etConvRUReur:
			if (multiplicator_EUR == 0)
				break;
			etConvEUR.setText(roundUp(Double.valueOf(etConvRUReur
					.getText().toString()) / multiplicator_EUR * 1.0));
			break;
		default:
			break;
		}
		return false;
	}

	
	/**������� ����������. ��������� ������ ����� ����������� �� 4 ����� ����� �������. 
	 */
	public String roundUp(double value){
		BigDecimal val = new BigDecimal(""+value).setScale(4, BigDecimal.ROUND_HALF_UP);		
	    return val.toString(); 
	}
	
	/**
	 * ����� ����������� ������� �� ������ ������� � ���������� LinearLayout
	 * 
	 * @param prices
	 *            ������ ���������
	 */
	protected void createGraph(Double[] prices) {
		llGraph.removeAllViews();
		mGraphObject = new MyGraphClass(this);
		llGraph.addView(mGraphObject.createGraph(prices));
	}

	
	protected void createRequest(String request) {
		Log.d(LOG_TAG, "CreateRequest: " + request);

		Bundle args = new Bundle();
		args.putString("REQUEST", request);

		getLoaderManager().initLoader(ID_LOADER, args, this);
		Loader<String> loader = getLoaderManager().getLoader(ID_LOADER);	
		loader.forceLoad();
		
	}
	
	

	@Override
	public Loader<String> onCreateLoader(int id, Bundle args) {
		Loader<String> loader = null;
		if (id == ID_LOADER) {
			loader = new MyXMLAsyncLoader(this, args);
			return loader;
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<String> loader, String data) {
		Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
		MyXMLParser mParser = new MyXMLParser(data);
		multiplicator_EUR = mParser.getEUR();
		multiplicator_USD = mParser.getUSD();
				
		tvUSDValue.setText(String.valueOf(multiplicator_USD));
		tvEURValue.setText(String.valueOf(multiplicator_EUR));
				
		getLoaderManager().destroyLoader(ID_LOADER);
	}

	@Override
	public void onLoaderReset(Loader<String> loader) {
		// TODO Auto-generated method stub

	}

}
