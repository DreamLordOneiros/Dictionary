package com.demo.support;

import android.app.Activity;

public class PassDataSupport extends Activity
{
	private PassData pd;
	
	public PassDataSupport()
	{
		pd = (PassData) getApplication();
	}
	
	public PassData getPassData()
	{
		return pd;
	}
}
