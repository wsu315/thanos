
package thanos;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import kareltherobot.*;


public class ThanosGameRunner {
	private JPanel panel;
	private ThanosGame game = new ThanosGame();
	private Timer timer;
	private int ticks;
	private int r;
	private int c;
	private Image img = getImage();
	GameLevel l = new GameLevel();
	Enemies s = new Enemies((int) (GameLevel.st.getX()),(int) (GameLevel.st.getY()), 10, 10, "thanos.jpg");


	
	// Notice this intuitive method for finding the screen size 
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) (screenSize.getWidth()*3/4),HEIGHT=(int) (screenSize.getHeight()*3/4);
	private static final int REFRESH_RATE = 10;

	public ThanosGameRunner() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					start();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
				}
			}
		});
	}

	private Image getImage(
			) {
		try {
			
			img = ImageIO.read(this.getClass().getResource("avenger.jpg"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
		
	}

	public static void main(String[] args) {
		ThanosGameRunner s = new ThanosGameRunner();
	}

	private void start() {
		JFrame frame = new JFrame("ThanosGame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, frame.getWidth(), frame.getHeight(), null);
			
				drawGame(g);
			}
		};
		// random color to the background
		
		
		//panel.setBackground(new Color(255, 182, 193));
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				clickedAt(me);				
				frame.repaint();
			}
		});
		// so that the frame isn't minimized
		panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		// so that the frame is placed a little way from top and left side
		frame.setLocation(WIDTH/10, HEIGHT/10);

		// map the keystrokes that the panel detects to the game
		mapKeyStrokesToActions(panel);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		// after setting visible to true, you can get focus.  You need focus to consume
		// the keystrokes hit by the user
		panel.requestFocusInWindow();
		
		// this timer controls the actions in the game and then repaints after each update to data
		timer = new Timer(REFRESH_RATE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateGame();
				
				panel.repaint();
			}

			
		});
		timer.start();
	}
	
	protected void clickedAt(MouseEvent me) {
		r = me.getX();
		c = me.getY();
		System.out.print(me);
		panel.repaint();
		
		
	}
	
	
	// this method is called every time the timer goes off (which right now is every 10 milliseconds = 100 times per second
	protected void updateGame() {
		ticks++;// keeps track of the number of times the timer has gone off
		
		int hurts = 1000/REFRESH_RATE;
		
		
		if(ticks %hurts == 0) {
			System.out.println(ticks/hurts+" seconds");
		}
		
		for(Avengers a : game.avengers) {
			if (ticks % hurts == 0 && (ticks%hurts)%a.getV() == 0) {
				a.process(game);
			}
		}
	}

	private void mapKeyStrokesToActions(JPanel panel) {

		// A map is an Data storage interface which defines
		// an association of a key with a value
		// to "add" to a map you use the "put" method
		// to "get" from a map you use "get(key)" and the 
		// value associated with the key is returned (or null)
		ActionMap map = panel.getActionMap();
		InputMap inMap = panel.getInputMap();

		// code below associates pressing the up arrow with the command "up"
		// essentially creating the command "up" being broadcast any time the 
		// up key is hit
		inMap.put(KeyStroke.getKeyStroke("pressed UP"), "up");
		inMap.put(KeyStroke.getKeyStroke("pressed W"), "up");
		// code below associates the "up" action with anything in the 
		// actionPerformed method.  Right now, it just prints something
		map.put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hit("up");
			}

			
		});
		panel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"left");
		panel.getActionMap().put("left",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				hit("left");
			}
		});

	}
	public  void hit(String s) {
	//	game.keyHit(s);
		
		panel.repaint();
	}
	protected void drawGame(Graphics g) {
		//g.drawLine(130, 500, r, c);
		game.draw(g);
		l.draw(g);
		s.draw(g);
		//game.draw(g);
	}

	private void move() {
		// TODO Auto-generated method stub
		for(Enemies e : game.enemies) {
			e.move(0, 0);
		}
		for(Avengers a : game.avengers) {
			//l.firedProjectiles;
			for(Projectile p : a.getFired()) {
				(p).move(0, 0) ;
			}
		}
	}
	
	

}
