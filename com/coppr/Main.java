package com.coppr;

import static java.nio.file.FileVisitResult.*;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.nio.file.Files;
import java.nio.file.AccessMode;




public class Main {

	//public static final FileVisitOption FOLLOW_LINKS;
	
	static Path startingDir = Paths.get("D:\\allProjects\\Comic\\Comic\\slcomics.files.wordpress.com");

	EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("running here");
		PrintFiles pf = new PrintFiles(PrintFiles.COUNT);
		try {
			Files.walkFileTree(startingDir, pf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.format("Total image files to traverse: %d \r\n\r\n",pf.getFileCount());
		PrintFiles pf2 = new PrintFiles(PrintFiles.TRAVERSE);
		pf2.setTotalCount(pf.getFileCount());
		try {
			Files.walkFileTree(startingDir, pf2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

