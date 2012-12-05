#!/bin/bash
# Run crux.Compiler on all the .crx files in the current directory, then
# execute spim on the resulting assembly file with the .in file as input,
# and lastly compare the result with the corresponding .out file.
# Author: Jack Cheng

for FILE in tests/lab6/public/*.crx
do
	echo "Testing $FILE..."
	java -cp bin: crux.Compiler $FILE
	spim -file ${FILE%crx}asm < ${FILE%crx}in | sed '1,5 d' > temp.out
	sed -i -e '$a\' temp.out
	sed -e '$a\' ${FILE%crx}out > temp2.out
	diff temp.out temp2.out --strip-trailing-cr
	if [ "$?" -ne "0" ]; then
		break
	fi
done
rm temp.asm temp.out temp2.out
