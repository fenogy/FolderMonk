package com.coppr;

import static java.nio.file.FileVisitResult.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.StringTokenizer;
import static java.nio.file.StandardCopyOption.*;

public class PrintFiles extends SimpleFileVisitor<Path> {

	public static int COUNT = 1;
	public static int TRAVERSE = 2;
	private int action;
	private int percentage;
	
	public int getPercentage() {
		return percentage;
	}

	public int getFileCount() {
		return fileCount;
	}
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	private int fileCount = 0;
	private int totalCount = 100;
	public PrintFiles(int action) {
		
		this.action = action;
		
	}
    // Print information about
    // each type of file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
    	
        if (attr.isSymbolicLink()) {
            //System.out.format("Symbolic link: %s ", file);
        } else if (attr.isRegularFile()) {
        	
        	if(action == TRAVERSE) {
        		checkFileName(file);
        		fileCount++;
        		if(totalCount > 0)
        		percentage = (int)  (fileCount*100/totalCount);
        	}else {
        		fileCount++;
        	}
        	
            //System.out.format("Regular file: %s ", file);
        } else {
            //System.out.format("Other: %s ", file);
        }
        //System.out.println("(" + attr.size() + "bytes)");
        return CONTINUE;
        
    }

    // Print each directory visited.
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
    	
        System.out.format("Directory: %s%n", dir);
        return CONTINUE;
    }

    // If there is some error accessing
    // the file, let the user know.
    // If you don't override this method
    // and an error occurs, an IOException 
    // is thrown.
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }
    
    static void copyFile(Path source, Path target) {
        CopyOption[] options =  new CopyOption[] { COPY_ATTRIBUTES, REPLACE_EXISTING };
           
            //new CopyOption[] { REPLACE_EXISTING };
        if (Files.notExists(target)) {
            try {
                Files.copy(source, target, options);
            } catch (IOException x) {
                System.err.format("Unable to copy: %s: %s%n", source, x);
            }
        }
    }
    private String checkFileName(Path File) {
    	
    	String fileName = "";
    	String directory = "";
    	String fileNameNoExtension = "";
    	String folderName ="";
    	String fullName = "";
    	
    	fullName = File.toString();
    	fileName = File.getFileName().toString(); 
    	String extension = "";
    	
    	int x = Math.max(fullName.lastIndexOf('/'), fullName.lastIndexOf('\\'));
    	int i = fileName.lastIndexOf('.');
    	int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

    	//int dirEnd = fileName.lastIndexOf('\\');
    	if(x>0) {
    		
//        	directory = fullName.substring(0,x);
//        	directory = directory + "\\test\\" + fileName ;

        	
        	
        	if (i > p) {
        	    extension = fileName.substring(i+1);
        	    fileNameNoExtension = fileName.substring(0, i);
        	    int dashIndex = fileName.lastIndexOf('-');
        	    
        	    if(dashIndex > 0) {
        	    	
        	    	int length =  fileNameNoExtension.length();
        	    	if((length - dashIndex) == 3 || (length - dashIndex) == 4 ) {
        	    	
        	    		folderName = fileNameNoExtension.substring(0, dashIndex);
        	        	if(extension.matches("jpg") && folderName !="") {
        	        		
        	        		//StringTokenizer st = new StringTokenizer(fileName);
        	        		//System.out.println(fileNameNoExtension);
        	        		directory = "D:\\allProjects\\Comic\\ImageSet\\" + folderName + "\\";
        	            	Path dir = Paths.get(directory);
        	            	
        	            	try {
        	            		Files.createDirectories(dir);
        	            	}catch (IOException xx) {
        	                    System.err.format("Unable to create: %s: %s%n", dir, xx);
        	                }
        	        		
        	        		
        	        		//directory = "D:\\allProjects\\Comic\\ImageSet\\" + folderName + "\\" + fileName;
        	            	Path copyFile = Paths.get("D:\\allProjects\\Comic\\ImageSet\\" + folderName + "\\" + fileName);
        	        		copyFile(File, copyFile);
        	        		System.out.format("Complete Percentage: %d Directory: %s Name Length: %d Dash Index: %d To Folder Name: %s \r\n",percentage,File,length,dashIndex,folderName);
        	            	
        	        	}else {
        	        		return "NOMATCH";
        	        	}
        	    	}
        	    	
        	    }
        	}
    	}

    	

    	
    	
    	
    	return "";
    }
}
