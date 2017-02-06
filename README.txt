
Put any comments for the markers in this file. If you don't have any comments you can delete the file from your repo.

We created a Command class to represent user inputs, and map them to ftp commands.
But we are not sure whether it's necessary to create a new instance for each user input, which seems inefficient from the
memory usage perspective. Anyways, now we only have one command instance from start to end. It works fine, but did have caused
some problem when shared among two or more user inputs.

