package clayindirt.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileFirePit extends TileEntityFurnace {

    private int furnaceBurnTime;
    public static int isStarted;

    @Override
    public String getName() {
        return "Fire Pit";
    }

    @Override
    public int getCookTime(ItemStack stack) {
        return 600;
    }

    @Override
    public boolean isBurning() {
        return this.furnaceBurnTime > 0 && isStarted == 1;
    }

}
