package hr.tennis.bot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.TreeMap;

public class FileBasedCache {

	private static final int MAX_DIRTY_COUNT = 1000;
	//private static final int MAX_DIRTY_COUNT = 1;

	private final String filename;
	private final File file;
	private Map<String, Object> cache;
	private int dirtyCount;

	public FileBasedCache(String filename) throws IOException {
		this.filename = filename;
		this.file = new File(filename);
		this.dirtyCount = 0;

		if(this.file.exists()){
			load();
		}else{
			this.cache = new TreeMap<String,Object>();
		}
	}

	public Object get(String key){
		return this.cache.get(key);
	}

	public void set(String key,Object value){
		this.cache.put(key,value);
		this.dirtyCount++;
		if(this.dirtyCount > MAX_DIRTY_COUNT){
			sync();
		}
	}

	private void load() throws FileNotFoundException, IOException{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		try {
			this.cache = (TreeMap<String, Object>) in.readObject();
		} catch (ClassNotFoundException e) {
			//ignorable
		}
		in.close();
	}

	public void sync(){
		if(this.dirtyCount > 0){
			try {
				ObjectOutput out = new ObjectOutputStream(new FileOutputStream(this.file,false));
				out.writeObject(this.cache);
				out.close();
				this.dirtyCount = 0;
				System.out.println("cache synced!");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public Map<String, Object> getCache() {
		return cache;
	}

	public void close() throws FileNotFoundException, IOException{
		sync();
		this.cache = null;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		FileBasedCache cache = new FileBasedCache("C:\\development\\personal\\TennisBot\\TennisBotLearner\\.featurecache");

		//		for(int i=10;i<20;i++){
		//			cache.set("k"+i,i);
		//		}
		//
		//		cache.close();

		System.out.println(cache.getCache().size());

		//		long start = System.currentTimeMillis();
		//		System.out.println(cache.get("83292.homePlayerWinningPercentage12Month"));
		//		long finish = System.currentTimeMillis();
		//		System.out.println("get:"+(finish-start)/1000.0);
		//
		//		start = System.currentTimeMillis();
		//		cache.set("83292.test",0.1234);
		//		finish = System.currentTimeMillis();
		//		System.out.println("put:"+(finish-start)/1000.0);

		//		for(Entry<String, Object> entry : cache.getCache().entrySet()){
		//			try {
		//				if (Double.isNaN((Double) entry.getValue())) {
		//					System.out.println(entry);
		//				}
		//			} catch (ClassCastException e) {
		//				//ignorable
		//			}
		//		}

	}


}
