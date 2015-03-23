package ru.sergeykarleev.useuconverter.classes;

import java.math.BigDecimal;

import ru.sergeykarleev.useuconverter.R;
import ru.sergeykarleev.useuconverter.interfaces.XMLParser;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author SergeyKarleev
 * 
 */
/**
 * @author skar011
 * 
 */
public class MainActivity extends Activity implements OnKeyListener,
		LoaderCallbacks<String> {

	final static int DIALOG_DATE = 1;
	final static String LOG_TAG = "myLogs";

	final static int LOADER_DAY = 1;
	final static int LOADER_MONTH = 2;

	final static int INFOVIEW_NONE = 0;
	final static int INFOVIEW_GRAPH = 1;
	final static int INFOVIEW_TABLE = 2;

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

	Spinner spMonth;
	Spinner spYear;
	Spinner spValutes;

	LinearLayout llContainer;
	ListView lView;

	int infoView = INFOVIEW_NONE;

	int mYear = 2015;
	int mMonth = 0;
	int mDay = 1;

	static MyMonthQuotesObject mQuotesObject;
	MyGraphClass mGraphObject;

	private static double multiplicator_USD = 1;
	private static double multiplicator_EUR = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnDate = (Button) findViewById(R.id.btnDate);

		llContainer = (LinearLayout) findViewById(R.id.llContainer);

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

		spMonth = (Spinner) findViewById(R.id.spMonth);
		spYear = (Spinner) findViewById(R.id.spYear);
		spValutes = (Spinner) findViewById(R.id.spValutes);

		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabCreator(tabHost);

		mQuotesObject = MyMonthQuotesObject.getInstance();
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

			String request = MyRequestHelper.getDayRequest(year, monthOfYear,
					dayOfMonth);
			btnDate.setText(MyRequestHelper.getDate(year, monthOfYear,
					dayOfMonth));

			createRequest(LOADER_DAY, request);

		}
	};

	public void onclick(View v) {
		// Toast.makeText(this, "onclick", Toast.LENGTH_SHORT).show();
		int year;
		int monthOfYear;
		String valute;

		if (!isOnline()){
			Toast.makeText(context, "Интернет-соединение недоступно", Toast.LENGTH_SHORT).show();
			return;}
		
		switch (v.getId()) {
		case R.id.btnDate:
			showDialog(DIALOG_DATE);
			break;
		case R.id.btnGetGraph:
			Toast.makeText(this, "Строим график", Toast.LENGTH_SHORT).show();

			year = Integer.valueOf(spYear.getSelectedItem().toString());
			monthOfYear = spMonth.getSelectedItemPosition();
			valute = spValutes.getSelectedItem().toString();

			if (mQuotesObject.isEmptyQuotes()) {
				createRequest(LOADER_MONTH, MyRequestHelper.getMonthRequest(
						year, monthOfYear, valute));
				Log.d(LOG_TAG, "Значений нет. Получаем значения");

			} else if (!mQuotesObject.isComparised(spYear.getSelectedItem()
					.toString(), spMonth.getSelectedItem().toString(),
					spValutes.getSelectedItem().toString())) {
				Log.d(LOG_TAG,
						"Входящие данные изменились. Получаем новые значения");
				mQuotesObject.clearQuotesList();
				createRequest(LOADER_MONTH, MyRequestHelper.getMonthRequest(
						year, monthOfYear, valute));
			} else {
				Log.d(LOG_TAG,
						"Строим график по существующим значениям\nвсего значений: "
								+ mQuotesObject.getQuotesList().length);
				createGraph(mQuotesObject);
			}

			infoView = INFOVIEW_GRAPH;
			break;
		case R.id.btnGetTable:

			year = Integer.valueOf(spYear.getSelectedItem().toString());
			monthOfYear = spMonth.getSelectedItemPosition();
			valute = spValutes.getSelectedItem().toString();

			if (mQuotesObject.isEmptyQuotes()) {
				mQuotesObject.setData(spYear.getSelectedItem().toString(),
						spMonth.getSelectedItem().toString(), spValutes
								.getSelectedItem().toString());
				createRequest(LOADER_MONTH, MyRequestHelper.getMonthRequest(
						year, monthOfYear, valute));
				Log.d(LOG_TAG, "Значений нет. Получаем значения");

			} else if (!mQuotesObject.isComparised(spYear.getSelectedItem()
					.toString(), spMonth.getSelectedItem().toString(),
					spValutes.getSelectedItem().toString())) {
				Log.d(LOG_TAG,
						"Входящие данные изменились. Получаем новые значения");
				mQuotesObject.clearQuotesList();
				mQuotesObject.setData(spYear.getSelectedItem().toString(),
						spMonth.getSelectedItem().toString(), spValutes
								.getSelectedItem().toString());
				createRequest(LOADER_MONTH, MyRequestHelper.getMonthRequest(
						year, monthOfYear, valute));
			} else {
				Log.d(LOG_TAG,
						"Строим график по существующим значениям\nвсего значений: "
								+ mQuotesObject.getQuotesList().length);
				createTable(mQuotesObject);
			}

			infoView = INFOVIEW_TABLE;
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
			etConvRURusd.setText(roundUp(Double.valueOf(etConvUSD.getText()
					.toString()) * multiplicator_USD));
			break;
		case R.id.etConvEUR:
			etConvRUReur.setText(roundUp(Double.valueOf(etConvEUR.getText()
					.toString()) * multiplicator_EUR));
			break;
		case R.id.etConvRURusd:
			if (multiplicator_USD == 0)
				break;
			etConvUSD.setText(roundUp(Double.valueOf(etConvRURusd.getText()
					.toString()) / multiplicator_USD * 1.0));
			break;
		case R.id.etConvRUReur:
			if (multiplicator_EUR == 0)
				break;
			etConvEUR.setText(roundUp(Double.valueOf(etConvRUReur.getText()
					.toString()) / multiplicator_EUR * 1.0));
			break;
		default:
			break;
		}
		return false;
	}

	/**
	 * Функция округления. Округляем валюту после конвертации до 4 знака после
	 * запятой.
	 */
	public String roundUp(double value) {
		BigDecimal val = new BigDecimal("" + value).setScale(4,
				BigDecimal.ROUND_HALF_UP);
		return val.toString();
	}

	/**
	 * Метод отображения графика во второй вкладке в контейнере LinearLayout
	 * 
	 * @param doubles
	 *            массив котировок
	 */
	protected void createGraph(MyMonthQuotesObject mQList) {
		Double[] doubles = mQList.getQuotesList();
		llContainer.removeAllViews();
		mGraphObject = new MyGraphClass(this);
		llContainer.addView(mGraphObject.createGraph(doubles));
	}

	protected void createTable(MyMonthQuotesObject mQList) {
		llContainer.removeAllViews();

		String[] listItems = { "Нет котировок по данному запросу" };
		try {
			listItems = mQList.getQuoteListForTable();
		} catch (Exception e) {
			// TODO: handle exception
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		TextView tvHeader = new TextView(context);
		tvHeader.setText(mQuotesObject.getMonthAndYear());

		tvHeader.setLayoutParams(params);
		tvHeader.setGravity(Gravity.CENTER_HORIZONTAL);

		lView = new ListView(context);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
				android.R.layout.simple_list_item_1, listItems);
		lView.setAdapter(adapter);
		lView.setLayoutParams(params);
		llContainer.addView(tvHeader);
		llContainer.addView(lView);
	}

	protected void createRequest(int loaderID, String request) {
		Log.d(LOG_TAG, "CreateRequest: " + request);
		Bundle args = new Bundle();
		args.putString("REQUEST", request);

		getLoaderManager().initLoader(loaderID, args, this);
		Loader<String> loader = getLoaderManager().getLoader(loaderID);
		loader.forceLoad();
	}

	@Override
	public Loader<String> onCreateLoader(int id, Bundle args) {
		Loader<String> loader = null;
		if (id == LOADER_DAY || id == LOADER_MONTH) {
			loader = new MyXMLAsyncLoader(this, args);
			return loader;
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<String> loader, String data) {
		//Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
		switch (loader.getId()) {
		case LOADER_DAY:
			MyDAYParser mDayParser = new MyDAYParser(data);
			multiplicator_EUR = mDayParser.getValute(XMLParser.VALUTE_EUR);
			multiplicator_USD = mDayParser.getValute(XMLParser.VALUTE_USD);

			tvUSDValue.setText(String.valueOf(multiplicator_USD));
			tvEURValue.setText(String.valueOf(multiplicator_EUR));
			getLoaderManager().destroyLoader(LOADER_DAY);
			break;
		case LOADER_MONTH:
			// TODO: ниже убрать 31 и поставить метод, возвращающий количество
			// дней в месяце
			MyMonthParser mMonthParser = new MyMonthParser(data);
			mQuotesObject.setQuotesList(mMonthParser.getQuotesList());
			getLoaderManager().destroyLoader(LOADER_MONTH);

			switch (infoView) {
			case INFOVIEW_GRAPH:
				createGraph(mQuotesObject);
				break;
			case INFOVIEW_TABLE:
				createTable(mQuotesObject);
				break;
			default:
				break;
			}
			infoView = INFOVIEW_NONE;
			break;
		default:
			break;
		}

	}

	@Override
	public void onLoaderReset(Loader<String> loader) {
		// TODO Auto-generated method stub

	}

	/**
	 * Метод проверяет наличие соединения с интернетом.
	 * 
	 * @return boolean
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}

}
