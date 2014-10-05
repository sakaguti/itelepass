#/bin/sh
cc capture.c -I/usr/local/include/opencv -I/usr/local/include/opencv2 /usr/local/lib/libopencv_core.dylib /usr/local/lib/libopencv_highgui.dylib /usr/local//lib/libopencv_imgproc.dylib -o capture
