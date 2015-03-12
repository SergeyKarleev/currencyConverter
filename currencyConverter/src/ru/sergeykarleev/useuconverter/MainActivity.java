package ru.sergeykarleev.useuconverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ru.sergeykarleev.useuconverter.R.string;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.content.Loader;
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
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

/**
 * @author sergey
 * 
 */
public class MainActivity extends Activity implements OnKeyListener, LoaderCallbacks<String> {

	final static int DIALOG_DATE = 1;
	final static String LOG_TAG = "myLogs";
	
	final static int ID_LOADER = 1;

	final String URL_DAY = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
	// final String URL_MONTH =
	// "http://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=02/03/2001&date_req2=14/03/2001&VAL_NM_RQ=R01235";

	TextView tvUSDValue;
	TextView tvEURValue;

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

	private static float multiplicator_USD = 1;
	private static float multiplicator_EUR = 1;

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

		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabCreator(tabHost);
	}

	public static void setMultiplicator_USD_EUR(float multiplicator_USD_EUR) {
		MainActivity.multiplicator_USD = multiplicator_USD_EUR;
	}

	public static void setMultiplicator_EUR_USD(float multiplicator_EUR_USD) {
		MainActivity.multiplicator_EUR = multiplicator_EUR_USD;
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
			// String date = dayOfMonth
			// mCurrencyObject = new MyCurrencyClass(context, dayOfMonth,
			// monthOfYear+1, year);
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
			// TODO: функция получения котировки на определённую дату
			// TODO: функция отображения полученного курса
			break;
		case R.id.btnGetGraph:
			Toast.makeText(this, "Строим график", Toast.LENGTH_SHORT).show();
			// TODO:Функция получения массива котировок на выбранный месяц
			// TODO: Метод класса MyGraphClass построения графика на основе
			// полученного массива
			// котировок (м.б. вызвана из предыдущей функции)

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
			etConvRURusd.setText(String.valueOf(Float.valueOf(etConvUSD
					.getText().toString()) * multiplicator_USD));
			break;
		case R.id.etConvEUR:
			etConvRUReur.setText(String.valueOf(Float.valueOf(etConvEUR
					.getText().toString()) / multiplicator_EUR));
			break;
		case R.id.etConvRURusd:
			if (multiplicator_USD == 0)
				break;
			etConvUSD.setText(String.valueOf(Float.valueOf(etConvRURusd
					.getText().toString()) / multiplicator_USD * 1.0));
			break;
		case R.id.etConvRUReur:
			if (multiplicator_EUR == 0)
				break;
			etConvEUR.setText(String.valueOf(Float.valueOf(etConvRUReur
					.getText().toString()) / multiplicator_EUR * 1.0));
			break;
		default:
			break;
		}
		return false;
	}

	/**
	 * Метод отображения графика во второй вкладке в контейнере LinearLayout
	 * 
	 * @param prices
	 *            массив котировок
	 */
	private void createGraph(Double[] prices) {
		llGraph.removeAllViews();
		mGraphObject = new MyGraphClass(this);
		llGraph.addView(mGraphObject.createGraph(prices));
	}

	public void testMessageXML(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

	}

	
	protected void createRequest(String request) 
	{	
		Bundle args = new Bundle();
		args.putString("REQUEST", request);
		getLoaderManager().initLoader(ID_LOADER, args, this);
	}
	
	@Override
	public android.content.Loader<String> onCreateLoader(int id, Bundle args) {
		Loader<String> loader = null;
		if (id==ID_LOADER){
			loader = new MyXMLAsyncLoader(this, args);
			return loader;
		}
		return null;
	}

	@Override
	public void onLoadFinished(android.content.Loader<String> loader,
			String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(android.content.Loader<String> loader) {
		// TODO Auto-generated method stub
		
	}

	
}
