import java.io.File;
import java.util.Arrays;


public class Brute {
	
	public Brute() {

	}

	private static void checkCollinear(Point[] points) {
		
		Arrays.sort(points);
//		System.out.println(Arrays.toString(points));
		
		int pointsCnt = points.length;
		
		for(int i=0;i<pointsCnt;i++){
			Point pi = points[i];
			for(int j=i+1;j<pointsCnt;j++){
//				if(j==i){
//					continue;
//				}
				Point pj = points[j];
				for(int k=j+1;k<pointsCnt; k++){
//					if(k==j || k==i){
//						continue;
//					}
					Point pk = points[k];
					for(int h=k+1;h<pointsCnt; h++){
//						if(h==k || h==j || h==i){
//							continue;
//						}
						
						Point ph = points[h];
						
						double slope_ij = pi.slopeTo(pj);
						double slope_jk = pj.slopeTo(pk);
						double slope_kh = pk.slopeTo(ph);
						
						if(slope_ij == slope_jk && slope_jk == slope_kh) {
//							System.out.println(slope_ij);
//							System.out.println(slope_jk);
//							System.out.println(slope_kh);
							System.out.printf("%s -> %s -> %s -> %s\n",pi,pj,pk,ph);
							pi.drawTo(ph);
						}
						
//						System.out.printf("i:%d j:%d k:%d h:%d\n",i,j,k,h);
					}
				}
			}
		}
		
	}
	
	/**
	 * @param args
	 */
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
