package br.com.artssabores.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.artssabores.R;
import br.com.artssabores.adapter.ProdutoListAdapter;
import br.com.artssabores.model.Produto;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

@SuppressLint("NewApi")
public class ProdutosFragment extends Fragment {

	private static final String JSON = "Json";

	private ProgressDialog dialog;

	List<Produto> produtoList;

	ListView lv;

	public ProdutosFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		produtoList = new ArrayList<Produto>();

		new ProdutosJson().execute();

		View view = inflater.inflate(R.layout.fragment_produtos, container,
				false);

		lv = (ListView) view.findViewById(R.id.produto_list);

		lv.setOnItemClickListener(new SlideMenuClickListener());

		return view;
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}

		private void displayView(int position) {
			switch (position) {
			case 0:
				Log.d("0", "Aqui");
				break;
			case 1:
				Log.d("1", "Aqui");
				break;
			case 2:
				Log.d("2", "Aqui");
				break;
			default:
				break;
			}
			
		}
	}

	class ProdutosJson extends AsyncTask<String, String, String> {

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
			String urlString = "http://192.168.43.119:8080/apirest/services/produtos/listargson";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(urlString);
			try {
				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					String json = getStringFromInputStream(instream);
					instream.close();
					produtoList = getProdutos(json);

				}
			} catch (Exception e) {
				Log.d(JSON, "Falha ao acessar Web service", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();

			lv.setAdapter(new ProdutoListAdapter(getActivity(), produtoList));

		}

		

		// Retorna uma lista de pessoas com as informações retornadas do JSON
		private List<Produto> getProdutos(String json) {
			List<Produto> produtos = new ArrayList<Produto>();

			try {
				JSONArray produtosJson = new JSONArray(json);
				JSONObject produto;

				for (int i = 0; i < produtosJson.length(); i++) {
					produto = new JSONObject(produtosJson.getString(i));
					Log.i("Pessoa encontrada: ",
							"nome " + produto.getString("nome"));
					Produto objetoProduto = new Produto();
					objetoProduto.setNome(produto.getString("nome"));
					objetoProduto.setDescricao(produto.getString("descricao"));
					produtos.add(objetoProduto);
				}

			} catch (JSONException e) {
				Log.e(JSON, "Erro no parse do JSON", e);
			}

			return produtos;
		}

		// Converte objeto InputStream para String
		private String getStringFromInputStream(InputStream is) {
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return sb.toString();
		}
	}
}
