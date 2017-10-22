package claytodust.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileFirePit extends TileEntityFurnace {

    public void breakBlock() {
    }

    public int getCookTime(ItemStack stack) {
        return 1600;
    }

}
