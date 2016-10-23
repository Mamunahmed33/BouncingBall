package Bouncing;
/** Developer: Khandaker Mamun Ahmed 
 * 	Date: 11-04-2014
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

public class GraphicsOval extends JPanel implements Runnable{
	private int width = 30, height = 20;
//	private GraphicsOvalMain graphicsOvalMain = new GraphicsOvalMain();

	private Vector<Integer> vecX= new Vector<Integer>();									// X position
	private Vector<Integer> vecY= new Vector<Integer>();									// Y position
	private Vector<Integer> vecBooleanX= new Vector<Integer>();							//check wheather x will need to increment(for 0) or decrement(for 1)
	private Vector<Integer> vecBooleanY= new Vector<Integer>();							//check wheather Y will need to increment(for 0) or decrement(for 1)
	
	private int timeSpeedUp = 0;
	private int timer = 20;
	private boolean generateBall;
	private boolean exit = true;
	private int xPos;
	private int setScore= 0;
	private Random randomGenerator = new Random();

	public synchronized void  paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(generateBall == false)
			for(int i = 0; i<10; i++){
				vecX.add(randomGenerator.nextInt(480));
				vecY.add(randomGenerator.nextInt(400));
				vecBooleanX.add(i % 2);
				vecBooleanY.add(0);
				
				// Not to generate ball all at once
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//System.out.println(vecBooleanX.get(i) +"  "+ vecX.get(i));	
			}
		
		for(int i = 0; i< vecX.size(); i++){
			g.setColor(Color.BLUE);
			g.fillOval(vecX.get(i), vecY.get(i), width, height);
		}
			this.generateBall = true;
			
			exitCheck();
	}
	
	public void run() {
		while(exit){
			
			timeSpeedUp++;
			this.checkValidation();
			
			//System.out.println(vecBooleanY.size()-1);
			
			for(int i= 0 ; i <= vecY.size() - 1; i++){
				if(vecBooleanX.get(i) == 0)
					vecX.set(i, vecX.get(i)+1);
				else if(vecBooleanX.get(i) == 1)
					vecX.set(i, vecX.get(i)-1);
				
				if(vecBooleanY.get(i) == 0)
					vecY.set(i, vecY.get(i)+1);
				else if(vecBooleanY.get(i) == 1)
					vecY.set(i, vecY.get(i)-1);
			}
			
			try {
				Thread.sleep(timer);
				
				if(timeSpeedUp%200 ==0 && timer > 1){
					timer--; timeSpeedUp=0;
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			repaint();
		}
	}

	
	private synchronized void checkValidation(){								// sets the x and y position boolean
		for(int i = 0; i<= vecX.size()-1; i++){
			
			if(vecY.get(i) == 0){
				vecBooleanY.set(i, 0);
				vecY.set(i, vecY.get(i)+3);
				//System.out.println("Hiii");
			}
			else if(vecX.get(i) >= (xPos-20) && vecX.get(i) <=  xPos+150 && vecY.get(i) == (480-(height+10))){
				vecBooleanY.set(i, 1);    
				//System.out.println("OOOW");
				
				setScore++;
				//graphicsOvalMain.setCounter(setScore);
				GraphicsOvalMain.scoreCounter.setText(""+setScore);
			}
			
			if(vecX.get(i) == (480 -width)){
				vecBooleanX.set(i, 1);
				//System.out.println("harder");
			}
			
			else if(vecX.get(i) == 0)
				vecBooleanX.set(i, 0);
			
			if(vecX.get(i) <  xPos-20 && vecY.get(i) == 480){
				
				//System.out.println( vecX.get(i)+ "  Hi  "+ new GraphicsOvalMain().getxPos()+ "   "+vecY.get(i) + "  " +((480-(height+10))));
				vecX.remove(i);
				vecY.remove(i);
				vecBooleanX.remove(i);
				vecBooleanY.remove(i);
			}
			
			else if(vecX.get(i) > (xPos+150) && vecY.get(i) == 480)
			{
				//System.out.println( vecX.get(i)+ "  Hello  "+ (xPos +150)+  "   " +vecY.get(i) + "  " +((480-height)));
				vecX.remove(i);
				vecY.remove(i);
				vecBooleanX.remove(i);
				vecBooleanY.remove(i);
			}
		}
	}
	
	public void setMotionSpeed(int value) {
		timer = value;
	}
	
	public void ballSize(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void setXpos(int x){
		this.xPos = x;
	}
	
	private void exitCheck(){									// checks when to exit the program
		if(vecX.isEmpty())
			exit = false;
	}
}

