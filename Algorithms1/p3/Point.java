import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
        this.SLOPE_ORDER = new SlopeComparator();
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {

    	int yDiff = that.y - this.y;
    	int xDiff = that.x - this.x;
    	
    	if(yDiff==0 && xDiff==0){
    		return Double.NEGATIVE_INFINITY;
    	}
    	if(xDiff==0){
    		return Double.POSITIVE_INFINITY;
    	}
    	if(yDiff==0){
    		return 0d;  //avoids + or - zeros
    	}
    	
    	return (double)yDiff / xDiff;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        int yCmp = ((Integer)this.y).compareTo(that.y);
        if(yCmp!=0){
        	return yCmp;
        }
        return ((Integer)this.x).compareTo(that.x);
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

	// unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
    
    
    private class SlopeComparator implements Comparator<Point> {
		@Override
		public int compare(Point o1, Point o2) {
			double slopeToP1 = slopeTo(o1);
			double slopeToP2 = slopeTo(o2);
			return Double.compare(slopeToP1, slopeToP2);
		}
    }
}
