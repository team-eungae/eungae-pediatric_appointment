package com.playdata.eungae.kakao.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KakaoApi {

	public String getAccessToken(String code) {
		String accessToken = "";
		String refreshToken = "";
		String requestUrl = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("grant_type=authorization_code");
			stringBuffer.append("&client_id=94fc9424ea7cee32e3fcec284eaaf2e8");
			stringBuffer.append("&redirect_uri=http://localhost:8090/auth/kakao");
			stringBuffer.append("&code=" + code);

			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();

			int responseCode = connection.getResponseCode();
			log.info("responseCode : {}", responseCode);

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line = "";
			String result = "";
			while ((line = bufferedReader.readLine()) != null) {
				result += line;
			}

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			accessToken = element.getAsJsonObject().get("access_token").getAsString();
			refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

			bufferedReader.close();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	public HashMap<String, Object> getUserInfo(String accessToken) {
		HashMap<String, Object> userInfo = new HashMap<String, Object>();

		String requestUrl = "https://kapi.kakao.com/v2/user/me";

		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + accessToken);

			int responseCode = connection.getResponseCode();
			log.info("responseCode : {}", responseCode);

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line = "";
			String result = "";

			while ((line = bufferedReader.readLine()) != null) {
				result += line;
			}

			log.info("responseBody : {}", result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_acoount").getAsJsonObject();

			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

			userInfo.put("nickname", nickname);
			userInfo.put("email", email);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userInfo;
	}


}
