#!/usr/bin/env python
import socket
import atexit
import os
import pyautogui
s=socket.socket()
x=500
y=500
def mouse(s):
	global x,y
	a,b=0,0
	#print s
	try:
		l=s.split('x')
		c=0
		i=l[0]
		while i != '':
			a=int(l[c])
			b=int(l[c+1])
			c=c+2
			i=l[c]
			x=x-a/10
			y=y-b/10
			pyautogui.moveTo(x,y)
	except ValueError:
		print s
		pass
		return
	
	
	
def Main():
	global s
	host='192.168.43.88'
	port=5520
	
	
	s.bind((host,port))
	
	s.listen(1)
	c , addr =s.accept()
	print "client ip "+str(c)
	while True:
		data=c.recv(1024)
		if not data:
			break
		mouse(str(data)[2:])
	

@atexit.register
def goodbye():
	global s
	print "You are now leaving the Python sector."
	s.close()
if __name__ == '__main__':
	Main()

