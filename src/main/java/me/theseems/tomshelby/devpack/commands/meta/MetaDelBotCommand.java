package me.theseems.tomshelby.devpack.commands.meta;

import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.command.CommandMeta;
import me.theseems.tomshelby.command.SimpleCommandMeta;
import me.theseems.tomshelby.devpack.commands.DevPermissibleBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MetaDelBotCommand implements DevPermissibleBotCommand {

  @Override
  public void handle(ThomasBot bot, String[] args, Update update) {
    if (args.length < 1){
      SendMessage sendMessage = new SendMessage();
      sendMessage.setText("Укажите ключ");

      bot.sendBack(update, sendMessage);
      return;
    }

    bot.getChatStorage()
        .getChatMeta(String.valueOf(update.getMessage().getChatId())).remove(args[0]);

    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("ОК");
    bot.sendBack(update, sendMessage);
  }

  @Override
  public CommandMeta getMeta() {
    return SimpleCommandMeta.onLabel("metadel").description("Удалить мету для чата по ключу");
  }
}
