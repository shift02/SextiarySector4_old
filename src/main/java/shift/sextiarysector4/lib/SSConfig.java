package shift.sextiarysector4.lib;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;

import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public class SSConfig {

	public static Configuration config;

	// Status
	public static boolean statusStamina;
	public static boolean statusMoisture;
	public static boolean peacefulStamina;
	public static boolean peacefulMoisture;

	public static final String SS_LANG = "config.ss.";
	public static final String CATEGORY_STATUS = "status";

	public static void initConfig() {

		if (config == null) {

			File file = new File(Loader.instance().getConfigDir(), "sextiarysector4.cfg");
			config = new Configuration(file);

			try {

				config.load();
			} catch (Exception e) {

				File dest = new File(file.getParentFile(), file.getName() + ".bak");

				if (dest.exists()) {

					dest.delete();
				}

				file.renameTo(dest);

				FMLLog.log(Level.ERROR, e, "A critical error occured reading the " + file.getName()
						+ " file, defaults will be used - the invalid file is backed up at " + dest.getName());
			}
		}
	}

	public static void syncConfig() {

		initConfig();

		String category = Configuration.CATEGORY_GENERAL;
		Property prop;
		List<String> propOrder = Lists.newArrayList();

		category = CATEGORY_STATUS;
		propOrder = Lists.newArrayList();

		//Stamina Status - スタミナのステータス
		prop = config.get(category, "statusStamina", true);
		prop.setLanguageKey(SS_LANG + category + "." + prop.getName());
		prop.setComment(I18n.translateToLocal(prop.getLanguageKey() + ".tooltip"));
		prop.setComment(prop.getComment() + " [default: " + prop.getDefault() + "]");
		propOrder.add(prop.getName());
		statusStamina = prop.getBoolean(statusStamina);

		//Moisture Status - 水分のステータス
		prop = config.get(category, "statusMoisture", true);
		prop.setLanguageKey(SS_LANG + category + "." + prop.getName());
		prop.setComment(I18n.translateToLocal(prop.getLanguageKey() + ".tooltip"));
		prop.setComment(prop.getComment() + " [default: " + prop.getDefault() + "]");
		propOrder.add(prop.getName());
		statusMoisture = prop.getBoolean(statusMoisture);

		//Peaceful Stamina - ピースフルのスタミナ
		prop = config.get(category, "peacefulStamina", false);
		prop.setLanguageKey(SS_LANG + category + "." + prop.getName());
		prop.setComment(I18n.translateToLocal(prop.getLanguageKey() + ".tooltip"));
		prop.setComment(prop.getComment() + " [default: " + prop.getDefault() + "]");
		propOrder.add(prop.getName());
		peacefulStamina = prop.getBoolean(peacefulStamina);

		//Peaceful Moisture - ピースフルの水分
		prop = config.get(category, "peacefulMoisture", false);
		prop.setLanguageKey(SS_LANG + category + "." + prop.getName());
		prop.setComment(I18n.translateToLocal(prop.getLanguageKey() + ".tooltip"));
		prop.setComment(prop.getComment() + " [default: " + prop.getDefault() + "]");
		propOrder.add(prop.getName());
		peacefulMoisture = prop.getBoolean(peacefulMoisture);

		config.setCategoryLanguageKey(category, SS_LANG + category);
		config.setCategoryPropertyOrder(category, propOrder);

		if (config.hasChanged()) {

			config.save();
		}
	}

}
