package hr.croz.intervals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

public class Intervals {
	
	private static final Pattern intervalPat = Pattern.compile("(\\d{2}):(\\d{2})-(\\d{2}):(\\d{2})");

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); 
        String line; 

        while ((line = stdin.readLine()) != null && line.length()!= 0) { 
            System.out.println(format(line)); 
        } 
	}
	
	static class IntervalRepo{
		
		Map<Integer,Integer> intervals = new TreeMap<Integer, Integer>();
		List<IntervalBoudary> intervalBoundaries = new ArrayList<IntervalBoudary>();
		
		int simpleTotal = 0;
		
		public IntervalRepo() {

		}

		public int getMaxFreeDuration() {
			
			int maxFreeDuration = 0;
			
			int prevStart = 0;
			
			for(Map.Entry<Integer, Integer> e : this.intervals.entrySet()){
				
				int freeDuration = e.getKey() - prevStart;
				
				if(freeDuration > maxFreeDuration){
					maxFreeDuration = freeDuration;
				}
				
				prevStart = e.getValue();
			}
			
			int freeDuration = 24*60 - prevStart;
			if(freeDuration > maxFreeDuration){
				maxFreeDuration = freeDuration;
			}
			
			return maxFreeDuration;
		}
		
		public IntervalRepo getFreeIntervalsRepo() {
			
			
			IntervalRepo repo = new IntervalRepo();
			
			int prevStart = 0;
			
			for(Map.Entry<Integer, Integer> e : this.intervals.entrySet()){
				
				Integer hourFrom = prevStart/60;
				Integer minFrom = prevStart%60;
				Integer hourTo = e.getKey()/60;
				Integer minTo = e.getKey()%60;
				
				repo.addInterval(hourFrom, minFrom, hourTo, minTo);
				
				prevStart = e.getValue();
			}
			
			Integer hourFrom = prevStart/60;
			Integer minFrom = prevStart%60;
			Integer hourTo = 24;
			Integer minTo = 0;
			
			repo.addInterval(hourFrom, minFrom, hourTo, minTo);
			
			return repo;
		}
		
		public int getMaxOverlapCount() {
			Collections.sort(this.intervalBoundaries);
			
			int currentOverlap = 0;
			int maxOverlap = 0;
			
			for(IntervalBoudary b : intervalBoundaries){
				currentOverlap += b.start ? 1 : -1;
				if(currentOverlap > maxOverlap){
					maxOverlap = currentOverlap;
				}
			}
			
			return maxOverlap;
		}
		
		public int getTotalDuration() {
			return simpleTotal;
		}

		public void addInterval(Integer hourFrom, Integer minFrom,Integer hourTo, Integer minTo) {
			
			int start = hourFrom*60+minFrom;
			int end = hourTo*60+minTo;
			if(end-start<=0){
				return;
			}
			
			boolean inserted = false;
			
			while(true){
				boolean overlaps = false;
				Integer keyToDelete = null;
				Integer keyToInsert = start;
				Integer valueToInsert = end;
				
				for(Map.Entry<Integer, Integer> e : this.intervals.entrySet()){
					int kStart = e.getKey();
					int kEnd = e.getValue();
					
					if(end > kStart && start < kEnd){
						keyToDelete = kStart;
						keyToInsert = Math.min(start, kStart);
						valueToInsert = Math.max(end,kEnd);
						overlaps = keyToInsert!=kStart || valueToInsert!=kEnd;
						if(overlaps){
							break;
						}
					}
				}
				
				if(inserted && !overlaps){
					break;
				}
				
				if(keyToDelete!=null){
					intervals.remove(keyToDelete);
				}
				intervals.put(keyToInsert, valueToInsert);
				inserted = true;
			}
			
			intervalBoundaries.add(new IntervalBoudary(true, start));
			intervalBoundaries.add(new IntervalBoudary(false, end));
			simpleTotal += (end-start);
		}
		
		@Override
		public String toString() {
			List<String> intervalStrs = new ArrayList<String>(this.intervals.size());
			for(Map.Entry<Integer, Integer> e : this.intervals.entrySet()){
				Integer hourFrom = e.getKey()/60;
				Integer minFrom = e.getKey()%60;
				Integer hourTo = e.getValue()/60;
				Integer minTo = e.getValue()%60;
				String intervalStr = String.format("%02d:%02d-%02d:%02d",hourFrom,minFrom,hourTo,minTo);
				intervalStrs.add(intervalStr);
			}
			return Joiner.on(' ').join(intervalStrs);
		}
		
		
		static class IntervalBoudary implements Comparable<IntervalBoudary>{
			boolean start;
			Integer ts;
			
			public IntervalBoudary(boolean start, Integer ts) {
				this.start = start;
				this.ts = ts;
			}

			public int compareTo(IntervalBoudary o) {
				return this.ts.compareTo(o.ts);
			}
			
			@Override
			public String toString() {
				return String.format("<%d,%b>", ts,start);
			}
		}
	}

	private static String format(String line) {
		
		IntervalRepo repo = new IntervalRepo();
		
		Matcher mat = intervalPat.matcher(line);
		
		while(mat.find()){	
			Integer hourFrom = Integer.valueOf(mat.group(1));
			Integer minFrom = Integer.valueOf(mat.group(2));
			
			Integer hourTo = Integer.valueOf(mat.group(3));
			Integer minTo = Integer.valueOf(mat.group(4));
			
			repo.addInterval(hourFrom,minFrom,hourTo,minTo);
		}
		
//		return String.valueOf(repo.getTotalDuration());
//		return String.valueOf(repo.getMaxFreeDuration());
//		return String.valueOf(repo.getFreeIntervalsRepo());
		return String.valueOf(repo.getMaxOverlapCount());
	}
	
}
