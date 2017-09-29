package net.dark_roleplay.claytodust.common.tile_entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TE_FirePit extends TileEntity implements ITickable, ISidedInventory{
	
	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
	private int furnaceBurnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;
	private String furnaceCustomName;

	public void readFromNBT(NBTTagCompound compound){
		furnaceItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, furnaceItemStacks);
		furnaceBurnTime = compound.getInteger("burn_time");
		cookTime = compound.getInteger("cook_time");
		totalCookTime = compound.getInteger("cook_time_total");
		currentItemBurnTime = furnaceItemStacks.get(1).getItem().getItemBurnTime(furnaceItemStacks.get(1));
		super.readFromNBT(compound);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		compound.setInteger("burn_time", furnaceBurnTime);
		compound.setInteger("cook_time", cookTime);
		compound.setInteger("cook_time_total", totalCookTime);
		ItemStackHelper.saveAllItems(compound, furnaceItemStacks);
		return super.writeToNBT(compound);
	}

	@Override
	public void update(){
		boolean isBurning = isBurning();
		boolean flag1 = false;

		if (isBurning){
			furnaceBurnTime--;
		}

		if (!world.isRemote){

			ItemStack stack0 = furnaceItemStacks.get(0);
			ItemStack stack1 = furnaceItemStacks.get(1);

			
			if (isBurning || !stack1.isEmpty() && !furnaceItemStacks.get(0).isEmpty()){
				if (isBurning && canSmelt()){
					furnaceBurnTime = stack1.getItem().getItemBurnTime(stack1);
					currentItemBurnTime = furnaceBurnTime;

					if (isBurning){
						flag1 = true;

						if (!stack1.isEmpty()){
							stack1.shrink(1);

							if (stack1.isEmpty()){
								ItemStack item1 = stack1.getItem().getContainerItem(stack1);
								furnaceItemStacks.set(1, item1);
							}
						}
					}
				}

				if (isBurning && canSmelt()){
					cookTime++;

					if (cookTime == totalCookTime){
						cookTime = 0;
						totalCookTime = getCookTime(furnaceItemStacks.get(0));
						smeltItem();
						flag1 = true;
					}
				}
				else
				{
					cookTime = 0;
				}
			}else if (!isBurning() && cookTime > 0){
				cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
			}

			if (isBurning != isBurning()){
				flag1 = true;
				world.setBlockState(pos, )
				BlockFurnace.setState(isBurning, world, pos);
			}
		}

		if (flag1)
		{
			markDirty();
		}

		checkIfDirty();
	}

	public int getCookTime(ItemStack stack)
	{
		return 200;
	}

	private boolean canSmelt()
	{
		if (furnaceItemStacks.get(0).isEmpty())
		{
			return false;
		}
		else
		{
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks.get(0));

			if (itemstack.isEmpty())
			{
				return false;
			}
			else
			{
				ItemStack itemstack1 = furnaceItemStacks.get(2);

				if (itemstack1.isEmpty())
				{
					return true;
				}
				else if (!itemstack1.isItemEqual(itemstack))
				{
					return false;
				}
				else if (itemstack1.getCount() + itemstack.getCount() <= 64 && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())
				{
					return true;
				}
				else
				{
					return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
				}
			}
		}
	}

	public void smeltItem()
	{
		if (canSmelt())
		{
			ItemStack itemstack = furnaceItemStacks.get(0);
			ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
			ItemStack itemstack2 = furnaceItemStacks.get(2);

			if (itemstack2.isEmpty())
			{
				furnaceItemStacks.set(2, itemstack1.copy());
			}
			else if (itemstack2.getItem() == itemstack1.getItem())
			{
				itemstack2.grow(itemstack1.getCount());
			}

			if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !furnaceItemStacks.get(1).isEmpty() && furnaceItemStacks.get(1).getItem() == Items.BUCKET)
			{
				furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
			}

			itemstack.shrink(1);
		}
	}

	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == 2)
		{
			return false;
		}
		else if (index != 1)
		{
			return true;
		}
		else
		{
			ItemStack itemstack = furnaceItemStacks.get(1);
			return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
		}
	}

	public void breakBlock()
	{
	}

	public boolean isBurning(){
		return false;
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		// TODO Auto-generated method stub
		return false;
	}
}