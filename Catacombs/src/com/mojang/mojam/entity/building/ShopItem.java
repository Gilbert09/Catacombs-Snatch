package com.mojang.mojam.entity.building;

import com.mojang.mojam.MojamComponent;
import com.mojang.mojam.entity.*;
import com.mojang.mojam.entity.mob.Team;
import com.mojang.mojam.gui.ButtonListener;
import com.mojang.mojam.gui.Font;
import com.mojang.mojam.gui.GamePowers;
import com.mojang.mojam.gui.GuiMenu;
import com.mojang.mojam.gui.HostingWaitMenu;
import com.mojang.mojam.screen.*;


public class ShopItem extends Building {

    private int facing = 0;

    public static final int SHOP_TURRET = 0;
    public static final int SHOP_HARVESTER = 1;
    public static final int SHOP_BOMB = 2;
    public static final int SHOP_POINT = 3;

    public static final int[] COST = {
            150, 0, 500, 0
    };

    private final int type;

    public ShopItem(double x, double y, int type, int team) {
        super(x, y, team);
        this.type = type;
        isImmortal = true;
        if (team == Team.Team1) {
            facing = 4;
        }
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);
        Bitmap image = getSprite();
        if (type != SHOP_POINT) Font.draw(screen, "" + COST[type], (int) (pos.x - image.w / 2) + 3, (int) (pos.y + 7));
        else Font.draw(screen, "Upgrade", (int) (pos.x - image.w / 2) + 3, (int) (pos.y + 7));
    }

    public void init() {
    }

    public void tick() {
        super.tick();
    }

    public Bitmap getSprite() {
        switch (type) {
        case SHOP_TURRET:
            return Art.turret[facing][0];
        case SHOP_HARVESTER:
            return Art.harvester[facing][0];
        case SHOP_BOMB:
            return Art.bomb;
        case SHOP_POINT:
        	return Art.points;
        }
        return Art.turret[facing][0];
    }

    @Override
    public void use(Entity user) {
        if (user instanceof Player && ((Player) user).getTeam() == team) {
            Player player = (Player) user;
            if (player.carrying == null && player.getScore() >= COST[type]) {
                player.payCost(COST[type]);
                Building item = null;
                switch (type) {
                case SHOP_TURRET:
                    item = new Turret(pos.x, pos.y, team);
                    break;
                case SHOP_HARVESTER:
                    item = new Harvester(pos.x, pos.y, team);
                    break;
                case SHOP_BOMB:
                    item = new Bomb(pos.x, pos.y);
                    break;
                case SHOP_POINT:
                	player.setImmortal(true);
                	GuiMenu menu = new GamePowers(MojamComponent.GAME_WIDTH, MojamComponent.GAME_HEIGHT, player);
                	MojamComponent.menuStack.add(menu);
                    menu.addButtonListener(menu);
                }
                if (item != null) {
                	level.addEntity(item);
                	player.pickup(item);
                }
            }
        }
    }

}
