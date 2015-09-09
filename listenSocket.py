#!/usr/bin/python

# Simple socket server using threads
 
import socket
import sys
 
HOST = ''   # Symbolic name, meaning all available interfaces
PORT = 5683 # Arbitrary non-privileged port
 
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print 'Socket created'
 
#Bind socket to local host and port
try:
    s.bind((HOST, PORT))
except socket.error as msg:
    print 'Bind failed. Error Code : ' + str(msg[0]) + ' Message ' + msg[1]
    sys.exit()
     
print 'Socket bind complete'
 
#Start listening on socket
s.listen(10)
print 'Socket now listening'
 
#now keep talking with the client
while 1:
    #wait to accept a connection - blocking call
    conn, addr = s.accept()
    print 'Connected with ' + addr[0] + ':' + str(addr[1])

    #conn.send('listenSocket listens\n');
    while True:         
        #Receiving from client
        data = conn.recv(1024)
        #reply = 'OK...' + data
        print "received: <{0}>.".format(data)
        if not data: 
            break
     
        #conn.sendall(reply)
     
    print 'EOD'
    conn.send('EOD\n');
    #came out of loop
    conn.close()
     
s.close()

