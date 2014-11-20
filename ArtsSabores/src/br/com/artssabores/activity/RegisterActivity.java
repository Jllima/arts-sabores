package br.com.artssabores.activity;

import com.google.gson.Gson;

import br.com.artssabores.R;
import br.com.artssabores.adapter.ProdutoListAdapter;
import br.com.artssabores.model.Cliente;
import br.com.artssabores.model.Produto;
import br.com.artssabores.util.Validation;
import br.com.artssabores.util.WebServiceCliente;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	EditText regNome;
	EditText regEmail;
	EditText regSenha;
	EditText regEndreco;

	ProgressBar progess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// progess = (ProgressBar) findViewById(R.id.barraProgresso);
		// progess.setProgress(0);

		regNome = (EditText) findViewById(R.id.reg_fullname);
		regEmail = (EditText) findViewById(R.id.reg_email);
		regSenha = (EditText) findViewById(R.id.reg_password);
		regEndreco = (EditText) findViewById(R.id.endereco);
		Button btn = (Button) findViewById(R.id.btnRegister);

		TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

		validFields();

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if ( checkValidation () )
					new InsereClienteJson().execute();
                else
                    Toast.makeText(RegisterActivity.this, "O formulario contem erros", Toast.LENGTH_LONG).show();

			}
		});

		// Listening to Login Screen link
		loginScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// Closing registration screen
				// Switching to Login Screen/closing register screen
				finish();
			}
		});
	}

	public void validFields() {
		regNome.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				Validation.hasText(regNome);
			}
		});
		regEmail.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				Validation.isEmailAddress(regEmail, true);
			}
		});
		regSenha.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				Validation.hasText(regSenha);
			}
		});
	}

	private boolean checkValidation() {
		boolean ret = true;

		if (!Validation.hasText(regNome))
			ret = false;
		if (!Validation.isEmailAddress(regEmail, true))
			ret = false;
		if (!Validation.hasText(regSenha))
			ret = false;

		return ret;
	}

	class InsereClienteJson extends AsyncTask<Void, Integer, String[]> {

		int progess_status;
		ProgressBar bar;
		private final ProgressDialog dialog = new ProgressDialog(
				RegisterActivity.this);

		/*
		 * public void setProgressBar(ProgressBar bar) { this.bar = bar; }
		 */

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("testando");
			dialog.show();
		}

		@Override
		protected String[] doInBackground(Void... params) {

			String nome = regNome.getText().toString();
			String email = regEmail.getText().toString();
			String senha = regSenha.getText().toString();
			String endereco = regEndreco.getText().toString();

			Cliente c = new Cliente(nome, email, senha, endereco);
			Gson gson = new Gson();
			String clienteJSON = "{'cliente':" + gson.toJson(c) + "}";
			Log.d("Aqui", clienteJSON);

			String[] resposta = new WebServiceCliente().post(
					"clientes/inserir", clienteJSON);

			return resposta;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

		}

		@Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if (!result[0].equals("0") && !result[1].equals("Falha de rede!")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						RegisterActivity.this).setTitle("ATENÇÂO")
						.setMessage("Registrado com sucesso!!")
						.setPositiveButton("OK", null);
				builder.create().show();

			} else if (result[0].equals("0")
					&& result[1].equals("email existente")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						RegisterActivity.this).setTitle("ATENÇÂO")
						.setMessage("email já existe!")
						.setPositiveButton("OK", null);
				builder.create().show();
			}

			else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						RegisterActivity.this).setTitle("ATENÇÂO")
						.setMessage("Não foi possivel registar")
						.setPositiveButton("OK", null);
				builder.create().show();

			}

		}

	}
}
