package com.demo.support;

import java.util.List;
import android.app.Application;

public class PassData extends Application
{
	private String origin;
	private String destination;
	private List<String> meanings;
	private List<String> synonyms;
	
	public void setOrigin(String _origin)
	{
		origin = _origin;
	}
	
	public void setDestination(String _destination)
	{
		destination = _destination;
	}
	
	public void setMeanings(List<String> _meanings)
	{
		meanings = _meanings;
	}
	
	public void setSynonyms(List<String> _synonyms)
	{
		synonyms = _synonyms;
	}
	
	public String getOrigin()
	{
		return origin;
	}
	
	public String getDestination()
	{
		return destination;
	}
	
	public List<String> getMeanings()
	{
		return meanings;
	}
	
	public List<String> getSynonyms()
	{
		return synonyms;
	}
}
