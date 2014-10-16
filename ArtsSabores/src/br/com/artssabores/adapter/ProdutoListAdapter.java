package br.com.artssabores.adapter;

import java.util.ArrayList;
import java.util.List;

import br.com.artssabores.R;
import br.com.artssabores.model.Produto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProdutoListAdapter extends BaseAdapter{
	
	private LayoutInflater layoutInflater;
	private List<Produto> produtos;
	
	public ProdutoListAdapter(Context context, List<Produto> produtoList) {
		this.produtos = produtoList;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return produtos.size();
	}

	@Override
	public Object getItem(int position) {
		return produtos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.produto_item, null);
			holder = new ViewHolder();
			holder.nome = (TextView) convertView.findViewById(R.id.produto_nome);
			holder.descricao = (TextView) convertView.findViewById(R.id.produto_descricao);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Produto produto = produtos.get(position);
		
		holder.nome.setText(produto.getNome());
		holder.descricao.setText(produto.getDescricao());
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView nome;
		TextView descricao;
	}

}
