package com.mojang.mojam.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.mojam.MouseButtons;

public abstract class ClickableComponent extends VisibleComponent {
	
	private List<ButtonListener> listeners;
	
	private boolean isPressed;
	private boolean performClick = false;
	
	public ClickableComponent(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	@Override
	public void tick(MouseButtons mouseButtons) {
		super.tick(mouseButtons);
		
		int mx = mouseButtons.getX() / 2;
		int my = mouseButtons.getY() / 2;
		isPressed = false;
		if (mx >= getX() && my >= getY() && mx < (getX() + getW()) && my < (getY() + getH())) {
			if (mouseButtons.isRelased(1)) {
				postClick();
			} else if (mouseButtons.isDown(1)) {
				isPressed = true;
			}
		}
		
		if (performClick) {
			clicked(mouseButtons);
			if (listeners != null) {
				for (ButtonListener listener : listeners) {
					listener.buttonPressed(this);
				}
			}
			performClick = false;
		}
	}
	
	protected void postClick() {
		performClick = true;
	}
	
	public boolean isPressed() {
		return isPressed;
	}
	
	public void addListener(ButtonListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<ButtonListener>();
		}
		listeners.add(listener);
	}
	
	protected abstract void clicked(MouseButtons mouseButtons);
}
