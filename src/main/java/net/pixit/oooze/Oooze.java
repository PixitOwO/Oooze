package net.pixit.oooze;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pixit.oooze.dye.DyeBall;
import net.pixit.oooze.dye.DyeItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Oooze implements ModInitializer {
	public static final String MOD_ID = "oooze";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final EntityType<DyeBall> DYE_BALL_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE, new Identifier(MOD_ID), FabricEntityTypeBuilder.<DyeBall>create(SpawnGroup.MISC, DyeBall::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build());
	@Override
	public void onInitialize() {
		LOGGER.info("Oooze is dripping.");
		UseItemCallback.EVENT.register(new DyeItems());
	}
}