#!/bin/bash


echo "Yes instances:"
for f in Yes/*.in; do echo "Processing $f file.."; java -jar test.jar $f; done
echo "No instances:"
for f in No/*.in; do echo "Processing $f file.."; java -jar test.jar $f; done


