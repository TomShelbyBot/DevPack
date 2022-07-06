package me.theseems.tomshelby.devpack.commands.meta;

import com.google.common.base.Joiner;
import me.theseems.tomshelby.ThomasBot;
import me.theseems.tomshelby.command.CommandMeta;
import me.theseems.tomshelby.command.SimpleCommandMeta;
import me.theseems.tomshelby.devpack.commands.DevPermissibleBotCommand;
import me.theseems.tomshelby.storage.SimpleTomMeta;
import me.theseems.tomshelby.storage.TomMeta;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class MetaMapBotCommand implements DevPermissibleBotCommand {

  private String traverseContainer(TomMeta meta, int level) {
    StringBuilder spacesBuilder = new StringBuilder();
    for (int i = 0; i < level; i++) {
      spacesBuilder.append(" ");
    }

    StringBuilder builder = new StringBuilder();

    for (String key : meta.getKeys()) {
      builder
          .append('\n')
          .append(spacesBuilder.toString())
          .append("'")
          .append(key)
          .append("'")
          .append(" ⟾ ");

      if (meta.getContainer(key).isPresent()) {
        TomMeta tomMeta = meta.getContainer(key).get();
        builder.append("Контейнер (").append(tomMeta.getKeys().size()).append("): ");
        builder.append(traverseContainer(tomMeta, level + 1));
      } else {
        builder.append(
            meta.get(key).flatMap(value -> Optional.of("'" + value + "'")).orElse("__null__"));
      }
    }

    return builder.toString();
  }

  @Override
  public void handle(ThomasBot bot, String[] args, Update update) {
    try {
      Long chatId;
      if (args.length == 0 || args[0].equals("this")) {
        chatId = update.getMessage().getChatId();
      } else {
        chatId = Long.parseLong(args[0]);
      }

      if (!bot.getChatStorage().getChatIds().contains(chatId.toString())) {
        bot.replyBackText(update, "Для чата нет сохраненной меты");
        return;
      }

      TomMeta meta = bot.getChatStorage().getChatMeta(chatId.toString());
      if (Joiner.on(' ').join(args).contains("-json")) {
        String infoString = "```\n" + SimpleTomMeta.jsonify(meta) + "\n```";
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(infoString);
        sendMessage.enableMarkdown(true);

        // Output in json
        bot.replyBack(update, sendMessage);
        return;
      }

      String builder =
          "Чат: _"
              + chatId
              + "_\n"
              + "Количество записей: _"
              + meta.getKeys().size()
              + "_\n"
              + "```"
              + '\n'
              + traverseContainer(meta, 0)
              + "```";

      SendMessage sendMessage = new SendMessage();
      sendMessage.setText(builder);
      sendMessage.enableMarkdown(true);

      bot.replyBack(update, sendMessage);
    } catch (NumberFormatException e) {
      bot.replyBackText(update, "Укажите валидный айди чата.");
    }
  }

  @Override
  public CommandMeta getMeta() {
    return SimpleCommandMeta.onLabel("metamap").description("Показать карту меты для чата");
  }
}
