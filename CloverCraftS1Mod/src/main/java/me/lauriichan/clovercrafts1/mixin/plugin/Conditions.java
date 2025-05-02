package me.lauriichan.clovercrafts1.mixin.plugin;

import java.util.function.Supplier;

import net.minecraftforge.fml.loading.LoadingModList;

final class Conditions {

    private Conditions() {
        throw new UnsupportedOperationException();
    }

    public static Supplier<Boolean> modLoaded(String modId) {
        return () -> LoadingModList.get().getModFileById(modId) != null;
    }

}
