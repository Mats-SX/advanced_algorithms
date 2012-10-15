#!/bin/bash


echo "Yes instances:"
for f in Yes/*.in; do echo "$f"; java -jar test.jar $f; done


