@echo off
set files1=./com/vincentcodes/vincentplayer/*.java ./com/vincentcodes/vincentplayer/exceptions/*.java ./com/vincentcodes/vincentplayer/commands/*.java
:: Linux command: find . -name *.java > files.txt

cd src
:: Build with release 10 or later
javac --release 10 -cp ../lib/*; -d ../ %files1%
rem javac -cp lib\*; -d . %first%%second%
rem javadoc -sourcepath ./src -d ./docs -cp ./lib/* com/vincentcodes/vincentplayer

pause