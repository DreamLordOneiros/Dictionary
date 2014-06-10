package com.demo;

import java.util.ArrayList;
import java.util.List;

import com.demo.db.DBDataSource;
import com.demo.modals.Language;
import com.demo.support.CustomSpinnerSelection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class TransActivity extends Activity
{
	Spinner spinnerOrigin, spinnerDestination;
	EditText et;
	DBDataSource datasource;
	String[] values;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trans_layout);
		
		et = (EditText)findViewById(R.id.editText1);
		
		datasource = new DBDataSource(this);
		datasource.open();
		
		values = datasource.getValues();
		
    	spinnerOrigin = (Spinner) findViewById(R.id.spinnerOrigin);
    	spinnerDestination = (Spinner) findViewById(R.id.spinnerDestination);
    	
    	Log.i("DICTIONARY DEMO", values[0] + " - " + values[1] + " - " + values[2]);
		
		List<String> languages = getLanguagesList(datasource.getLanguages());
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
		
		spinnerOrigin.setAdapter(adapter);
		spinnerDestination.setAdapter(adapter);
		
		addListenerOnSpinnerItemSelection();
		
		//Toast.makeText(this, "PassData: " + pd.getOrigin() + " | " + pd.getDestination(), Toast.LENGTH_SHORT).show();
	}
    
    public void addListenerOnSpinnerItemSelection()
    {
    	spinnerOrigin.setOnItemSelectedListener(new CustomSpinnerSelection(datasource));
    	spinnerDestination.setOnItemSelectedListener(new CustomSpinnerSelection(datasource));
    }
    
    @Override
    protected void onResume()
    {
    	super.onResume();
    	datasource.open();
    }
    
    @Override
    protected void onPause()
    {
    	super.onPause();
    	datasource.close();
    }
    
    public List<String> getLanguagesList(List<Language> languagesList)
    {
    	List<String> list = new ArrayList<String>();
    	for (Language language : languagesList)
    	{
			list.add(language.getName());
		}
    	return list;
    }
    
    public void translateData(View v)
    {
    	datasource.updateWord(et.getText().toString());
    	Intent i = new Intent(this, DictListActivity.class);
    	startActivity(i);
    }
}
