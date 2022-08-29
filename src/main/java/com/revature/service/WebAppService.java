package com.revature.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.revature.models.*;
import com.revature.repository.*;

public class WebAppService {
	
	WebAppRepo repo;
	
	public WebAppService() {
		repo = new WebAppRepoImp();
	}

	public boolean writeTableJson(PrintWriter out) {
		Table table = repo.getTable();
		if(table == null) {
			return false;
		}
		String json = table.toJson();
		out.append(json);
		return true;
	}

	public void addRowFromJson(BufferedReader read) {
		StringBuilder json = new StringBuilder();
		String nextLine;
		try {
			while((nextLine = read.readLine()) != null) {
				json.append(nextLine);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		repo.addRow(fromJson(json.toString()));
		
	}

	public void updateRowFromJson(BufferedReader read) {
		StringBuilder json = new StringBuilder();
		String nextLine;
		try {
			while((nextLine = read.readLine()) != null) {
				json.append(nextLine);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		String toParse = json.toString();
		toParse = toParse.substring(toParse.indexOf('{'),toParse.lastIndexOf('}')).trim();
		String[] rows = toParse.split("\\}\\p{Space}*,\\p{Space}*\\{");
		Hashtable<String,VariableObject> toReplace = fromJson(rows[0]+"}");
		Hashtable<String,VariableObject> newRow = fromJson("{" + rows[1]);
		repo.updateRow(toReplace,newRow);
	}

	public void deleteRowFromJson(BufferedReader read) {
		StringBuilder json = new StringBuilder();
		String nextLine;
		try {
			while((nextLine = read.readLine()) != null) {
				json.append(nextLine);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		repo.deleteRow(fromJson(json.toString()));
	}
	
	private Hashtable<String,VariableObject> fromJson(String read){
		Hashtable<String,VariableObject> out = new Hashtable<String,VariableObject>();
		String toParse = read.toString();
		String fieldPattern = "\\p{Space}*[^,]+\\p{Space}*:\\p{Space}*[^,]+\\p{Space}*";
		Pattern fieldsFind = Pattern.compile(fieldPattern);
		toParse = toParse.strip();
		toParse = toParse.substring(toParse.indexOf("{"),toParse.lastIndexOf("}")).strip();
		Matcher m = fieldsFind.matcher(toParse);
		while(m.find()) {
			String[] item = m.group().split(":");
			item[0] = item[0].substring(item[0].indexOf("\"")+1,item[0].lastIndexOf("\""));
			item[1] = item[1].strip();
			try {
				int value = Integer.parseInt(item[1]);
				out.put(item[0], new VariableObject(value));
			} catch (NumberFormatException e) {
				item[1] = item[1].trim().substring(item[1].indexOf("\"")+1,item[1].lastIndexOf("\""));
				out.put(item[0], new VariableObject(item[1]));
			}
		}
		return out;
	}
}
