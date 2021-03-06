package thanos;
import kareltherobot.*;
import kareltherobot.Robot;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.*;
import java.util.ArrayList;

import thanos.GameObject;
import thanos.Projectile;
import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.shape.Circle;

public class Avengers extends GameObject{
	private int x, y;
	private int v;
	private int refresh;
	private int vX, vY;
	protected Image img;
	private Circle range;
	private Circle clickedCircle;
	private boolean firstClick = true;
	private ArrayList<Projectile> firedProjectiles = new ArrayList<Projectile>();

	protected Image getImage(String imgName) {
		try {
			img = ImageIO.read(this.getClass().getResource(imgName));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public Avengers(int x, int y, int vel, int r, String str) {
		super(x, y, str);
		v = vel;
		range = new Circle();
		range.setCenterX(x);
		range.setCenterY(y);
		range.setRadius(r);
	}
	
	public Avengers(int x, int y, int vel, int r, int ref, String str) {
		super(x, y, str);
		v = vel;
		refresh = ref;
		range = new Circle();
		range.setCenterX(x);
		range.setCenterY(y);
		range.setRadius(r);
	}
	
	public int getV() {
		return v;
	}
	
	
	public void draw(Graphics g) {
		g.drawImage(img, locX , locY, 100, 100, null);

	}
	public double radius() {
		return this.range.getRadius();
	}
	//not done
	public boolean isInCirc(Enemies e) {
		//if enemy xyloc is in circle return true
		if (range.contains(e.getX(), e.getY()))
			return true;
		return false;
	}
	
	//not done
	public Projectile makeProjectile() {
		return new Projectile(x,y, refresh, null);
	}
	void shoot(Enemies e) {
		Projectile p = this.makeProjectile();
		p.finishX = e.getX();
		p.finishY = (int) e.getY();
		p.launch();
		firedProjectiles.add(p);
	}
	void shooting() {
		for(Projectile p : firedProjectiles) {
			p.move(p.dX, p.dY);
		}
	}
	//the process the avenger every time each time elapsed = speed;
	public void process(ThanosGame g) {
		//mod timer % refresh
		//the mod goes outside the process. i think it goes int the game runner?
		for (int i = 0; i < g.enemies.size() ; i++) {
			if(this.isInCirc(g.enemies.get(i))) {
				shoot(g.enemies.get(i));
				break;
			}
		}
		
	}


	private void setLoc(int X, int Y) {
		locX = X;
		locY = Y;
	}
	/*
	 * 
	 * this is for when we want to to show the range so this like hovers over
	public void mouseMoved(MouseEvent e){
		if(circle.contains(e.getPoint())){
			//the mouse pointer is over the circle. So set a Message or whatever you want to do
			msg = "You are over circle 1";
		}else{
			msg = "You are not over the circle";
		}
	}
	*/

	public void setVelocityXY(Enemies e) {
		int xDis = e.getX()-this.locX;
		int yDis = e.getY()-this.locY;
		int dis = Math.abs(xDis*xDis + yDis*yDis);
		vX = v*xDis/dis;
		vY = v*yDis/dis;

	}
	
	public ArrayList<Projectile> getFired(){
		return this.firedProjectiles;
	}
	
	/*public void mousePressed(MouseEvent me) {
		clickedAt(me);
		if (range.contains(me.getX(), me.getY()) && firstClick) {
			this.secondClick());
		}
		//frame.repaint();
	}*/

	
	private void secondClick(int x2, int y2) {
		// TODO Auto-generated method stub
		
	}

	private void clickedAt(MouseEvent me) {
		System.out.print(me);
		
	}

}
