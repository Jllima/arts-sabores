package br.com.artssabores.adapter;

import java.util.List;

import br.com.artssabores.R;
import br.com.artssabores.adapter.ProdutoListAdapter.ViewHolder;
import br.com.artssabores.model.Cesta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CestaListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<Cesta> cestas;

	public CestaListAdapter(Context context, List<Cesta> cestaList) {
		this.cestas = cestaList;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return cestas.size();
	}

	@Override
	public Object getItem(int position) {
		return cestas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.cesta_item, null);
			holder = new ViewHolder();
			holder.titulo = (TextView) convertView.findViewById(R.id.titulo);
			holder.subtitulo = (TextView) convertView
					.findViewById(R.id.subtitulo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Cesta cesta = cestas.get(position);
		holder.titulo.setText(cesta.getNome());
		String preco = "R$ "+Double.toString(cesta.getPreco());
		holder.subtitulo.setText(preco);
		return convertView;
	}

	static class ViewHolder {
		TextView titulo;
		TextView subtitulo;
	}

}
