#!/bin/bash
FILE=/tmp/pid
RESULT=/tmp/result
if [ -f "$FILE" ]; then
  docker stop $(cat $FILE) > $RESULT
  if [ "$(cat $RESULT)" = "$(cat $FILE)" ]; then
    rm -rf $FILE
  else
    echo "Result failure"
  fi
else
  echo "File pid does not exist"
fi
