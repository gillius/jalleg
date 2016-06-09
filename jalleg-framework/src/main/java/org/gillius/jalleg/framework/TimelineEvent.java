package org.gillius.jalleg.framework;

public class TimelineEvent<T> implements Comparable<TimelineEvent> {
	private final T data;
	private final double time;

	public TimelineEvent(T data, double time) {
		this.data = data;
		this.time = time;
	}

	public T getData() {
		return data;
	}

	public double getTime() {
		return time;
	}

	@Override
	public int compareTo(TimelineEvent o) {
		return Double.compare(time, o.time);
	}
}
