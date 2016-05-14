import socket
import atexit
import os
import pyautogui


s=socket.socket()
'''dirs =["up","down","right","left","down right","down left","up right","up left","none"]
k=-1


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
'''

def Main():
	global s
	host='192.168.43.88'
	port=5501
	
	s.bind((host,port))
	
	s.listen(1)
	c , addr =s.accept()
	print "client ip "+str(c)
	while True:
		data=c.recv(1024)
		if not data:
			break
		print str(data)
		#joy(str(data)[2:])
	

@atexit.register
def goodbye():
	global s
	print "You are now leaving the Python sector."
	s.close()	


if __name__ == '__main__':
	Main()

