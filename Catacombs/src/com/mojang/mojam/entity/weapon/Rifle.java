package com.mojang.mojam.entity.weapon;

import com.mojang.mojam.entity.Player;

public class Rifle extends Weapon {
	
	public Rifle(Player player) {
		damage = 1;
		speed = 8;
		accuracy = 0.1;
		pushBack = 0.1;
		this.player = player;
		name = "Rifle";
		cost = 0;
		id = 1;
	}
}
