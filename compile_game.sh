#!/bin/zsh

cd /Users/gabriel/Documents/Etudes/M3/JAVA/Perso/src/tetris
mkdir -p out
javac --release 17 -d out *.java

jar --create --file Tetris.jar --main-class tetris.Game -C out .

echo "\nDo 'java -jar Tetris.jar' to launch the game. \n"
