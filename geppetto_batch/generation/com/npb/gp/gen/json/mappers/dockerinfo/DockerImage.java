package com.npb.gp.gen.json.mappers.dockerinfo;

public class DockerImage {

	private String userdir;
	private String port;
	private String contname;
	private String mysqlrootpw;
	private String dockerimage;
	private String linkdb;
	
	public String getUserdir() {
		return userdir;
	}
	public void setUserdir(String userdir) {
		this.userdir = userdir;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getContname() {
		return contname;
	}
	public void setContname(String contname) {
		this.contname = contname;
	}
	public String getMysqlrootpw() {
		return mysqlrootpw;
	}
	public void setMysqlrootpw(String mysqlrootpw) {
		this.mysqlrootpw = mysqlrootpw;
	}
	public String getDockerimage() {
		return dockerimage;
	}
	public void setDockerimage(String dockerimage) {
		this.dockerimage = dockerimage;
	}
	public String getLinkdb() {
		return linkdb;
	}
	public void setLinkdb(String linkdb) {
		this.linkdb = linkdb;
	}
	
}
