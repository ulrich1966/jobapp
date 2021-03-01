package de.juli.jobapp.jobweb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class HttpRequestTest {
	private static final String REQ_URL = "https://jobboerse.arbeitsagentur.de/vamJB/bewerbungenAnzeigen.html?execution=e1s1&_eventId_stellenangebot&encs=3v%2B5TruYHAkRbaMsqwZ97jeGAEGsM20%2FxtQg4R8ZjkVkcaa2wB%2BmemQGlnnFtP%2BtZBPlplPqVujo5yln1nKF4g%3D%3D&encs=60xpKFldSSc37T4r9TQ5NtJ9jTwWYdkBEGUlM05eVa1fj2gYK5QwR5o5x2Dq%2BdJMpCh5gcMpOxMUkWU51MKwan9yjdT8JDc5GUCVrkNWFdFVvONbXkU8OZt0deRi4LS78rpNZelX157TtwsClSfLhw%3D%3D&encs=dU%2FrrBfNnj369d02uLkaEHmHRdYqofFdMoezE3bp4%2BunWhSP9FTjlsDcWTpoEe6Q&encs=JYMUGs4haCCN4Hh8i2RmUKnfeWcQ4QaVlDNo6MyNHPIJ3Jvxnb0SsK9H%2BVPvgkcK&encs=tuQrxkv1c3hJEyL6RKqQlBbS9%2F9U6JKkXVmagpi2QkvDsKAVzlQ%2FgNgMaDrh7Buc"; 
			
	@Test
	public void test() throws Exception {
	
		System.out.println("Testing 1 - Send Http GET request");
		sendGet();
	
	}

	// HTTP GET request
	private void sendGet() throws Exception {

		String url = REQ_URL;
		
		URL obj = new URL(REQ_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("POST");

	
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine+"\n");
		}
		in.close();

		//print result
		System.out.println(response.toString());

		
	}

}
