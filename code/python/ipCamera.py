import cv2
import time
from threading import Timer


class IpCamera:
    def __init__(self, ip, interval, duration, callback):
        self.ip = ip
        self.interval = interval
        self.duration = duration
        self.callback = callback
        self.cap = cv2.VideoCapture()

    def connect(self):
        self.cap = cv2.VideoCapture(self.ip)
        return self.cap.isOpened()

    def isOpened(self):
        return self.cap.isOpened()

    def getFrame(self):
        ret, frame = self.cap.read()
        return frame

    def saveAs(self, filename, frame):
        cv2.imwrite(filename, frame)

    def run(self):
        now = time.strftime("%Y%m%d%H%M%S", time.localtime())
        filename = now + '.jpg'
        self.saveAs(filename, self.getFrame())
        self.callback(filename)

    def destroy(self):
        self.cap.release()

    def schedule(self):
        for i in range(0, self.duration, self.interval):
            Timer(i, self.run).start()
        Timer(self.duration, self.destroy).start()

