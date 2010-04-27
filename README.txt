CS3240 Spring 2010
Final Project

James Jones
James King
Corey Mayo

To Compile:
From the project's root directory, type "./compile". This will invoke Apache Maven, which was used as this projects build tool. A file called "project.jar" will be created in the "target/" directory.

If you are running windows, from the command line navigate to the project folder and type: "apache-maven-2.2.1/bin/mvn install".


To Run:
This program requires the Sun Java runtime environment to be installed.

Type "java -jar target/program.jar" from the project's root directory. This will invoke the system's java command. When called without any parameters, the program will print usage then exit.

Usage:
-g (--grammar) FILE     : File containing grammar specification
-i (--input) FILE       : File containing input to check against the supplied
                          grammar.If this is not supplied, then the parse
                          table is written out to the specified filename.
-o (--output) FILE      : When a test input is not supplied, the parse table
                          is written to this file.
-pt (--parseTable) FILE : File containing a parseTable to check input against,
                          cannot be using with grammar file input


The following combinations of parameters are valid:
  -g and either -i or -o
  -pt and -i

