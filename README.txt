
Put any comments for the markers in this file. If you don't have any comments you can delete the file from your repo.

We created a Command class to represent user inputs, and map them to ftp commands.
But we are not sure whether it's necessary to create a new instance for each user input, which seems inefficient from the
memory usage perspective. Anyways, now we only have one command instance from start to end. It works fine, but did have caused
some problems when shared among two or more user inputs.

Brief description of our classes:
CSftp: Main class; waiting for user inputs; project classes instantiation; error messages related to user inputs
theSocket: to create or destroy a Java socket; to read from or write to the socket; error messages related to these processes
Command: parse user inputs; convert them to proper ftp commands
fromServer: logics of actual interactions with command or data socket connections
Utils: contains all static fields and methods; deal with tasks beyond the main logic, eg: parse server responses

