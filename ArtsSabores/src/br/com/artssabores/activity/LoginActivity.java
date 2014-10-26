package br.com.artssabores.activity;

import br.com.artssabores.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		TextView registerTela = (TextView) findViewById(R.id.link_to_register);
		
		registerTela.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(it);
			}
		});
	}
}
