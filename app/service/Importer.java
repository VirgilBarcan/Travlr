package service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import play.db.DB;

public class Importer {
	private class Parameter {
		public Object defaultValue;
		public int csvIndex;
		public String format;
		
		Parameter(){
			defaultValue = null;
			csvIndex = 0;
			format = null;
		}
		
		void setDefaultValue(Object o) {this.defaultValue = o;}
		void setCsvIndex(int pos) {this.csvIndex = pos;}
		void setFormat(String format) {this.format = format;}
		
		public String getValue(String tokens[]){
			String value = null;
			
			if (csvIndex == 0)
				value = defaultValue.toString();
			if (tokens.length>=csvIndex)
				value = tokens[csvIndex-1];
			if (defaultValue!=null)
				value = defaultValue.toString();
			if (value!=null){
				if (format!=null)
					value = String.format(format, value);
				return value;
			}
			return "NULL";
		}
	}
	
	private String tableName;
	private ArrayList<Parameter> order;
	
	public Importer(){
		tableName = null;
		order = null;
	}
	
	public void setTableName(String tableName){
		if (DatabaseLayer.isTable(tableName)){
			this.tableName = tableName;
		}
	}
	
	public void setOrder(int ... order){
		if (order.length>0){
			this.order = new ArrayList<Parameter>();
			for (int index : order){
				Parameter p = new Parameter();
				p.setCsvIndex(index);
				this.order.add(p);
			}
		}
	}
	
	public void setArgument(int index, Object defaultValue, int csvIndex, String format){
		if (order.size()>=index){
			order.get(index).setDefaultValue(defaultValue);
			order.get(index).setCsvIndex(csvIndex);
			order.get(index).setFormat(format);
		}
	}
	
	public void importFile(String path){
		Connection con = DB.getConnection();
		Statement stmt = null;
		Scanner scanner = null;
		String query = "insert into %s values(%s)";
		try {
			scanner = new Scanner(new FileReader(path));
			stmt = con.createStatement();
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				String tokens[] = line.split(",");
				StringBuilder values = new StringBuilder();
				for (Parameter p : order){
					if (values.length()>0){
						values.append(", " + p.getValue(tokens));
					}
					else{
						values.append(p.getValue(tokens));
					}
				}
				stmt.executeQuery(String.format(query, tableName, values));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
