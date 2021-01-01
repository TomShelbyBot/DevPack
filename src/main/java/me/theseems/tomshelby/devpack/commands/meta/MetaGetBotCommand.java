package me.theseems.tomshelby.devpack.commands.meta;

import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.command.CommandMeta;
import me.theseems.tomshelby.command.SimpleCommandMeta;
import me.theseems.tomshelby.devpack.commands.DevPermissibleBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MetaGetBotCommand implements DevPermissibleBotCommand {

  @Override
  public void handle(ThomasBot bot, String[] args, Update update) {
    if (args.length < 1){
      bot.sendBack(update, new SendMessage().setText("Укажите ключ"));
      return;
    }

    Object value =
        bot.getChatStorage()
            .getChatMeta(update.getMessage().getChatId())
            .get(args[0])
            .orElse("_<отсутствует>_");
    bot.sendBack(update, new SendMessage().setText(value.toString()).enableMarkdown(true));
  }

  @Override
  public CommandMeta getMeta() {
    return SimpleCommandMeta.onLabel("metaget");
  }
}
