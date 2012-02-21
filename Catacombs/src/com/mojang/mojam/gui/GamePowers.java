package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;

import com.mojang.mojam.MojamComponent;
import com.mojang.mojam.entity.Player;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class GamePowers extends GuiMenu {
	
	private Player player;

	public GamePowers(int gameWidth, int gameHeight, Player player) {
		super();
		this.player = player;
		addButton(new Button(999, 5, gameWidth - 147, gameHeight - 45));
		addButton(new Button(998, 7, 55, 70));
		addButton(new Button(997, 8, 55, 120));
	}
	
	@Override
	public void render(Screen screen) {
		screen.clear(0);
		screen.blit(Art.upgradesBg, 0, 0);
		super.render(screen);
		Font.draw(screen, "Increase Damage", 55, 55);
		Font.draw(screen, "Remove Push Back", 55, 105);
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
    public void buttonPressed(Button button) {
		if (button.getId() == 999) {
			MojamComponent.menuStack.remove(this);
			player.setImmortal(false);
		} else if (button.getId() == 998) {
			if (player.useMoney(1500)) player.setDamageMultiplier(player.getDamageMultiplier() + 1.5);
		} else if (button.getId() == 997) {
			if (player.getPushBack() != 1) {
				if (player.useMoney(2000)) player.setPushBack(1);
			}
		}
    }
}
