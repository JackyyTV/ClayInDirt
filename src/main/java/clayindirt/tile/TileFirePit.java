package clayindirt.tile;

import clayindirt.ModRegistry;
import clayindirt.block.BlockFirePit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class TileFirePit extends TileEntity implements ITickable {

	protected ItemStackHandler inv = new ItemStackHandler(3);

	protected int progress = 0;
	protected int burnTime = 0;
	protected ItemStack curSmelt = ItemStack.EMPTY;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setTag("inventory", inv.serializeNBT());
		tag.setInteger("prog", progress);
		tag.setInteger("burntime", burnTime);
		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		inv.deserializeNBT(tag.getCompoundTag("inventory"));
		progress = tag.getInteger("prog");
		burnTime = tag.getInteger("burntime");
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 127, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	public boolean isBurning() {
		return world.getBlockState(pos).getValue(BlockFirePit.BURNING);
	}

	public boolean isFueled() {
		return world.getBlockState(pos).getValue(BlockFirePit.FUELED);
	}

	@Override
	public void update() {
		if (world.isRemote) return;
		if (isBurning()) {
			if (burnTime == 0 && !getFuel().isEmpty()) {
				burnTime = TileEntityFurnace.getItemBurnTime(getFuel());
				getFuel().shrink(1);
			}
			if (burnTime > 0) {
				burnTime--;
				if (!getInput().isEmpty() && curSmelt.isEmpty()) {
					curSmelt = FurnaceRecipes.instance().getSmeltingResult(getInput()).copy();
					if (!curSmelt.isEmpty() && progress == 0) {
						progress = 600;
					}
				}
				if (getInput().isEmpty() && !curSmelt.isEmpty()) {
                    curSmelt = ItemStack.EMPTY;
				    progress = 0;
                }
				if (!curSmelt.isEmpty() && progress > 0 && (getOutput().isEmpty() || curSmelt.isItemEqual(getOutput()))) {
					if (--progress == 0 && addOutput(curSmelt, true).isEmpty()) {
						addOutput(curSmelt, false);
						curSmelt = ItemStack.EMPTY;
						getInput().shrink(1);
					}
				}
			}
			if (burnTime == 0 && getFuel().isEmpty()) {
				progress = 0;
				curSmelt = ItemStack.EMPTY;
				world.setBlockState(pos, ModRegistry.FIREPIT.getDefaultState());
			}
		}
	}

	public ItemStackHandler getInv() {
		return inv;
	}

	public ItemStack getOutput() {
		return inv.getStackInSlot(2);
	}

	public ItemStack getFuel() {
		return inv.getStackInSlot(1);
	}

	public ItemStack getInput() {
		return inv.getStackInSlot(0);
	}

    public int getBurnTime() {
        return burnTime;
    }

	public int getProgress() {
	    return progress;
    }

	public ItemStack addFuel(ItemStack fuel, boolean simulate) {
		ItemStack s = inv.insertItem(1, fuel, simulate);
		markDirty();
		world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockFirePit.FUELED, true));
		return s;
	}

	public ItemStack addInput(ItemStack input, boolean simulate) {
		ItemStack s = inv.insertItem(0, input, simulate);
		markDirty();
		return s;
	}
	
	public ItemStack addOutput(ItemStack output, boolean simulate) {
		ItemStack s = inv.insertItem(2, output, simulate);
		markDirty();
		return s;
	}

    public void emptyFuel() {
        inv.setStackInSlot(1, ItemStack.EMPTY);
    }

    public void emptyInput() {
        inv.setStackInSlot(0, ItemStack.EMPTY);
    }

	public void emptyOutput() {
		inv.setStackInSlot(2, ItemStack.EMPTY);
	}

	@Override
	public void markDirty() {
		super.markDirty();
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState state1, IBlockState state2) {
		return state1.getBlock() != state2.getBlock();
	}

}
