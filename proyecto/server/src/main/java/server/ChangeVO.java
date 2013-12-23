package server;

import java.sql.Date;

public class ChangeVO {
	
	private int idData;
	private int idChange;
	private Date date;
	private boolean change;
	
	public ChangeVO(int idData, int idChange, Date date, boolean change){
		this.idData = idData;
		this.idChange = idChange;
		this.date = date;
		this.change = change;
	}

	public int getIdData(){
		return idData;
	}
	
	public int getIdChange(){
		return idChange;
	}
	
	public Date getDate(){
		return date;
	}
	
	public boolean getChange(){
		return change;
	}
}
