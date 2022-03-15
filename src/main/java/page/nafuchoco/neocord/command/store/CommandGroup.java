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

import page.nafuchoco.neocord.command.executor.CommandExecutor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandGroup {
    private final String groupName;
    private final Map<String, CommandExecutor> executors;
    private boolean enabled;

    protected CommandGroup(String groupName) {
        this.groupName = groupName;
        executors = new LinkedHashMap<>();
        enabled = true;
    }

    public String getGroupName() {
        return groupName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void registerCommand(CommandExecutor executor) {
        String name = executor.getName();
        executors.put(name, executor);
        for (String alias : executor.getAliases())
            executors.put(alias, executor);
    }

    public void unregisterCommand(CommandExecutor executor) {
        executors.remove(executor.getName());
        executor.getAliases().forEach(executors::remove);
    }

    public List<CommandExecutor> getCommands() {
        return executors.values().stream().toList();
    }

    public CommandExecutor getExecutor(String name) {
        return executors.get(name);
    }
}
