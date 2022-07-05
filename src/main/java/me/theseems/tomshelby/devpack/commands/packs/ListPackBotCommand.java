package me.theseems.tomshelby.devpack.commands.packs;

import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.command.SimpleBotCommand;
import me.theseems.tomshelby.command.SimpleCommandMeta;
import me.theseems.tomshelby.devpack.commands.DevPermissibleBotCommand;
import me.theseems.tomshelby.pack.BotPackage;
import me.theseems.tomshelby.pack.BotPackageManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ListPackBotCommand extends SimpleBotCommand implements DevPermissibleBotCommand {
  public ListPackBotCommand() {
    super(
        SimpleCommandMeta.onLabel("listpacks")
            .description("Показать список всех установленных паков"));
  }

  public static String getPackageEntry(BotPackageManager manager, BotPackage pack) {
    StringBuilder builder = new StringBuilder();
    boolean isEnabled = manager.isEnabled(pack.getInfo().getName());
    builder
        .append("*").append(pack.getInfo().getName()).append("*")
        .append('\n')
        .append("|  Статус: ")
        .append("_").append(isEnabled ? "☑️ (включен)" : "✖️ (выключен)").append("_")
        .append('\n')
        .append("|  Версия: ")
        .append(pack.getInfo().getVersion())
        .append('\n')
        .append("|  Автор: ")
        .append(pack.getInfo().getAuthor())
        .append('\n')
        .append("|  Описание: ")
        .append(pack.getInfo().getDescription())
        .append("\n\n");
    return builder.toString();
  }

  @Override
  public void handle(ThomasBot bot, String[] args, Update update) {
    StringBuilder builder = new StringBuilder();
    for (BotPackage pack : bot.getPackageManager().getPackages()) {
      builder.append(getPackageEntry(bot.getPackageManager(), pack));
    }

    SendMessage sendMessage = new SendMessage();
    sendMessage.setText(builder.toString());
    sendMessage.enableMarkdown(true);

    bot.replyBack(update, sendMessage);
  }
}
