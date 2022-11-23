import socket

HOST = "127.0.0.1"
PORT = 35135


data = None
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    data = ""
    while data != "/stop":
        toSend = input()
        s.send((toSend+"\n\r").encode())
        recieved = s.recv(1024)
        print(recieved.decode())

