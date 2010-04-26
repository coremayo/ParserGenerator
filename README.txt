CS3240 Spring 2010
Final Project

James Jones
James King
Corey Mayo

To Compile:
From the project's root directory, type "./compile". This will invoke apacke maven, which was used as this projects build tool. A file called "project.jar" will be created in the "target/" directory.

To Run:
This program requires the Sun Java runtime environment to be installed and a Unix/Linux based system.

Type "./run" from the project's root directory. This will invoke the system's java command. When called without any parameters, the program will print usage then exit. 

-g (--grammar) FILE : File containing grammar specification
-i (--input) FILE   : File containing input to check against the supplied
                      grammar. If this is not supplied, then the parse table is
                      written out to the specified filename.
-o (--output) FILE  : When a test input is not supplied, the parse table is
                      written to this file.

The -g flag and either -i or -o are required. The grammar flag should be followed by the path to a sample grammar file. If the -i option is used, then it should be followed by the location of a file containing input to check against the grammar. If the -o option is used, it should be followed by a filename to which the LL(1) parse table generated from the grammar will be written.
