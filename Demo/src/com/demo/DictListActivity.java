package com.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.db.DBDataSource;
import com.demo.modals.Language;
 
public class DictListActivity extends Activity
{ 
	ListView lv1, lv2;
	DBDataSource datasource;
    EditText etResponse;
    TextView tvIsConnected;
    String[] values;
    String url;
    String id;
    List<Language> languages;

	List<String> words;
	List<String> eg;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictword_layout);
        
        lv1 = (ListView)findViewById(R.id.synonymsLV);
        lv2 = (ListView)findViewById(R.id.egLV);
        
        datasource = new DBDataSource(this);
        datasource.open();
        values = datasource.getValues();
        id = datasource.getSearchID(values);
        languages = datasource.getLanguages();
        
        Log.i("Search ID: ", id);
        
        if(id.equals("-1"))
        {
        	url = "http://glosbe.com/gapi/translate?from=" + values[2] + "&dest=" + values[4] + "&format=json&phrase=" + values[0].toLowerCase();
        	
        	// check if you are connected or not
        	if(isConnected())
        	{
        		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        		// call AsynTask to perform network operation on separate thread
        		new MyAsyngTask().execute(url);        	
        		Log.i("DICTIONARY DEMO", url);
        	}
        	else
        		Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
        }
        else
        {
        	Log.i("PUNTO", "Pass!");
        	String searchID = datasource.getSearchID(values);
        	
        	words = datasource.getSynonyms(searchID);
        	eg = datasource.getMeanings(searchID);
        	
        	for (String word : words)
        	{
				Log.i("WORD: ", word);
			}
        	
        	for (String example : eg)
        	{
				Log.i("EXAMPLE: ", example);
			}

			ArrayAdapter<String> transAdapter = new ArrayAdapter<String>(DictListActivity.this, android.R.layout.simple_list_item_1, eg);
			lv1.setAdapter(transAdapter);

			ArrayAdapter<String> egAdapter = new ArrayAdapter<String>(DictListActivity.this, android.R.layout.simple_list_item_1, words);				
			lv2.setAdapter(egAdapter);
			
        	try
        	{
        		datasource.close();
        	}
        	catch(Exception e)
        	{
        	
        	}
        }
    }
    
    private class MyAsyngTask extends AsyncTask<String, Void, Boolean>
    {

		@Override
		protected Boolean doInBackground(String... params)
		{
			try
			{
		        HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(params[0]);
				HttpResponse response = client.execute(get);
				
				words = new ArrayList<String>();
				eg = new ArrayList<String>();
				
				int status = response.getStatusLine().getStatusCode();
				
				Log.i("DICTIONARY DEMO", "Status: " + status);
				
				if(status == 200)
				{
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);
					
					JSONObject jsonObject = new JSONObject(data);
					JSONArray dicResult = jsonObject.getJSONArray("tuc");
					
					for (int i = 0; i < dicResult.length(); i++)
					{
						if (dicResult.getJSONObject(i).has("phrase") && dicResult.getJSONObject(i).has("meanings"))
						{
							JSONObject js = dicResult.getJSONObject(i);
							String retrievedWord = js.getJSONObject("phrase").getString("text");
							String retrievedLanguage = js.getJSONObject("phrase").getString("language");
							Log.i("DICTIONARY " + retrievedLanguage, retrievedWord);
							words.add(retrievedWord);
							
							JSONArray ja = js.getJSONArray("meanings");
							
							for (int j=0; j < ja.length(); j++)
							{
								JSONObject temp = ja.getJSONObject(j);
								if(temp.getString("language").equals(values[2]))
								{
									String example = temp.getString("text");
									Log.i("DICTIONARY L " + temp.getString("language"), example);									
									eg.add(example);
								}
							}
						}
						else if(dicResult.getJSONObject(i).has("phrase") && !dicResult.getJSONObject(i).has("meanings"))
						{
							JSONObject js = dicResult.getJSONObject(i);
							String retrievedWord = js.getJSONObject("phrase").getString("text");
							String retrievedLanguage = js.getJSONObject("phrase").getString("language");
							Log.i("DICTIONARY " + retrievedLanguage, retrievedWord);
						}
					}
				}
				
				if(!words.isEmpty() || !eg.isEmpty())
	        	{
	        		long wordID = datasource.insertWord(values[0].toLowerCase());
	        		
	        		long searchID = datasource.insertSearch(values, wordID);
	        		
	        		if(!words.isEmpty())
	        		{
	        			datasource.insertExamples(searchID, words);
	        			ArrayAdapter<String> transAdapter = new ArrayAdapter<String>(DictListActivity.this, android.R.layout.simple_list_item_1, words);
	        			lv1.setAdapter(transAdapter);
	        		}
	        		if(!eg.isEmpty())
	        		{
	        			datasource.insertMeanings(searchID, eg);
	        			ArrayAdapter<String> egAdapter = new ArrayAdapter<String>(DictListActivity.this, android.R.layout.simple_list_item_1, eg);				
	        			lv2.setAdapter(egAdapter);
	        		}
	        	}
	        	else
	        	{
	        		Toast.makeText(DictListActivity.this, "No se encontraron coincidencias!", Toast.LENGTH_SHORT).show();
	        	}
			}
			catch (ClientProtocolException  cpe)
			{
				cpe.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			datasource.close();
			return null;
		}
    	
		@Override
		protected void onPostExecute(Boolean result)
		{
			super.onPostExecute(result);
		}
    }
 
    public boolean isConnected()
    {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        
        if (networkInfo != null && networkInfo.isConnected())
        	return true;
        else
            return false;  
    }
}