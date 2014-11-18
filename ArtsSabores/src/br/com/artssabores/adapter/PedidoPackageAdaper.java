package br.com.artssabores.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import br.com.artssabores.R;
import br.com.artssabores.database.DatabaseHandler;
import br.com.artssabores.model.Cesta;
import br.com.artssabores.model.Cliente;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PedidoPackageAdaper extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<Cesta> cestas;
	private final Context context;
	Cliente c;

	public PedidoPackageAdaper(Context context, List<Cesta> cestas,
			Cliente cliente) {
		this.context = context;
		this.cestas = cestas;
		layoutInflater = LayoutInflater.from(context);
		if (cliente != null) {
			c = cliente;
		} else {
			c = null;
		}
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
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.fragment_pedido_packages, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.imgPackage);
			holder.titulo = (TextView) convertView
					.findViewById(R.id.txtFavorite);
			holder.subtitulo = (TextView) convertView
					.findViewById(R.id.txtPreco);

			holder.user = (TextView) convertView.findViewById(R.id.txtPeople);

			if (c != null) {
				holder.user.setText(c.getNome());
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// String imageUrl =
		// "https://lh3.googleusercontent.com/-PYC-wp1YmYk/UvvOCEHTioI/AAAAAAAAALQ/LCu1W7yW58Q/s369/00.png";

		// Picasso.with(context).load(imageUrl).placeholder(R.drawable.default_img).into(holder.image);

		Cesta cesta = cestas.get(position);
		// if (cesta.getId().equals("1")) {
		// holder.image.setBackgroundResource(R.drawable.cesta);
		String imageUrl = "https://lh3.googleusercontent.com/-PYC-wp1YmYk/UvvOCEHTioI/AAAAAAAAALQ/LCu1W7yW58Q/s369/00.png";
		// Log.i("imagen", cesta.getImage());
		Picasso.with(context).load(cesta.getImage())
				.placeholder(R.drawable.default_img).into(holder.image);
		// }

		holder.titulo.setText(cesta.getNome());
		String preco = "R$ " + Double.toString(cesta.getPreco());
		holder.subtitulo.setText(preco);
		return convertView;
	}

	static class ViewHolder {
		ImageView image;
		TextView user;
		TextView titulo;
		TextView subtitulo;
	}

}
