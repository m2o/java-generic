package hr.tennis.bot.optimizer.function.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JPanel;




public class ParettoFrontGUI extends AlgorithmGUI {

	private static final long serialVersionUID = 1L;
	
	protected JPanel optionsPanel;
	private FunctionJComponent objectiveComponent;
	private FunctionJComponent decisionComponent;

	public ParettoFrontGUI(double[][] objectiveIntervals,double[][] decisionIntervals) {
		super(null);
		objectiveComponent = new FunctionJComponent(objectiveIntervals);
		decisionComponent = new FunctionJComponent(decisionIntervals);
	}
	
	public void init(){
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Objective space / Decision space");
		this.setSize(1200,600);
		
		super.init();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		
		panel.add(objectiveComponent);
		panel.add(decisionComponent);
		
		add(panel,BorderLayout.CENTER);

		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
//				objectiveComponent.repaint();
//				decisionComponent.repaint();
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
	
	public void setObjectiveSolutions(double[][] objectiveSolutions) {
		this.objectiveComponent.setSolutions(objectiveSolutions);
	}
	
	public void setDecisionSolutions(double[][] decisionSolutions){
		this.decisionComponent.setSolutions(decisionSolutions);
	}
	
	public void setFronts(int[] fronts){
		this.decisionComponent.setFronts(fronts);
		this.objectiveComponent.setFronts(fronts);
	}
	
	@Override
	public void repaint() {
		super.repaint();
		this.decisionComponent.repaint();
		this.objectiveComponent.repaint();
	}
	
	private class FunctionJComponent extends JComponent{
		
		private static final long serialVersionUID = 1L;
		
		private double xRange;
		private double yRange;
		
		private double[][] xIntervals;
		
		private double[][] solutions;
		private int[] fronts;
			
		public FunctionJComponent(double[][] intervals) {
			this.xIntervals = intervals;
			this.xRange=xIntervals[0][1]-xIntervals[0][0];
			this.yRange=xIntervals[1][1]-xIntervals[1][0];
		}
		
		private Point toPixel(double[] var,int width,int height) {
		
			double x=var[0];
			double y=var[1];
			
			x = (x-xIntervals[0][0])/xRange;
	    	y = (y-xIntervals[1][0])/yRange;
	    	
			int actualX=(int) (x*width);
	    	int actualY=(int) (height-y*height);
			
			return new Point(actualX,actualY);
		}

		@Override
		public void paint(Graphics g) {
			
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, getWidth(),getHeight());

			double[] zero = {0.0,0.0};
			Point point = toPixel(zero,getWidth(),getHeight());
			
			g2.setColor(Color.BLACK);
			g2.drawLine(point.x,0, point.x, getHeight());
			g2.drawLine(0,point.y, getWidth(),point.y);
			
			if(solutions!=null){
				for (int i = 0; i < solutions.length; i++) {
					
					if(fronts[i]%5==0){
						g2.setColor(Color.RED);
					}else if(fronts[i]%5==1){
						g2.setColor(Color.BLUE);
					}else if(fronts[i]%5==2){
						g2.setColor(Color.GREEN);
					}else if(fronts[i]%5==3){
						g2.setColor(Color.ORANGE);
					}else if(fronts[i]%5==4){
						g2.setColor(Color.CYAN);
					}
					
					point = toPixel(solutions[i],getWidth(),getHeight());
					g2.fillOval(point.x-2, point.y-2, 4, 4);
				}
			}
		}

		public void setSolutions(double[][] solutions) {
			this.solutions = solutions;
		}

		public void setFronts(int[] fronts) {
			this.fronts = fronts;
		}
	}
	
	
}
