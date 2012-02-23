package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mojang.mojam.MojamComponent;
import com.mojang.mojam.MouseButtons;
import com.mojang.mojam.entity.Player;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class GamePowers extends GuiMenu {
	
	private static Player player;
	public static List<Note> notes = new CopyOnWriteArrayList<Note>();
	private static GamePowers instance = null;
	public static int gameWidth;
	public static int gameHeight;
	private int count = 0;

	public GamePowers(int gameWidth, int gameHeight, Player player) {
		super();
		GamePowers.player = player;
		GamePowers.gameHeight = gameHeight;
		GamePowers.gameWidth = gameWidth;
		addButton(new Button(999, "Close", gameWidth - 147, gameHeight - 45));
		addButton(new Button(998, "1500", 55, 70));
		addButton(new Button(997, "2000", 55, 120));
		addButton(new Button(996, "1000", 55, 170));
		addButton(new Button(995, "Weapons", gameWidth - 280, gameHeight - 45));
	}
	
	@Override
	public void render(Screen screen) {
		screen.clear(0);
		screen.blit(Art.upgradesBg, 0, 0);
		super.render(screen);
		Font.draw(screen, "Increase Damage", 55, 55);
		Font.draw(screen, "Remove Push Back", 55, 105);
		Font.draw(screen, "Increase Loot Range", 55, 155);
		
		Iterator<com.mojang.mojam.gui.GamePowers.Note> it = notes.iterator();
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
	
	public static synchronized GamePowers getInstance() {
        if (instance == null) {
            instance = new GamePowers(gameWidth, gameHeight, player);
        }

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
                GamePowers.notes.remove(this);
            }
        }
    }

	@Override
	public void buttonPressed(ClickableComponent component) {
		if (component instanceof Button) {
			final Button button = (Button) component;
			if (count == 0) {
				count = 1;
				if (button.getId() == 999) {
					MojamComponent.menuStack.remove(this);
					player.setImmortal(false);
					notes = new CopyOnWriteArrayList<Note>();
				} else if (button.getId() == 998) {
					if (player.useMoney(1500)) {
						player.setDamageMultiplier(player.getDamageMultiplier() + 1);
						notes.add(new Note("Purchase Complete", 150));
					}
					else notes.add(new Note("You do not have enough money", 150));
				} else if (button.getId() == 997) {
					if (player.getPushBack() != 1) {
						if (player.useMoney(2000)) {
							player.setPushBack(1);
							notes.add(new Note("Purchase Complete", 150));
						}
						else notes.add(new Note("You do not have enough money", 150));
					}
				} else if (button.getId() == 996) {
					if (player.useMoney(1000)) {
						player.setSuckingRadius(player.getSuckingRadius() + 20);
						notes.add(new Note("Purchase Complete", 150));
					}
					else notes.add(new Note("You do not have enough money", 150));
				} else if (button.getId() == 995) {
					GuiMenu menu = new WeaponScreen(gameWidth, gameHeight, player);
					MojamComponent.menuStack.add(menu);
	                menu.addButtonListener(menu);
				}
			} else count = 0;
		}
	}
}
