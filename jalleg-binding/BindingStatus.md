### Binding Status

Almost all of the Allegro API is mapped. Some of the API is mapped but hasn't been tested yet.

* Core API
  * Configuration files   - :+1: (plus convenience API in framework)
  * Displays              - :+1:
  * Events                - :+1:
  * File I/O              - :+1: (but missing *fprintf due to varargs)
  * Filesystem            - (not tested yet). No solution yet for time_t and off_t functions due to size
                            differences between platforms. This also impacts the ability to
                            use ALLEGRO_FS_INTERFACE. Use native Java functionality where possible.
  * Fixed point math      - No plans to port -- most calls are macros anyway; could be easily re-implemented in Java.
  * Fullscreen modes      - :+1:
  * Graphics routines     - :+1:
  * Haptic routines       - :+1: (note this is an ALLEGRO_UNSTABLE API)
  * Joystick routines     - :+1:
  * Keyboard routines     - :+1:
  * Memory management     - :+1:
  * Monitors              - :+1:
  * Mouse routines        - :+1:
  * Path structures       - (not tested yet) (suggest Java native)
  * Shader                - (not tested yet)
  * State                 - :+1:
  * System routines       - :+1:
  * Threads               - (not tested yet) (**highly** suggest Java native threads)
  * Time                  - :+1:
  * Timer                 - :+1:
  * Touch input           - (not tested yet)
  * Transformations       - :+1:
  * UTF-8 string routines - (not tested yet) (varargs methods missing) (**highly** suggest Java String)
  * Miscellaneous         - need to determine if al_run_main is needed
  * Platform-specific     - cannot implement these yet, may find workaround for Android methods if someone tries jalleg there
  * Direct3D integration  - no current plan to implement unless a D3D Java binding is found and use case
  * OpenGL integration    - no current plan to implement unless there is a use case

* Addons
  * Audio addon           - :+1:
  * Audio codecs          - :+1:
  * Color addon           - :+1:
  * Font addons           - :+1: (varargs methods missing)
  * Image I/O addon       - :+1:
  * Main addon            - n/a
  * Memfile addon         - :+1:
  * Native dialogs addon  - binding, but without al_build_menu (consider Java native for file chooser)
  * PhysicsFS addon       - :+1:
  * Primitives addon      - :+1:
