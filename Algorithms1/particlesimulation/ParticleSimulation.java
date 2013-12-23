
public class ParticleSimulation {

	
	public static void main(String[] args) {
		
		int n = 5;
		Particle2[] particles = new Particle2[n];
		
		for(int i=0; i<n; i++){
			particles[i] = new Particle2();
		}
		
		while(true){
			StdDraw.clear();
			StdDraw.line(0d, 0d, 0d, 1d);
			StdDraw.line(0d, 1d, 1d, 1d);
			StdDraw.line(1d, 1d, 1d, 0d);
			StdDraw.line(1d, 0d, 0d, 0d);
			
			for(Particle2 p : particles){
				p.move(0.1);
				drawParticle(p);
			}
			
			StdDraw.show(10);
		}
		
	}

	private static void drawParticle(Particle2 p) {
        StdDraw.setPenColor(p.getColor());
        StdDraw.filledCircle(p.getrX(), p.getrY(), p.getR());
	}
}
