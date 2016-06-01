Process used to generate the jalleg binding
===========================================

I followed the following steps to generate the source code for this library. This is only useful if you want to update
the binding. None of these steps are required to use the binding.

Setup
-----

1. Download jnaerator-0.12-shaded.jar. I downloaded it from Maven Central:
   http://repo1.maven.org/maven2/com/nativelibs4java/jnaerator/0.12/
2. Download Allegro5 headers and DLL. I got mine from http://download.gna.org/allegro/allegro-bin/5.2.0/. Put the
   headers in "include" and the DLL in the project root folder.

Initial Generation (from scratch)
---------------------------------

1. Run jnaerate.bat
2. Copy the files from the "gen" folder into the matching folder in "src".
3. Update AllegroLibrary JNA\_LIBRARY\_NAME to match the real DLL.

Refactoring Steps
-----------------

Here are the changes I've made from the raw JNAerator output.

1. Clean up the code with some automated refactorings from IntelliJ IDEA:
   remove redundant interface identifiers, unnecessary semicolons, redundant casts.
2. Replace NativeSize class provided by JNAerator with my own size\_t, so there is no dependency on JNAerator's lib.
   This is in AllegroLibrary as well as a few of the structs.
3. Since ALLEGRO_COLOR is always used by value and is supposed to be "opaque", remove constructor taking rgba and turn
   off "autoSync"
4. Since varargs is not supported in JNA direct mapping, comment these out. None of the methods "lost" this way are
   relevant on Java, which has its own String format and UTF-8 functions, but could theoretically be readded as normal
   JNA mapping
5. For whatever reason, JNAerator generates a "Deprecated" version and non-deprecated version of the same method it
   thinks is "safer" or "better". As far as I can tell from JNA documentation, it's doing the opposite of proper advice.
   So I have modified methods to return the best thing. Typically this means:
   1. Almost always Replacing "byte" with boolean
   2. Almost always replacing PointerByReference with a proper typed pointer like ALLEGRO_BITMAP
   3. Changing Pointer to String where strings are used
   4. Changing Pointer to Buffer as appropriate where user-generated data is fed into Allegro
6. JNAerator interpreted the typed pointers like void\*\* (JNA PointerByReference). I am manually replacing these with
   their typed equivalents like ALLEGRO\_BITMAP.
7. Created size_t class.

Updating Generation
-------------------

1. For simple work, it is normally quite easy to just type in the missing function or struct.
2. Alternative is to run the "Initial Generation" steps to re-generate the raw output from jnaerator. From there you can
   observe how the struct, enum, functions, etc. are mapped. You can then copy the relevant parts into AllegroLibrary.
   See the "Refactoring Steps" for how I've tweaked the output.
