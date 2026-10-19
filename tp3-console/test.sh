#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mkdir -p out
find src/main/java src/test/java -name '*.java' -print > .java-sources
javac --release 17 -encoding UTF-8 -d out @.java-sources
rm .java-sources
java -cp out com.menualdia.tp3.PruebasTP3
