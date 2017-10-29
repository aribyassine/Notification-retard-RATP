package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import controllers.exceptions.DataException;

/**
 * @author Mohamed T. KASSAR
 */
public class LinesController {

	private static String lines = null;

	public String getLinesOnJSON() throws DataException {
		if (lines == null) {
			updateLinesFromServer();
		}
		return lines;
	}

	public static void updateLinesFromServer() throws DataException {
		URL url;
		try {
			url = new URL("https://api-ratp.pierre-grimaud.fr/v3/lines");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;
				Files.deleteIfExists(Paths.get("Lines.json"));

				while ((output = br.readLine()) != null) {
					Files.write(Paths.get("Lines.json"), output.replaceAll("é", "e").getBytes(),
							StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
				}
				conn.disconnect();
			}
			updateLinesFromFile();
		} catch (IOException e) {
			updateLinesFromFile();
		}
	}

	public static void updateLinesFromFile() throws DataException {
		try {
			lines = "";
			Files.readAllLines(Paths.get("Lines.json")).forEach(line -> lines += line);
		} catch (IOException e) {
			throw new DataException("Unable to load lines");
		}
	}

	
	public static void startLinesUpdater() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, (Runnable r) -> {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		});
		
		scheduler.scheduleAtFixedRate(() -> {
			try {
				updateLinesFromServer();
			} catch (DataException e) {
				// TODO SMS
			}
		}, 1, 1, TimeUnit.DAYS);
	}
}
