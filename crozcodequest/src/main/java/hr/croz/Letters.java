package hr.croz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

public class Letters {

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
	
	static class CharOccurence{
		Character c;
		Integer cnt;
		
		public CharOccurence(Character c, Integer cnt) {
			this.c = c;
			this.cnt = cnt;
		}
		
		@Override
		public String toString() {
			return String.format("%d%c",this.cnt,this.c);
		}
		
		static enum OccurenceComparator implements Comparator<CharOccurence>{
			
			INSTANCE;

			public int compare(CharOccurence o1, CharOccurence o2) {
				return Integer.compare(o1.cnt, o2.cnt);
			}
			
		}
	}

	private static String format(String line) {
		
		boolean sortByOccurences = false;
		if(line.startsWith("#")){
			line = line.substring(1);
			sortByOccurences = true;
		}
		
		List<CharOccurence> ocs = countOccurences(line);
		if(sortByOccurences){
			Collections.sort(ocs, Ordering.from(CharOccurence.OccurenceComparator.INSTANCE).reverse());
		}
		return Joiner.on(' ').join(ocs);
	}

	private static List<CharOccurence> countOccurences(String line) {
		int[] charCnts = new int[26];
		Arrays.fill(charCnts, 0);
		
		char[] charIns = line.toCharArray();
		
		int lastStoredIx = -1;
		int multiplier = -1;
		
		for(int cix=0; cix<charIns.length; cix++){
			char c = charIns[cix];
			
			if('.' == c){
				if(lastStoredIx >= 0){
					charCnts[lastStoredIx] += 1;
				}else{
					//ignored
				}
			}else if(Character.isDigit(c) && multiplier<0){
				multiplier = (int)c-'0';
			}else if(Character.isDigit(c) && multiplier>=0){
				multiplier = multiplier*10 + ((int)c-'0');
			}else {
				int inc = multiplier < 0 ? 1 : multiplier;
				int charIx = (int) (Character.toLowerCase(c)-'a');
				charCnts[charIx] += inc;
				if(inc > 0){
					lastStoredIx = charIx;
				}
				multiplier = -1;
			}
		}
		
		List<CharOccurence> ocs = new ArrayList<CharOccurence>(26);
		for(int cix=0;cix<charCnts.length;cix++){
			if(charCnts[cix]>0){
				ocs.add(new CharOccurence((char)('a'+cix), charCnts[cix]));
			}
		}
		return ocs;
	}

}
