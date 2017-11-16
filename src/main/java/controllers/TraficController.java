package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;

import controllers.exceptions.DataException;
import model.entities.Line.LineType;

public class TraficController {
	
	public static class TraficInfo{
		private String line;
		private String slug;
		private String message;
		public TraficInfo(String line, String slug, String message) {
			super();
			this.line = line;
			this.slug = slug;
			this.message = message;
		}
		public String getLine() {
			return line;
		}
		public String getSlug() {
			return slug;
		}
		public String getMessage() {
			return message;
		}
		
	}

	public static TraficInfo checkLineTrafic(LineType type, String name ) throws DataException {
		URL url;
		InputStream is = null;
		BufferedReader br;
		String line;
		String tmp="";
		String type1;
		try {
			switch (type) {
			case rer:
				type1 = "rers";
				break;
			case metro:
				type1 = "metros";
				break;
			case tramway:
				type1 = "tramways";
				break;
	
			default:
				throw new DataException("Invalid line type");
			}

			url = new URL("https://api-ratp.pierre-grimaud.fr/v3/traffic/"+type1+"/"+name);
			is = url.openStream();  
			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null) {
				tmp+=line;
			}
			JSONObject obj = new JSONObject(tmp);
			JSONObject result = obj.getJSONObject("result");

			return new TraficInfo(result.getString("line"), result.getString("slug"), result.getString("slug"));
			
		} catch ( IOException mue) {
			mue.printStackTrace();
			throw new DataException("Server couldn't get information about the line's traffic");
		}  finally {
			try {
				if (is != null) is.close();
			} catch (final IOException ioe) {

			}
			
		}
		

	}
	public static void main(String[] args) {
		
	}
}
