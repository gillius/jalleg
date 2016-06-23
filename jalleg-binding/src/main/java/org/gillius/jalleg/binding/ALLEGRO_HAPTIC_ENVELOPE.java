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
public class ALLEGRO_HAPTIC_ENVELOPE extends Structure {
	public double attack_length;
	public double attack_level;
	public double fade_length;
	public double fade_level;
	public ALLEGRO_HAPTIC_ENVELOPE() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("attack_length", "attack_level", "fade_length", "fade_level");
	}
	public ALLEGRO_HAPTIC_ENVELOPE(double attack_length, double attack_level, double fade_length, double fade_level) {
		super();
		this.attack_length = attack_length;
		this.attack_level = attack_level;
		this.fade_length = fade_length;
		this.fade_level = fade_level;
	}
	public ALLEGRO_HAPTIC_ENVELOPE(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends ALLEGRO_HAPTIC_ENVELOPE implements Structure.ByReference {
		
	};
	public static class ByValue extends ALLEGRO_HAPTIC_ENVELOPE implements Structure.ByValue {
		
	};
}