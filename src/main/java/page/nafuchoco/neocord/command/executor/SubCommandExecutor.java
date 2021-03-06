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

import net.dv8tion.jda.api.interactions.commands.OptionType;
import page.nafuchoco.neocord.command.option.CommandOption;

public abstract class SubCommandExecutor extends CommandExecutor implements CommandOption {

    public SubCommandExecutor(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public final OptionType optionType() {
        return OptionType.SUB_COMMAND;
    }

    @Override
    public final String optionName() {
        return getName();
    }

    @Override
    public final String optionDescription() {
        return getDescription();
    }
}
