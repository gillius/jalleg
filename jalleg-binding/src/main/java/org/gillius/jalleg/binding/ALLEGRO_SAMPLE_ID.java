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
public class ALLEGRO_SAMPLE_ID extends Structure {
	public int _index;
	public int _id;
	public ALLEGRO_SAMPLE_ID() {
		super();
		setAutoSynch(false);
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("_index", "_id");
	}
	public ALLEGRO_SAMPLE_ID(int _index, int _id) {
		super();
		this._index = _index;
		this._id = _id;
	}
	public ALLEGRO_SAMPLE_ID(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends ALLEGRO_SAMPLE_ID implements Structure.ByReference {
		
	};
	public static class ByValue extends ALLEGRO_SAMPLE_ID implements Structure.ByValue {
		
	};
}
