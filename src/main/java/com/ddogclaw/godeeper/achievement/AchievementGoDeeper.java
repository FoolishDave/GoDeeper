package com.ddogclaw.godeeper.achievement;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.block.GoDeeperBlocks;

public class AchievementGoDeeper 
{	
	static final Achievement cuilCraft = new Achievement("achievement" + GoDeeper.MODID + "abstractionTinker", "AbstractionTinkerer", -2, 0, GoDeeperBlocks.blockCuilGenerator, (Achievement)null).initIndependentStat().registerStat();
	static final Achievement bigMachine = new Achievement("achievement.bigMachine", "Big Machines", 2, 0, GoDeeperBlocks.blockMassPulverizer, (Achievement) null).initIndependentStat().registerStat();
	
	public static AchievementPage goDeeperAchievements = new AchievementPage("Go Deeper", cuilCraft, bigMachine);
	
	public static void init()
	{
		AchievementPage.registerAchievementPage(goDeeperAchievements);
	}
}
