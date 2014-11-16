package br.com.artssabores.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import br.com.artssabores.R;
import br.com.artssabores.database.DatabaseHandler;
import br.com.artssabores.library.ClientFunctions;
import br.com.artssabores.model.Cesta;
import br.com.artssabores.model.Cliente;
import br.com.artssabores.model.Pedido;
import br.com.artssabores.util.WebServiceCliente;
import android.annotation.SuppressLint;
import android.app.ActionBar;
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
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CestaViewActivity extends Activity {

	private TextView txtNome;
	private TextView txtDescricao;
	private TextView txtPreco;
	private EditText endereco;
	private Button btn;
	private Cliente cliente;
	private String urlString = "/pedidos/inserir";
	private Bundle extras;
	private String preco;
	private Cesta cesta;
	private Pedido pedido;
	private ImageView image;

	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cesta_view);
		// get action bar
		ActionBar actionBar = getActionBar();
		// Enabling Up / Back navigation
		actionBar.setDisplayHomeAsUpEnabled(true);

		txtNome = (TextView) findViewById(R.id.txtCab2);
		txtDescricao = (TextView) findViewById(R.id.txtValue2);
		txtPreco = (TextView) findViewById(R.id.txtPreco2);
		btn = (Button) findViewById(R.id.btnPedido);
		endereco = (EditText) findViewById(R.id.txtEnd);
		image = (ImageView) findViewById(R.id.imgPackage2);
		getInfoCesta();

		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		cliente = db.getUserDetails();
		pedido = new Pedido(cliente, cesta);

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String end = endereco.getText().toString();
				Log.i("endereco", end);
				pedido.setEndereco(end);
				new PedidoTask().execute(pedido);
			}
		});
	}

	private void getInfoCesta() {
		cesta = (Cesta) getIntent().getSerializableExtra("Cesta");
		// extras = getIntent().getExtras();
		Picasso.with(this).load(cesta.getImage()).placeholder(R.drawable.ic_pages).into(image);
		
		preco = Double.toString(cesta.getPreco());// extras.getDouble("Preco")
		txtNome.setText(cesta.getNome());// extras.getString("Nome")
		txtDescricao.setText(cesta.getDescricao());// extras.getString("Descricao")
		txtPreco.setText("R$: " + preco);
	}

	class PedidoTask extends AsyncTask<Pedido, Void, String[]> {

		private final ProgressDialog dialog = new ProgressDialog(
				CestaViewActivity.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Aguarde..");
			dialog.show();
		}

		@Override
		protected String[] doInBackground(Pedido... pedido) {
			String end = endereco.getText().toString();
			Log.i("endereco", end);

			Gson gson = new Gson();
			String objGson = gson.toJson(pedido);
			String nova = objGson.replaceAll("\\[", "").replaceAll("\\]", "");
			Log.i("json", nova);
			String pedidoJSON = "{'pedido':" + nova + "}";
			String[] response = new WebServiceCliente().post(urlString, pedidoJSON);
			return response;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (!result[0].equals("0") && !result[1].equals("Falha de rede!")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						CestaViewActivity.this).setTitle("Obrigado")
						.setMessage("pedido realizado com secesso!!")
						.setPositiveButton("OK", null);
				builder.create().show();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						CestaViewActivity.this).setTitle("ATENÇÂO")
						.setMessage("Não foi possivel efetuar o pedido")
						.setPositiveButton("OK", null);
				builder.create().show();
			}

		}

	}
}
