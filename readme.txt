Regarding VincentPlayer,

From version 5xx/1.5xx onwards...

vincentplayer_vxxx.jar must be run by java version 13 or higher
Moreover, vincentplayer_vxxx.jar is now a standalone jar file, meaning ffmpeg/ is not required anymore.

=======================================
Format of WAV(Newest convention "LIST INFO" section):
Ref: https://wiki.audacityteam.org/wiki/WAV

Artist (IART)
Title (INAM) - called "Track Title" in Metadata Editor
Product (IPRD) - called "Album Title" in Metadata Editor
Track Number (ITRK) (not specified in the original RIFF standard but players supporting LIST INFO tags often support it)
Date Created (ICRD) - called "Year" in Metadata Editor
Genre (IGNR)
Comments (ICMT)
Copyright (ICOP)
Software (ISFT)

========================================

https://stackoverflow.com/questions/11927518/java-unicode-utf-8-and-windows-command-prompt/11927598#11927598