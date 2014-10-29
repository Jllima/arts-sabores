package br.com.artssabores.database;

import java.util.HashMap;

import br.com.artssabores.model.Cliente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_CLIENTE = "cliente";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_UID = "uid";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_ENDERECO = "endereco";
	private static final String KEY_DATA_DE_CADASTRO = "dataDeCadastro";

	// private static final String KEY_FOTO = "foto";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CLIENTE_TABLE = "CREATE TABLE " + TABLE_CLIENTE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_UID + " TEXT,"
				+ KEY_NAME + " TEXT," 
				+ KEY_EMAIL + " TEXT UNIQUE,"
				+ KEY_ENDERECO + " TEXT," 
				+ KEY_DATA_DE_CADASTRO + " NUMERIC"
				+ ")";
		db.execSQL(CREATE_CLIENTE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE);

		// Create tables again
		onCreate(db);
	}

	public void addCliente(Cliente c) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_UID, c.getUid());
		values.put(KEY_NAME, c.getNome());
		values.put(KEY_EMAIL, c.getEmail());
		values.put(KEY_ENDERECO, c.getEndereco());
		db.insert(TABLE_CLIENTE, null, values);
		db.close();
	}

	/**
	 * Getting user data from database
	 * */
	public Cliente getUserDetails() {
		Cliente c = new Cliente();
		String selectQuery = "SELECT  * FROM " + TABLE_CLIENTE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			c.setUid(cursor.getString(1));
			c.setNome(cursor.getString(2));
			c.setEmail(cursor.getString(3));
			c.setEndereco(cursor.getString(4));
		}
		cursor.close();
		db.close();
		// return user
		return c;
	}

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CLIENTE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_CLIENTE, null, null);
		db.close();
	}
}
