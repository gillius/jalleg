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
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class ALLEGRO_JOYSTICK_STATE extends Structure {
	public ALLEGRO_JOYSTICK_STATE.stick_struct[] stick = new ALLEGRO_JOYSTICK_STATE.stick_struct[16];
	/** 0 to 32767 */
	public int[] button = new int[32];
	public static class stick_struct extends Structure {
		/** -1.0 to 1.0 */
		public float[] axis = new float[3];
		public stick_struct() {
			super();
		}
		protected List<? > getFieldOrder() {
			return Arrays.asList("axis");
		}
		public stick_struct(float axis[]) {
			super();
			if ((axis.length != this.axis.length)) 
				throw new IllegalArgumentException("Wrong array size !");
			this.axis = axis;
		}
		public stick_struct(Pointer peer) {
			super(peer);
		}
		public static class ByReference extends stick_struct implements Structure.ByReference {
			
		};
		public static class ByValue extends stick_struct implements Structure.ByValue {
			
		};
	};
	public ALLEGRO_JOYSTICK_STATE() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("stick", "button");
	}
	public ALLEGRO_JOYSTICK_STATE(ALLEGRO_JOYSTICK_STATE.stick_struct stick[], int button[]) {
		super();
		if ((stick.length != this.stick.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.stick = stick;
		if ((button.length != this.button.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.button = button;
	}
	public ALLEGRO_JOYSTICK_STATE(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends ALLEGRO_JOYSTICK_STATE implements Structure.ByReference {
		
	};
	public static class ByValue extends ALLEGRO_JOYSTICK_STATE implements Structure.ByValue {
		
	};
}
