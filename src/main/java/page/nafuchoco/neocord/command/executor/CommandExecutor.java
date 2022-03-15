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

package page.nafuchoco.neocord.command.executor;

import page.nafuchoco.neocord.command.option.CommandOption;
import page.nafuchoco.neocord.command.option.CommandValueOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandExecutor implements ICommandExecutor {
    private final List<CommandOption> options = new ArrayList<>();

    private final String name;
    private final List<String> aliases;

    protected CommandExecutor(String name, String... aliases) {
        this.name = name;
        this.aliases = Arrays.asList(aliases);
    }


    /**
     * Get Command name from Executor
     *
     * @return return Command Name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get all aliases registered for this command
     *
     * @return List of aliases
     */
    @Override
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * Get options for this command (in Slash command)
     *
     * @return List of options in this command
     */
    public List<CommandOption> getOptions() {
        return options;
    }

    /**
     * Get value-only options for this command
     *
     * @return List of value-only Options
     */
    public List<CommandValueOption> getValueOptions() {
        return options.stream()
                .filter(CommandValueOption.class::isInstance)
                .map(CommandValueOption.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Get attached sub-commands
     *
     * @return Subcommand's Executor
     */
    public List<SubCommandExecutor> getSubCommands() {
        return options.stream()
                .filter(SubCommandExecutor.class::isInstance)
                .map(SubCommandExecutor.class::cast)
                .collect(Collectors.toList());
    }
}
