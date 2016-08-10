package com.npb.gp.gen.json.mappers;

import com.npb.gp.gen.json.mappers.dockerinfo.DockerImage;

public class DockerInfo {
	private String user;
	private DockerImage images[];
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public DockerImage[] getImages() {
		return images;
	}
	public void setImages(DockerImage[] images) {
		this.images = images;
	}
	
	
}
