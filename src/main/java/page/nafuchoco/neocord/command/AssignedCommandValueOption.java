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

import lombok.ToString;
import page.nafuchoco.neocord.command.option.CommandValueOption;

/**
 * Value assigned CommandValueOption
 *
 * @param <T> Type of assigned value
 * @see CommandValueOption
 */
@ToString
public class AssignedCommandValueOption<T> extends CommandValueOption {
    private final T value;

    protected AssignedCommandValueOption(CommandValueOption commandValueOption, T value) {
        super(commandValueOption.optionType(),
                commandValueOption.optionName(),
                commandValueOption.optionDescription(),
                commandValueOption.required(),
                commandValueOption.autoComplete());
        this.value = value;
    }

    /**
     * @return assigned value
     */
    public T getValue() {
        return value;
    }
}
