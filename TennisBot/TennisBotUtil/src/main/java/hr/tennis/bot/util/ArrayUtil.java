/**
 *
 */
package hr.tennis.bot.util;

import java.util.ArrayList;
import java.util.List;


/**
 * @author edajzve
 *
 */
public class ArrayUtil {

	public static String[] concatArrays(String[] array1, String[] array2) {

		String[] finalArray = new String[array1.length+array2.length];
		System.arraycopy(array1, 0, finalArray, 0, array1.length);
		System.arraycopy(array2, 0, finalArray, array1.length, array2.length);

		return finalArray;
	}

	public static List<Integer> toOrdinalArray(Enum[] enums) {

		if(enums==null){
			return null;
		}
		List<Integer> ordinals = new ArrayList<Integer>();
		for(int i=0;i<enums.length;i++){
			ordinals.add(enums[i].ordinal());
		}

		return ordinals;
	}

	public static double[][] expand(double from,double to, int dimensions){

		double[] interval = {from,to};
		double[][] intervals = new double[dimensions][];

		for(int i=0;i<dimensions;i++){
			intervals[i]=interval;
		}

		return intervals;
	}

	public static void copyMatrixToArray(double[][] matrix,
			int rows,
			int cols,
			double[] data,
			int destPos) {

		for(int i=0;i<rows;i++){
			System.arraycopy(matrix[i],0,data,destPos,cols);
			destPos+=cols;
		}
	}

	public static void copyArrayToMatrix(double[] data,
			int srcPos,
			double[][] matrix,
			int rows,
			int cols) {

		for(int i=0;i<rows;i++){
			System.arraycopy(data,srcPos,matrix[i],0,cols);
			srcPos+=cols;
		}
	}

	public static void normalizeArray(double[] output,int dimensions){
		double sum=0;
		for(int i=0;i<dimensions;i++){
			sum+=output[i];
		}
		for(int i=0;i<dimensions;i++){
			output[i]/=sum;
		}
	}

}
