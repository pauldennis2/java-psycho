#Paint With Friends
Welcome to the Paint With Friends AKA "Java Psycho" read-me. This project includes a few related mini-projects, all
based around JavaFX. The featured program is Paint With friends, a JavaFX app that allows two users (potentially on
different machines, connected over a LAN) to draw a drawing together. The users can pass control of the drawing back and
forth, as well as change the color and stroke size. The information for this is passed through simple network sockets.

Users can also save a drawing to file through JSON serialization. When loaded, the program will "animate" the drawing
in the sequence it was originally drawn rather than just appearing all at once.

This was one of my earlier network projects. I learned a lot about working with sockets and sending various kinds of
information through that connection. An interesting part of the design process was figuring out a syntax of how to
communicate this information most efficiently. One efficiency feature to note is that the information about stroke color
and size is NOT communicated at every stroke (this reduces the size of the message we need to send). That info is only
communicated when it changes; it's up to the receiver to keep track of it.

Future goals:
* Add a GUI for the drawing (i.e. have clickable buttons to select color, change stroke size, etc.)
* Within that GUI, have the ability to select any color, or save preferred colors.
* Possible additional paint features; Undo changes, work in layers, etc.

The "Psycho Canvas" (originally the main project) creates random colored circles between the user's mouse and the top
left corner of the screen (looks somewhat "psychedelic" if you have an active imagination).