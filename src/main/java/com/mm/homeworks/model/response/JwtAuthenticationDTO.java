package com.mm.homeworks.model.response;

public class JwtAuthenticationDTO {
	private String accessToken;

    public JwtAuthenticationDTO(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getAccessToken() {
		return accessToken;
	}
}
