package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mojang.mojam.MojamComponent;
import com.mojang.mojam.MouseButtons;
import com.mojang.mojam.entity.Player;
import com.mojang.mojam.entity.weapon.Weapon;
import com.mojang.mojam.gui.GamePowers.Note;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class WeaponScreen extends GuiMenu {
	
	private static int gameWidth;
	private static int gameHeight;
	private static Player player;
	private int count = 0;
	private List<Integer> buttonId = new ArrayList<Integer>();
	public static List<Note> notes = new CopyOnWriteArrayList<Note>();
	private static WeaponScreen instance = null;
	
	public WeaponScreen(int gameWidth, int gameHeight, Player player) {
		super();
		WeaponScreen.gameWidth = gameWidth;
		WeaponScreen.gameHeight = gameHeight;
		WeaponScreen.player = player;
		addButton(new Button(999, "Close", gameWidth - 147, gameHeight - 45));
	}
	
	public void render(Screen screen) {
		screen.clear(0);
		screen.blit(Art.upgradesBg, 0, 0);
		addWeaponsToScreen(screen);
		super.render(screen);
		Iterator<Note> it = notes.iterator();
        while (it.hasNext()) {
            Note note = (Note) it.next();
            Font.draw(screen, note.message, (MojamComponent.GAME_WIDTH / 2) - (Font.getStringWidth(note.message) / 2), MojamComponent.GAME_HEIGHT - 72);
        }
	}
	
	@Override
	public void tick(MouseButtons mouseButtons) {
		super.tick(mouseButtons);
		for (Note n : notes) {
            n.tick();
        }
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buttonPressed(ClickableComponent component) {
		if (component instanceof Button) {
			Button button = (Button) component;
			if (count == 0) {
				count = 1;
				if (button.getId() == 999) {
					MojamComponent.menuStack.remove(this);
				} else {
					for (Weapon w : Weapon.getAllWeapons(player)) {
						if (button.getId() == w.getId()) {
							if (player.useMoney(w.getCost())) {
								player.setCurrentWeapon(w);
							} else notes.add(new Note("You do not have enough money", 150));
						}
					}
				}
			} else count = 0;
		}
	}
	
	private void addWeaponsToScreen(Screen screen) {
		int width = 22;
		int height = 21;
		int count = 1;
		for (Weapon w : Weapon.getAllWeapons(player)) {
			screen.blit(Art.weaponBg, width, height);
			Font.drawCentered(screen, w.getName(), (155 / 2) + width, height + 10);
			Font.draw(screen, "Damage", width + 5, height + 17);
			screen.blit(Art.weaponStats100[0][(int) w.getDamage() * 10], width + Font.getStringWidth("Push Back") + 5, height + 17);
			Font.draw(screen, "Speed", width + 5, height + 30);
			screen.blit(Art.weaponStats45[0][(int) 45 - w.getSpeed()], width + Font.getStringWidth("Push Back") + 5, height + 30);
			Font.draw(screen, "Accuracy", width + 5, height + 43);
			screen.blit(Art.weaponStats100[0][(int) (100 - (w.getAccuracy() * 100))], width + Font.getStringWidth("Push Back") + 5, height + 43);
			Font.draw(screen, "Push Back", width + 5, height + 56);
			screen.blit(Art.weaponStats100[0][(int) (100 * w.getPushBack())], width + Font.getStringWidth("Push Back") + 5, height + 56);
			addButtonOnce(new Button(w.getId(), "Buy " + String.valueOf(w.getCost()), ((155 / 2) + width) - 64, height + 68));
			if (count == 3) {
				count = 1;
				height += 102;
				width = 22;
				continue;
			}
			count++;
			width += 157;
		}
	}
	
	private void addButtonOnce(Button button) {
		if (buttonId.contains(button.getId())) return;
		buttonId.add(button.getId());
		addButton(button);
	}
	
	public static synchronized WeaponScreen getInstance() {
        if (instance == null) instance = new WeaponScreen(gameWidth, gameHeight, player);
        return instance;
    }
	
	public class Note {
        public String message;
        public int life;

        public Note(String message, int life) {
            this.message = message;
            this.life = life;
        }

        public void tick() {
            if (life-- <= 0) {
                notes.remove(this);
            }
        }
    }
}