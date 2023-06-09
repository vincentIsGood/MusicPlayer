# Vincent Player
Since the project is kinda old and the project structure is a little messy. 
This project is put on halt (not in development), just bear with it.

Note: This project is no longer maintained.

## How you should run the jar
```sh
java -Dfile.encoding=utf-8 -jar vincentplayer_v5.0.jar
```

## Sample commands
help [cmd]  -- get info of the specified command
addall      -- add all mp3 songs in this directory
list        -- list mp3 songs
play        -- play the latest song added
func [file] -- eg. to run `init.vpc`, do `func init`

## Scripting
See `*.vpc` files for reference. Comments are provided within the files.

## Caution
UDP library for streaming is still very unstable. Do not recommend using it.
UDP library is **ALREADY** deprecated. It served as a proof of concept of using
UDP for streaming.