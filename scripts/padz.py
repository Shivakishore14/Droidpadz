#!/usr/bin/env python
import socket
import atexit
import os
import sys
import pyautogui
import thread
import Xlib.threaded



sm=socket.socket()
sm.setsockopt(socket.SOL_SOCKET , socket.SO_REUSEADDR ,1)
sk=socket.socket()
sk.setsockopt(socket.SOL_SOCKET , socket.SO_REUSEADDR ,1)
sj=socket.socket()
sj.setsockopt(socket.SOL_SOCKET , socket.SO_REUSEADDR ,1)
sb=socket.socket()
sb.setsockopt(socket.SOL_SOCKET , socket.SO_REUSEADDR ,1)
x=500
y=500
dirs =["up","down","right","left","down right","down left","up right","up left","none"]
k=-1
########mouse##############
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
def mousestart1(c):
	while True:
		data=c.recv(1024)
		if not data:
			break
		mouse(str(data)[2:])
	
def mousestart(sm):
	#global sm
	print "mse"
	while True:
		c , addr =sm.accept()	
		print str(c) 
		thread.start_new_thread(mousestart1,(c,))
			
#########mouse end#############

#########key###################
def key(s):
	if 'enter' in s:
		pyautogui.press('enter')
	elif 'back' in s:
		pyautogui.press('back')
	else:
		pyautogui.typewrite(s)
def keystart1(c):
	while True:
		data=c.recv(1024)
		if not data:
			break
		print str(data)
		key(str(data)[2:])
	
def keystart(sk):
	print "keystart"
	while True:
		c , addr =sk.accept()
		thread.start_new_thread(keystart1,(c,))
#########key end###############
#########joy###################
def joy(s):
	global k
	a=k
	try:
		a=int(s)
	except ValueError:
		pass
		return	
	if (k != a):
		#do key up
		if (k!=-1):
			l=dirs[k].find(' ')
			if l > 1:
				pyautogui.keyUp(dirs[k][:l])
				pyautogui.keyUp(dirs[k][l+1:])
			else:
				pyautogui.keyUp(dirs[k])	
		l=dirs[a].find(' ')
		#do key down
		if l > 1:
			pyautogui.keyDown(dirs[a][:l])
			pyautogui.keyDown(dirs[a][l+1:])
		else:
			pyautogui.keyDown(dirs[a])
		k=a
def joystart1(c):
	while True:
		data=c.recv(1024)
		if not data:
			break
		print str(data)
		joy(str(data)[2:])

def joystart(sk):
	
	while True:
		c , addr =sk.accept()
		print "client ip "+str(c)
		thread.start_new_thread(joystart1,(c,))
		

#############joy end###########
#############joybutton#########
def joybutton(s):
	print s
	if len(s) == 1:
		pyautogui.press(s)
def joybuttonstart1(c):
	while True:
		data=c.recv(1024)
		if not data:
			break
		joybutton(str(data)[2:])	
def joybuttonstart(sb):
	while True:
		c , addr =sb.accept()
		print "client ip "+str(c)
		thread.start_new_thread(joybuttonstart1,(c,))
	
#############joybutton end#####

def Main():
	global sm
	host='192.168.43.88'
	portm=5520
	portk=5521
	portj=5522
	portb=5523
	
	sm.bind((host,portm))
	sk.bind((host,portk))
	sj.bind((host,portj))
	sb.bind((host,portb))
	
	sm.listen(1)
	sk.listen(1)
	sj.listen(1)
	sb.listen(1)
	
	print "SERVER STARTED"
	#mouse(sm)
	thread.start_new_thread(mousestart,(sm,))
	thread.start_new_thread(keystart,(sk,))
	thread.start_new_thread(joystart,(sj,))
	thread.start_new_thread(joybuttonstart,(sb,))
		
	'''t =Thread(target= mouse,args=(sm,))
	t.start()
	e =Thread(target= ext,args=(1,))
	e.start()'''
	while True:
		pass
	
	
@atexit.register
def goodbye():
	global sm,sk,sj,sb,k
	print "You are now leaving the Python sector."
	sm.close()
	sk.close()
	sj.close()
	sb.close()
	if k != -1:
		l=dirs[k].find(' ')
		if l > 1:
			pyautogui.keyUp(dirs[k][:l])
			pyautogui.keyUp(dirs[k][l+1:])
		else:
			pyautogui.keyUp(dirs[k])

if __name__ == '__main__':
	Main()

	

