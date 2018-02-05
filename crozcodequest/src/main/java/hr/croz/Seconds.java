package hr.croz;

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

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

public class Seconds {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); 
        String line; 

        while ((line = stdin.readLine()) != null && line.length()!= 0) { 
        	Integer seconds = Integer.valueOf(line.trim());
            System.out.println(format(seconds)); 
        } 
	}

	private static String format(int seconds) {
		
		int hours = 0,minutes = 0;
		String hoursUnit = null,minUnit = null,secUnit = null;
		
		hours = seconds/3600;
		hoursUnit = formatHoursUnit(hours);
		seconds -= hours*3600;
		
		minutes = seconds/60;
		minUnit = formatMinutesUnit(minutes);
		seconds -= minutes*60;
		
		secUnit = formatSecondsUnit(seconds);
		
		List<String> parts = new LinkedList<String>();
		parts.add("za");
		if(hours > 0){
			parts.add(String.format("%d %s",hours,hoursUnit));
		}
		if(hours > 0 || minutes > 0){
			parts.add(String.format("%d %s",minutes,minUnit));
		}
		parts.add(String.format("%d %s",seconds,secUnit));
		
		return Joiner.on(' ').join(parts);
	}
	
	private static String formatSecondsUnit(int seconds) {
		
		int unitSeconds = seconds % 10;
		
		String unit = "sekundi";
		
		if(seconds >= 11 && seconds <= 19){
			unit = "sekundi";
		}else if(unitSeconds == 1){
			unit = "sekundu";
		}else if(unitSeconds >=2 && unitSeconds <= 4){
			unit = "sekunde";
		}

		return unit;
	}
	
	private static String formatMinutesUnit(int minutes) {
		
		int unitSeconds = minutes % 10;
		
		String unit = "minuta";
		
		if(minutes >= 11 && minutes <= 19){
			unit = "minuta";
		}else if(unitSeconds == 1){
			unit = "minutu";
		}else if(unitSeconds >=2 && unitSeconds <= 4){
			unit = "minute";
		}

		return unit;
	}
	
	private static String formatHoursUnit(int hours) {
		
		int unitSeconds = hours % 10;
		
		String unit = "sati";
		
		if(hours >= 11 && hours <= 19){
			unit = "sati";
		}else if(unitSeconds == 1){
			unit = "sat";
		}else if(unitSeconds >=2 && unitSeconds <= 4){
			unit = "sata";
		}

		return unit;
	}


}
