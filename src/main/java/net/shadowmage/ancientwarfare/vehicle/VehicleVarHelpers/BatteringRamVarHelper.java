/**
 * Copyright 2012 John Cummens (aka Shadowmage, Shadowmage4513)
 * This software is distributed under the terms of the GNU General Public License.
 * Please see COPYING for precise license information.
 * <p>
 * This file is part of Ancient Warfare.
 * <p>
 * Ancient Warfare is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Ancient Warfare is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Ancient Warfare.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.shadowmage.ancientwarfare.vehicle.VehicleVarHelpers;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.shadowmage.ancientwarfare.core.util.BlockTools;
import net.shadowmage.ancientwarfare.vehicle.entity.VehicleBase;
import net.shadowmage.ancientwarfare.vehicle.entity.types.VehicleTypeBatteringRam;
import net.shadowmage.ancientwarfare.vehicle.helpers.VehicleFiringVarsHelper;
import net.shadowmage.ancientwarfare.vehicle.missiles.DamageType;

import java.util.List;

public class BatteringRamVarHelper extends VehicleFiringVarsHelper {

	float logAngle = 0.f;
	float logSpeed = 0.f;

	/**
	 * @param vehicle
	 */
	public BatteringRamVarHelper(VehicleBase vehicle) {
		super(vehicle);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("lA", logAngle);
		tag.setFloat("lS", logSpeed);
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		logAngle = tag.getFloat("lA");
		logSpeed = tag.getFloat("lS");
	}

	@Override
	public void onFiringUpdate() {
		if (logAngle >= 30) {
			vehicle.firingHelper.startLaunching();
			logSpeed = 0;
		} else {
			logAngle++;
			logSpeed = 1;
		}
	}

	@Override
	public void onReloadUpdate() {
		if (this.logAngle < 0) {
			this.logAngle++;
			this.logSpeed = 1;
		} else {
			this.logAngle = 0;
			this.logSpeed = 0;
		}
	}

	@Override
	public void onLaunchingUpdate() {
		if (logAngle <= -30) {
			this.vehicle.firingHelper.setFinishedLaunching();
			this.doDamageEffects();
			this.logSpeed = 0;
		} else {
			logAngle -= 2;
			this.logSpeed = -2;
		}
	}

	public void doDamageEffects() {
		if (vehicle.world.isRemote) {
			return;
		}
		BlockPos[] effectedPositions = VehicleTypeBatteringRam.getEffectedPositions(vehicle);
		AxisAlignedBB bb;
		List<Entity> hitEntities;
		for (BlockPos pos : effectedPositions) {
			if (pos == null) {
				continue;
			}
			bb = new AxisAlignedBB(pos, pos.add(1, 1, 1));
			hitEntities = vehicle.world.getEntitiesWithinAABBExcludingEntity(vehicle, bb);
			if (hitEntities != null) {
				for (Entity ent : hitEntities) {
					ent.attackEntityFrom(DamageType.batteringDamage, 5 + vehicle.vehicleMaterialLevel);
				}
			}
/* TODO warzone implementation?
			if (WarzoneManager.instance().shouldBreakBlock(vehicle.world, pos.x, pos.y, pos.z)) {
*/
			BlockTools.breakBlockAndDrop(vehicle.world, pos);
		}
	}

	@Override
	public void onReloadingFinished() {
		this.logAngle = 0;
		this.logSpeed = 0;
	}

	@Override
	public float getVar1() {
		return logAngle;
	}

	@Override
	public float getVar2() {
		return logSpeed;
	}

	@Override
	public float getVar3() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVar4() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVar5() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVar6() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVar7() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVar8() {
		// TODO Auto-generated method stub
		return 0;
	}

}
