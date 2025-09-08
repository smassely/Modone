package com.smassely.modone.item;

import com.smassely.modone.ModOne;
import com.smassely.modone.item.custom.DeadBoneSwordItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item DEADBONESWORD = registerItem("dead_bone_sword", new DeadBoneSwordItem(ToolMaterials.NETHERITE,5,-2.4f, new FabricItemSettings()));

    public static void addItemsToIngredientGroup(FabricItemGroupEntries entries){

    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(ModOne.MOD_ID, name), item);
    }

    public static void registerModItems(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientGroup);

    }
}
