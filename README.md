jalleg - JVM Binding for Allegro
================================

* Inception: May 2016
* Current Version: 0.1.0-SNAPSHOT
* No releases yet

jalleg is a JVM binding for [Allegro](http://liballeg.org/) 5.2 to be used by any
[JVM language](https://en.wikipedia.org/wiki/List_of_JVM_languages)
(Java obviously, but also other languages such as Groovy, Kotlin, JavaScript, Scala, Jython, JRuby, and Clojure).

(Note: JavaScript on JVM does not mean it can run in a browser. If you want a true JS port of Allegro, check out
[Allegro.js](http://allegrojs.net/))

This project was inspired to build a modern version of a very old project,
[JAllegro](https://sourceforge.net/projects/jallegro/) by Kazzmir.

The binding is based on the [JNA library](https://github.com/java-native-access/jna) to bind to the pre-built Allegro
DLL/so library. The bindings were generated in part from the [JNAerator](http://jnaerator.googlecode.com/) project, then
manipulated to fit the needs and style for jalleg.

Allegro was a library I originally worked with a lot when I was learning C/C++ development in DOS in the late 1990s, and
has always had a special meaning to me. Allegro 5 is a modern, cross-platform variation of the library supporting hardware
rendering and even mobile development. Allegro is a simple library to use and programming a game is a great way to get
into learning programming. My hope is that jalleg expands the Allegro fun to Java and all the JVM languages.

Features / Status
-----------------

Besides the direct binding, jalleg provides a framework around Allegro with the following currently implemented features:
* Automatically unpack and load Allegro DLL for you (Win64 only currently)
* Basic game loop / initialization
* Statistics tracking (rendered frames/sec, logic frames/etc, percentage idle time, etc.)
* Ability to load Allegro resources and create memfiles from JARs and Java byte arrays
* "Retro" audio system capable of generating and playing square, triangle, sawtooth, sine waves or user-defined waveforms

### How you can help

The main thing I need help with is pre-built Linux "portable" (i.e. static linked) versions of Allegro to include in the
auto-loader. It also would be nice if someone can demonstrate that jalleg can work on Android as I suspect it should.

I also currently do not know how to bind to varargs methods when using the "Direct Method Optimization" mode of JNA. A
few methods like al_draw_textf are not available. However, Java has String.format you can use to do a printf-style
formatting and pass into al_draw_text and similar methods.

### Binding Status

I first started out auto-generating all of the core API. The auto-generation works but does not pick the most ideal Java
types for use and in some cases just uses "Pointer" type instead of String or ALLEGRO_BITMAP for example. So I've been
going through and fixing up the API. Anything not marked as "hand-tuned" is definitely subject to change.

The priority will be on APIs that don't already exist in Java. For example in Java there is not much use for a Unicode
strings API since that is already native to Java. Likewise for files, path handling, threads, etc. If developing with
jalleg, I suggest using Java-provided features where possible.

* Core API
  * Configuration files   - hand-tuned (but not tested yet and need to revisit use of ALLEGRO_CONFIG_SECTION)
  * Displays              - :+1: hand-tuned
  * Events                - :+1: hand-tuned
  * File I/O              - :+1: hand-tuned (but missing *fprintf due to varargs)
  * Filesystem            - auto-generated only (suggest Java native)
  * Fixed point math      - No plans to port -- most calls are macros anyway; could be easily re-implemented in Java.
  * Fullscreen modes      - :+1: hand-tuned
  * Graphics routines     - :+1: hand-tuned
  * Haptic routines       - no binding yet (ALLEGRO_UNSTABLE API)
  * Joystick routines     - :+1: hand-tuned
  * Keyboard routines     - :+1: hand-tuned
  * Memory management     - :+1: hand-tuned
  * Monitors              - :+1: hand-tuned
  * Mouse routines        - :+1: hand-tuned
  * Path structures       - auto-generated only (suggest Java native)
  * Shader                - auto-generated only
  * State                 - :+1: hand-tuned
  * System routines       - :+1: hand-tuned
  * Threads               - auto-generated only (suggest Java native)
  * Time                  - :+1: hand-tuned
  * Timer                 - :+1: hand-tuned
  * Touch input           - auto-generated only
  * Transformations       - :+1: hand-tuned
  * UTF-8 string routines - auto-generated only (varargs methods missing)
  * Miscellaneous         - auto-generated only
  * Platform-specific     - auto-generated only
  * Direct3D integration  - auto-generated only
  * OpenGL integration    - auto-generated only

* Addons
  * Audio addon           - :+1: hand-tuned
  * Audio codecs          - :+1: hand-tuned
  * Color addon           - no binding yet
  * Font addons           - :+1: hand-tuned (varargs methods missing)
  * Image I/O addon       - :+1: hand-tuned
  * Main addon            - n/a
  * Memfile addon         - :+1: hand-tuned
  * Native dialogs addon  - no binding yet (suggest Java native)
  * PhysicsFS addon       - no binding yet
  * Primitives addon      - :+1: hand-tuned


Getting Started
---------------

There is currently no pre-built distribution of jalleg. You will have to build it yourself; however, this is quite easy
as you need only Java 7+ installed and on your path, and a connection to the Internet. The project is built with
[Gradle](http://gradle.org/). Gradle is capable of downloading itself and all dependencies needed to build the project.

Simply get the source code and run `gradlew build` in Windows or `sh gradlew build` for Unix-like shells such as BASH.
If you've cloned from git or run `chmod u+x gradlew` then you can just use `./gradlew` in Unix-like systems. Gradle
invocation examples from onward will use `gradlew` as example, but replace with `sh gradlew` or `./gradlew` as
appropriate.

There are 3 libraries built by the project:

1. **jalleg-binding:** Contains the JNA-based bindings themselves to load and use the Allegro library.
2. **jalleg-rt:** Optional, contains within a Windows 64-bit pre-built Allegro DLL. If this JAR is included in your
   project, JNA will automatically unpack the DLL and use it when on Windows. Other OS may be added in the future.
3. **jalleg-framework:** framework to make using the binding from Java easier and to support the examples.
4. **jalleg-examples:** An example project using jalleg-binding and jalleg-rt.

Currently since jalleg-rt is supporting Windows 64 bit only at this time, if you want to run jalleg on another OS, you
need to have `allegro_monolith-5.2.dll` or `liballegro_monolith-5.2.so` in a location where the OS can load it (such as
in PATH on Windows).

If you want to run the example program:

```
gradlew :jalleg-examples:run
```

Documentation
-------------

The binding is a straight port of the Allegro library. Use the
[official Allegro documentation](http://liballeg.org/a5docs/5.2.0/).

Another good place to go for help is the [Allegro.cc](https://www.allegro.cc/) community, one of the most supportive
communities I've ever seen on the Internet and a great help and inspiration to my own learning when I started many
years ago.

Example
-------

Here is a very simple example showing how to open a display and clear it to a color:

```java
import static org.gillius.jalleg.binding.AllegroLibrary.*;

/**
 * Displays a 640x480 red window for 1 second.
 */
public class HelloAllegro {
	public static void main(String[] args) throws Exception {
		al_install_system(ALLEGRO_VERSION_INT, null);

		ALLEGRO_BITMAP display = al_create_display(640, 480);

		al_clear_to_color(al_map_rgb_f(1f, 0f, 0f));
		al_flip_display();
		Thread.sleep(1000);

		al_destroy_display(display);

		al_uninstall_system();
	}
}
```