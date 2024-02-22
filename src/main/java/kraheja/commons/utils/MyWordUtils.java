package kraheja.commons.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

//import java.io.FileInputStream;
//import java.io.FileOutputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MyWordUtils {
	
	INSTANCE;
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public File replplaceholdersworddoc(String templateFileWithPath, String[][] fieldsList ) throws InvalidFormatException {

		try {
			
				XWPFDocument wordFile = new XWPFDocument(OPCPackage.open(templateFileWithPath));
		       
//				LOGGER.info("TempFile :: {}", wordFile);
			
	        // Iterate over the result set and replace placeholders with data
	        for (int i = 0; i < fieldsList.length; i++) {

	        	// Replace placeholders with data (for example)
	            String text = fieldsList[i][0];
	            text = text.replace(fieldsList[i][0], fieldsList[i][1]);
	        }

	        // Save the document
	        wordFile.write(new FileOutputStream("d:\\temp\\output.docx"));

			
	        } catch (IOException e) {
				System.out.println("File IO error:");	        	
	            e.printStackTrace();
	        }
		return null;
	}

}
