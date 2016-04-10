RipRouting Simulator
====================

This was a project assigned in my 2012 Data Communications and Networking 2
class at Rochester Institute of Technology. It is designed to take in a file
that defines the connections between routers and their various costs. The
program then simulates the RIPv2 routing protocol [RFC 2453](https://tools.ietf.org/html/rfc2453).

The application is written using Java and is implemented as a multi-threaded
application. Each router acts as a separate thread in order to do proper simulaton.

Network File Format
-------------------
Input is via the file "network" in the same folder as the running java files.
The file must contain (in this order):
- A line SUBNETMASK:{IP ADDRESS}
- A line ROUTERS-- to denote where routers begin
- Multiple lines of {IP ADDRESS} to define the list of routers
- A line CONNECTIONS-- to denote where routers are finished and connections begin
- Multiple lines of {IP ADDRESS} {IP ADDRESS} {LINK COST} to define the connection
Note: {IP ADDRESS} corresponds to a valid IP address. {LINK COST} corresponds
to a positive integer.

Output
------
Output may be taken from straight from the command line via output redirection.

Dynamic CONNECTIONS
-------------------
In order to better simulate a true network, there's a percentage chance that a
connection will fail. It is then up to the router to broadcast that the connection
is down. By default connections will fail at a fairly high rate. To turn this off,
specify the command line option ```--no-dynamics``` to turn it off.
