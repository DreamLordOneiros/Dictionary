package com.demo.db;

import java.util.ArrayList;
import java.util.List;

import com.demo.modals.DictionaryWord;
import com.demo.modals.Language;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBDataSource
{
	private static final String LOGTAG = "DICTIONARY DEMO";
	
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	
	private static final String[] allLanguages =
	{
		DBOpenHelper.COLUMN_LANG_ID,
		DBOpenHelper.COLUMN_LANG_NAME,
		DBOpenHelper.COLUMN_LANG_SHORT_NAME
	};
	
	private static final String[] valuesArray =
		{
			DBOpenHelper.COLUMN_VALUES_WORD,
			DBOpenHelper.COLUMN_VALUES_ORIGIN,
			DBOpenHelper.COLUMN_VALUES_ORIGIN_SHORT,
			DBOpenHelper.COLUMN_VALUES_DESTINATION,
			DBOpenHelper.COLUMN_VALUES_DESTINATION_SHORT
		};
	
	public DBDataSource(Context context)
	{
		dbHelper = new DBOpenHelper(context);
	}

	public void open()
	{
		database = dbHelper.getWritableDatabase();
		Log.i(LOGTAG, "Database Open");
	}
	
	public void close()
	{
		dbHelper.close();
		Log.i(LOGTAG, "Database Closed");
	}
	
	public List<Language> getLanguages()
	{
		List<Language> languages = new ArrayList<Language>();
		
		Cursor cursor = database.query(DBOpenHelper.TABLE_LANGUAGES, allLanguages, null, null, null, null, null);
		
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		
		if(cursor.getCount() > 0)
		{
			while(cursor.moveToNext())
			{
				Language language = new Language();
				language.setID(cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_LANG_ID)));
				language.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_LANG_NAME)));
				language.setShortName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_LANG_SHORT_NAME)));
				languages.add(language);
			}
		}
		
		return languages;
	}
	
	public String[] getValues()
	{
		String[] values = new String[5];
		Cursor cursor = database.query(DBOpenHelper.TABLE_VALUES, valuesArray, null, null, null, null, null);
		
		if(cursor.getCount() > 0)
		{
			while(cursor.moveToNext())
			{
				values[0] = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_VALUES_WORD));
				values[1] = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_VALUES_ORIGIN));
				values[2] = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_VALUES_ORIGIN_SHORT));
				values[3] = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_VALUES_DESTINATION));
				values[4] = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_VALUES_DESTINATION_SHORT));
			}
		}
		return values;
	}
	
	public void updateOrigin(String origin)
	{
		database.execSQL("UPDATE " + DBOpenHelper.TABLE_VALUES + " SET " + DBOpenHelper.COLUMN_VALUES_ORIGIN + " = '" + origin + "', " + DBOpenHelper.COLUMN_VALUES_ORIGIN_SHORT + " = '" + getShortName(origin) + "'");
	}
	
	public void updateDestination(String destination)
	{
		database.execSQL("UPDATE " + DBOpenHelper.TABLE_VALUES + " SET " + DBOpenHelper.COLUMN_VALUES_DESTINATION + " = '" + destination + "', " + DBOpenHelper.COLUMN_VALUES_DESTINATION_SHORT + " = '" + getShortName(destination) + "'");
	}
	
	public void updateWord(String word)
	{
		database.execSQL("UPDATE " + DBOpenHelper.TABLE_VALUES + " SET " + DBOpenHelper.COLUMN_VALUES_WORD + " = '" + word + "'");
	}
	
	public String getShortName(String name)
	{
		String id = "No Short Name";
		String sql = "SELECT " + DBOpenHelper.COLUMN_LANG_SHORT_NAME + " FROM " + DBOpenHelper.TABLE_LANGUAGES + " WHERE " + DBOpenHelper.COLUMN_LANG_NAME + " = '" + name + "'";
		Cursor cursor = database.rawQuery(sql, null);
		
		if(cursor.moveToFirst())
		{
			id = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_LANG_SHORT_NAME));
		}
		
		return id;
	}
	
	public String getSearchID(String[] values)
	{
		String id = "-1";
		String sql = "SELECT " + DBOpenHelper.COLUMN_SEARCH_ID + " FROM " + DBOpenHelper.TABLE_SEARCH + " INNER JOIN " + DBOpenHelper.TABLE_WORDS + " ON " + DBOpenHelper.TABLE_SEARCH + "." + DBOpenHelper.COLUMN_SEARCH_WORD_ID + " = " + DBOpenHelper.TABLE_WORDS + "." + DBOpenHelper.COLUMN_WORDS_ID + " WHERE " + DBOpenHelper.TABLE_WORDS + "." + DBOpenHelper.COLUMN_WORDS_WORD + "= '" + values[0].toLowerCase() + "' AND " + DBOpenHelper.TABLE_SEARCH + "." + DBOpenHelper.COLUMN_SEARCH_ORIGIN_ID + " = " + getLangID(values[1]) + " AND " + DBOpenHelper.TABLE_SEARCH + "." + DBOpenHelper.COLUMN_SEARCH_DESTINATION_ID + " = " + getLangID(values[3]);
		Cursor cursor = database.rawQuery(sql, null);
		
		Log.i("Query: ", sql);
		Log.i("Value 0: ", values[0]);
		Log.i("Value 1: ", values[1]);
		Log.i("Value 2: ", values[2]);
		Log.i("Value 3: ", values[3]);
		Log.i("Value 4: ", values[4]);
		Log.i("Search ID: ", id);
		
		if(cursor.moveToFirst())
		{
			id = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_SEARCH_ID));
		}
		
		return id;
	}
	
	public String getLangID(String language)
	{
		String id = "-1";
		String sql = "SELECT " + DBOpenHelper.COLUMN_LANG_ID + " FROM " + DBOpenHelper.TABLE_LANGUAGES + " WHERE " + DBOpenHelper.COLUMN_LANG_NAME + " = '" + language + "'";
		
		Cursor cursor = database.rawQuery(sql , null);
		
		if(cursor.moveToFirst())
		{
			id = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_LANG_ID));
		}

		Log.i("Lang ID: ", id);
		Log.i("Lang Query: ", sql);
		return id;
	}
	
	public String getWordID(String word)
	{
		String id = "-1";
		String sql = "SELECT " + DBOpenHelper.COLUMN_WORDS_ID + " FROM " + DBOpenHelper.TABLE_WORDS + " WHERE " + DBOpenHelper.COLUMN_WORDS_WORD + " = '" + word.toLowerCase() + "'";
		
		Cursor cursor = database.rawQuery(sql , null);
		
		if(cursor.moveToFirst())
		{
			id = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_WORDS_ID));
		}

		Log.i("Word ID: ", id);
		Log.i("Word Query: ", sql);
		return id;
	}
	
	public long insertWord(String word)
	{
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.COLUMN_WORDS_WORD, word);
		long insertID = database.insert(DBOpenHelper.TABLE_WORDS, null, values);
		Log.i("DIC LOG", "Word inserted with id: " + insertID);
		return insertID;
	}
	
	public long insertSearch(String[] inputValues, long wordID)
	{
		ContentValues insertValues = new ContentValues();
		
		insertValues.put(DBOpenHelper.COLUMN_SEARCH_WORD_ID, wordID);
		insertValues.put(DBOpenHelper.COLUMN_SEARCH_ORIGIN_ID, getLangID(inputValues[1]));
		insertValues.put(DBOpenHelper.COLUMN_SEARCH_DESTINATION_ID, getLangID(inputValues[3]));
		
		long insertID = database.insert(DBOpenHelper.TABLE_SEARCH, null, insertValues);
		Log.i("DIC LOG", "Search inserted with id: " + insertID);
		return insertID;		
	}
	
	public void insertMeanings(long searchID, List<String> synonyms)
	{
		ContentValues insertValues = new ContentValues();
		
		for (String synonym : synonyms)
		{
			insertValues.put(DBOpenHelper.COLUMN_SYNONYMS_SEARCH_ID, searchID);
			insertValues.put(DBOpenHelper.COLUMN_SYNONYMS_SYNONYM, synonym);
			long id = database.insert(DBOpenHelper.TABLE_SYNONYMS, null, insertValues);

			Log.i("DIC LOG", "Synonym inserted with id: " + id);
		}
	}
	
	public void insertExamples(long searchID, List<String> examples)
	{
		ContentValues insertValues = new ContentValues();
		
		for (String example : examples)
		{
			insertValues.put(DBOpenHelper.COLUMN_MEANINGS_SEARCH_ID, searchID);
			insertValues.put(DBOpenHelper.COLUMN_MEANINGS_MEANING, example);
			long id = database.insert(DBOpenHelper.TABLE_MEANINGS, null, insertValues);

			Log.i("DIC LOG", "Meaning inserted with id: " + id);
		}
	}
	
	public List<String> getMeanings(String searchID)
	{
		List<String> meanings = new ArrayList<String>();

		String sql = "SELECT " + DBOpenHelper.COLUMN_MEANINGS_MEANING + " FROM " + DBOpenHelper.TABLE_MEANINGS + " WHERE " + DBOpenHelper.COLUMN_MEANINGS_SEARCH_ID + " = " + searchID ;
		Cursor cursor = database.rawQuery(sql, null);
		
		if(cursor.getCount() > 0)
		{
			while(cursor.moveToNext())
			{
				String meaning = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_MEANINGS_MEANING));
				meanings.add(meaning);
			}
		}
		return meanings;
	}
	
	public List<String> getSynonyms(String searchID)
	{
		List<String> synonyms = new ArrayList<String>();

		String sql = "SELECT " + DBOpenHelper.COLUMN_SYNONYMS_SYNONYM + " FROM " + DBOpenHelper.TABLE_SYNONYMS + " WHERE " + DBOpenHelper.COLUMN_SYNONYMS_SEARCH_ID + " = " + searchID ;
		Cursor cursor = database.rawQuery(sql, null);
		
		if(cursor.getCount() > 0)
		{
			while(cursor.moveToNext())
			{
				String meaning = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_SYNONYMS_SYNONYM));
				synonyms.add(meaning);
			}
		}
		return synonyms;
	}
	
	public List<DictionaryWord> getLanguageDictionary(String language)
	{
		List<DictionaryWord> words = new ArrayList<DictionaryWord>();
		
		String langID = getLangID(language);
		String sql = "SELECT " + DBOpenHelper.TABLE_WORDS + "." + DBOpenHelper.COLUMN_WORDS_WORD + ", " + DBOpenHelper.TABLE_SEARCH + "." + DBOpenHelper.COLUMN_SEARCH_ID + ", " + DBOpenHelper.TABLE_LANGUAGES + "." + DBOpenHelper.COLUMN_LANG_NAME + " FROM " + DBOpenHelper.TABLE_SEARCH + " INNER JOIN " + DBOpenHelper.TABLE_WORDS + " ON " + DBOpenHelper.TABLE_SEARCH + "." + DBOpenHelper.COLUMN_SEARCH_WORD_ID + " = " + DBOpenHelper.TABLE_WORDS + "." + DBOpenHelper.COLUMN_WORDS_ID + " INNER JOIN " + DBOpenHelper.TABLE_LANGUAGES + " ON " + DBOpenHelper.TABLE_LANGUAGES + "." + DBOpenHelper.COLUMN_LANG_ID + " = " + DBOpenHelper.TABLE_SEARCH + "." + DBOpenHelper.COLUMN_SEARCH_DESTINATION_ID + " WHERE " + DBOpenHelper.TABLE_SEARCH + "." + DBOpenHelper.COLUMN_SEARCH_ORIGIN_ID + " = " + langID;
		Cursor cursor = database.rawQuery(sql, null);
		
		if(cursor.getCount() > 0)
		{
			while(cursor.moveToNext())
			{
				DictionaryWord dw = new DictionaryWord();
				
				dw.setID(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_SEARCH_ID)));
				dw.setWord(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_WORDS_WORD)));
				dw.setDestination(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_LANG_NAME)));
				
				words.add(dw);
			}
		}
		return words;
	}
}
