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
3. **jalleg-example:** An example project using jalleg-binding and jalleg-rt.

Currently since jalleg-rt is supporting Windows 64 bit only at this time, if you want to run jalleg on another OS, you
need to have `allegro_monolith-5.2.dll` or `liballegro_monolith-5.2.so` in a location where the OS can load it (such as
in PATH on Windows).

If you want to run the example program:

```
gradlew :jalleg-example:run
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