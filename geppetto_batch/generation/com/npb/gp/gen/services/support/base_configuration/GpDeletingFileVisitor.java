package com.npb.gp.gen.services.support.base_configuration;

import java.nio.file.SimpleFileVisitor;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 06/14/2014</br>
 * @since .35</p>  
 *
 *The purpose of this class is to support the recursive delete of directories</br>
 *and files</p>
 *
 */
public class GpDeletingFileVisitor extends SimpleFileVisitor<Path> {
	String project_folder;
	private String file_separator = System.getProperty("file.separator");
	public GpDeletingFileVisitor(String project_folder) {
		super();
		this.project_folder = project_folder;
	}

	private Log log = LogFactory.getLog(getClass());
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes)
	        throws IOException {
    	log.debug("In GpDeletingFileVisitor - Deleting Regular File: " + file.getFileName());
    	
    	int index_of_git_folder = file.toString().indexOf(".git"); //don't delete content inside .git folder
    	String main_folder = project_folder + this.file_separator;
    	int index_of_project_folder = file.toString().indexOf(main_folder);
    	if((index_of_project_folder+main_folder.length()) == index_of_git_folder){
    		return FileVisitResult.CONTINUE;
    	}
    	Files.delete(file);
    	return FileVisitResult.CONTINUE;
	}
	 
	@Override
	    public FileVisitResult postVisitDirectory(Path directory, IOException ioe)
	            throws IOException {
    	String main_folder = project_folder;
    	int index_of_project_folder = directory.toString().indexOf(main_folder);
    	int index_of_git_folder = directory.toString().indexOf(".git");
    	
    	if((index_of_project_folder+main_folder.length()+1) == index_of_git_folder){//don't delete .git/ folder 
    		return FileVisitResult.CONTINUE;
    	}
    	if((index_of_project_folder+main_folder.length()) == directory.toString().length()){//don't delete project folder
    		return FileVisitResult.CONTINUE;
    	}
    	Files.delete(directory);
		return FileVisitResult.CONTINUE;
	    }
	 
	@Override
	    public FileVisitResult visitFileFailed(Path file, IOException ioe)
	            throws IOException {
			log.debug("GpDeletingFileVisitor - Something went wrong while working on : " + file.getFileName());
	        ioe.printStackTrace();
	        return FileVisitResult.CONTINUE;
	    }
}
