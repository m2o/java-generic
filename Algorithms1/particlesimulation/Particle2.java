import java.awt.Color;
import java.util.Random;


public class Particle2 {
	
	private static final double X_MAX = 1.0;
	private static final double Y_MAX = 1.0;
	private static final double VX_MAX = 0.02;
	private static final double VY_MAX = 0.02;
	private static final double RADIUS = 0.1;
	private static final Random random = new Random();

	private Color color;
	
	private double rX;
	private double rY;
	private double r;
	
	private double vX;
	private double vY;
	
	public Particle2() {
		this(Color.BLACK,
				0.5*X_MAX,
				0.5*Y_MAX,
				RADIUS,
				(random.nextDouble()-0.5)*2*VX_MAX,
				(random.nextDouble()-0.5)*2*VY_MAX);
	}
	
	public Particle2(Color color, double rX, double rY,double r,double vX, double vY) {
		this.color = color;
		this.rX = rX;
		this.rY = rY;
		this.vX = vX;
		this.vY = vY;
		this.r = r;
	}

	public Color getColor() {
		return color;
	}
	
	public double getR() {
		return r;
	}
	
	public double getrX() {
		return rX;
	}
	
	public double getrY() {
		return rY;
	}

	public void move(double dt) {
		this.rX += this.vX*dt;
		if(rX+r > X_MAX){
			rX = X_MAX - (rX+r-X_MAX) - r;
			vX*=-1;
		}else if(rX-r<0){
			rX = r + -1*(rX-r);
			vX*=-1;
		}
		
		this.rY += this.vY*dt;
		if(rY+r > Y_MAX){
			rY = Y_MAX - (rY+r - Y_MAX) - r;
			vY*=-1;
		}else if(rY-r<0){
			rY = r + -1*(rY-r);
			vY*=-1;
		}
	}
}
