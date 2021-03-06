package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class MouseMotionInput extends MouseMotionAdapter {

	private Handler handler;
	//private PlayerObject player; 
	private ReticleObject reticle; 
	public MouseMotionInput(Handler h) {
		handler = h;
		try {
			//player = (PlayerObject)handler.getObjectAt(0);
			reticle = (ReticleObject)handler.getObjectAt(1);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void mouseMoved(MouseEvent e) {
		reticle.setX(e.getX());
		reticle.setY(e.getY());
	}
	public void mouseDragged(MouseEvent e) {
		reticle.setX(e.getX());
		reticle.setY(e.getY());
	}

}
