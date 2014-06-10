package com.demo.modals;

public class Language
{
	private long id;
	private String name;
	private String short_name;
	
	public void setID(long _id)
	{
		id = _id;
	}
	
	public void setName(String _name)
	{
		name = _name;
	}
	
	public void setShortName(String _short)
	{
		short_name = _short;
	}
	
	public long getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getShortName()
	{
		return short_name;
	}

}
