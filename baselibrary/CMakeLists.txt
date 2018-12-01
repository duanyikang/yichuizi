cmake_minimum_required(VERSION 3.4.1)

find_library( log-lib
              log )

add_library( pkginfo-lib
             SHARED
             src/main/cpp/pkginfo.cpp )

find_library( android-lib
        android )

target_link_libraries( pkginfo-lib
                       log
                       android)