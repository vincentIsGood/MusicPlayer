@echo off
set files1=./com/vincentcodes/vincentplayer/*
jar -cvfm vincentplayer_v5.0.jar Manifest.txt %files1%
pause