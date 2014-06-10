package com.demo.support;

import com.demo.db.DBDataSource;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomSpinnerSelection extends Activity implements OnItemSelectedListener
{	
	DBDataSource datasource;
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
	{
		if("Origin".equals(parent.getTag().toString()))
		{
			Log.i("DICTIONARY DEMO", parent.getTag().toString());
			Log.i("DICTIONARY DEMO", parent.getItemAtPosition(pos).toString());
			
			datasource.updateOrigin(parent.getItemAtPosition(pos).toString());
			Log.i("DICTIONARY ID", datasource.getLangID(parent.getItemAtPosition(pos).toString()));
		}
		
		if("Destination".equals(parent.getTag().toString()))
		{
			Log.i("DICTIONARY DEMO", parent.getTag().toString());

			datasource.updateDestination(parent.getItemAtPosition(pos).toString());
		}
			
		Toast.makeText(parent.getContext(), parent.getTag().toString() + " Language: " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0)
	{
		// TODO Auto-generated method stub
	}
	
	public CustomSpinnerSelection(DBDataSource datasource)
	{
		this.datasource = datasource;
	}
}
