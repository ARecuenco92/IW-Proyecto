package server;

import java.sql.Timestamp;

/**
 * Clase que representa la información correspondiente a un cambio en una página.
 */
public class ChangeVO {
	
	private int idData;
	private int idChange;
	private Timestamp date;
	private boolean change;
	
	public ChangeVO(int idData, int idChange, Timestamp date, boolean change){
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
	
	public Timestamp getDate(){
		return date;
	}
	
	public boolean getChange(){
		return change;
	}
}
