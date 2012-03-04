package com.mojang.mojam.gui;

import java.awt.event.KeyEvent;

import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Screen;

public class PauseMenu extends GuiMenu {
	
	private int selectedItem = 0;
	private final int gameWidth;
	
	private Button resumeButton;
	
	public PauseMenu(int gameWidth, int gameHeight) {
		super();
		this.gameWidth = gameWidth;
		
		resumeButton = (Button) addButton(new Button(TitleMenu.RETURN_ID, "Resume", (gameWidth - 128) / 2, 200));
		addButton(new Button(TitleMenu.RETURN_TO_TITLESCREEN, "Exit", (gameWidth - 128) / 2, 230));
	}
	
	public void render(Screen screen) {
		screen.blit(Art.upgradesBg, 0, 0);
		super.render(screen);
		screen.blit(Art.lordLard[0][6], (gameWidth - 128) /2 - 40, 190 + selectedItem * 40);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			selectedItem--;
			if (selectedItem < 1) selectedItem = 0;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			selectedItem++;
			if (selectedItem > 1) selectedItem = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			buttons.get(selectedItem).postClick();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resumeButton.postClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buttonPressed(ClickableComponent button) {
		// TODO Auto-generated method stub
		
	}

}
