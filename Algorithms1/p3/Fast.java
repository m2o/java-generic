import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Fast {

	public Fast() {

	}
	
	private static int MIN_POINT_AMOUNT = 3;

	private static void checkCollinear(Point[] points) {

		int pointsCnt = points.length;
		Arrays.sort(points);
		Point[] pointsCmp = Arrays.copyOf(points, points.length);
		
		Set<PointSlopeData> donePointSlopes = new HashSet<PointSlopeData>();
		
		for(Point p : points) {
			
//			System.out.printf("sort compared to %s\n",p);
			
			Arrays.sort(pointsCmp,p.SLOPE_ORDER);
			assert p.slopeTo(pointsCmp[0])==Double.NEGATIVE_INFINITY: "first slope not -inf";
			
//			for(Point op : pointsCmp) {
//				System.out.printf("%s %f\n",op,p.slopeTo(op));
//			}
			
			int cnt=1;
			double testSlope = Double.NEGATIVE_INFINITY;
			double currentSlope;
			for(int i=1;i<pointsCnt+1;i++){
				if(i<pointsCnt){
					currentSlope = p.slopeTo(pointsCmp[i]);
				}else{
					//last
					currentSlope = Double.NEGATIVE_INFINITY;
				}

				if(currentSlope==testSlope){
					cnt++;
				}else{
					if(cnt<MIN_POINT_AMOUNT ){
						cnt = 1;
					}else{
					
						Point[] results = new Point[cnt+1];
						results[0] = p;
						
//						System.out.printf("%s -> ",p);
						for(int j=0;j<cnt;j++){
							Point pC = pointsCmp[i-cnt+j];
							results[j+1]=pC;
//							if(j<cnt-1){
//								System.out.printf("%s -> ",pC);
//							}else{
//								System.out.printf("%s\n",pC);
//							}
						}
						
						Arrays.sort(results);
						
						PointSlopeData pds = new PointSlopeData(results[0],testSlope);
						if(donePointSlopes.add(pds)){
							
							for(int j=0;j<results.length;j++){
								if(j<results.length-1){
									System.out.printf("%s -> ",results[j]);
								}else{
									System.out.printf("%s\n",results[j]);
								}
							}
							
							results[0].drawTo(results[results.length-1]);
						}
						
						cnt = 1;
					}
					testSlope = currentSlope;
				}
			}
		}
		
	}
	
	private static class PointSlopeData {
		public Point point;
		public Double slope;
		
		public PointSlopeData(Point point, Double slope) {
			this.point = point;
			this.slope = slope;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((point == null) ? 0 : point.hashCode());
			result = prime * result + ((slope == null) ? 0 : slope.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PointSlopeData other = (PointSlopeData) obj;
			if (point == null) {
				if (other.point != null)
					return false;
			} else if (!point.equals(other.point))
				return false;
			if (slope == null) {
				if (other.slope != null)
					return false;
			} else if (!slope.equals(other.slope))
				return false;
			return true;
		}		
	}

	public static void main(String[] args) {
		String filename = args[0];
		File file = new File(filename);
		In fileIn = new In(file);
		
		int n = fileIn.readInt();
		Point[] points = new Point[n];
		
		for(int i=0;i<n;i++){
			points[i] = new Point(fileIn.readInt(), fileIn.readInt());
		}

		fileIn.close();
		
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.setPenColor();
		StdDraw.setPenRadius(0.008);
		for(Point p : points){
			p.draw();
		}
		StdDraw.setPenRadius(0.002);
		checkCollinear(points);
	}
}
