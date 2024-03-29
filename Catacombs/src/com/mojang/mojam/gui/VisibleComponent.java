package com.mojang.mojam.gui;

import com.mojang.mojam.MouseButtons;
import com.mojang.mojam.screen.Screen;

public class VisibleComponent extends GuiComponent {
	
	private int x, y, w, h;
	
	@Override
	public void render(Screen screen) {
		super.render(screen);
	}
	
	@Override
	public void tick(MouseButtons mouseButtons) {
		super.tick(mouseButtons);
	}
	
	public VisibleComponent(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
}
