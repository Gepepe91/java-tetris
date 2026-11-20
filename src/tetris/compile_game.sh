#!/bin/zsh
set -e   # arrête le script à la première commande en erreur

# Se placer dans le dossier où se trouve le script
cd "${0:A:h}"

rm -rf out
mkdir -p out

javac --release 17 -d out *.java

# Copier les ressources (musique) dans le dossier de sortie,
# au même endroit que dans le classpath d'Eclipse : tetris/media/
mkdir -p out/tetris
cp -R media out/tetris/media

jar --create --file Tetris.jar --main-class tetris.Game -C out .

echo "Compilation réussie : Tetris.jar"
echo "Pour lancer le jeu : java -jar Tetris.jar"
