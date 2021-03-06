package net.shadowmage.ancientwarfare.structure.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.shadowmage.ancientwarfare.structure.tile.ISpecialLootContainer;
import net.shadowmage.ancientwarfare.structure.tile.LootSettings;

import java.util.Optional;

public class RenderLootInfo<T extends TileEntity & ISpecialLootContainer> extends TileEntitySpecialRenderer<T> {
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		//noinspection ConstantConditions
		if ((Minecraft.getMinecraft().player.capabilities.isCreativeMode || Minecraft.getMinecraft().playerController.getCurrentGameType() == GameType.SPECTATOR)
				&& rendererDispatcher.cameraHitResult != null && te.getPos().equals(rendererDispatcher.cameraHitResult.getBlockPos())) {
			setLightmapDisabled(true);
			drawLootInfo(te, getNameplateOffsetX(te, x), y, getNameplateOffsetZ(te, z), 12);
			setLightmapDisabled(false);
			te.getLootSettings().getLootTableName().ifPresent(lt -> {
			});
		}
	}

	private void drawLootInfo(T te, double x, double y, double z, int maxDistance) {
		Entity entity = this.rendererDispatcher.entity;
		double d0 = te.getDistanceSq(entity.posX, entity.posY, entity.posZ);

		if (d0 <= (double) (maxDistance * maxDistance)) {
			float f = this.rendererDispatcher.entityYaw;
			float f1 = this.rendererDispatcher.entityPitch;

			LootSettings lootSettings = te.getLootSettings();
			float verticalOffset = 0;

			if (lootSettings.getSpawnEntity()) {
				renderString((float) x, (float) y + verticalOffset, (float) z, f, f1, 0, lootSettings.getEntity().toString());
				verticalOffset += 0.3;
			}

			if (lootSettings.getSplashPotion()) {
				for (PotionEffect effect : lootSettings.getEffects()) {
					renderString((float) x, (float) y + verticalOffset, (float) z, f, f1, 0,
							effect.getPotion().getRegistryName().toString() + " " + effect.getAmplifier() + " "
									+ effect.getDuration() / 1200 + ":" + (effect.getDuration() % 1200) / 20);
					verticalOffset += 0.3;
				}
			}

			if (lootSettings.hasLoot()) {
				Optional<ResourceLocation> lt = lootSettings.getLootTableName();
				if (lt.isPresent()) {
					String str = te.getLootSettings().getLootRolls() + " x " + lt.get().toString();
					renderString((float) x, (float) y + verticalOffset, (float) z, f, f1, 0, str);
				}
			}
		}
	}

	private void renderString(float x, float y, float z, float f, float f1, int verticalShift, String str) {
		EntityRenderer.drawNameplate(getFontRenderer(), str,
				x + 0.5F, y + 1.5F, z + 0.5F, verticalShift, f, f1, false, false);
	}

	protected double getNameplateOffsetZ(T te, double z) {
		return z;
	}

	protected double getNameplateOffsetX(T te, double x) {
		return x;
	}
}
