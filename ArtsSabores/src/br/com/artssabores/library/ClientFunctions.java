package br.com.artssabores.library;

import android.content.Context;
import br.com.artssabores.database.DatabaseHandler;

public class ClientFunctions {

	public static ClientFunctions functions;

	public static ClientFunctions getInstance() {
		if (functions == null)
			functions = new ClientFunctions();
		return functions;
	}

	/**
	 * Function get Login status
	 * */
	public boolean isClienteLoggedIn(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if (count > 0) {
			// user logged in
			return true;
		}
		return false;
	}

	/**
	 * Function to logout user Reset Database
	 * */
	public boolean logoutClient(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}

}
