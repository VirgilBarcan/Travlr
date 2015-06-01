package service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
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
		
		public String getValue(Object tokens[]){
			String value = null;
			
			if (csvIndex == 0)
				value = defaultValue.toString();
			else if (tokens.length>=csvIndex)
				value = tokens[csvIndex-1].toString();
			else if (defaultValue!=null)
				value = defaultValue.toString();
			if (value!=null){
				value = strip(value, " \t\n\"'");
				if (format!=null)
					value = String.format(format, value.toString());
				return value;
			}
			return "NULL";
		}
	}
	
	private String procName;
	private ArrayList<Parameter> order;
	
	public Importer(){
		procName = null;
		order = null;
	}
	
	public void setProcName(String procName){
		if (DatabaseLayer.isTable(procName)){
			this.procName = procName;
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

	public Object[] split(String str, char sep){
		boolean in = false;
		
		ArrayList<StringBuilder> tokens = new ArrayList<StringBuilder>();
		for (int i=0; i<str.length(); ++i){
			if (str.charAt(i)=='"'){
				in = !in;
				continue;
			}
			if (in){
				tokens.get(tokens.size()-1).append(str.charAt(i));
			}
			else{
				if (str.charAt(i)==sep){
					tokens.add(new StringBuilder());
					continue;
				}
				if (tokens.isEmpty()){
					tokens.add(new StringBuilder());
				}
				tokens.get(tokens.size()-1).append(str.charAt(i));
			}
		}
		
		ArrayList<String> stringTokens = new ArrayList<String>();
		for (int i=0; i<tokens.size(); ++i){
			stringTokens.add(tokens.get(i).toString());
		}
		return stringTokens.toArray();
	}
	
	public void importFile(String path){
		if (procName==null || path==null)
			return;
		
		Connection con = null;
		ArrayList<SQLType> procedureTypes = new ArrayList<SQLType>();
		String query = "call %s (%s)";
		try{
			con = DB.getConnection();
			DatabaseMetaData metadata = con.getMetaData();
			ResultSet columns = metadata.getProcedureColumns(null, null, procName.substring(procName.indexOf('.')+1), null);
			StringBuilder queryValues = new StringBuilder();
			for (int i=0; columns.next(); ++i){
				if (columns.getString("TYPE_NAME").equals("VARCHAR2")){
					//order.get(i).setFormat("'%s'");
				}
				procedureTypes.add(JDBCType.values()[columns.getInt("DATA_TYPE")]);
				if (queryValues.length()>0){
					queryValues.append(", ?");
				}
				else{
					queryValues.append("?");
				}
			}
			//System.out.println(procedureTypes);
			query = String.format(query, procName, queryValues);
		}
		catch (SQLException e){
			e.printStackTrace();
			return;
		}
		
		try {
			Scanner scanner = new Scanner(new FileReader(path));
			CallableStatement stmt = con.prepareCall(query);
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				Object tokens[] = split(line, ',');
				System.out.println(line);
				if (order==null){
					//values.append(line);
				}
				else{
					for (int i=0; i<order.size(); ++i){
						stmt.setObject(i+1, order.get(i).getValue(tokens));
					}
				}
				stmt.execute();
			}
			stmt.execute("commit");
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
