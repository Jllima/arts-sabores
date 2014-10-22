package br.com.artssabores.fragments;

import java.util.ArrayList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.artssabores.R;
import br.com.artssabores.adapter.ProdutoListAdapter;
import br.com.artssabores.model.Produto;
import br.com.artssabores.util.HttpUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ProdutosFragment extends Fragment {

	private static final String JSON = "Json";

	private ProgressDialog dialog;

	ProdutoListAdapter adaptador;

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

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Produto p = (Produto) adaptador.getItem(position);
				String s = p.getNome();

				Toast.makeText(getActivity(), "Contato selecionado: " + s,
						Toast.LENGTH_SHORT).show();
			}
		});

		return view;
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

			try {
				String json = HttpUtil.getInstance().httpGetJson(urlString);
				produtoList = getProdutos(json);
			} catch (Throwable e) {
				Log.i("ERRO", e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (produtoList.size() > 0) {
				adaptador = new ProdutoListAdapter(getActivity(), produtoList);
				lv.setAdapter(adaptador);
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity())
						.setTitle("ATENÇÂO")
						.setMessage(
								"Não foi possivel carregar as cestas via ws, sera apresentado um exemplo teste")
						.setPositiveButton("OK", null);
				builder.create().show();
				for (int i = 0; i < 5; i++) {
					Long id = (long) i;
					Produto produto = new Produto(id, "teste", "exemplo teste",
							32.30);
					produtoList.add(produto);
				}
				adaptador = new ProdutoListAdapter(getActivity(), produtoList);
				lv.setAdapter(adaptador);
			}
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

	}
}
