package com.example.application.functionalities;

import lombok.Getter;
import org.apache.log4j.Logger;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CreatingSQLFile {

	private static final Logger logger = Logger.getLogger(CreatingSQLFile.class);

	@Getter
	File file;
	String homeDir = FileSystemView.getFileSystemView().getHomeDirectory().toString();
	public Path locationOfSavingFiles = Paths.get(homeDir + "Users" + File.separator + File.separator
		+ "lachh" + File.separator + File.separator + "Desktop");


	public void create(List<String> list, String wersjaRWA) throws IOException {

		locationOfSavingFiles = Paths.get(locationOfSavingFiles.toString() + File.separator + File.separator
			+ "ProcedureRWATable" + getFileIteration() + ".sql");
		file = new File(locationOfSavingFiles.toUri());

		FileWriter writer = new FileWriter(file.getName());
		BufferedWriter bWriter = new BufferedWriter(writer);

		addQueryToFile(bWriter, "BEGIN WORK;");
		addQueryToFile(bWriter, "INSERT INTO wersje_rwa(nazwa, aktywny,  domyslny, data_utworzenia, utworzony_przez ) "
			+ "VALUES ('" + wersjaRWA + "', 'T', 'N', current_timestamp::timestamp(0), 1);");

		list.stream().forEach(s -> {
			try {
				addQueryToFile(bWriter, s);
			} catch (IOException e) {
				logger.error("The file write operation failed: " + e.getMessage());
				file.delete();
				e.printStackTrace();
			}
		});

		addQueryToFile(bWriter, "SELECT konfiguruj_rwa(currval('wersje_rwa_id_wr_seq')); ");
		addQueryToFile(bWriter, "COMMIT;");

		bWriter.close();
		writer.close();
	}

	void addQueryToFile(BufferedWriter writer, String query) throws IOException {
		writer.write(query);
		writer.newLine();
	}

	public int getFileIteration() {
		boolean oldFile = false;
		int fileIteration = 0;
		do {
			fileIteration++;
			oldFile = Files.exists(Paths.get("ProcedureRWATable" + fileIteration + ".sql"), LinkOption.NOFOLLOW_LINKS);

		} while (oldFile);
		return fileIteration;
	}

}
