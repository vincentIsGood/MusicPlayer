# Vincent Player
Since the project is kinda old and the project structure is a little messy. 
This project is put on halt (not in development), just bear with it.

Edit: Just said I won't maintain the code. But I will still fix any bugs that occur.

## How to play musics
To run the jar:
```sh
java -Dfile.encoding=utf-8 -jar vincentplayer_v5.0.jar
```

Flow: 
- Step 1: Add music
- Step 2: Play music

Optional Features:
- Create playlist
- Create scripts with `.vpc`
- Stream music to other users [beta]

## Sample commands
Useful commands: `ls, cd, list, addall`

```
help [cmd]   -- get info of the specified command
addall       -- add all files in this directory to the store 
                (recommend a folder with audio files ONLY)
list         -- list imported mp3 songs
play [name]  -- play the latest song added (non-english titles may cause problems on Windows)
func [file]  -- eg. to run "init.vpc", do "func init"
```

### Start stream [beta]:
Simple Demonstration on localhost. 

Client 1:
```
add test.mp3
play test.mp3

startstream 1234 tcp

// if you wanna stop
stopstream
```

Client 2:
```
connectstream 127.0.0.1 1234

// if you wanna stop
stopstream
```

## Scripting
See `*.vpc` files for reference. Comments are provided within the files.

## Caution
UDP library for streaming is still very unstable. Do not recommend using it.
UDP library is **ALREADY** deprecated. It served as a proof of concept of using
UDP for streaming.

## Personal
This is one of the old & sizeable projects built back in the old days using Java.
With the rise of Spotify, I downloaded fewer mp3 from the Internet. However, I still 
hope someone can find this project useful.