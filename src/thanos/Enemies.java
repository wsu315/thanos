package thanos;

import java.awt.Graphics;
import kareltherobot.*;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.shape.Circle;

public class Enemies extends GameObject {


	//the final int needs to be changed i'm too lazy rn to figure out the pixels of each ave and st
	static final int AVE_SQUARE_SIZE = 64, ST_SQUARE_SIZE = 64;
	private Rectangle hitbox;
	private int health, speed;
	private double startX, startY;
	private int ticks;
	private Direction direction;
	protected int currentX;
	protected int currentY;
	private boolean isDead = false;
	protected Image image;
	Robot r;
	private int count = 0;
	public int cost = 300;
	private int xPerT, yPerT;
	private int iSize=0;
	protected int size = 100;



	//the initial x and y are pixels. they are then converted to st/ave coordinates 
	//and passed into the robot constructor

	//when workng with the enemey class we need to be very clear is we are
	//working with a ave/st x/y or a x/y in pixels

	/*public Enemies(int h, int s, String str) {
		// TODO Auto-generated constructor stub
		super((int) ThanosGameRunner.st.getX(), (int) ThanosGameRunner.st.getY(), str);
		startX = (int) ThanosGameRunner.st.getX();
		startY = (int) ThanosGameRunner.st.getY();
		currentX = startX;
		currentY = startY;
		image = getImage(str);
		health = h;
		speed = s;
	}
	 */

	public Enemies(int h, int s, String str) {
		super((int)ThanosGameRunner.st.getX()-25,(int)ThanosGameRunner.st.getY()-25, str);
		startX = ThanosGameRunner.st.getX()-25;
		startY = ThanosGameRunner.st.getY()-25;
		currentX = (int)startX;
		currentY = (int)startY;
		health = h;
		speed = s;
		image = getImage(str);
	}

	protected Image getImage(String imgName) {
		Image img = null;
		try {
			img = ImageIO.read(this.getClass().getResource(imgName));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	private void isHit(Projectile p) {
		if(p.getHitBox().contains(hitbox)) {
			health -= p.getStrength();
		}
		if(health<=0) {
			destroy();
		}
	}

	private void destroy() {
		image = null;
	}

	private void updateX() {
		//updates the xLoc in pixels based off of the changed avenue
		currentX = r.avenue()*this.AVE_SQUARE_SIZE;
	}

	private void updateY() {
		//updates the yLoc in pixels based off the changed street
		currentY = r.street()*this.ST_SQUARE_SIZE;
	}

	public int getX() {
		return currentX;
	}



	public int getY() {
		return currentY;
	}
	//once it find that initial and finish points it doesn't need to keep on calculating x/y per t
	//it just nees to store that value and move by that

	public void moveXY() {

		if(count  ==0) {

			double totalX = (ThanosGameRunner.junc1.getX() - ThanosGameRunner.st.getX());

			double totalY = (ThanosGameRunner.junc1.getY() - ThanosGameRunner.st.getY());


			//int totalX = (int)(ThanosGameRunner.junc1.getX() - ThanosGameRunnerRunner.st.getX());
			//int totalY = (int)(ThanosGameRunnerRunner.junc1.getY() - ThanosGameRunnerRunner.st.getY());
			double dist = Math.sqrt((totalX*totalX)+(totalY*totalY));
			double time = (dist/speed);
			//this should actually be dependent on ticks
			xPerT =  (int)(totalX/time);
			yPerT = (int)(totalY/time);
			currentX += xPerT;
			currentY += yPerT;
			if(currentX > (ThanosGameRunner.junc1.getX())) {
				count++;
				//System.out.println("count0");
			}
		}
		if(count == 1) {
			//int totalX = (int)(ThanosGameRunner.junc2.getX() - currentX);
			//int totalY = (int)(ThanosGameRunner.junc2.getY() - currentY);
			double totalX = (ThanosGameRunner.junc2.getX() - ThanosGameRunner.junc1.getX() );
			double totalY = (ThanosGameRunner.junc2.getY() - ThanosGameRunner.junc1.getY());
			double dist = Math.sqrt((totalX*totalX)+(totalY*totalY));
			double time = (dist/speed);
			//this should actually be dependent on ticks
			xPerT = (int) Math.round(totalX/time);
			yPerT = (int) Math.round(totalY/time);
			currentX += xPerT;
			currentY += yPerT;
			if(currentY > (ThanosGameRunner.junc2.getY())) {
				count++;
				//System.out.println("count1");
			}

		}
		if(count == 2) {
			double totalX = (ThanosGameRunner.junc3.getX() - ThanosGameRunner.junc2.getX());
			//start, junction 1, junction 3

			double totalY = (ThanosGameRunner.junc3.getY() - ThanosGameRunner.junc2.getY());
			double dist = Math.sqrt((totalX*totalX)+(totalY*totalY));
			double time = (dist/speed);
			//this should actually be dependent on ticks

			xPerT = (int) Math.round(totalX/time);
			yPerT = (int) Math.round(totalY/time);

//			System.out.print(time);
//			System.out.println(totalY);

			currentX += xPerT;
			currentY += yPerT;
			if(currentY < ThanosGameRunner.junc3.getY()) {
				count++;
				//System.out.println("count2");
			}
			//System.out.println("yee haw");
		}
		if(count == 3) {
			//System.out.println("yeehaw");
			double totalX = (ThanosGameRunner.end.getX()- ThanosGameRunner.junc3.getX());

			double totalY = (ThanosGameRunner.junc3.getY() - ThanosGameRunner.end.getY());


			//int totalX = (int)(ThanosGameRunner.junc1.getX() - ThanosGameRunnerRunner.st.getX());
			//int totalY = (int)(ThanosGameRunnerRunner.junc1.getY() - ThanosGameRunnerRunner.st.getY());
			double dist = Math.sqrt((totalX*totalX)+(totalY*totalY));
			double time = (dist/speed);
			//this should actually be dependent on ticks
			xPerT =  (int)(totalX/time);
			yPerT = (int)(totalY/time);
			currentX += xPerT;
			currentY += yPerT;
			if(currentX > ThanosGameRunner.end.getX()) {
				count++;
				//System.out.println("count3");
			}
		}
	}
	
	public void add(Avengers a) {
		ticks += a.getV();
		
	}
	public int getTicks() {
		return ticks;
	}

	public int getMoveX() {
		moveXY();
		return (int)xPerT;
	}

	public int getMoveY() {
		moveXY();
		return (int)yPerT;
	}
	public void reduceSize(Avengers a) {
		iSize++;
		if(iSize == a.getV()*2) {
		size-=3;
		iSize = 0;
		}
		if(size<=0)
			size = 0;
		
	}
}




