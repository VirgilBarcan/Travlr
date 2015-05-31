package service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
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

		private String strip(String str, String chars){
			int l, r;
			
			for (l=0; l<str.length() && chars.indexOf(str.charAt(l))!=-1; ++l);
			for (r=str.length(); r>l && chars.indexOf(str.charAt(r-1))!=-1; --r);
			
			return str.substring(l, r);
		}
		
		public String getValue(String tokens[]){
			String value = null;
			
			if (csvIndex == 0)
				value = defaultValue.toString();
			else if (tokens.length>=csvIndex)
				value = tokens[csvIndex-1];
			else if (defaultValue!=null)
				value = defaultValue.toString();
			if (value!=null){
				value = strip(value, " \t\n\"'");
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
			order.get(index-1).setDefaultValue(defaultValue);
			order.get(index-1).setCsvIndex(csvIndex);
			order.get(index-1).setFormat(format);
		}
	}
	
	public void importFile(String path){
		if (tableName==null || path==null)
			return;
		
		Connection con = null;
		DatabaseMetaData metadata = null;
		ResultSet columns = null;
		Statement stmt = null;
		Scanner scanner = null;
		String query = "insert into %s values(%s)";
		try {
			con = DB.getConnection();
			metadata = con.getMetaData();
			columns = metadata.getColumns(null, null, tableName, null);
			for (int i=0; i<order.size(); ++i){
				columns.next();
				if (columns.getString("TYPE_NAME").equals("VARCHAR2")){
					order.get(i).setFormat("'%s'");
				}
			}
			
			
			scanner = new Scanner(new FileReader(path));
			stmt = con.createStatement();
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				String tokens[] = line.split(",");
				StringBuilder values = new StringBuilder();
				if (order==null){
					values.append(line);
				}
				else{
					for (Parameter p : order){
						if (values.length()>0){
							values.append(", " + p.getValue(tokens));
						}
						else{
							values.append(p.getValue(tokens));
						}
					}
				}
				stmt.execute(String.format(query, tableName, values));
			}
			stmt.execute("commit");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
