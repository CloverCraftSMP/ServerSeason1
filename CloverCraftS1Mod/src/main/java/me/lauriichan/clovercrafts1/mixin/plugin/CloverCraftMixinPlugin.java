package me.lauriichan.clovercrafts1.mixin.plugin;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMaps;

public class CloverCraftMixinPlugin implements IMixinConfigPlugin {

    public static final Object2ReferenceMap<String, Supplier<Boolean>> CONDITIONS = Object2ReferenceMaps.unmodifiable(buildConditions());

    private static Object2ReferenceArrayMap<String, Supplier<Boolean>> buildConditions() {
        Object2ReferenceArrayMap<String, Supplier<Boolean>> map = new Object2ReferenceArrayMap<>();
        map.put("client.compat.emf.EMFPlayerModelFixMixin", Conditions.modLoaded("entity_model_features"));
        return map;
    }

    private final int baseLength;

    public CloverCraftMixinPlugin() {
        this.baseLength = CloverCraftMixinPlugin.class.getPackageName().length() + 1;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        Supplier<Boolean> condition = CONDITIONS.get(mixinClassName.substring(baseLength));
        if (condition == null) {
            return true;
        }
        return condition.get();
    }

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

}
