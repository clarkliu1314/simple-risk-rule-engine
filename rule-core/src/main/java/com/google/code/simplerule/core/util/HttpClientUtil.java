package com.google.code.simplerule.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	private static HttpClient httpclient = null;

	public static HttpClient getLoginConnection(String url) {
		httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpget);
			String entity = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpclient;

	}

	public static HttpClient getConnection() {
		return new DefaultHttpClient();
	}

	public static String postFile(HttpClient client, String postFileurl,
			String fileParamName, String filePath) {
		HttpEntity resEntity = null;
		HttpPost httppost = new HttpPost(postFileurl);
		File file = new File(filePath);
		MultipartEntity mpEntity = new MultipartEntity();
		ContentBody cbFile = new FileBody(file);
		mpEntity.addPart(fileParamName, cbFile);
		httppost.setEntity(mpEntity);
		System.out.println("executing request " + httppost.getRequestLine());
		try {
			HttpResponse response = client.execute(httppost);
			resEntity = response.getEntity();
			if (resEntity != null) {
				return EntityUtils.toString(resEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		client.getConnectionManager().shutdown();

		return null;

	}

	public static String sendRequest(HttpClient client, String method,
			String url, Map<String, Object> params, Map<String, Object> headers) {
		HttpPost httpPost = null;
		HttpGet httpGet = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		if (params == null)
			params = new HashMap<String, Object>();
		if (headers == null)
			headers = new HashMap<String, Object>();

		try {
			if ("POST".equalsIgnoreCase(method)) {
				httpPost = new HttpPost(url);
				response = client.execute(httpPost);
				for (String key : headers.keySet()) {
					httpPost.addHeader(key, headers.get(key).toString());
				}
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (String key : params.keySet()) {
					nvps.add(new BasicNameValuePair(key, params.get(key)
							.toString()));
					httpPost.setEntity(new UrlEncodedFormEntity(nvps,
							HTTP.UTF_8));
				}
			} else if ("GET".equalsIgnoreCase(method)) {
				httpGet = new HttpGet(url);
				for (String key : headers.keySet()) {
					httpGet.addHeader(key, headers.get(key).toString());
				}
				response = client.execute(httpGet);
			}
			entity = response.getEntity();
			String responseString = EntityUtils.toString(response.getEntity());
			return responseString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
