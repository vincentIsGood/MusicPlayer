@echo off
rem chcp 65001 -- change code page to "utf8" (not quite necessary)
rem -Dfile.encoding=UTF-8 -- try (for ffmpeg to read utf-8 encoded file names)

rem chcp 932
rem 932 shift_jis

java -cp lib\*; -Dfile.encoding=UTF-8 com.vincentcodes.vincentplayer.Main
rem java -cp lib\*; com.vincentcodes.vincentplayer.Main
pause