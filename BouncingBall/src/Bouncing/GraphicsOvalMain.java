package Bouncing;
/** Developer: Khandaker Mamun Ahmed 
 * 	Date: 11-04-2014
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*import org.jdesktop.swingx.painter.AbstractLayoutPainter.HorizontalAlignment;
*/
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.luna.LunaLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.jtattoo.plaf.texture.TextureLookAndFeel;


public class GraphicsOvalMain extends JFrame{
	//private JPanel contentPane;
	private static GraphicsOval gh;
	private JLabel stant, timer, ballSize, dxBall, score;
	//private JTextField scoreCounter;
	private JSlider timeSlider, ballSizeSlider;
	private int xPos= 150;
	private JButton leftButton, rightButton;
	private static int scoreCount = 0;
	
	
	public static	JTextField scoreCounter = new JTextField(""+ scoreCount);
    JPanel	contentPane = new JPanel();
    
	GraphicsOvalMain(){
		//scoreCounter.setText(""+ scoreCounter);
	}
	
	public void setCounter(int score){
		scoreCounter.setText(""+ scoreCounter);
	}
	
	public void initCOmponent(){
		setTitle("Bouncing Ball");
		
		ImageIcon icon = new ImageIcon(getClass().getResource("icon.jpg"));  
        setIconImage(icon.getImage());  
		//contentPane = new JPanel();
		contentPane.setLayout(null);
		//contentPane.setBackground(Color.lightGray);
		
		gh = new GraphicsOval();
		gh.setLayout(null);
		gh.setBounds(0, 0, 480, 480);
		gh.addMouseMotionListener(new MyMouseAdapter());
		contentPane.add(gh);
		
		dxBall = new JLabel("**DX BALL**");
		dxBall.setBounds(500, 20, 170, 30);
		dxBall.setFont(new java.awt.Font("SansSerif", 1, 28));
		//dxBall.setForeground(new Color(180, 50, 200));
		contentPane.add(dxBall);
		
		stant = new JLabel();
		stant.setIcon(new ImageIcon(getClass().getResource("Stant.JPG")));
		stant.setBounds(xPos , 460, 150, 30);
		stant.addMouseMotionListener(new MyMouseAdapter());
		gh.add(stant);
		gh.setXpos(xPos);
		
		timer = new JLabel("Speed");
		timer.setBounds(530, 100, 100, 30);
		timer.setFont(new java.awt.Font("SansSerif", 1, 24));
		timer.setForeground(new Color(180, 50, 200));
		contentPane.add(timer);
		
		timeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 200, 0);
		timeSlider.setBounds(530, 130, 80, 15);
		timeSlider.setMinorTickSpacing(2);
		timeSlider.setMajorTickSpacing(5);
		timeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(timeSlider.getValue() < 1)
					gh.setMotionSpeed(1);
				else if(timeSlider.getValue() > 250)
					gh.setMotionSpeed(250);
				else
					gh.setMotionSpeed(timeSlider.getValue());
			}});
		contentPane.add(timeSlider);
		
		ballSize = new JLabel("Ball Size");
		ballSize.setBounds(530, 150, 100, 30);
		ballSize.setFont(new java.awt.Font("SansSerif", 1, 24));
		ballSize.setForeground(new Color(180, 50, 200));
		contentPane.add(ballSize);
		
		ballSizeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 200, 0);
		ballSizeSlider.setBounds(530, 180, 50, 15);
		ballSizeSlider.setMajorTickSpacing(1);
		ballSizeSlider.setMinorTickSpacing((int) .5);
		ballSizeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(ballSizeSlider.getValue() < 15)
					gh.ballSize(15, 10);
				else if(ballSizeSlider.getValue() > 70)
					gh.ballSize(70, 55);
				else
					gh.ballSize(ballSizeSlider.getValue(), (ballSizeSlider.getValue()*2)/ 3);
			}});
		contentPane.add(ballSizeSlider);

		leftButton = new JButton();
		leftButton.setIcon(new ImageIcon(getClass().getResource("left.JPG")));
		leftButton.setBounds(530, 220, 50, 30);
		leftButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				if(xPos < 0 )
					xPos = 0;
				else
					xPos -= 10;
				gh.setXpos(xPos);
				stant.setBounds(xPos , 460, 150, 30);
			}
		});
		contentPane.add(leftButton);

		rightButton = new JButton();
		rightButton.setIcon(new ImageIcon(getClass().getResource("Right.JPG")));
		rightButton.setBounds(580, 220, 50, 30);
		rightButton.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e) {
				if(xPos > 330)
					xPos = 330;
				else
					xPos += 10;
				gh.setXpos(xPos);
				stant.setBounds(xPos , 460, 150, 30);
			}
		});
		contentPane.add(rightButton);
		
		score = new JLabel("Score");
		score.setFont(new java.awt.Font("SansSerif", 1, 24));
		score.setForeground(new Color(180, 50, 200));
		score.setBounds(530, 280, 70, 30);
		contentPane.add(score);
		
		//scoreCounter.setHorizontalAlignment(JTextField.CENTER);
		scoreCounter.setEditable(false);
		//scoreCounter.setFont(new java.awt.Font("SansSerif", 1, 24));
		scoreCounter.setForeground(new Color(180, 50, 200));
		scoreCounter.setBounds(540, 320, 50, 30);
		contentPane.add(scoreCounter);
		
		setContentPane(contentPane);
		setVisible(true);
		setResizable(false);
		setSize(700, 520);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class MyMouseAdapter extends MouseAdapter {
		   
		   public void mouseMoved(MouseEvent e) {
			   xPos = e.getX();
			   
			   //scoreCounter.setText(""+ scoreCount);
				
			   //System.out.println("\t\t\t\t"+xPos);
			   gh.setXpos(e.getX());
			     stant.setBounds(xPos , 460, 150, 30);
		   }
		
		
		@Override
		   public void mouseDragged(MouseEvent e) {
		     xPos = e.getX();
		     if(xPos< 0)
		    	 xPos = 0;
		     else if(xPos> 400)
		    	 xPos = 250;
		     stant.setBounds(xPos , 460, 150, 30);
		   }

	}
	
	public int getxPos() {
		return xPos;
	}

//	public void setCounter(int score){
//		this.scoreCount = score;
//		System.out.println(scoreCount+"            :)");		
//	}

	
	
	public static void main(String[] args) {
		try {
		//	UIManager.setLookAndFeel(new BernsteinLookAndFeel());
		//	UIManager.setLookAndFeel(new AcrylLookAndFeel());
		//	UIManager.setLookAndFeel(new AluminiumLookAndFeel());
		//	UIManager.setLookAndFeel(new FastLookAndFeel());
		//	UIManager.setLookAndFeel(new GraphiteLookAndFeel());
		//	UIManager.setLookAndFeel(new LunaLookAndFeel());
			UIManager.setLookAndFeel(new McWinLookAndFeel());
		//	UIManager.setLookAndFeel(new MintLookAndFeel());
		//	UIManager.setLookAndFeel(new NoireLookAndFeel());
		//	UIManager.setLookAndFeel(new SmartLookAndFeel());
		//	UIManager.setLookAndFeel(new TextureLookAndFeel());
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		new GraphicsOvalMain().initCOmponent();
		
		ExecutorService e1 = Executors.newCachedThreadPool();
		//ExecutorService e2 = Executors.newCachedThreadPool();
		
		
		e1.execute(gh);
		//e2.execute(new Timer());
		
		e1.shutdown();
		//e2.shutdown();
	}
}
