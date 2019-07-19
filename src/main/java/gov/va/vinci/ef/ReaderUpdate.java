/*******************************************************************************
 * Copyright (c) 2014 Prakash. All rights reserved.
 * This program and the accompanying materials are made available under the terms of the new BSD license which
 * accompanies this distribution, and is available at http://www.opensource.org/licenses/bsd-license.html Contributors:
 * Eliza Chan - initial API and implementation
 ******************************************************************************/
package gov.va.vinci.ef;

import java.awt.color.CMMException;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * This class populate a target table using data from a source table using ETL_DATE for a given concept.
 */
public class ReaderUpdate {
	
	public static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(gov.va.vinci.leo.tools.LeoUtils.getRuntimeClass().toString());

	private String url = null;
	private String username = null;
	private String password = null;
	
	private Properties props = new Properties();

	private FileHandler fh;

	private void init(String dbPropertiesFile, String readerConfigFile ) {

		// get today's date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		try {
			props.load(new FileInputStream(dbPropertiesFile));
		} catch (FileNotFoundException e) {
			log.info("DB Properties File not found error: " + e);
		} catch (IOException e) {
			log.info("IOException error: " + e);
		}
		
		// database properties
		url = props.getProperty("Db.url");
		username = props.getProperty("Db.username");
		password = props.getProperty("Db.password");

		// Go ahead and run 
		if (!url.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
			run();
		}

	}
	
	
	// Begin run
	private void run() {
		
		// get last date for Pathology reports run		
		String table = "DM_NLP.dbo.LEO_RUNS";
		int concept_id = 1;
		Boolean dataExist = false;
		
		String last_run = get_last_run(table, concept_id);
				 
		if (!last_run.isEmpty()) {
			// Check if data exist to run Leo on the View for pathology reports. 
			// For testing DM_NLP.PATHOLOGY_TNM_TRAIN table used.
			dataExist = check_new_data("DM_NLP.PATHOLOGY_TNM_TRAIN", last_run);
			
			if (dataExist == true) {
				
				// Modify reader config file with the new etl_date
				
				
				// Check leo output to see if the current run completes.
				
				
				// Update last run  DM_NLP.dbo.LEO_RUNS with the last run
				Boolean done = false;
				done = update_last_run ("DM_NLP.dbo.LEO_RUNS", last_run);
				if(done == true) {
					// Update LEO_RUNS with the current date 
					
				}
								
			}
			
		}

	}
	
	// Transfer data from a source table to target table.
	public Boolean update_last_run(String from_tbl, String dt) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		Boolean isDone = false;
		
		String sql = "";
		sql = "select * from " + from_tbl + " where ETL_DATE > '" + dt + "'"  ;
		
		// System.out.println(sql);

		try {

			conn = DriverManager.getConnection(url, username, password);

			stmt = conn.createStatement();

			int count = stmt.executeUpdate(sql);
			
			isDone = (count > 0 )? true : false;
			
		} catch (Exception Ex) {
			System.out.print(Ex.getMessage());

		} finally {
			try {
				result.close();
			} catch (Exception e) {
			}
			try {
				stmt.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return isDone;

	}
		
	// Get last run date for a concept
	public String get_last_run(String tbl, int concept_id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;

		ArrayList<HashMap<String, Object>> records = new ArrayList<HashMap<String, Object>>();

		String run_date ="";


		String sql = "";
		sql = "select RUN_DATE from " + tbl + " WHERE ID = " + concept_id ;
		
		// System.out.println(sql);

		try {

			conn = DriverManager.getConnection(url, username, password);

			stmt = conn.createStatement();

			result = stmt.executeQuery(sql);

			while (result.next()) {
				run_date = result.getString("RUN_DATE");
			}

		} catch (Exception Ex) {
			System.out.print(Ex.getMessage());

		} finally {
			try {
				result.close();
			} catch (Exception e) {
			}
			try {
				stmt.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return run_date;

	}
	
	// Check, if new data available to retrieve. 
	public Boolean check_new_data(String tbl, String dt) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;

		int total = 0;
		Boolean isExist = false; 

		String sql = "";

		sql = "select count(*) as total from " + tbl + " where ETL_DATE > '" + dt + "'";

		try {

			conn = DriverManager.getConnection(url, username, password);

			stmt = conn.createStatement();

			result = stmt.executeQuery(sql);

			while (result.next()) {
				total = result.getInt("total");
			}

		} catch (Exception Ex) {
			System.out.print(Ex.getMessage());

		} finally {
			try {
				result.close();
			} catch (Exception e) {
			}
			try {
				stmt.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		isExist =  (total > 0 )? true : false;
		
		return isExist;

	}

	// Check if string a numeric value.
	private boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Main method
	 * 
	 * @param args
	 *            command-line arguments
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String... args) {
		
		if (args.length < 2) {
			System.out.println("Usage: java ReaderUpdate [db properties filename] [reader config file]");
		}else {
			new ReaderUpdate().init(args[0], args[1]);  
		}
				
	}
}
