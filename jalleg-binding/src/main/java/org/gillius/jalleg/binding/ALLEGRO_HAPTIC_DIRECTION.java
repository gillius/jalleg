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
public class ALLEGRO_HAPTIC_DIRECTION extends Structure {
	public double angle;
	public double radius;
	public double azimuth;
	public ALLEGRO_HAPTIC_DIRECTION() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("angle", "radius", "azimuth");
	}
	public ALLEGRO_HAPTIC_DIRECTION(double angle, double radius, double azimuth) {
		super();
		this.angle = angle;
		this.radius = radius;
		this.azimuth = azimuth;
	}
	public ALLEGRO_HAPTIC_DIRECTION(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends ALLEGRO_HAPTIC_DIRECTION implements Structure.ByReference {
		
	};
	public static class ByValue extends ALLEGRO_HAPTIC_DIRECTION implements Structure.ByValue {
		
	};
}
