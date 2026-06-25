package io.github.akashiikun.creaddon.content.liquidfuel.eventhandlers;

import com.zurrtum.create.AllBlockEntityTypes;
import com.zurrtum.create.infrastructure.transfer.FluidInventoryStorage;
import io.github.akashiikun.creaddon.content.liquidfuel.core.IHasStomach;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;

public class ModEventHandler {
    public static void registerApiLookups() {
        FluidStorage.SIDED.registerForBlockEntity(
                (blockEntity, side) -> FluidInventoryStorage.of(
                        ((IHasStomach) blockEntity).getStomach().getCapability(),
                        side
                ),
                AllBlockEntityTypes.HEATER
        );
    }
}