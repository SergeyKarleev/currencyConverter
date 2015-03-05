package ru.sergeykarleev.useuconverter;

import ru.sergeykarleev.useuconverter.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
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
}
