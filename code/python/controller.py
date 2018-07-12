import faceClass
import ipCamera
import pythonSender

class Controller:
    
    
    def __init__(self,id,ip,interval,duration):
        self.id = id
        self.ip = ip
        self.interval = interval
        self.duration = duration

    def onTaken(self, filename, timestamp):
        face = faceClass.Face(filename, timestamp, self.onProcessed)
        face.run()
        
    def onProcessed(self, face):
        imgUrl = face.getPicture()
        info = face.getData()
        imgSender = pythonSender.Sender("exchange","pictureQueue","picture")
        imgSender.send({"id":self.id, "url":imgUrl, "timestamp":face.timestamp})
        infoSender = pythonSender.Sender("exchange","infoQueue","info")
        infoSender.send({"id":self.id, "info":info})
            
    def run(self):
        ipc = ipCamera.IpCamera(self.ip, self.interval, self.duration, self.onTaken)
        ipc.connect()
        ipc.schedule()

if __name__ == "__main__":
    aController = Controller(1, "http://admin:admin@192.168.1.59:8081/",5, 20)
    aController.run()
