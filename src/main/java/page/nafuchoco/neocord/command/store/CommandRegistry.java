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

package page.nafuchoco.neocord.command.store;

import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.Nullable;
import page.nafuchoco.neocord.command.CommandRegistrar;
import page.nafuchoco.neocord.command.executor.CommandExecutor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandRegistry extends CommandRegistrar {
    private final Map<String, CommandGroup> groups = new LinkedHashMap<>();

    public CommandRegistry(JDA jda, boolean registerAlias) {
        super(jda, registerAlias);
    }

    public void registerCommand(CommandExecutor executor) {
        registerCommand(executor, null);
    }

    public void registerCommand(CommandExecutor executor, String groupName) {
        for (CommandGroup g : groups.values()) {
            if (g.getCommands().contains(executor))
                throw new IllegalArgumentException("Cannot register a command executor that has already been registered in another command group.");
        }

        CommandGroup group = groups.computeIfAbsent(groupName, key -> new CommandGroup(groupName));
        group.registerCommand(executor);

        registerCommandToDiscord(executor);
    }

    public void unregisterCommand(String name) {
        unregisterCommand(getExecutor(name));
    }

    public void unregisterCommand(CommandExecutor executor) {
        for (CommandGroup g : groups.values())
            g.unregisterCommand(executor);
        unregisterCommandFromDiscord(executor);
    }

    public void deleteCommandGroup(String groupName) {
        groups.remove(groupName);
    }

    public void deleteCommandGroup(CommandGroup commandGroup) {
        groups.remove(commandGroup);
    }

    public List<CommandGroup> getCommandGroups() {
        return new ArrayList<>(groups.values());
    }

    public List<String> getCommandGroupsNames() {
        return new ArrayList<>(groups.keySet());
    }

    public List<CommandExecutor> getCommands() {
        return groups.values().stream().flatMap(v -> v.getCommands().stream()).distinct().collect(Collectors.toList());
    }

    public CommandGroup getCommandGroup(@Nullable String groupName) {
        return groups.get(groupName);
    }

    public CommandExecutor getExecutor(String name) {
        for (CommandGroup group : groups.values()) {
            if (group != null && !group.isEnabled())
                continue;
            return group.getExecutor(name);
        }
        return null;
    }
}
