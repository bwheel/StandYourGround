package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import game.Program.STATE;

public class SaveData implements Serializable {

	private static final long serialVersionUID = 6194456681958310352L;
	
	private int money;
	private int moneyAtRoundStart;
	private int level;
	private LinkedList<Integer> magSizes;
	private LinkedList<Integer> ammoCaps;
	private LinkedList<Boolean> gunsOwned;
	
	public SaveData() {
		
	}
	
	public void saveToFile(String filename, PlayerObject player) {
		try {
	        FileOutputStream file = new FileOutputStream(filename);
	        ObjectOutputStream out = new ObjectOutputStream(file);
	         
			LinkedList<Gun> arsenal;
			magSizes = new LinkedList<Integer>();
			ammoCaps = new LinkedList<Integer>();
			gunsOwned = new LinkedList<Boolean>();
			money = player.getMoney();
			moneyAtRoundStart = player.getMoneyAtRoundStart();
			level = player.getLevel();
			arsenal = player.getArsenal();
			for(int i = 0; i < arsenal.size(); i++) {
				Gun g = arsenal.get(i);
				magSizes.add(i, g.getMagSize());
				ammoCaps.add(i, g.getAmmoCapacity());
				gunsOwned.add(i, g.getOwned());
			} 
	         
	        out.writeObject(this);
	        out.close();
	        file.close();
	        System.out.println("Level: "+ level + " Money: " + money + " MoneyAtStart: " + moneyAtRoundStart);
	        for (int i = 0; i < magSizes.size(); i++) {
				System.out.println("Mag Sizes: " + magSizes.get(i) + " Caps: " + ammoCaps.get(i));
	        }
	        System.out.println("Saved current game state: " + filename);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public SaveData loadFromFile(String filename) {
		try {
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			SaveData temp = (SaveData)in.readObject();
			in.close();
			file.close();
			System.out.println("Loaded game state from: " + filename);
			
			return temp;
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void setPlayerAfterLoad(PlayerObject player) {
		LinkedList<Gun> arsenal = player.getArsenal();
		player.setMoney(moneyAtRoundStart);
		player.setMoneyAtRoundStart(moneyAtRoundStart);
		player.setLevel(level);
		Program.spawnSys.setLevel(level);
		for(int i = 0; i < arsenal.size(); i++) {
			Gun g = arsenal.get(i);
			g.setMagSize(magSizes.get(i));
			g.setAmmoCapacity(ammoCaps.get(i));
			g.setOwned(gunsOwned.get(i));
			g.resetAmmo();
		}
		Program.gameState = STATE.StoreMenu;
	}

}
