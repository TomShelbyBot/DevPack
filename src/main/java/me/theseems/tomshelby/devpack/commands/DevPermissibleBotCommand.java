package me.theseems.tomshelby.devpack.commands;

import me.theseems.tomshelby.command.PermissibleBotCommand;
import me.theseems.tomshelby.devpack.Main;

public interface DevPermissibleBotCommand extends PermissibleBotCommand {
  @Override
  default boolean canUse(String chatId, Long userId) {
    // Just check if we can consider the user as a developer
    return Main.getDevConfig().getDeveloperIds().contains(userId);
  }
}
