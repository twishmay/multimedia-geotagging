package gr.iti.mklab.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import gr.iti.mklab.util.EasyBufferedReader;
import gr.iti.mklab.util.TextUtil;

/**
 * Data manager
 * @author gkordo
 *
 */
public class DataManager {

	static Logger logger = Logger.getLogger("gr.iti.mklab.tools.DataManager");

	// return a set contain the image IDs of the provided dataset
	public static Set<String> getSetOfImageIDs(String file){

		Set<String> usersIncludedInFile = new HashSet<String>();

		EasyBufferedReader reader = new EasyBufferedReader(file);

		String input;

		logger.info("images contained in file " + file);
		while ((input= reader.readLine())!=null){
			usersIncludedInFile.add(input.split("\t")[1]);			
		}
		logger.info(usersIncludedInFile.size()+" total images included in file");
		reader.close();

		return usersIncludedInFile;
	}

	// return a set contain the individual tags of the provided dataset
	public static Set<String> getSetOfTerms(String file){

		EasyBufferedReader reader = new EasyBufferedReader(file);
		Set<String> termsIncludedInFile = new HashSet<String>();
		
		String line;

		logger.info("deterim the diffrent tags contained in file " + file);
		while ((line= reader.readLine())!=null){

			Set<String> terms = new HashSet<String>();
			TextUtil.parse(line.split("\t")[10], terms);
			TextUtil.parse(line.split("\t")[8], terms);

			termsIncludedInFile.addAll(terms);

		}
		logger.info(termsIncludedInFile.size()+" total tags included in file");
		reader.close();

		return termsIncludedInFile;
	}

	// return a set contain the different users in the provided dataset
	public static Set<String> getSetOfUserID (String file){

		Set<String> usersIncludedInFile = new HashSet<String>();

		EasyBufferedReader reader = new EasyBufferedReader(file);

		String input;

		logger.info("deterim the diffrent users contained in file " + file);
		while ((input= reader.readLine())!=null){
			usersIncludedInFile.add(input.split("\t")[3]);			
		}
		logger.info(usersIncludedInFile.size()+" total users included in file");
		reader.close();

		return usersIncludedInFile;
	}

	// create a temporary file containing a specific file
	public static void createTempFile(String dir, String file) throws IOException {
		logger.info("create temporary file for " + file);
		new File(dir + "temp/").mkdir();
		Files.copy(new File(dir + file).toPath(), new File(dir + "temp/" +
				file.split("/")[file.split("/").length-1]).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
	}

	// delete the temporary file
	public static void deleteTempFile(String dir) throws IOException {
		FileUtils.cleanDirectory(new File(dir + "/temp"));
		FileUtils.forceDelete(new File(dir + "temp"));
		logger.info("temporary file deleted");
	}
}
