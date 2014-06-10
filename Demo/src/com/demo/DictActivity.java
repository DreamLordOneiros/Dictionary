package com.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DictActivity extends Activity
{
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dict_layout);
	}
	
	public Intent getIntent(String language)
	{
		return new Intent(this, DictWordList.class).putExtra("Language", language);
	}
	
	public void getSpaDictionary(View v)
	{
		startActivity(getIntent("Español"));
	}
	
	public void getEngDictionary(View v)
	{
		startActivity(getIntent("English"));
	}
	
	public void getItaDictionary(View v)
	{
		startActivity(getIntent("Italiano"));
	}
	
	public void getPorDictionary(View v)
	{
		startActivity(getIntent("Portuguesse"));
	}
	
	public void getJapDictionary(View v)
	{
		startActivity(getIntent("Japanesse"));
	}
	
	public void getFreDictionary(View v)
	{
		startActivity(getIntent("French"));
	}
	
	public void getPolDictionary(View v)
	{
		startActivity(getIntent("Polish"));
	}
	
	public void getHinDictionary(View v)
	{
		startActivity(getIntent("Hindi"));
	}
	
	/*
	Intent intent = new Intent(this, DictListActivity.class);

    public void getSpaDictionary(View v)
    {
    	intent.putExtra("Language", "Español");
    	startActivity(intent);
    }

    public void getEngDictionary(View v)
    {
    	intent.putExtra("Language", "English");
    	startActivity(intent);
    }

    public void getJapDictionary(View v)
    {
    	intent.putExtra("Language", "Japanesse");
    	startActivity(intent);
    }

    public void getItaDictionary(View v)
    {
    	intent.putExtra("Language", "Italiano");
    	startActivity(intent);
    }

    public void getPorDictionary(View v)
    {
    	intent.putExtra("Language", "Portuguesse");
    	startActivity(intent);
    }

    public void getFreDictionary(View v)
    {
    	intent.putExtra("Language", "French");
    	startActivity(intent);
    }*/
}
