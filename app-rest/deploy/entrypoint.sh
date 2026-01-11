#!/bin/sh

set -e

cd /opt/app/ais

exec java -jar "$(ls *.jar)"
