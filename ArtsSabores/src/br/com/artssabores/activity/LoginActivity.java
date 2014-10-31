package br.com.artssabores.activity;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.artssabores.R;
import br.com.artssabores.database.DatabaseHandler;
import br.com.artssabores.library.ClientFunctions;
import br.com.artssabores.model.Cliente;
import br.com.artssabores.util.WebServiceCliente;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	TextView registerTela;
	Button btn;
	EditText inputEmail;
	EditText inputPassword;
	Cliente cliente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);

		registerTela = (TextView) findViewById(R.id.link_to_register);

		btn = (Button) findViewById(R.id.btnLogin);

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new LoginTask().execute();
			}
		});
		{

		}

		registerTela.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(it);
			}
		});
	}

	class LoginTask extends AsyncTask<Void, Void, Void> {
		
		private final ProgressDialog dialog = new ProgressDialog(
				LoginActivity.this);
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Aguarde..");
			dialog.show();
		}

		
		@Override
		protected Void doInBackground(Void... params) {
			String email = inputEmail.getText().toString();
			String password = inputPassword.getText().toString();
			String urlString = "clientes/login/"
					+ email + "/" + password;
			try {
				String json = new WebServiceCliente().get(urlString);
				Log.i("ClienteJson", json);
				cliente = getCliente(json);
			} catch (Throwable e) {
				Log.i("ERRO", e.getMessage(), e);
			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (cliente != null) {
				DatabaseHandler db = new DatabaseHandler(getApplicationContext());
				ClientFunctions.getInstance().logoutClient(getApplicationContext());
				db.addCliente(cliente);
				Log.i("Busacar cliente: ",db.getUserDetails().toString());
				Intent it = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(it);	
			}else{
				AlertDialog.Builder builder = new AlertDialog.Builder(
						LoginActivity.this).setTitle("ATENÇÂO")
						.setMessage("Login ou senha incorretos")
						.setPositiveButton("OK", null);
				builder.create().show();
			}
			
		}

	}

	private Cliente getCliente(String json) {
		Cliente c = new Cliente();
		try {
			JSONObject josn_cliente = new JSONObject(json);
			c.setUid(josn_cliente.getString("id"));
			c.setNome(josn_cliente.getString("nome"));
			c.setEmail(josn_cliente.getString("email"));
			c.setEndereco(josn_cliente.getString("endereco"));
			return c;
		} catch (JSONException e) {
			Log.e("JSON", "Erro no parse do JSON", e);
		}
		return null;
	}
}
