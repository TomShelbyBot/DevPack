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
      SendMessage sendMessage = new SendMessage();
      sendMessage.setText("Укажите ключ");

      bot.sendBack(update, sendMessage);
      return;
    }

    Object value =
        bot.getChatStorage()
            .getChatMeta(String.valueOf(update.getMessage().getChatId()))
            .get(args[0])
            .orElse("_<отсутствует>_");

    SendMessage sendMessage = new SendMessage();
    sendMessage.setText(value.toString());
    sendMessage.enableMarkdown(true);

    bot.sendBack(update, sendMessage);
  }

  @Override
  public CommandMeta getMeta() {
    return SimpleCommandMeta.onLabel("metaget").description("Получить из меты значение для чата по ключу");
  }
}
