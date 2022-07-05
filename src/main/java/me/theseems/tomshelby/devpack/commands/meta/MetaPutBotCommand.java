package me.theseems.tomshelby.devpack.commands.meta;

import com.google.common.base.Joiner;
import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.command.CommandMeta;
import me.theseems.tomshelby.command.SimpleCommandMeta;
import me.theseems.tomshelby.devpack.commands.DevPermissibleBotCommand;
import me.theseems.tomshelby.storage.TomMeta;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MetaPutBotCommand implements DevPermissibleBotCommand {

  @Override
  public void handle(ThomasBot bot, String[] args, Update update) {
    if (args.length < 2){
      SendMessage sendMessage = new SendMessage();
      sendMessage.setText("Укажите ключ и значение");

      bot.sendBack(update, sendMessage);
      return;
    }

    String key = args[0];
    String value = Joiner.on(' ').join(Arrays.stream(args).skip(1).collect(Collectors.toList()));

    TomMeta meta = bot.getChatStorage().getChatMeta(String.valueOf(update.getMessage().getChatId()));
    meta.set(key, value);

    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("ОК");

    bot.sendBack(update, sendMessage);
  }

  @Override
  public CommandMeta getMeta() {
    return SimpleCommandMeta.onLabel("metaput").description("Установить мету: <ключ> - <значение>");
  }
}
