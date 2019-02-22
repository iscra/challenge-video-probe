package com.example.videoprobe.demo;

public class VideoInfo {
	
    private String name;
	
    private String formatName;
    private Double duration;

    @Override
	public String toString() {
		return "VideoInfo [ name=" + name + ", formatName=" + formatName + ", duration=" + duration + "]";
	}

	public VideoInfo() {}
	  
    public void setName(String name) {
        this.name = name;
    }
     
    public String getName() {
        return name;
    }

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}
}
