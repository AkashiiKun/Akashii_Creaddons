package io.github.akashiikun.creaddon.tags;

import com.zurrtum.create.client.Create;
import io.github.akashiikun.creaddon.CreaddonMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> PREVENTS_LEVITATION = createTag("prevents/levitation");

    private static TagKey<Item> createTag(String name) {
        return TagKey.create(Registries.ITEM, CreaddonMod.asId(name));
    }
}
