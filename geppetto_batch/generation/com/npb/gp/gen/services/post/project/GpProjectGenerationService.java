package com.npb.gp.gen.services.post.project;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.GpBaseGenerationService;

@Component("GpProjectGenerationService")
public class GpProjectGenerationService extends GpBaseGenerationService{
	
	
	@Override
	public void generate(IGpGenManager gen_manager) throws Exception {
		check_project_type(gen_manager);
	}
}
