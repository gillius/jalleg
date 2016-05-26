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
public class ALLEGRO_FILE_INTERFACE extends Structure {
	public ALLEGRO_FILE_INTERFACE.fi_fopen_callback fi_fopen;
	public ALLEGRO_FILE_INTERFACE.fi_fclose_callback fi_fclose;
	public ALLEGRO_FILE_INTERFACE.fi_fread_callback fi_fread;
	public ALLEGRO_FILE_INTERFACE.fi_fwrite_callback fi_fwrite;
	public ALLEGRO_FILE_INTERFACE.fi_fflush_callback fi_fflush;
	public ALLEGRO_FILE_INTERFACE.fi_ftell_callback fi_ftell;
	public ALLEGRO_FILE_INTERFACE.fi_fseek_callback fi_fseek;
	public ALLEGRO_FILE_INTERFACE.fi_feof_callback fi_feof;
	public ALLEGRO_FILE_INTERFACE.fi_ferror_callback fi_ferror;
	public ALLEGRO_FILE_INTERFACE.fi_ferrmsg_callback fi_ferrmsg;
	public ALLEGRO_FILE_INTERFACE.fi_fclearerr_callback fi_fclearerr;
	public ALLEGRO_FILE_INTERFACE.fi_fungetc_callback fi_fungetc;
	public interface fi_fopen_callback extends Callback {
		Pointer apply(Pointer path, Pointer mode);
	};
	public interface fi_fclose_callback extends Callback {
		byte apply(Pointer handle);
	};
	public interface fi_fread_callback extends Callback {
		size_t apply(Pointer f, Pointer ptr, size_t size);
	};
	public interface fi_fwrite_callback extends Callback {
		size_t apply(Pointer f, Pointer ptr, size_t size);
	};
	public interface fi_fflush_callback extends Callback {
		byte apply(Pointer f);
	};
	public interface fi_ftell_callback extends Callback {
		long apply(Pointer f);
	};
	public interface fi_fseek_callback extends Callback {
		byte apply(Pointer f, long offset, int whence);
	};
	public interface fi_feof_callback extends Callback {
		byte apply(Pointer f);
	};
	public interface fi_ferror_callback extends Callback {
		int apply(Pointer f);
	};
	public interface fi_ferrmsg_callback extends Callback {
		String apply(Pointer f);
	};
	public interface fi_fclearerr_callback extends Callback {
		void apply(Pointer f);
	};
	public interface fi_fungetc_callback extends Callback {
		int apply(Pointer f, int c);
	};
	public interface off_t_callback extends Callback {
		int apply(Pointer f);
	};
	public ALLEGRO_FILE_INTERFACE() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("fi_fopen", "fi_fclose", "fi_fread", "fi_fwrite", "fi_fflush", "fi_ftell", "fi_fseek", "fi_feof", "fi_ferror", "fi_ferrmsg", "fi_fclearerr", "fi_fungetc");
	}
	public ALLEGRO_FILE_INTERFACE(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends ALLEGRO_FILE_INTERFACE implements Structure.ByReference {
		
	};
	public static class ByValue extends ALLEGRO_FILE_INTERFACE implements Structure.ByValue {
		
	};
}
