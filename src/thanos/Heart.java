package thanos;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Heart {

	public int num = 5;
	private Image img;
	
	public Heart() {
		img = getImage("heart.png");
	}
	
	private Image getImage(String str) {
		try {
			img = ImageIO.read(this.getClass().getResource(str));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i<num; i++) {
			g.drawImage(img, (int)ThanosGameRunner.WIDTH-270-(50*i), (int)ThanosGameRunner.HEIGHT-100, 50, 50, null);
		}
	}
	public int getNum() {
		return num;
	}
}
