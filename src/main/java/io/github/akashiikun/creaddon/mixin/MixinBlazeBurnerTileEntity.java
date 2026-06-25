package io.github.akashiikun.creaddon.mixin;

import com.zurrtum.create.api.behaviour.BlockEntityBehaviour;
import com.zurrtum.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.zurrtum.create.infrastructure.fluids.FluidStack;
import com.zurrtum.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.zurrtum.create.foundation.blockEntity.SmartBlockEntity;
import io.github.akashiikun.creaddon.content.liquidfuel.core.BurnerStomachHandler;
import io.github.akashiikun.creaddon.content.liquidfuel.core.IHasStomach;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = BlazeBurnerBlockEntity.class, remap = false)
public abstract class MixinBlazeBurnerTileEntity extends SmartBlockEntity implements IHasStomach {
    @Unique public SmartFluidTankBehaviour creaddon$stomach;

    public MixinBlazeBurnerTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Unique
    public SmartFluidTankBehaviour getStomach() {
        return creaddon$stomach;
    }

    @Inject(method = "addBehaviours", at = @At("TAIL"))
    private void creaddon$addBehaviours(List<BlockEntityBehaviour<?>> behaviours, CallbackInfo ci) {
        creaddon$stomach = SmartFluidTankBehaviour.single(
                this,
                BurnerStomachHandler.TANK_CAPACITY,
                (behaviour, enforceVariety, capacity) -> new SmartFluidTankBehaviour.InternalFluidHandler(
                        behaviour,
                        enforceVariety,
                        capacity
                ) {
            @Override
            public boolean canInsert(int slot, FluidStack stack, @Nullable Direction direction) {
                return super.canInsert(slot, stack, direction)
                        && BurnerStomachHandler.LIQUID_BURNER_FUEL_MAP.containsKey(stack.getFluid());
            }
        });
        behaviours.add(creaddon$stomach);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo info) {
        BurnerStomachHandler.tick(this);
    }

    @Inject(method = "tryUpdateFuel", at = @At(value = "TAIL"), cancellable = true)
    public void tryUpdateFuel(ItemStack itemStack, boolean forceOverflow, boolean simulate, CallbackInfoReturnable<Boolean> cir) {
        BurnerStomachHandler.tryUpdateFuel(this, itemStack, forceOverflow, simulate, cir);
    }
}