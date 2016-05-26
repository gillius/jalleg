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
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;
/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class ALLEGRO_MEMORY_INTERFACE extends Structure {
	public ALLEGRO_MEMORY_INTERFACE.mi_malloc_callback mi_malloc;
	public ALLEGRO_MEMORY_INTERFACE.mi_free_callback mi_free;
	public ALLEGRO_MEMORY_INTERFACE.mi_realloc_callback mi_realloc;
	public ALLEGRO_MEMORY_INTERFACE.mi_calloc_callback mi_calloc;
	public interface mi_malloc_callback extends Callback {
		Pointer apply(size_t n, int line, Pointer file, Pointer func);
	};
	public interface mi_free_callback extends Callback {
		void apply(Pointer ptr, int line, Pointer file, Pointer func);
	};
	public interface mi_realloc_callback extends Callback {
		Pointer apply(Pointer ptr, size_t n, int line, Pointer file, Pointer func);
	};
	public interface mi_calloc_callback extends Callback {
		Pointer apply(size_t count, size_t n, int line, Pointer file, Pointer func);
	};
	public ALLEGRO_MEMORY_INTERFACE() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("mi_malloc", "mi_free", "mi_realloc", "mi_calloc");
	}
	public ALLEGRO_MEMORY_INTERFACE(ALLEGRO_MEMORY_INTERFACE.mi_malloc_callback mi_malloc, ALLEGRO_MEMORY_INTERFACE.mi_free_callback mi_free, ALLEGRO_MEMORY_INTERFACE.mi_realloc_callback mi_realloc, ALLEGRO_MEMORY_INTERFACE.mi_calloc_callback mi_calloc) {
		super();
		this.mi_malloc = mi_malloc;
		this.mi_free = mi_free;
		this.mi_realloc = mi_realloc;
		this.mi_calloc = mi_calloc;
	}
	public ALLEGRO_MEMORY_INTERFACE(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends ALLEGRO_MEMORY_INTERFACE implements Structure.ByReference {
		
	};
	public static class ByValue extends ALLEGRO_MEMORY_INTERFACE implements Structure.ByValue {
		
	};
}
