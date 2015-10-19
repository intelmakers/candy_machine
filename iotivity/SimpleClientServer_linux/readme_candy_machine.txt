Used as IoTivity client for the candy_machine.
This code is from iotivity-0.9.0-abd7880,
  in dir:
iotivity-0.9.0-abd7880/resource/csdk/stack/samples/linux/SimpleClientServer

it compiles on linux (probably after installing the right oic, boost and libssl-dev version),
just do "make" in the above directory.

We only used the client of this code (occlient.cpp) to comunicate with
the edison. Edison's IP is hardcoded in the client (TRG_IP),
so you must recompile if the IP changes.

Usage:
  ./release/occlient -u 1 -t 3

