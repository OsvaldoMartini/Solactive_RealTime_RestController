package com.solactive.pojos;

import java.io.Serializable;

public class TicksDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String instrument;
	private Double price;
	private long timeStamp;
	
	public TicksDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public TicksDTO(String instrument, Double price, long timeStamp) {
		super();
		this.instrument = instrument;
		this.price = price;
		this.timeStamp = timeStamp;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
