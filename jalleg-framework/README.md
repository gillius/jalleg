jalleg Framework
================

The jalleg framework is a wrapper sitting on top of the jalleg-binding of Allegro. Goals that I have for this code:

1. Provide convenience wrappers around standard Allegro functionality such as initialization and de-initialization.
2. Make integration with Java easier, such as loading Allegro assets from JARs.
3. Provide a simple game "engine" to manage a typical game loop with timers, update and render stages.
4. Provide a simple maths library. This part is still fuzzy in my mind because there are plenty of vector/math/physics
   implementations for Java out there, although most are not aimed at simple Allegro-style games.

The current code implementation that exists so far is extremely tentative and subject to refactor/rename/etc. It's
mostly to support the examples testing the current bindings.