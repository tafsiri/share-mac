#!/bin/sh
cd "${0%/*}"
cd src
java -Xms256m -Xmx512m -Xdock:name="Share" -Xdock:icon=../data/images/share.icns -jar ../jruby/jruby_complete.jar -S main.class