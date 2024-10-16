package net.pixit.oooze.goop;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.pixit.oooze.Oooze;

public class TagRegistry {
    public static final TagKey<DamageType> PROJECTILE = TagKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Oooze.MOD_ID, "projectile"));
}
