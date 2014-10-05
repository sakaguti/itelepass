ope#!/bin/bash
gcc -o capture capture.c
install_name_tool capture -change /usr/local/lib/libusb-1.0.0.dylib ./libusb-1.0.0.dylib
