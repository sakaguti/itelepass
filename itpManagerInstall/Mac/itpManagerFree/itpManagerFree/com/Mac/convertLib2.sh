#!/bin/bash
install_name_tool convert -change /usr/local/Cellar/imagemagick/6.7.1-1/lib/libMagickCore.4.dylib com/Mac/lib/libMagickCore.4.dylib

install_name_tool convert -change /usr/local/Cellar/imagemagick/6.7.1-1/lib/libMagickWand.4.dylib com/Mac/lib/libMagickWand.4.dylib

install_name_tool convert -change /usr/local/lib/liblcms.1.0.19.dylib com/Mac/lib/liblcms.1.0.19.dylib

install_name_tool convert -change /usr/local/lib/libtiff.3.dylib com/Mac/lib/libtiff.3.dylib

install_name_tool convert -change /usr/X11/lib/libfreetype.6.dylib com/Mac/lib/libfreetype.6.dylib 

install_name_tool convert -change /usr/local/lib/libjpeg.8.dylib com/Mac/lib/libjpeg.8.dylib 

install_name_tool convert -change /usr/X11/lib/libfontconfig.1.dylib com/Mac/lib/libfontconfig.1.dylib 

install_name_tool convert -change /usr/X11/lib/libXext.6.dylib com/Mac/lib/libXext.6.dylib 

install_name_tool convert -change /usr/X11/lib/libXt.6.dylib com/Mac/lib/libXt.6.dylib

install_name_tool convert -change /usr/lib/libbz2.1.0.dylib com/Mac/lib/libbz2.1.0.dylib 

install_name_tool convert -change /usr/lib/libz.1.dylib com/Mac/lib/libz.1.dylib

install_name_tool convert -change /usr/lib/libSystem.B.dylib com/Mac/lib/libSystem.B.dylib

install_name_tool convert -change /usr/lib/libltdl.7.dylib com/Mac/lib/libltdl.7.dylib 

install_name_tool convert -change /usr/X11/lib/libSM.6.dylib com/Mac/lib/libSM.6.dylib

install_name_tool convert -change /usr/X11/lib/libICE.6.dylib com/Mac/lib/libICE.6.dylib

install_name_tool convert -change /usr/X11/lib/libX11.6.dylib com/Mac/lib/libX11.6.dylib
