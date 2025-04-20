package me.lauriichan.clovercrafts1.common;

import me.lauriichan.clovercrafts1.CloverCraftS1;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

@EventBusSubscriber(modid = CloverCraftS1.MOD_ID, bus = Bus.FORGE)
public class CommonEventHandler {

    @SubscribeEvent
    public static void updateName(PlayerEvent.NameFormat event) {
        event.setDisplayname(event.getEntity().getName());
    }

    @SubscribeEvent
    public static void updateTablistName(PlayerEvent.TabListNameFormat event) {
        event.setDisplayName(event.getEntity().getName());
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack itemStack = event.getObject();
        if (!itemStack.is(Items.NAME_TAG)) {
            return;
        }
        event.addCapability(CuriosCapability.ID_ITEM, CuriosApi.createCurioProvider(new ICurio() {

            @Override
            public ItemStack getStack() {
                return itemStack;
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                if (!(slotContext.entity() instanceof ServerPlayer player)) {
                    return;
                }
                updateNametag(player);
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                if (!(slotContext.entity() instanceof ServerPlayer player)) {
                    return;
                }
                updateNametag(player);
            }

            private void updateNametag(ServerPlayer player) {
                player.refreshDisplayName();
                player.refreshTabListName();
                player.setCustomName(player.getName());
                player.setCustomNameVisible(true);
                
            }

        }));
    }

}
