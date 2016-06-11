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
import com.sun.jna.ptr.PointerByReference;
import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_JOYSTICK;

import java.util.Arrays;
import java.util.List;
/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class ALLEGRO_JOYSTICK_EVENT extends Structure {
	public int type;
	public PointerByReference source;
	public double timestamp;
	public ALLEGRO_JOYSTICK id;
	public int stick;
	public int axis;
	public float pos;
	public int button;
	public ALLEGRO_JOYSTICK_EVENT() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("type", "source", "timestamp", "id", "stick", "axis", "pos", "button");
	}
	public ALLEGRO_JOYSTICK_EVENT(int type, PointerByReference source, double timestamp, ALLEGRO_JOYSTICK id, int stick, int axis, float pos, int button) {
		super();
		this.type = type;
		this.source = source;
		this.timestamp = timestamp;
		this.id = id;
		this.stick = stick;
		this.axis = axis;
		this.pos = pos;
		this.button = button;
	}
	public ALLEGRO_JOYSTICK_EVENT(Pointer peer) {
		super(peer);
	}

	public static class ByReference extends ALLEGRO_JOYSTICK_EVENT implements Structure.ByReference { }
	public static class ByValue extends ALLEGRO_JOYSTICK_EVENT implements Structure.ByValue { }
}
