package com.mojang.mojam.entity.weapon;

import com.mojang.mojam.entity.Player;

public class Sniper extends Weapon {
	
	public Sniper (Player player) {
		damage = 8;
		speed = 40;
		accuracy = 0;
		pushBack = 0.5;
		this.player = player;
		name = "Sniper";
		cost = 3000;
		id = 2;
	}
}
