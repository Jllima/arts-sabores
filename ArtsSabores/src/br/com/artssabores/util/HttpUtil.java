package br.com.artssabores.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class HttpUtil {
	
	protected static final String CATEGORIA = "HttpUtil";

	public static HttpUtil instancia;

	public static HttpUtil getInstance() {
		if (instancia == null)
			instancia = new HttpUtil();
		return instancia;
	}

	public String httpGetJson(String url) {
		String urlString = url;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		HttpConnectionParams.setSoTimeout(httpParameters, 5000);
		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		HttpGet httpget = new HttpGet(urlString);
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String json = getStringFromInputStream(instream);
				instream.close();
				return json;
			}
		} catch (IOException e) {
			Log.e(CATEGORIA, e.getMessage(), e);
		}
		return null;

	}

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
