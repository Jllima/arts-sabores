package br.com.artssabores.activity;

import br.com.artssabores.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ProdutoViewActivity extends Activity {
	
	private TextView txtNome;
	private TextView txtDescricao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produto_view);
		
		//get action bar
		ActionBar actionBar = getActionBar();
		//Enabling Up / Back navigation
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		txtNome = (TextView) findViewById(R.id.txtCabecalho);
		txtDescricao = (TextView) findViewById(R.id.txtRodape);
		getInfoProdutos();
	}

	private void getInfoProdutos() {
		Bundle extras = getIntent().getExtras();
		txtNome.setText(extras.getString("Nome"));
		txtDescricao.setText(extras.getString("Descricao"));
	}
}
