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

import javax.swing.JComponent;
import javax.swing.JPanel;




public class Function1DGUI extends AlgorithmGUI {

	private static final long serialVersionUID = 1L;

	private Function function;
	
	private double[][] xIntervals;
	
	private double xRange;
	
	private double[] bestSolution;
	private double[][] otherSolutions;

	protected JPanel optionsPanel;
	private FunctionJComponent functionComponent;
	
	public Function1DGUI(Function function,double[][] xIntervals,String[] strategyNames) {
		super(strategyNames);
		this.function=function;
		this.xIntervals = xIntervals;
		this.xRange=xIntervals[0][1]-xIntervals[0][0];
	}
	
	public void init(){
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(function!=null?function.toString():"");
		this.setSize(800,800);
		
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
		functionComponent.repaint();
	}
	
	private class FunctionJComponent extends JComponent{
		
		private static final long serialVersionUID = 1L;
		
		private BufferedImage backgroundImage;

		private double yMin=-1;
		private double yMax=-1;
		private double yRange=-1;
		
		public FunctionJComponent() {
			
		}
		
		private Point toPixel(double[] var,int width,int height) {
		
			double x=var[0];
			double y=function.value(var);
			
			x = (x-xIntervals[0][0])/xRange;
	    	y = (y-yMin)/yRange;
	    	
			int actualX=(int) (x/xRange*width);
	    	int actualY=(int) (height-y/yRange*height);
			
			return new Point(actualX,actualY);
		}
	
		private double[] fromPixel(int pixel,int width,int height) {
	    	
			double actualX = ((double)pixel)/width*xRange+xIntervals[0][0];
			
	    	double[] position = {actualX};
			return position;
		}

		
		public void paintBackground() {
			
			System.out.println("background painted!");
			
			int backgroundWidth = getWidth();
			int backgroundHeight = getHeight();
			
			backgroundImage = new BufferedImage(backgroundWidth,backgroundHeight, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2 = (Graphics2D) backgroundImage.getGraphics();
			
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, backgroundWidth, backgroundHeight);
			
			if(yMin==-1){
				
				yMin=Double.POSITIVE_INFINITY;
				yMax=Double.NEGATIVE_INFINITY;
				
				for(int xPixel=0;xPixel<backgroundWidth;xPixel++){
					
						double[] position = fromPixel(xPixel,backgroundWidth,backgroundHeight);
						double value=function.value(position);
						
						yMin=value<yMin?value:yMin;
						yMax=value>yMax?value:yMax;
				}
				
				yRange = yMax-yMin;
			}
			
			g2.setColor(Color.BLACK);
			
			int prevX=-1,prevY=-1;
			
			for(int xPixel=0;xPixel<backgroundWidth;xPixel+=1){
				
				double[] position = fromPixel(xPixel,backgroundWidth,backgroundHeight);
				double value=function.value(position);
				double percent=(value-yMin)/yRange;
				int yPixel = (int)((1-percent)*backgroundHeight);
				
				if(prevX==-1){
					g2.fillRect(xPixel, yPixel,1,1);
				}else{
					g2.drawLine(prevX, prevY, xPixel, yPixel);
				}
				prevX=xPixel;
				prevY=yPixel;
			}
			
			g2.finalize();
			backgroundImage.flush();
		}

		@Override
		public void paint(Graphics g) {
			
			int particleWidth=4;
			int particleHeight=4;
			
			Graphics2D g2 = (Graphics2D) g;
			
			if(backgroundImage!=null){
				g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), 0, 0, getWidth(),getHeight(), null);
			}

			if(otherSolutions!=null){
				for (int i = 0; i < otherSolutions.length; i++) {
					Point point = toPixel(otherSolutions[i],getWidth(),getHeight());
					g2.setColor(Color.RED);
					g2.drawLine(point.x-3, point.y-3, point.x+3, point.y+3);
					g2.drawLine(point.x-3, point.y+3, point.x+3, point.y-3);
				}
			}
			
		    if(bestSolution!=null){
				Point point = toPixel(bestSolution,getWidth(),getHeight());
				g2.setColor(Color.BLUE);
				g2.drawLine(point.x-3, point.y-3, point.x+3, point.y+3);
				g2.drawLine(point.x-3, point.y+3, point.x+3, point.y-3);
		    }
		}
	}
	
	public void clearSolutions() {
		this.bestSolution=null;
		this.otherSolutions=null;
	}
	
	public static void main(String[] args) {

	}


}
