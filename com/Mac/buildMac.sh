#/bin/sh
cc -DFRAMEWIN ../capture.c -I/usr/local/include /usr/local/lib/libopencv_core.dylib /usr/local/lib/libopencv_highgui.dylib /usr/local//lib/libopencv_imgproc.dylib -o captureWin
