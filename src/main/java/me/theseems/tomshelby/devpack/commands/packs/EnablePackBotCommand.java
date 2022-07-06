package me.theseems.tomshelby.devpack.commands.packs;

import me.theseems.tomshelby.Main;
import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.command.SimpleBotCommand;
import me.theseems.tomshelby.command.SimpleCommandMeta;
import me.theseems.tomshelby.devpack.commands.DevPermissibleBotCommand;
import me.theseems.tomshelby.pack.BotPackage;
import me.theseems.tomshelby.pack.JarBotPackageManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class EnablePackBotCommand extends SimpleBotCommand implements DevPermissibleBotCommand {
  public EnablePackBotCommand() {
    super(SimpleCommandMeta.onLabel("enablepack").description("Включить пак"));
  }

  @Override
  public void handle(ThomasBot bot, String[] args, Update update) {
    if (args.length == 0) {
      bot.replyBackText(update, "Укажите название пакета для включения");
      return;
    }

    String packageName = args[0];
    Optional<BotPackage> botPackage = bot.getPackageManager().getPackageByName(packageName);

    if (!botPackage.isPresent()) {
      bot.replyBackText(
          update, "Этого пакета нет среди доступных. Список можно получить написав /listpacks");
      return;
    }

    if (bot.getPackageManager().isEnabled(packageName)) {
      bot.replyBackText(update, "Этот пакет уже включен");
      return;
    }


    ((JarBotPackageManager) Main.getBotPackageManager()).enablePackage(Main.getBot(), packageName);
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText(ListPackBotCommand.getPackageEntry(bot.getPackageManager(), botPackage.get()));
    sendMessage.enableMarkdown(true);

    bot.replyBack(update, sendMessage);
  }
}
