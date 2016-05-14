#!/usr/bin/env python
import socket
import atexit
import os
import pyautogui
s=socket.socket()

def key(s):
	if 'enter' in s:
		pyautogui.press('enter')
	elif 'back' in s:
		pyautogui.press('back')
	else:
		pyautogui.typewrite(s)
	




def Main():
	global s
	host='192.168.43.88'
	port=5521
	
	s.bind((host,port))
	
	s.listen(1)
	c , addr =s.accept()
	print "client ip "+str(c)
	while True:
		data=c.recv(1024)
		if not data:
			break
		print str(data)
		key(str(data)[2:])

@atexit.register
def goodbye():
	global s
	print "You are now leaving the Python sector."
	s.close()
if __name__ == '__main__':
	Main()

