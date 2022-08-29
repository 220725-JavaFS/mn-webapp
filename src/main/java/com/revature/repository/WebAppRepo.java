package com.revature.repository;

import java.util.Hashtable;

import com.revature.models.*;


public interface WebAppRepo {

	Table getTable();

	boolean addRow(Hashtable<String, VariableObject> newRow);

	boolean deleteRow(Hashtable<String, VariableObject> toRemove);

	void updateRow(Hashtable<String, VariableObject> toReplace, Hashtable<String, VariableObject> newRow);


}
