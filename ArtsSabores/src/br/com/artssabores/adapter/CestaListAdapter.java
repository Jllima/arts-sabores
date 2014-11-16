package br.com.artssabores.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import br.com.artssabores.R;
import br.com.artssabores.adapter.ProdutoListAdapter.ViewHolder;
import br.com.artssabores.model.Cesta;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CestaListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<Cesta> cestas;
	private final Context context;

	public CestaListAdapter(Context context, List<Cesta> cestaList) {
		this.context = context;
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
			holder.image = (ImageView) convertView
					.findViewById(R.id.icone);
			holder.titulo = (TextView) convertView.findViewById(R.id.titulo);
			holder.subtitulo = (TextView) convertView
					.findViewById(R.id.subtitulo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Cesta cesta = cestas.get(position);
		
		String url = findImage(Integer.parseInt(cesta.getId()));
		
		Picasso.with(context).load(cesta.getImage()).placeholder(R.drawable.ic_pages).into(holder.image);
		
		holder.titulo.setText(cesta.getNome());
		String preco = "R$ "+Double.toString(cesta.getPreco());
		holder.subtitulo.setText(preco);
		return convertView;
	}

	static class ViewHolder {
		ImageView image;
		TextView titulo;
		TextView subtitulo;
	}
	private String findImage(int i){
		switch (i) {
		case 1:
			
			break;

		default:
			break;
		}
		return null;
	}

}
