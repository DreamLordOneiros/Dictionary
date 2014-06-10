package com.demo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DictionaryTabActivity extends FragmentActivity implements TabListener
{
	ActionBar ab;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dictionary_tab_layout);
		
		ab = getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.Tab meaningsTab = ab.newTab();
		meaningsTab.setText("Meanings");
		meaningsTab.setTabListener(this);
		
		ActionBar.Tab synonymsTab = ab.newTab();
		synonymsTab.setText("Synonyms");
		synonymsTab.setTabListener(this);
		
		ab.addTab(meaningsTab);
		ab.addTab(synonymsTab);
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}
}
