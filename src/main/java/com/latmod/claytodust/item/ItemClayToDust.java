package com.latmod.claytodust.item;

import com.feed_the_beast.ftbl.lib.item.ItemBase;
import com.latmod.yabba.ClayToDust;

/**
 * @author LatvianModder
 */
public class ItemClayToDust extends ItemBase
{
	public ItemClayToDust(String id)
	{
		super(ClayToDust.MOD_ID, id);
		setCreativeTab(ClayToDust.TAB);
	}
}