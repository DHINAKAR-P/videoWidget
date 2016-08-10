package com.npb.gp.gen.workers;

import com.npb.gp.gen.services.server.node.GpServerNodeExpressGenService;

public class GpGenNodeServerExpressBaseWorker extends GpGenBaseWorker{
	private GpServerNodeExpressGenService gen_service;

	public GpServerNodeExpressGenService getGen_service() {
		return gen_service;
	}

	public void setGen_service(GpServerNodeExpressGenService gen_service) {
		this.gen_service = gen_service;
	}
	
	
}
