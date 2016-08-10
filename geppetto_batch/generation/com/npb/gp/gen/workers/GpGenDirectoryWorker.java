package com.npb.gp.gen.workers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.npb.gp.gen.constants.GpGenConstants;

/**
 * Date Created: 15/09/2015
 * @author Reinaldo
 *	Generates directories copying them from a base path 
 */
@Component("GpGenDirectoryWorker")
public class GpGenDirectoryWorker extends GpGenBaseWorker {
		

	public void copy_directory(File base_directory, File final_directory)
			throws Exception {
		if(base_directory.exists()){
			try {
				if (base_directory.isDirectory()) {
					if (!final_directory.exists())
						final_directory.mkdir();
	
					String[] chidren = base_directory.list();
					for (int i = 0; i < chidren.length; i++) {
						copy_directory(new File(base_directory, chidren[i]),
								new File(final_directory, chidren[i]));
					}
				} else {
					Files.copy(base_directory.toPath(), final_directory.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
			} catch (Exception e) {
				throw e;
			}
		}
		else{
			System.out.println("This directory doesn't exist");
		}
	}
	
	public void handle_module_import(File base_directory, File final_directory) throws Exception{
		if(base_directory.exists()){
			try {
				if (base_directory.isDirectory()) {
					if (!final_directory.exists())
						final_directory.mkdir();
	
					String[] chidren = base_directory.list();
					for (int i = 0; i < chidren.length; i++) {
						
						handle_module_import(new File(base_directory, chidren[i]),
								new File(final_directory, chidren[i]));
					}
				} else {//is a file
					Files.copy(base_directory.toPath(), final_directory.toPath(), StandardCopyOption.REPLACE_EXISTING);
					String content = IOUtils.toString(new FileInputStream(final_directory), "UTF-8");
					String gp_user_package = this.derived_configs.get("domain_core_package_name").getValue() + ".GpUser";
					content = content.replaceAll("<GP_GPUSER_PACKAGE>", gp_user_package);
					
					String gp_authority_package = this.derived_configs.get("domain_core_package_name").getValue() + ".GpAuthority";
					content = content.replaceAll("<GP_GPAUTHORITY_PACKAGE>", gp_authority_package);
					
					String gp_base_service_package = this.derived_configs.get(GpGenConstants.APP_SERVICES_PACKAGE).getValue() + ".GpBaseService";
					content = content.replaceAll("<GP_GPBASE_SERVICE_PACKAGE>", gp_base_service_package);
					
					IOUtils.write(content, new FileOutputStream(final_directory), "UTF-8");
				}
			} catch (Exception e) {
				throw e;
			}
		}
		else{
			System.out.println("This directory doesn't exist");
		}
	}
}
