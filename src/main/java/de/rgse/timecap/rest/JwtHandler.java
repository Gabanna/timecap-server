package de.rgse.timecap.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

import io.jsonwebtoken.Jwts;

public class JwtHandler {

	private String key;

	public JwtHandler() {
		try (InputStream in = getClass().getResourceAsStream("/timecap.properties")) {
			Properties properties = new Properties();
			properties.load(in);
			key = Base64.getEncoder().encodeToString(properties.getProperty("rest.jwt.key").getBytes());
		
		} catch(IOException exception) {
			
		}
	}
	
	public void validateToken(String token) {
		Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	}
}
