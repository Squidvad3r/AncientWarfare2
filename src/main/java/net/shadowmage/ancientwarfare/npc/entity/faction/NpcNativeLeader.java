package net.shadowmage.ancientwarfare.npc.entity.faction;

import net.minecraft.world.World;

public class NpcNativeLeader extends NpcFactionLeader {

    public NpcNativeLeader(World par1World) {
        super(par1World);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getNpcType() {
        return "native.leader";
    }

}
