package me.theseems.tomshelby.devpack.commands;

import me.theseems.tomshelby.command.PermissibleBotCommand;

public interface DevPermissibleBotCommand extends PermissibleBotCommand {
  @Override
  default boolean canUse(Long chatId, Integer userId) {
    return userId == 311245296; // Temp. Only for @theseems
  }
}