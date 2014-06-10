package com.demo.db;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DBOpenHelper extends SQLiteOpenHelper
{
	private static final String LOGTAG = "DICTIONARY DEMO";

	//Database Name
	private static final String DATABASE_NAME = "dictionary.db";
	private static final int DATABASE_VERSION = 1;
	
	//Languages Table Constants
	public static final String TABLE_LANGUAGES = "languages";
	public static final String COLUMN_LANG_ID = "id_lang";
	public static final String COLUMN_LANG_NAME = "name";
	public static final String COLUMN_LANG_SHORT_NAME = "short_name";
	
	//Words Table Constants
	public static final String TABLE_WORDS = "words";
	public static final String COLUMN_WORDS_ID = "id_word";
	public static final String COLUMN_WORDS_WORD = "word";
	
	//Search Table Constants
	public static final String TABLE_SEARCH = "search";
	public static final String COLUMN_SEARCH_ID = "id_search";
	public static final String COLUMN_SEARCH_WORD_ID = "id_word";
	public static final String COLUMN_SEARCH_ORIGIN_ID = "id_origin";
	public static final String COLUMN_SEARCH_DESTINATION_ID = "id_destination";
	
	//Meanings Table Constants
	public static final String TABLE_MEANINGS = "meanings";
	public static final String COLUMN_MEANINGS_ID = "id_meaning";
	public static final String COLUMN_MEANINGS_SEARCH_ID = "id_search";
	public static final String COLUMN_MEANINGS_MEANING = "meaning";
	
	//Synonyms Table Constants
	public static final String TABLE_SYNONYMS = "synonyms";
	public static final String COLUMN_SYNONYMS_ID = "id_synonym";
	public static final String COLUMN_SYNONYMS_SEARCH_ID = "id_search";
	public static final String COLUMN_SYNONYMS_SYNONYM = "synonym";
	
	//Values Table Constants
	public static final String TABLE_VALUES = "values_table";
	public static final String COLUMN_VALUES_WORD = "word";
	public static final String COLUMN_VALUES_ORIGIN = "origin";
	public static final String COLUMN_VALUES_ORIGIN_SHORT = "origin_short";
	public static final String COLUMN_VALUES_DESTINATION = "destination";
	public static final String COLUMN_VALUES_DESTINATION_SHORT = "destination_short";
	
	//Table Creation Constants
	private static final String TABLE_CREATE_LANGUAGES = 
			"CREATE TABLE " + TABLE_LANGUAGES + " (" +
			COLUMN_LANG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_LANG_NAME + " TEXT, " +
			COLUMN_LANG_SHORT_NAME + " TEXT)";
	
	private static final String TABLE_CREATE_WORDS = 
			"CREATE TABLE " + TABLE_WORDS + " (" + 
			COLUMN_WORDS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_WORDS_WORD + " TEXT)";
	
	private static final String TABLE_CREATE_SEARCH =
			"CREATE TABLE " + TABLE_SEARCH + " (" +
			COLUMN_SEARCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_SEARCH_WORD_ID + " INTEGER, " +
			COLUMN_SEARCH_ORIGIN_ID + " INTEGER, " +
			COLUMN_SEARCH_DESTINATION_ID + " INTENER)";
	
	private static final String TABLE_CREATE_MEANINGS =
			"CREATE TABLE " + TABLE_MEANINGS + " (" +
			COLUMN_MEANINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_MEANINGS_SEARCH_ID + " INTEGER, " +
			COLUMN_MEANINGS_MEANING + " TEXT)";
	
	private static final String TABLE_CREATE_SYNONYMS = 
			"CREATE TABLE " + TABLE_SYNONYMS + " (" +
			COLUMN_SYNONYMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_SYNONYMS_SEARCH_ID + " INTEGER, " +
			COLUMN_SYNONYMS_SYNONYM + " TEXT)";
	
	private static final String TABLE_CREATE_VALUES =
			"CREATE TABLE " + TABLE_VALUES + " (" +
			COLUMN_VALUES_WORD + " TEXT, " +
			COLUMN_VALUES_ORIGIN + " TEXT, " +
			COLUMN_VALUES_ORIGIN_SHORT + " TEXT, " +
			COLUMN_VALUES_DESTINATION + " TEXT, " +
			COLUMN_VALUES_DESTINATION_SHORT + " TEXT)";

	public DBOpenHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(TABLE_CREATE_LANGUAGES);
		initLanguages(db);
		Log.i(LOGTAG, "Languages Created!");
		db.execSQL(TABLE_CREATE_WORDS);
		Log.i(LOGTAG, "Words Created!");
		db.execSQL(TABLE_CREATE_SEARCH);
		Log.i(LOGTAG, "Search Created!");
		db.execSQL(TABLE_CREATE_MEANINGS);
		Log.i(LOGTAG, "Meanings Created!");
		db.execSQL(TABLE_CREATE_SYNONYMS);
		Log.i(LOGTAG, "Synonyms Created!");
		db.execSQL(TABLE_CREATE_VALUES);
		Log.i(LOGTAG, "Values Created!");
		initValues(db, "English", "eng", "English", "eng");
		Log.i(LOGTAG, "Tables Created!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEANINGS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYNONYMS);
		db.execSQL("DROM TABLE IF EXISTS " + TABLE_VALUES);
		onCreate(db);
	}
	
	private void initLanguages(SQLiteDatabase db)
	{
		insertLanguage(db, "eng", "English");
		insertLanguage(db, "fra", "French");
		insertLanguage(db, "hin", "Hindi");
		insertLanguage(db, "ita", "Italian");
		insertLanguage(db, "jpn", "Japan");
		insertLanguage(db, "pol", "Polish");
		insertLanguage(db, "por", "Portugese");
		insertLanguage(db, "spa", "Spanish");
		insertLanguage(db, "tel", "Telugu");
	}

	private void insertLanguage(SQLiteDatabase db, String shortName, String longName)
	{
		db.execSQL("INSERT INTO " + TABLE_LANGUAGES + " (" + COLUMN_LANG_SHORT_NAME + ", " + COLUMN_LANG_NAME + ") VALUES ('" + shortName + "', '" + longName + "')");
	}
	
	private void initValues(SQLiteDatabase db, String origin, String origin_short, String destination, String destination_short)
	{
		db.execSQL("INSERT INTO " + TABLE_VALUES + " (" + COLUMN_VALUES_WORD + ", " + COLUMN_VALUES_ORIGIN + ", " + COLUMN_VALUES_ORIGIN_SHORT + ", " + COLUMN_VALUES_DESTINATION + ", " + COLUMN_VALUES_DESTINATION_SHORT + ") VALUES ('Word', '" + origin + "', '" + origin_short + "', '" + destination + "', '" + destination_short + "')");
	}
}
