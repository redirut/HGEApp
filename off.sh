#!/bin/bash
FILE=/tmp/pid
if [ -f "$FILE" ]; then
docker stop $(cat $FILE)
else
echo "File pid does not exist"
fi