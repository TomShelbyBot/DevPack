package me.theseems.tomshelby.devpack;

import me.theseems.tomshelby.command.BotCommand;
import me.theseems.tomshelby.command.CommandContainer;
import me.theseems.tomshelby.devpack.commands.meta.*;
import me.theseems.tomshelby.devpack.commands.misc.FatherExportBotCommand;
import me.theseems.tomshelby.devpack.commands.packs.DisablePackBotCommand;
import me.theseems.tomshelby.devpack.commands.packs.EnablePackBotCommand;
import me.theseems.tomshelby.devpack.commands.packs.ListPackBotCommand;
import me.theseems.tomshelby.pack.JavaBotPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends JavaBotPackage {
  private static List<BotCommand> list;

  public Main() {
    list = new ArrayList<>();
  }

  @Override
  public void onEnable() {
    CommandContainer commandContainer = getBot().getCommandContainer();
    list.addAll(Arrays.asList(
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
        new FatherExportBotCommand()
    ));

    for (BotCommand botCommand : list) {
      commandContainer.attach(botCommand);
    }
  }

  @Override
  public void onDisable() {
    CommandContainer commandContainer = getBot().getCommandContainer();
    for (BotCommand botCommand : list) {
      commandContainer.detach(botCommand.getMeta().getLabel());
    }

    list.clear();
  }
}
