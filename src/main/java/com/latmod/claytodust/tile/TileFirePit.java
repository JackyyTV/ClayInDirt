package com.latmod.claytodust.tile;

import com.feed_the_beast.ftbl.lib.tile.EnumSaveType;
import com.feed_the_beast.ftbl.lib.tile.TileBase;
import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

/**
 * @author LatvianModder
 */
public class TileFirePit extends TileBase implements ITickable
{
	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
	private int furnaceBurnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;
	private String furnaceCustomName;

	@Override
	protected void writeData(NBTTagCompound nbt, EnumSaveType type)
	{
		nbt.setInteger("BurnTime", (short) furnaceBurnTime);
		nbt.setInteger("CookTime", (short) cookTime);
		nbt.setInteger("CookTimeTotal", (short) totalCookTime);
		ItemStackHelper.saveAllItems(nbt, furnaceItemStacks);
	}

	@Override
	protected void readData(NBTTagCompound nbt, EnumSaveType type)
	{
		furnaceItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, furnaceItemStacks);
		furnaceBurnTime = nbt.getInteger("BurnTime");
		cookTime = nbt.getInteger("CookTime");
		totalCookTime = nbt.getInteger("CookTimeTotal");
		currentItemBurnTime = TileEntityFurnace.getItemBurnTime(furnaceItemStacks.get(1));
	}

	@Override
	public void update()
	{
		boolean flag = isBurning();
		boolean flag1 = false;

		if (isBurning())
		{
			--furnaceBurnTime;
		}

		if (!world.isRemote)
		{
			ItemStack itemstack = furnaceItemStacks.get(1);

			if (isBurning() || !itemstack.isEmpty() && !furnaceItemStacks.get(0).isEmpty())
			{
				if (!isBurning() && canSmelt())
				{
					furnaceBurnTime = TileEntityFurnace.getItemBurnTime(itemstack);
					currentItemBurnTime = furnaceBurnTime;

					if (isBurning())
					{
						flag1 = true;

						if (!itemstack.isEmpty())
						{
							Item item = itemstack.getItem();
							itemstack.shrink(1);

							if (itemstack.isEmpty())
							{
								ItemStack item1 = item.getContainerItem(itemstack);
								furnaceItemStacks.set(1, item1);
							}
						}
					}
				}

				if (isBurning() && canSmelt())
				{
					++cookTime;

					if (cookTime == totalCookTime)
					{
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
			}
			else if (!isBurning() && cookTime > 0)
			{
				cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
			}

			if (flag != isBurning())
			{
				flag1 = true;
				BlockFurnace.setState(isBurning(), world, pos);
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

	public boolean isBurning()
	{
		return false;
	}
}