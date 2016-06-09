package org.gillius.jalleg.framework;

import java.util.PriorityQueue;

public class Timeline<T> {
	private final PriorityQueue<TimelineEvent<T>> queue = new PriorityQueue<>();
	private double start;

	public void add(T item, double time) {
		queue.add(new TimelineEvent<>(item, time));
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public boolean hasEvent(double currentTime) {
		TimelineEvent<T> event = queue.peek();
		return event != null && event.getTime() <= (currentTime - start);
	}

	public T getEvent() {
		return queue.poll().getData();
	}

	public T poll(double currentTime) {
		if (hasEvent(currentTime))
			return getEvent();
		else
			return null;
	}
}
