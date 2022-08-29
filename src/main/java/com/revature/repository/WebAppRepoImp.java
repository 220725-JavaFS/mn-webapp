package com.revature.repository;

import java.util.Hashtable;

import com.revature.models.Table;
import com.revature.models.VariableObject;
import com.revature.models.DatabaseInfo;


import com.revature.controller.OrmController;

public class WebAppRepoImp implements WebAppRepo {
	
	OrmController orm;
	
	public WebAppRepoImp() {
		orm = new OrmController();
		orm.tryConnect(new DatabaseInfo("javafs-revature.c9geusqxvm75.us-west-2.rds.amazonaws.com", "project1", "postgres", "password"));
	}

	@Override
	public Table getTable() {
		return orm.getTableByName("movies");
	}

	@Override
	public void addRow(Hashtable<String, VariableObject> newRow) {
		orm.addToTable("movies", newRow);
	}

	@Override
	public void deleteRow(Hashtable<String, VariableObject> toRemove) {
		orm.removeMatchingRow("movies", toRemove);
	}

	@Override
	public void updateRow(Hashtable<String, VariableObject> toReplace, Hashtable<String, VariableObject> newRow) {
		orm.updateRow("movies", toReplace, newRow);
	}

}
