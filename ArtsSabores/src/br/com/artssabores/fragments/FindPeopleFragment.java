package br.com.artssabores.fragments;



import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.artssabores.R;
import br.com.artssabores.model.Cliente;
import br.com.artssabores.util.HttpUtil;
import br.com.artssabores.util.WebServiceCliente;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressLint("NewApi")
public class FindPeopleFragment extends Fragment {

	public FindPeopleFragment() {
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_find_people, container, false);
        
        Button bt = (Button) rootView.findViewById(R.id.button1);
        
        bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new InsereJson().execute();
			}
		});
                 
        return rootView;
    }
	
	class InsereJson extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			Cliente c = new Cliente();
			c.setNome("android");
			c.setEndereco("funcionando");
			Gson gson = new Gson();
			String clienteJSON = "{'cliente':"+gson.toJson(c)+"}";
			Log.d("Aqui", clienteJSON);
			
			String[] resposta = new WebServiceCliente().post("http://192.168.25.15:8080/apirest/services/clientes/inserir",clienteJSON);
			return null;
		}
		
	}

	public String inserirCliente(Cliente cliente) throws Exception {

		Gson gson = new Gson();
		String clienteJSON = gson.toJson(cliente);
		String[] resposta = new WebServiceCliente().post("http://192.168.25.15:8080/apirest/services/clientes/inserir",clienteJSON);
		if (resposta[0].equals("200")) {
			return resposta[1];
		} else {
			throw new Exception(resposta[1]);
		}
	}
}
