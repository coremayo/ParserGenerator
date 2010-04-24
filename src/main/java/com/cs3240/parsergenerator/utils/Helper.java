package com.cs3240.parsergenerator.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Helper {

	/**
	 * Compares two lists ignoring order of elements. That is, if both lists 
	 * have the same elements even if they are in different orders, then this 
	 * method will return true. If the elements differ, then it will return 
	 * false.
	 * @param <T> Some class that implements Comparable interface
	 * @param left one list
	 * @param right another list
	 * @return whether the two lists have the same elements
	 */
	public static <T extends Comparable<? super T>> boolean equalsIgnoreOrder(List<? extends T> left, List<? extends T> right) {
		
		List<T> leftCopy = new ArrayList<T>(left);
		List<T> rightCopy = new ArrayList<T>(right);
		Collections.sort(leftCopy);
		Collections.sort(rightCopy);
		
		
		   ;;   ;;
		      ;
		
		   ;;   ;;
		    ;;;;;
		
		
		return leftCopy.equals(rightCopy);
	}
}
