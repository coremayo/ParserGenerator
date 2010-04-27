CS3240 Spring 2010
Final Project

James Jones
James King
Corey Mayo

Prerequisites:
This program requires the Sun Java runtime environment to be installed. It should run and compile on either windows or linux.

To Compile:
From the command line navigate to the project folder and type: "apache-maven-2.2.1/bin/mvn install".
If you are running windows, use backslashes in the path instead.
A precompiled jar file is included if compilation problems occur.

To Run:
Type "java -jar target/program.jar" from the project's root directory. This will invoke the system's java command. When called without any parameters, the program will print usage then exit.

Usage:
-g (--grammar) FILE       : File containing grammar specification
-i (--input) FILE         : File containing input to check against the
                            supplied grammar.If this is not supplied, then the
                            parse table is written out to the specified
                            filename.
-o (--output) FILE        : When a test input is not supplied, the parse table
                            is written to this file.
-pt (--parseTable) FILE   : File containing a parseTable to check input
                            against, cannot be using with grammar file input
-s (--scan) FILE          : Takes in a tiny program file, and outputs tokens
                            to a file called <filename>-tokenized.txt
-so (--scannedOutput) VAL : File to output tokenized tiny program to

The following combinations of parameters are valid:
  -g and -i
  -g and -o
  -g, -s, and -so
  -pt and -i
  -pt, -g, and -so

