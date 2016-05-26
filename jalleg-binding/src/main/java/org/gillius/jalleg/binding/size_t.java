/*
 * Copyright 2016 Jason Winnebeck
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

package org.gillius.jalleg.binding;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;

/**
 * Represents a C size_t. For whatever reason, JNA does not include this functionality by default, this particular class
 * found from https://github.com/java-native-access/jna/issues/191
 */
public class size_t extends IntegerType {
	public size_t() {
		this(0);
	}

	public size_t(long value) {
		super(Native.SIZE_T_SIZE, value);
	}
}