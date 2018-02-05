package hr.tennis.bot.optimizer.function.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AlgorithmGUI extends JFrame {

	public static final int STATE_NONE=-1;
	public static final int STATE_STEP=0;
	public static final int STATE_AUTO=1;
	
	protected JPanel optionsPanel;
	
	private Integer stepState=STATE_NONE;
	private JButton buttonStep;
	private JButton buttonAuto;
	
	private String[] strategyNames;
	private JComboBox strategySelect;
	
	public AlgorithmGUI(String[] strategyNames) {
		this.strategyNames = strategyNames;
	}
	
	public void init(){
		
		optionsPanel = new JPanel();
		
		buttonStep = new JButton("Step");
		buttonStep.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(stepState==STATE_AUTO){
					buttonAuto.setEnabled(true);
					stepState=STATE_NONE;
				}else if(stepState==STATE_NONE){
					buttonStep.setEnabled(false);
					stepState=STATE_STEP;
				}
			}
		});
		
		buttonAuto = new JButton("Auto");
		buttonAuto.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				buttonAuto.setEnabled(false);
				stepState=STATE_AUTO;
			}
		});
		
		optionsPanel.add(buttonStep);
		optionsPanel.add(buttonAuto);
		
		if(strategyNames!=null){
			strategySelect = new JComboBox(strategyNames);
			optionsPanel.add(strategySelect);
		}
		
		add(optionsPanel,BorderLayout.PAGE_END);

	}
	
	public int getStepState() {
		return stepState;
	}
	
	public void setStepState(Integer stepState) {
		if(stepState.equals(STATE_NONE)){
			buttonAuto.setEnabled(true);
			buttonStep.setEnabled(true);
		}
		this.stepState = stepState;
	}
	
	public String getCurrentStrategy(){
		if(strategySelect!=null){
			return (String)strategySelect.getSelectedItem();
		}else{
			return null;
		}
	}
}
