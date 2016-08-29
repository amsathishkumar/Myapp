package com.sat.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class ResHelper {

	public String satjson() {
		String b = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpResponse response = httpClient.execute(new HttpGet(
					"http://10.143.26.182:3000/sat"));
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String body = "";

			while ((body = rd.readLine()) != null) {
				b = b + body;
				Log.e("HttpResponse", body);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(b);
		return b;

	}

	public LinkedHashMap<String, String> satjson1() {
		
		LinkedHashMap<String, String> lk = new LinkedHashMap<String, String>();
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpResponse response = httpClient.execute(new HttpGet(
					"http://10.143.26.182:3000/sat"));

			InputStream is = response.getEntity().getContent();
			JsonFactory factory = new JsonFactory();
			JsonParser jsonParser = factory.createJsonParser(is);
			JsonToken token = jsonParser.nextToken();
			

			if (token == JsonToken.START_ARRAY) {
				while (token != JsonToken.END_ARRAY) {
					token = jsonParser.nextToken();
					if (token == JsonToken.START_OBJECT) {
						while (token != JsonToken.END_OBJECT) {
							token = jsonParser.nextToken();
							if (token == JsonToken.FIELD_NAME) {
								String objectName = jsonParser.getCurrentName();
								jsonParser.nextToken();
								lk.put(objectName, jsonParser.getText());
							}
						}
					}
				}
			}
			System.out.println(lk);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lk;

	}
}
