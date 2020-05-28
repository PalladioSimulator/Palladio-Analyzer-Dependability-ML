package org.palladiosimulator.dependability.ml.util;

public class Tuple<T,U> {

	private final T first;
	private final U second;
	
	private Tuple(T first, U second) {
		this.first = first;
		this.second = second;
	}
	
	public static <T,U> Tuple<T, U> of(T first, U second) {
		return new Tuple<T, U>(first, second);
	}

	public T getFirst() {
		return first;
	}
	
	public U getSecond() {
		return second;
	}
	
}
