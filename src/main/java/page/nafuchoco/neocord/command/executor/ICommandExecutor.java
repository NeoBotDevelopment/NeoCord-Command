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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import page.nafuchoco.neocord.command.CommandContext;

import java.util.List;

public interface ICommandExecutor {

    /**
     * Get Command name from Executor
     *
     * @return return Command Name
     */
    @NotNull
    String getName();

    /**
     * Get all aliases registered for this command
     *
     * @return List of aliases
     */
    @NotNull
    List<String> getAliases();

    /**
     * Processing when the command is called.
     *
     * @param context The command context to use at runtime
     */
    @Nullable
    void onInvoke(CommandContext context);

    /**
     * @return Description of command
     */
    @NotNull
    String getDescription();
}
