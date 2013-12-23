package hr.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello world!");
		
		//diamond operator
		List<Integer> ints = new ArrayList<>();
		ints.add(1);
		ints.add(2);
		System.out.println(ints);
		
		//string switch
		String value = "bla2";
		switch (value) {
		case "bla":
			System.out.println(1);
			break;
		case "bla2":
			System.out.println(2);
			break;
		default:
			System.out.println(3);
			break;
		}
		
		//automatic resource management
		try(Resource r = new Resource();){
			r.work();
			System.out.println("finished ok");
		}catch (Exception e) {
			System.out.println("finished in error");
			e.printStackTrace();
		}
		
		try(Resource r = new Resource();){
			r.work2();
			System.out.println("finished ok");
		}catch (Exception e) {
			System.out.println("finished in error");
			e.printStackTrace();
		}
		
		//numeric literals with underscores
		long onemil = 1_000_000;
		System.out.println(onemil);
		
		//multicatch
		try{
			if(Math.random()<0.5){
				throw new NullPointerException();
			}else{
				throw new IndexOutOfBoundsException();	
			}
		}catch (NullPointerException | IndexOutOfBoundsException e) {
			System.out.println("handling exception NullPointerException or IndexOutOfBoundsException...");
			e.printStackTrace();
		}
		
		//New file system API (NIO 2.0)
		Path f = Paths.get("/var/log/otus");
		System.out.println(f.toString());
		System.out.println(f.getFileName());
		System.out.println(f.getNameCount());
		System.out.println(f.getRoot());
		System.out.println(f.getParent());
		
		//Files.delete(path)
		//Files.deleteIfExists(path)
		//Files.move(source, target, CopyOption)
		//Files.copy(source, target, options)
		
	}

}
