import socket
import json
import time

class Myscoket():
    def __init__(self,ip,port):
        self.desIP=ip
        self.desPort=port

    def socket_init(self):
        self.s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        self.s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.s.connect((self.desIP, int(self.desPort)))

    def recive_Msg(self):
        return self.s.recv(5000*1024)

    def send_Msg(self,msg):
        self.s.send(bytes(msg, encoding="UTF-8"))

def JsonSerialize(data):
    Jsondata=json.dumps(data)
    return Jsondata

def JsonDeserialize(Jsondata):
    data=json.loads(Jsondata)
    return data

# data = {'ID':'1001','bool':False}
# Jsondata = JsonSerialize(data)

data = "".join(["[msgMethod:1000, msgLength:359, msgPack:<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
            "<MSGREQUEST>", 
                "<msgHeader>", 
                   "<sysNo>HX</sysNo>", 
                    "<tranCode>1000</tranCode>",
                    "<tranDate>20170929</tranDate>", 
                    "<tranTime>132030</tranTime>", 
                "</msgHeader>",
                "<reqBody>",
                    "<seqNo>HX2017092900000001</seqNo>", 
                    "<certificateType>11</certificateType>", 
                    "<certificateNumber>0653101197211020819</certificateNumber>", 
                    "<accountCode></accountCode>",
                    "</reqBody>",
            "</MSGREQUEST>\n",
            "]"])



while True:
    m = Myscoket("127.0.0.1",8888)
    m.socket_init()
    m.send_Msg(data)
    time.sleep(5)
#m.send_Msg('\n')