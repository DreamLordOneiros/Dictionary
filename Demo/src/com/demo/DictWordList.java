package com.demo;

import java.util.List;

import com.demo.db.DBDataSource;
import com.demo.modals.DictionaryWord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DictWordList extends Activity
{
	DBDataSource datasource;
	TextView tv;
	ListView lv;
	List<DictionaryWord> dw;
	String language;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dictwordlist_layout);
		
		datasource = new DBDataSource(this);
		datasource.open();
		
		Intent intent = getIntent();
		language = intent.getStringExtra("Language").toString();
		dw = datasource.getLanguageDictionary(language);
		
		tv = (TextView) findViewById(R.id.textView1);
		tv.setText(language);
		
		lv = (ListView) findViewById(R.id.wordsLV);

		ArrayAdapter<DictionaryWord> transAdapter = new ArrayAdapter<DictionaryWord>(DictWordList.this, android.R.layout.simple_list_item_1, dw);
		lv.setAdapter(transAdapter);
	}
}
