package br.com.artssabores.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import br.com.artssabores.R;
import br.com.artssabores.activity.CestaViewActivity;
import br.com.artssabores.adapter.CestaListAdapter;
import br.com.artssabores.adapter.PedidoPackageAdaper;
import br.com.artssabores.database.DatabaseHandler;
import br.com.artssabores.model.Cesta;
import br.com.artssabores.model.Cliente;
import br.com.artssabores.util.WebServiceCliente;

@SuppressLint("NewApi")
public class PedidoFragment extends Fragment {
	private ProgressDialog dialog;

	CestaListAdapter adaptador;

	List<Cesta> cestaList;

	ListView lv;

	public PedidoFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		cestaList = new ArrayList<Cesta>();
		
		View view = inflater.inflate(R.layout.default_list, container, false);
		lv = (ListView) view.findViewById(R.id.listPackages);

		new CestaJson().execute();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				

				 Toast.makeText(getActivity(), "Descrição da Cesta: "+cestaList.get(position).getDescricao(),
						Toast.LENGTH_SHORT).show();

			}
		});

		return view;
	}

	class CestaJson extends AsyncTask<String, String, String> {
		Cliente c;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Carregando...");
			dialog.setIndeterminate(false);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			DatabaseHandler db = new DatabaseHandler(getActivity());
			c = db.getUserDetails();
			String id = c.getId();
			String urlString = "pedidos/"+id;
			try {
				String response = new WebServiceCliente().get(urlString);
				cestaList = getCesta(response);
				Log.i("descricao", cestaList.get(1).getDescricao());
			} catch (Throwable e) {
				Log.i("ERRO", e.getMessage(), e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (cestaList.size() > 0) {
				adaptador = new CestaListAdapter(getActivity(), cestaList);
				lv.setAdapter(adaptador);
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity())
						.setTitle("ATENÇÂO")
						.setMessage(
								"Não foi possivel carregar as cestas via ws, sera apresentado um exemplo teste")
						.setPositiveButton("OK", null);
				builder.create().show();
			}
		}
	}

	// Retorna uma lista de pessoas com as informações retornadas do JSON
	private List<Cesta> getCesta(String json) {
		List<Cesta> cestas = new ArrayList<Cesta>();

		try {
			JSONArray cestasJson = new JSONArray(json);
			JSONObject cesta;

			for (int i = 0; i < cestasJson.length(); i++) {
				cesta = new JSONObject(cestasJson.getString(i));
				Log.i("Cesta encontrada: ", "nome " + cesta.getString("nome"));
				Cesta objCesta = new Cesta();
				objCesta.setId(cesta.getString("id"));
				objCesta.setNome(cesta.getString("nome"));
				objCesta.setDescricao(cesta.getString("descricao"));
				objCesta.setPreco((Double) cesta.get("preco"));
				objCesta.setImage(cesta.getString("image"));
				cestas.add(objCesta);
			}

		} catch (JSONException e) {
			Log.e("JSON", "Erro no parse do JSON", e);
		}

		return cestas;
	}

}
