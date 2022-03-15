/*
 * Copyright 2022 NAFU_at.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package page.nafuchoco.neocord.command;

import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import page.nafuchoco.neocord.command.executor.CommandExecutor;
import page.nafuchoco.neocord.command.store.CommandGroup;
import page.nafuchoco.neocord.command.store.CommandRegistry;

import java.util.List;

/**
 * This class defines command system for NeoCord.
 * This class contains every command's control flows.
 * You must register any command inherits {@link CommandExecutor} to here.
 */
public final class CommandManager {

    public static CommandManager getInstance(@NotNull JDA jda) {
        return getInstance(jda, false);
    }

    public static CommandManager getInstance(@NotNull JDA jda, boolean registerAlias) {
        return new CommandManager(jda, registerAlias);
    }


    private final CommandRegistry registry;
    private final SlashCommandEventHandler handler;

    public CommandManager(JDA jda, boolean registerAlias) {
        registry = new CommandRegistry(jda, registerAlias);
        handler = new SlashCommandEventHandler(registry);
        jda.addEventListener(new SlashCommandEventHandler(registry));
    }

    public void registerCommand(@NotNull CommandExecutor executor) {
        registry.registerCommand(executor);
    }

    public void registerCommand(@NotNull CommandExecutor executor, @Nullable String groupName) {
        registry.registerCommand(executor, groupName);
    }

    public void unregisterCommand(@NotNull String name) {
        registry.unregisterCommand(name);
    }

    public void unregisterCommand(@NotNull CommandExecutor executor) {
        registry.unregisterCommand(executor);
    }

    public void deleteCommandGroup(@Nullable String groupName) {
        registry.deleteCommandGroup(groupName);
    }

    public void deleteCommandGroup(@NotNull CommandGroup commandGroup) {
        registry.deleteCommandGroup(commandGroup);
    }

    public List<CommandGroup> getCommandGroups() {
        return registry.getCommandGroups();
    }

    public List<String> getCommandGroupsNames() {
        return registry.getCommandGroupsNames();
    }

    public List<CommandExecutor> getCommands() {
        return registry.getCommands();
    }

    public CommandGroup getCommandGroup(@Nullable String groupName) {
        return registry.getCommandGroup(groupName);
    }
}
