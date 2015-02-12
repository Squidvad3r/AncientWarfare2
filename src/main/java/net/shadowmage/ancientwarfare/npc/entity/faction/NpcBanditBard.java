package net.shadowmage.ancientwarfare.npc.entity.faction;

import net.minecraft.world.World;

public class NpcBanditBard extends NpcFactionBard {

    public NpcBanditBard(World par1World) {
        super(par1World);
    }

    @Override
    public String getNpcType() {
        return "bandit.bard";
    }

}
