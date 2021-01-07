package me.theseems.tomshelby.devpack;

import com.google.gson.Gson;
import me.theseems.tomshelby.command.BotCommand;
import me.theseems.tomshelby.command.CommandContainer;
import me.theseems.tomshelby.devpack.commands.meta.*;
import me.theseems.tomshelby.devpack.commands.misc.FatherExportBotCommand;
import me.theseems.tomshelby.devpack.commands.packs.DisablePackBotCommand;
import me.theseems.tomshelby.devpack.commands.packs.EnablePackBotCommand;
import me.theseems.tomshelby.devpack.commands.packs.ListPackBotCommand;
import me.theseems.tomshelby.devpack.config.DevConfig;
import me.theseems.tomshelby.pack.JavaBotPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main extends JavaBotPackage {
  private static List<BotCommand> commandList;
  private static DevConfig devConfig;

  private void loadDevConfig() {
    File devConfigFile = new File(getPackageFolder(), "config.json");
    if (!devConfigFile.exists()) {
      try {
        devConfigFile.createNewFile();
      } catch (IOException e) {
        System.err.println(
            "Error creating dev config file. Please make sure that you have an access to files inside package directory.");
      }
    }

    try {
      devConfig = new Gson().fromJson(new FileReader(devConfigFile), DevConfig.class);
    } catch (FileNotFoundException e) {
      System.err.println("Cannot find dev config file.");
      e.printStackTrace();
    }
  }

  @Override
  public void onLoad() {
    // Create package's folder
    if (!getPackageFolder().exists()) {
      getPackageFolder().mkdir();
    }

    // Loading contents of the config
    loadDevConfig();

    if (devConfig == null
        || devConfig.getDeveloperIds() == null
        || devConfig.getDeveloperIds().isEmpty()) {
      // If there's nothing, then there'll be me ;)
      devConfig = new DevConfig(Collections.singletonList(311245296));
      System.err.println(
          "No one found in developers config. Please, fill it in with your telegram id!");
      System.err.println("By default there's only the developer of the bot itself");
    }

    commandList = new ArrayList<>();
  }

  @Override
  public void onEnable() {
    CommandContainer commandContainer = getBot().getCommandContainer();
    commandList.addAll(
        Arrays.asList(
            // Meta
            new MetaDelBotCommand(),
            new MetaGetBotCommand(),
            new MetaMapBotCommand(),
            new MetaPutBotCommand(),
            new SaveAllBotCommand(),

            // Packs
            new DisablePackBotCommand(),
            new EnablePackBotCommand(),
            new ListPackBotCommand(),

            // Misc
            new FatherExportBotCommand()));

    for (BotCommand botCommand : commandList) {
      commandContainer.attach(botCommand);
    }
  }

  @Override
  public void onDisable() {
    CommandContainer commandContainer = getBot().getCommandContainer();
    for (BotCommand botCommand : commandList) {
      commandContainer.detach(botCommand.getMeta().getLabel());
    }

    commandList.clear();
  }

  public static DevConfig getDevConfig() {
    return devConfig;
  }
}
