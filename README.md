# ChatRoom_MultipleClients
Here's how to run the application in IntelliJ for both single and multiple client cases:
For single-client case-
1. Open the client, client handler, and server class on IntelliJ
2. Run the server class (Note that you don't need to run the client handler class as it has no 'main' method)
3. Once you are sure that the server is running, then, you can run the client class
4. A welcome message is printed on the console and you're asked for your username to join the chat
5. Holla! You're all set up to send messages to the server

   For multiple-client case-
   1. Open the client, client handler, and server class on IntelliJ
   2. Run the server class (Note that you don't need to run the client handler class as it has no 'main' method)
   3. Once you are sure that the server is running, then, click on the drop-down button on the client class while it is active.
   4. You'll find an option that says "edit configurations", go right ahead and click on this.
   5. This opens up a dialog box with a "modify options" link on the top-righthand corner, click on this is check "Allow multiple instances"
   6. You can determine the number of clients by going through steps 3-5 based on this number. That is, you go through the process 2x if you're testing for 2 clients and on and on.
