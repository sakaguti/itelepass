project( capture )
find_package( OpenCV REQUIRED )
add_executable( capture capture )
target_link_libraries( capture ${OpenCV_LIBS} )
