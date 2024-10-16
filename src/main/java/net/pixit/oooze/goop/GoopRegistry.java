package net.pixit.oooze.goop;

import absolutelyaya.goop.Goop;
import absolutelyaya.goop.api.*;
import absolutelyaya.goop.registries.TagRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.pixit.oooze.Oooze;
import net.pixit.oooze.dye.DyeBall;
import org.joml.Vector4f;

public class GoopRegistry implements GoopInitializer {
    @Override
    public void registerGoopEmitters() {
        //Snowball landing effects.
        GoopEmitterRegistry.registerProjectileEmitter(EntityType.SNOWBALL, new ProjectileHitGoopEmitter<SnowballEntity>(
                (snowball, data) -> 0xccf7ff,
                (snowball, data) -> {
                    Vec3d vel = snowball.getVelocity();
                    return new Vector4f((float)vel.x, (float)vel.y, (float)vel.z, 0f);
                },
                (snowball, data) -> 1,
                (snowball, data) -> 0.5f
        ).setWaterHandling(WaterHandling.REMOVE_PARTICLE).noDrip().noDeform());

        //Dye landing effects.
        GoopEmitterRegistry.registerProjectileEmitter(Oooze.DYE_BALL_ENTITY_TYPE, new ProjectileHitGoopEmitter<DyeBall>(
                (dye, data) -> dye.getColor().getFireworkColor(),
                (dye, data) -> {
                    Vec3d vel = dye.getVelocity();
                    return new Vector4f((float)vel.x, (float)vel.y, (float)vel.z, 0f);
                },
                (dye, data) -> 1,
                (dye, data) -> 0.5f
        ).setWaterHandling(WaterHandling.REMOVE_PARTICLE).noDrip().noDeform());

        // Egg effects.
        GoopEmitterRegistry.registerProjectileEmitter(EntityType.EGG, new ProjectileHitGoopEmitter<EggEntity>(
                (egg, data) -> 0xffffff,
                (egg, data) -> {
                    Vec3d vel = egg.getVelocity();
                    return new Vector4f((float)vel.x, (float)vel.y, (float)vel.z, 0f);
                },
                (egg, data) -> 1,
                (egg, data) -> 0.5f
        ).noDrip().setParticleEffectOverride(new Identifier(Goop.MOD_ID, "egg_goop"), new ExtraGoopData()));

        //Slime Effects.
        GoopEmitterRegistry.registerEmitter(EntityType.SLIME, new LandingGoopEmitter<SlimeEntity>(
                (slime, height) -> 0x2caa3b,
                (slime, height) -> new Vector4f(0f, -0f, 0f, 0.1f),
                (slime, height) -> 1,
                (slime, height) -> MathHelper.clamp(height / 4f, 0.25f, 1) * slime.getSize()
        ));

        GoopEmitterRegistry.registerEmitter(EntityType.SLIME, new DamageGoopEmitter<SlimeEntity>(
                (slime, data) -> 0x2caa3b,
                (slime, data) -> new Vector4f(0f, 0f, 0f, MathHelper.clamp(data.amount() / 8f, 0.25f, 2f)),
                (slime, data) -> data.source().isIn(TagRegistry.PHYSICAL) ? Math.round(MathHelper.clamp(data.amount() / 2f, 2f, 12f)) : 0,
                (slime, data) -> MathHelper.clamp(data.amount() / 4f, 0.25f, 1)
        ));

        //Magma Cube Effects.
        GoopEmitterRegistry.registerEmitter(EntityType.MAGMA_CUBE, new LandingGoopEmitter<MagmaCubeEntity>(
                (slime, height) -> 0x240802,
                (slime, height) -> new Vector4f(0f, -0f, 0f, 0.1f),
                (slime, height) -> 1,
                (slime, height) -> MathHelper.clamp(height / 4f, 0.25f, 1) * slime.getSize()
        ).setWaterHandling(WaterHandling.REPLACE_WITH_CLOUD_PARTICLE));

        GoopEmitterRegistry.registerEmitter(EntityType.MAGMA_CUBE, new DamageGoopEmitter<MagmaCubeEntity>(
                (slime, data) -> 0x240802,
                (slime, data) -> new Vector4f(0f, 0f, 0f, MathHelper.clamp(data.amount() / 8f, 0.25f, 2f)),
                (slime, data) -> data.source().isIn(TagRegistry.PHYSICAL) ? Math.round(MathHelper.clamp(data.amount() / 2f, 2f, 12f)) : 0,
                (slime, data) -> MathHelper.clamp(data.amount() / 4f, 0.25f, 1)
        ).setWaterHandling(WaterHandling.REPLACE_WITH_CLOUD_PARTICLE));

        // Snow Golem
        GoopEmitterRegistry.registerEmitter(EntityType.SNOW_GOLEM, new DeathGoopEmitter<SnowGolemEntity>(
                (snowGolem, data) -> 0xccf7ff,
                (snowGolem, data) -> new Vector4f(0f, 0f, 0f, 0.5f),
                (snowGolem, data) -> 2 + snowGolem.getRandom().nextInt(4),
                (snowGolem, data) -> 0.5f + snowGolem.getRandom().nextFloat() / 0.5f
        ).setWaterHandling(WaterHandling.REMOVE_PARTICLE));

        // Bee

        GoopEmitterRegistry.registerEmitter(EntityType.BEE, new DamageGoopEmitter<BeeEntity>(
                (entity, data) -> 0xf0be0c,
                (entity, data) -> new Vector4f(0f, 0f, 0f, MathHelper.clamp(data.amount() / 8f, 0.25f, 1.5f)),
                (entity, data) -> data.source().isIn(TagRegistry.PHYSICAL) ? Math.round(MathHelper.clamp(data.amount() / 2f, 1f, 4)) : 0,
                (entity, data) -> MathHelper.clamp(data.amount() / 8f, 0.25f, 0.5f)
        ).setWaterHandling(WaterHandling.REMOVE_PARTICLE).noDrip());

        // Normal Blood

        EntityType<?>[] NormalBloodTypes = {
                EntityType.PIG,
                EntityType.COW,
                EntityType.SHEEP,
                EntityType.CHICKEN,
                EntityType.MOOSHROOM,
                EntityType.SALMON,
                EntityType.SQUID,
                EntityType.GLOW_SQUID,
                EntityType.GUARDIAN,
                EntityType.ELDER_GUARDIAN,
                EntityType.CAMEL,
                EntityType.CAT,
                EntityType.AXOLOTL,
                EntityType.COD,
                EntityType.DOLPHIN,
                EntityType.DONKEY,
                EntityType.FOX,
                EntityType.FROG,
                EntityType.GOAT,
                EntityType.HORSE,
                EntityType.LLAMA,
                EntityType.MOOSHROOM,
                EntityType.MULE,
                EntityType.OCELOT,
                EntityType.PANDA,
                EntityType.PARROT,
                EntityType.POLAR_BEAR,
                EntityType.PUFFERFISH,
                EntityType.RABBIT,
                EntityType.SNIFFER,
                EntityType.TRADER_LLAMA,
                EntityType.WANDERING_TRADER,
                EntityType.VILLAGER,
                EntityType.TROPICAL_FISH,
                EntityType.TURTLE,
                EntityType.WOLF,
                EntityType.PLAYER
        };

        for (EntityType<?> mobType : NormalBloodTypes) {
            GoopEmitterRegistry.registerEmitter((EntityType<? extends LivingEntity>) mobType, new DamageGoopEmitter<>(
                    (entity, data) -> 0x940904,
                    (entity, data) -> new Vector4f(0f, MathHelper.clamp(data.amount() / 10f, 0f, 0.5f), 0f, MathHelper.clamp(data.amount() / 6f, 0.5f, 2f)),
                    (entity, data) -> data.source().isIn(TagRegistry.PHYSICAL) ? Math.round(MathHelper.clamp(data.amount() / 2f, 2f, 12f)) : 0,
                    (entity, data) -> MathHelper.clamp(data.amount() / 6f, 0.25f, 1)
            ).markMature());
        }

        // Dark Blood

        EntityType<?>[] DarkBloodTypes = {
                EntityType.ZOMBIE,
                EntityType.VINDICATOR,
                EntityType.EVOKER,
                EntityType.DROWNED,
                EntityType.HUSK,
                EntityType.PILLAGER, EntityType.RAVAGER,
                EntityType.HOGLIN, EntityType.PIGLIN,
                EntityType.PIGLIN_BRUTE,
                EntityType.ZOMBIFIED_PIGLIN,
                EntityType.ZOGLIN,
                EntityType.ZOMBIE_VILLAGER,
                EntityType.ZOMBIE_HORSE,
                EntityType.PHANTOM,
                EntityType.SPIDER,
                EntityType.CAVE_SPIDER,
                EntityType.BAT,
                EntityType.WITCH,
                EntityType.SILVERFISH
        };

        for (EntityType<?> mobType : DarkBloodTypes) {
            GoopEmitterRegistry.registerEmitter((EntityType<? extends LivingEntity>) mobType, new DamageGoopEmitter<>(
                    (entity, data) -> 0x6b0804,
                    (entity, data) -> new Vector4f(0f, MathHelper.clamp(data.amount() / 10f, 0f, 0.5f), 0f, MathHelper.clamp(data.amount() / 6f, 0.5f, 2f)),
                    (entity, data) -> data.source().isIn(TagRegistry.PHYSICAL) ? Math.round(MathHelper.clamp(data.amount() / 2f, 2f, 12f)) : 0,
                    (entity, data) -> MathHelper.clamp(data.amount() / 6f, 0.25f, 1)
            ).markMature());
        }

        /*
        EntityType<?>[] mobTypes = {EntityType.ZOMBIE, EntityType.VINDICATOR, EntityType.EVOKER};

        for (EntityType<?> mobType : mobTypes) {
            GoopEmitterRegistry.registerEmitter((EntityType<? extends LivingEntity>) mobType, new DamageGoopEmitter<>(
                    (entity, data) -> 0x940904,
                    (entity, data) -> new Vector4f(0f, 0f, 0f, MathHelper.clamp(data.amount() / 8f, 0.25f, 2f)),
                    (entity, data) -> data.source().isIn(TagRegistry.PHYSICAL) ? Math.round(MathHelper.clamp(data.amount() / 2f, 2f, 12f)) : 0,
                    (entity, data) -> MathHelper.clamp(data.amount() / 4f, 0.25f, 1)
            ).markMature());
        }
        */
    }
}
