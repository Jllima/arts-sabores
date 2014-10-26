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
	private static final int JSON_CONNECTION_TIMEOUT = 5000;
	private static final int JSON_SOCKET_TIMEOUT = 5000;
	private HttpParams httpParameters;
	private DefaultHttpClient httpclient;
	public static HttpUtil instancia;

	public HttpUtil() {
		httpParameters = new BasicHttpParams();
		setTimeOut(httpParameters);
		httpclient = new DefaultHttpClient(httpParameters);
	}

	public static DefaultHttpClient getInstance() {
		if (instancia == null)
			instancia = new HttpUtil();
		return instancia.httpclient;
	}

	private void setTimeOut(HttpParams params) {
		HttpConnectionParams.setConnectionTimeout(params,
				JSON_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, JSON_SOCKET_TIMEOUT);
	}

}
