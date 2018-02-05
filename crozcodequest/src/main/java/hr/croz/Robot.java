package hr.croz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

public class Robot {
	
	Coordinates coordinates = new Coordinates(0, 0);
	Orientation orientation = Orientation.NORTH;
	boolean smallRotation = false;
	boolean forwardDrive = true;
	int ts = 0;
	
	public Robot() {

	}
	
	private void move(int i) {
		
		if(i<0){
			throw new IllegalArgumentException("expected non-negative move count");
		}
		
		if(!forwardDrive){
			i *= -1;
		}
		
		switch (orientation) {
		case NORTH:
			coordinates = coordinates.moveY(i);
			break;
		case NORTHWEST:
			coordinates = coordinates.moveY(i).moveX(-1*i);
			break;
		case WEST:
			coordinates = coordinates.moveX(-1*i);
			break;
		case SOUTHWEST:
			coordinates = coordinates.moveY(-1*i).moveX(-1*i);
			break;
		case SOUTH:
			coordinates = coordinates.moveY(-1*i);
			break;
		case SOUTHEAST:
			coordinates = coordinates.moveY(-1*i).moveX(i);
			break;
		case EAST:
			coordinates = coordinates.moveX(i);
			break;
		case NORTHEAST:
			coordinates = coordinates.moveY(i).moveX(i);
			break;
		default:
			break;
		}
		
		int unitTime = Orientation.ORIENTATION_CARDINAL.contains(this.orientation) ? 10 : 14;
		
		ts += i!=0 ? Math.abs(i)*unitTime : 10;
	}
	
	private void rotateRight() {
		if(smallRotation){
			this.orientation = this.orientation.rotateRight();
			ts += 3;
		}else{
			this.orientation = this.orientation.rotateRight().rotateRight();
			ts += 5;
		}
	}

	private void rotateLeft() {
		if(smallRotation){
			this.orientation = this.orientation.rotateLeft();
			ts += 3;
		}else{
			this.orientation = this.orientation.rotateLeft().rotateLeft();
			ts += 5;
		}
	}
	
	private void changeRotationMode() {
		this.smallRotation = !this.smallRotation;
	}
	
	private void changeDriveMode() {
		this.forwardDrive = !this.forwardDrive;
		this.ts += 1;
	}
	
	@Override
	public String toString() {
		return String.format("%s%s t=%d",coordinates,orientation,ts);
	}

	static enum Orientation{
		NORTH("N"),NORTHWEST("NW"),WEST("W"),SOUTHWEST("SW"),SOUTH("S"),SOUTHEAST("SE"),EAST("E"),NORTHEAST("NE");
		
		final static Set<Orientation> ORIENTATION_CARDINAL = EnumSet.of(Orientation.NORTH,Orientation.WEST,Orientation.SOUTH,Orientation.EAST);
		final static Map<Orientation,Orientation> ORIENTATION_LEFT_ROTATIONS_SMALL;
		final static Map<Orientation,Orientation> ORIENTATION_RIGHT_ROTATIONS_SMALL;
		
		static{
			ORIENTATION_LEFT_ROTATIONS_SMALL = new EnumMap<Orientation,Orientation>(Orientation.class);
			ORIENTATION_LEFT_ROTATIONS_SMALL.put(Orientation.NORTH, Orientation.NORTHWEST);
			ORIENTATION_LEFT_ROTATIONS_SMALL.put(Orientation.NORTHWEST, Orientation.WEST);
			ORIENTATION_LEFT_ROTATIONS_SMALL.put(Orientation.WEST, Orientation.SOUTHWEST);
			ORIENTATION_LEFT_ROTATIONS_SMALL.put(Orientation.SOUTHWEST, Orientation.SOUTH);
			ORIENTATION_LEFT_ROTATIONS_SMALL.put(Orientation.SOUTH, Orientation.SOUTHEAST);
			ORIENTATION_LEFT_ROTATIONS_SMALL.put(Orientation.SOUTHEAST, Orientation.EAST);
			ORIENTATION_LEFT_ROTATIONS_SMALL.put(Orientation.EAST, Orientation.NORTHEAST);
			ORIENTATION_LEFT_ROTATIONS_SMALL.put(Orientation.NORTHEAST, Orientation.NORTH);
			
			ORIENTATION_RIGHT_ROTATIONS_SMALL = new EnumMap<Orientation,Orientation>(Orientation.class);
			ORIENTATION_RIGHT_ROTATIONS_SMALL.put(Orientation.NORTH, Orientation.NORTHEAST);
			ORIENTATION_RIGHT_ROTATIONS_SMALL.put(Orientation.NORTHWEST, Orientation.NORTH);
			ORIENTATION_RIGHT_ROTATIONS_SMALL.put(Orientation.WEST, Orientation.NORTHWEST);
			ORIENTATION_RIGHT_ROTATIONS_SMALL.put(Orientation.SOUTHWEST, Orientation.WEST);
			ORIENTATION_RIGHT_ROTATIONS_SMALL.put(Orientation.SOUTH, Orientation.SOUTHWEST);
			ORIENTATION_RIGHT_ROTATIONS_SMALL.put(Orientation.SOUTHEAST, Orientation.SOUTH);
			ORIENTATION_RIGHT_ROTATIONS_SMALL.put(Orientation.EAST, Orientation.SOUTHEAST);
			ORIENTATION_RIGHT_ROTATIONS_SMALL.put(Orientation.NORTHEAST, Orientation.EAST);
		}
		
		private String sign;
		
		private Orientation(String sign) {
			this.sign = sign;
		}
		
		public Orientation rotateLeft() {
			return ORIENTATION_LEFT_ROTATIONS_SMALL.get(this);
		}

		public Orientation rotateRight() {
			return ORIENTATION_RIGHT_ROTATIONS_SMALL.get(this);
		}

		@Override
		public String toString() {
			return sign;
		}
	}
	
	static class Coordinates{
		final int x;
		final int y;
		
		public Coordinates(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public Coordinates moveY(int i) {
			return new Coordinates(this.x, this.y+i);
		}

		public Coordinates moveX(int i) {
			return new Coordinates(this.x+i, this.y);
		}

		@Override
		public String toString() {
			return String.format("<%d,%d>",x,y);
		}
	}
	
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

	private static String format(String commands) {
		Robot robot = new Robot();
		for(Character c : commands.toCharArray()){
			if(Character.isDigit(c)){
				robot.move(c-'0');
			}else if('L' == c){
				robot.rotateLeft();
			}else if('R' == c){
				robot.rotateRight();
			}else if('A' == c){
				robot.changeRotationMode();
			}else if('#' == c){
				robot.changeDriveMode();
			}
		}
		return robot.toString();
	}
}
