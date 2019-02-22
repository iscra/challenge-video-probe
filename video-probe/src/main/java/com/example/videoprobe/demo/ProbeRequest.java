package com.example.videoprobe.demo;

public class ProbeRequest {
	
    private String url;

	@Override
	public String toString() {
		return "ProbeRequest [url=" + url + "]";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}
