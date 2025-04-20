package me.lauriichan.clovercrafts1;

import com.mojang.logging.LogUtils;

import me.lauriichan.clovercrafts1.util.Slf4jSimpleLogger;
import me.lauriichan.laylib.logger.ISimpleLogger;
import net.minecraftforge.fml.common.Mod;

@Mod(CloverCraftS1.MOD_ID)
public class CloverCraftS1 {

    public static final String MOD_ID = "clovercrafts1";
    public static final ISimpleLogger LOGGER = new Slf4jSimpleLogger(LogUtils.getLogger());

}
