package com.solactive.pojos;

public class StatisticsDTO {
	private double avg;
	private double max;
	private double min;
	private long count;

	public StatisticsDTO() {
		// TODO Auto-generated constructor stub
	}

	public StatisticsDTO(double avg, double max, double min, long count) {
		super();
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
