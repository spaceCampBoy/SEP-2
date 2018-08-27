package server.util;
/**
 * Sorting class contains diffrent methods for sorting integer Array
 */
public class Sorting {
	/**
	 * Empty constructor for Sorting class
	 */
	private Sorting(){}
	/**
	 * method to sort an array of integers in ascending order
	 * @param arr array of integers to sort
	 */
	public static void selectionSort(int[] arr) {
		int i = 0; 
		while (i < arr.length) 
		{ 
			int idx = findSmallestElemIn(arr, i, arr.length);
			swap(arr, i,idx);
			i++;
		}
	}
	
	/**
	 * method for finding smallest element in an array of integers 
	 * @param A array to search the smallest index in
	 * @param from  index of array A 
	 * @param to index of array A 
	 * @return
	 */
	private static int findSmallestElemIn(int[] A, int from, int to) {
		int idxToSmallest = from;
		for (int i = from + 1; i < to; i++) {
			if (A[i] < A[idxToSmallest])
				idxToSmallest = i;
		}
		return idxToSmallest;
	}
	
	/**
	 * swaps two integers in an array
	 * @param A array with values to swap
	 * @param i integer to swap
	 * @param j integer to swap
	 */
	private static void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}
}
