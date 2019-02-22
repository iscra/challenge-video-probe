package com.example.videoinfo.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
 
@Entity
@Table
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames= "name")}) for testing
public class VideoInfo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @NotNull(message = "Name is required")
    @Size(min = 1, max=20, message = "Name must be between 1 and 20 characters long")
    private String name;
	
    private String formatName;
    private Double duration;

    @Override
	public String toString() {
		return "VideoInfo [id=" + id + ", name=" + name + ", formatName=" + formatName + ", duration=" + duration + "]";
	}

	public VideoInfo() {}
	  
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
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
