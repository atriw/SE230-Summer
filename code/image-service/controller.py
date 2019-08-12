import faceClass
import ipCamera
import pythonSender

class Controller:
    
    
    def __init__(self,sectionId,camera,interval,duration):
        self.sectionId = sectionId
        self.camera = camera
        self.interval = interval
        self.duration = duration

    def onError(self, errorMsg):
        infoSender = pythonSender.Sender("exchange","infoQueue","info")
        infoSender.send({"success": False, "errorMsg": errorMsg})            

    def onTaken(self, filename, timestamp):
        face = faceClass.Face(filename, timestamp, self.onProcessed)
        face.run()
        
    def onProcessed(self, face):
        imgUrl = face.getPicture()
        info = face.getData()
        # imgSender = pythonSender.Sender("exchange","pictureQueue","picture")
        # imgSender.send({"sectionId":self.sectionId, "url":imgUrl, "timestamp":int(round(face.timestamp*1000))})
        infoSender = pythonSender.Sender("exchange","infoQueue","info")
        infoSender.send({"success": True, "sectionId":self.sectionId, "info":info, "url": imgUrl, "timestamp":int(round(face.timestamp*1000))})
            
    def run(self):
        ipc = ipCamera.IpCamera(self.camera, self.interval, self.duration, self.sectionId, self.onTaken, self.onError)
        ipc.connect()
        ipc.schedule()

if __name__ == "__main__":
    aController = Controller(1, "http://admin:admin@192.168.1.59:8081/",5, 20)
    aController.run()
