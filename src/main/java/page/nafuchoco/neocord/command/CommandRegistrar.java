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

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import page.nafuchoco.neocord.command.executor.CommandExecutor;

import java.util.*;

@Slf4j
public abstract class CommandRegistrar {
    private final JDA jda;
    private final boolean registerAlias;

    private final Map<String, Command> registeredCommands = new LinkedHashMap<>();

    protected CommandRegistrar(JDA jda, boolean registerAlias) {
        this.jda = jda;
        this.registerAlias = registerAlias;
    }

    protected final void registerCommandToDiscord(@NonNull @NotNull CommandExecutor executor) {
        val commandData = Commands.slash(executor.getName(), executor.getDescription());
        addCommandOptions(executor, commandData);
        jda.upsertCommand(commandData).queue(command -> registeredCommands.put(command.getName(), command));

        if (registerAlias) {
            executor.getAliases().forEach(alias -> {
                val commandAlias = Commands.slash(alias, executor.getDescription());
                addCommandOptions(executor, commandAlias);
                jda.upsertCommand(commandAlias).queue(command -> registeredCommands.put(command.getName(), command));
            });
        }
    }

    protected final void unregisterCommandFromDiscord(@NonNull @NotNull CommandExecutor executor) {
        registeredCommands.remove(executor.getName()).delete().queue();
        executor.getAliases().stream()
                .map(registeredCommands::remove)
                .filter(Objects::nonNull)
                .forEach(command -> command.delete().queue());
    }

    private void addCommandOptions(CommandExecutor executor, SlashCommandData command) {
        executor.getValueOptions().forEach(option -> command.addOption(option.optionType(), option.optionName(), option.optionDescription(), option.required(), option.autoComplete()));
        executor.getSubCommands().forEach(sub -> {
            val subCommand = new SubcommandData(sub.optionName(), sub.optionDescription());
            sub.getValueOptions().forEach(option -> subCommand.addOption(option.optionType(), option.optionName(), option.optionDescription(), option.required(), option.autoComplete()));
            command.addSubcommands(subCommand);
        });
    }

    protected List<Command> getRegisteredCommands() {
        return new ArrayList<>(registeredCommands.values());
    }
}
