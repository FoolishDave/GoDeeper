package com.ddogclaw.godeeper.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PulverizerRecipes
{
	private static final PulverizerRecipes pulverizerRecipeBase = new PulverizerRecipes();

	
	
	public PulverizerRecipes()
	{
		loadRecipes();
	}
	
	
	
	private static Map massPulverizerList = new HashMap();
	
	public static PulverizerRecipes massPulverize()
	{
		return pulverizerRecipeBase;
	}
	
	
	// ~~~~~~~~~~~~~ Functions for accessing the list of recipes ~~~~~~~~~~~~~~~~~~~~~~~~//
	
	public static void addPulverizerRecipe(ItemStack input, ItemStack output)
	{
		massPulverizerList.put(input, output);
	}
	
	public static ItemStack getPulverizeResult (ItemStack item)
	{
		Iterator iterator = massPulverizerList.entrySet().iterator();
		Entry entry;
		
		do
		{
			if (iterator.hasNext() == false)
			{
				return null;
			}
			
			entry = (Entry)iterator.next();
		}
		
		while (!checkItems(item, (ItemStack)entry.getKey()));
		
		return (ItemStack)entry.getValue();
	}
	
	private static boolean checkItems(ItemStack item1, ItemStack item2)
	{
		return item1.getItem() == item2.getItem() && (item2.getItemDamage() == 32767 || item2.getItemDamage() == item1.getItemDamage());
	}
	
	public Map getMassPulverizerList ()
	{
		return this.massPulverizerList;
	}
	
	
	public static void addOreDictionaryRecipe(String string)
	{
		addOreDictionaryRecipe(string, "", 2);
	}
	
	public static void addOreDictionaryRecipe(String string1, String string2, int numOut)
	{
		String str1 = "ore" + StringHelper.titleCase(string1);
		String str2 = "dust" + StringHelper.titleCase(string1);
		String str3 = "ingot" + StringHelper.titleCase(string1);
		
		ArrayList oreArrayList = OreDictionary.getOres(str1);
		ArrayList dustArrayList = OreDictionary.getOres(str2);
		ArrayList ingotArrayList = OreDictionary.getOres(str3);
		ArrayList string2ArrayList = new ArrayList();
		
		String str4 = "cluster" + StringHelper.titleCase(string1);
		ArrayList clusterArrayList = OreDictionary.getOres(str4);
		
		if (string2 != "")
		{
			String str5 = "dust" + StringHelper.titleCase(string2);
			string2ArrayList = OreDictionary.getOres(str5);
		}
		
		if (dustArrayList.isEmpty())
		{
			return;
		}
		if (ingotArrayList.isEmpty())
		{
			str3 = null;
		}
		if (oreArrayList.isEmpty())
		{
			str1 = null;
		}

		if (string2ArrayList.isEmpty())
		{
			addPulverizerRecipe(ItemHelper.cloneStack((ItemStack)oreArrayList.get(0), 1), ItemHelper.cloneStack((ItemStack)dustArrayList.get(0), 2));
			addPulverizerRecipe(ItemHelper.cloneStack((ItemStack)ingotArrayList.get(0), 1), ItemHelper.cloneStack((ItemStack)dustArrayList.get(0), 1));
		}
		else
		{
			addPulverizerRecipe(ItemHelper.cloneStack((ItemStack)OreDictionary.getOres(string1).get(0), 1), ItemHelper.cloneStack((ItemStack)OreDictionary.getOres(string2).get(0), numOut));
		}
		
	}
	
	// ~~~~~~~~~~~~~~~~~~ Manually adding dusts like a boss ~~~~~~~~~~~~~~~ //
	
	public static void loadRecipes()
	{
		if (ItemHelper.oreNameExists("dustEnderPearl"))
		{
			addPulverizerRecipe(new ItemStack(Items.ender_pearl), ItemHelper.cloneStack((ItemStack)OreDictionary.getOres("dustEnderPearl").get(0), 1));
		}
		if (ItemHelper.oreNameExists("oreSaltpeter") && ItemHelper.oreNameExists("dustNiter"))
		{
			addPulverizerRecipe((ItemStack)OreDictionary.getOres("oreSaltpeter").get(0), ItemHelper.cloneStack((ItemStack)OreDictionary.getOres("dustNiter").get(0), 1));
		}
		if (ItemHelper.oreNameExists("oreSulfur"))
		{
			addPulverizerRecipe((ItemStack)OreDictionary.getOres("oreSulfur").get(0), ItemHelper.cloneStack((ItemStack)OreDictionary.getOres("dustSulfur").get(0), 6));
		}
		if ((ItemHelper.oreNameExists("oreCertusQuartz")) && (ItemHelper.oreNameExists("dustCertusQuartz")) && (ItemHelper.oreNameExists("crystalCertusQuartz")))
		{
			addPulverizerRecipe((ItemStack)OreDictionary.getOres("oreCertusQuartz").get(0), ItemHelper.cloneStack((ItemStack)OreDictionary.getOres("crystalCertusQuartz").get(0), 4));
			
			addPulverizerRecipe((ItemStack)OreDictionary.getOres("crystalCertusQuartz").get(0), (ItemStack)OreDictionary.getOres("dustCertusQuartz").get(0));
		}
		if ((ItemHelper.oreNameExists("dustFluix")) && (ItemHelper.oreNameExists("crystalFluix")))
		{
			addPulverizerRecipe((ItemStack)OreDictionary.getOres("crystalFluix").get(0), (ItemStack)OreDictionary.getOres("dustFluix").get(0));
		}
		if ((ItemHelper.oreNameExists("dustNetherQuartz"))) 
		{
			addPulverizerRecipe(new ItemStack(Items.quartz, 1), ItemHelper.cloneStack((ItemStack)OreDictionary.getOres("dustNetherQuartz").get(0), 1));
		}
		
		String[] arrayOfString = OreDictionary.getOreNames();
		String str = "";
		for (int i = 0; i < arrayOfString.length; i++)
		{
			if (arrayOfString[i].startsWith("ore"))
			{
				str = arrayOfString[i].substring(3, arrayOfString[i].length());
				if (str != null)
				addOreDictionaryRecipe(str);
			}
			else if (arrayOfString[i].startsWith("dust"))
			{
				str = arrayOfString[i].substring(4, arrayOfString[i].length());
				addOreDictionaryRecipe(str);
			}
		}
	}












}
