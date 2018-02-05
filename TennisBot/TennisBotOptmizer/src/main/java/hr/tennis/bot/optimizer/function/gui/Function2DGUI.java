package hr.tennis.bot.optimizer.function.gui;

import hr.tennis.bot.optimizer.function.Function;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JComponent;




public class Function2DGUI extends AlgorithmGUI {

	private static final long serialVersionUID = 1L;

	public static final int STATE_NONE=-1;
	public static final int STATE_STEP=0;
	public static final int STATE_AUTO=1;
	
	private Function function;
	
	private double[] xIntervals;
	private double[] yIntervals;
	
	private double xRange;
	private double yRange;
	
	private boolean showLocal=false;
	
	private int contourFactor;
	
	private double[] bestSolution;
	private double[][] otherSolutions;

	private FunctionJComponent functionComponent;
	
	public Function2DGUI(Function function,double[] xIntervals,double[] yIntervals,int contourFactor,String[] strategyNames) {
		
		super(strategyNames);
		this.function=function;
		
		this.xIntervals = xIntervals;
		this.yIntervals = yIntervals;
		
		this.xRange=xIntervals[1]-xIntervals[0];
		this.yRange=yIntervals[1]-yIntervals[0];
		
		this.contourFactor=contourFactor;
	}
	
	private Point toPixel(double[] position,int width,int height) {
		
		double x = position[0]-xIntervals[0];
    	double y = position[1]-yIntervals[0];
    	
		int actualX=(int) (x/xRange*width);
    	int actualY=(int) (height-y/yRange*height);
		
		return new Point(actualX,actualY);
	}
	
	private double[] fromPixel(Point point,int width,int height) {
    	
		double actualX = ((double)point.x)/width*xRange+xIntervals[0];
    	double actualY = ((double)(height-point.y))/height*yRange+yIntervals[0];
		
    	double[] position = {actualX,actualY};
		return position;
	}

	public void init(){
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(function.toString());
		this.setSize(800,800);
		setLayout(new BorderLayout());
		
		super.init();
		
		functionComponent = new FunctionJComponent();
		add(functionComponent,BorderLayout.CENTER);
		
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				functionComponent.paintBackground();
				functionComponent.repaint();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		setVisible(true);
	}
	
	public void setBestSolution(double[] bestSolution) {
		this.bestSolution = bestSolution;
	}
	
	public void setOtherSolutions(double[][] otherSolutions) {
		this.otherSolutions = otherSolutions;
	}
	
	public void clearSolutions() {
		this.bestSolution=null;
		this.otherSolutions=null;
	}
	
	private class FunctionJComponent extends JComponent{
		
		private static final long serialVersionUID = 1L;
		
		private BufferedImage backgroundImage;

		private double zMin=-1;
		private double zMax=-1;
		private double zRange=-1;
		
		public FunctionJComponent() {
			
		}
		
		public void paintBackground() {
			
			int backgroundWidth = getWidth();
			int backgroundHeight = getHeight();
			
			backgroundImage = new BufferedImage(backgroundWidth,backgroundHeight, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2 = (Graphics2D) backgroundImage.getGraphics();
			
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, backgroundWidth, backgroundHeight);
			
			if(zMin==-1){
				zMin=Double.POSITIVE_INFINITY;
				zMax=Double.NEGATIVE_INFINITY;
				
				for(int xPixel=0;xPixel<backgroundWidth;xPixel++){
					for(int yPixel=0;yPixel<backgroundHeight;yPixel++){
					
						double[] position = fromPixel(new Point(xPixel,yPixel),backgroundWidth,backgroundHeight);
						double value=function.value(position);
						
						zMin=value<zMin?value:zMin;
						zMax=value>zMax?value:zMax;
					}
				}
				
				zRange = zMax-zMin;
			}
			
			for(int xPixel=0;xPixel<backgroundWidth;xPixel+=4){
				for(int yPixel=0;yPixel<backgroundHeight;yPixel+=4){
				
					double[] position = fromPixel(new Point(xPixel,yPixel),backgroundWidth,backgroundHeight);
					double value=function.value(position);
					double percent=(value-zMin)/zRange;
					int alpha = (int)(256*Math.pow(Math.E, -1*percent*contourFactor));
					g2.setColor(new Color(0, 0, 0, alpha));
					
					g2.fillRect(xPixel, yPixel,4,4);
				}
			}
			
			g2.finalize();
			backgroundImage.flush();
		}

		@Override
		public void paint(Graphics g) {
			
			int particleWidth=4;
			int particleHeight=4;
			
			Graphics2D g2 = (Graphics2D) g;
			
			if(backgroundImage==null){
				paintBackground();
			}
			
			g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), 0, 0, getWidth(),getHeight(), null);
		    
			if(otherSolutions!=null){
				for (int i = 0; i < otherSolutions.length; i++) {
					Point point = toPixel(otherSolutions[i],getWidth(),getHeight());
					g2.setColor(Color.BLUE);
			    	g2.fillOval(point.x, point.y, particleWidth, particleHeight);
				}
			}
			
		    if(bestSolution!=null){
			    g2.setColor(Color.RED);
			    Point gBest = toPixel(bestSolution,getWidth(),getHeight());
			    g2.fillOval(gBest.x,gBest.y, 6, 6);
			    g2.setColor(Color.BLACK);
			    g2.drawString(Arrays.toString(bestSolution),10,getHeight()-10);
		    }
		}
	}
	
	public static void main(String[] args) {

	}
}
