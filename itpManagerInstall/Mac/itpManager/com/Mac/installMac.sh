ope#!/bin/bash
gcc -o sendcom libusb-1.0.0.dylib sendCom.c
install_name_tool sendcom -change /usr/local/lib/libusb-1.0.0.dylib ./libusb-1.0.0.dylib
