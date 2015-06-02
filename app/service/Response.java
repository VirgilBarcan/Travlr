package service;

public class Response {
	String[] columnNames;
	Object[][] data;
	
	private boolean validateData(String[] columnNames, Object[][] data){
		int length = columnNames.length;
		
		if (length==0)
			return false;
		if (data==null)
			return false;
		for (int i=0; i<data.length; ++i)
			if (data[i].length!=length)
				return false;
		return true;
	}
	
	public Response(String[] columnNames, Object[][] data) {
		this.columnNames = null;
		this.data = null;
		if (validateData(columnNames, data)){
			this.columnNames = columnNames;
			this.data = data;
		}
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public Object[][] getData() {
		return data;
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		
		if (columnNames!=null){
			for (int j=0; j<columnNames.length; ++j)
				str.append(columnNames[j] + " ");
			str.append("\n");
		}
		if (data!=null){
			for (int i=0; i<data.length; ++i){
				for (int j=0; j<data[i].length; ++j)
					str.append(data[i][j].toString() + " ");
				str.append("\n");
			}
		}
		
		return str.toString();
	}
}
