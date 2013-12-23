package hr.fer.guavatest.collections;

import java.util.Random;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

public class TableTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Random rand = new Random();

		Table<String, Integer, String> studentGradesByYear = HashBasedTable.create();

		for(int i = 0;i<10;i++){
			String name = String.format("Student_%d",i);
			for(int j=0; j<5; j++){
				Integer year = j;
				String grade = Character.toString((char) ('A'+rand.nextInt(5)));
				studentGradesByYear.put(name, year, grade);
			}
		}

		System.out.println(studentGradesByYear);
		System.out.println("row map:"+studentGradesByYear.rowMap());
		System.out.println("col map:"+studentGradesByYear.columnMap());

		for(Cell<String, Integer, String> cell : studentGradesByYear.cellSet()){
			System.out.println(String.format("%s-%d = %s", cell.getRowKey(),cell.getColumnKey(),cell.getValue()));
		}

	}

}
