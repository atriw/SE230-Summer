import cv2
import time
from threading import Timer


class IpCamera:
    def __init__(self, camera, interval, duration, sectionId, callback):
        self.camera = camera
        self.interval = interval
        self.duration = duration
        self.sectionId = sectionId
        self.callback = callback
        self.cap = cv2.VideoCapture()
        self.frame = None
        self.isDone = False
        self.elapse = 0

    def connect(self):
        self.cap = cv2.VideoCapture(self.camera)
        return self.cap.isOpened()

    def isOpened(self):
        return self.cap.isOpened()

    def record(self):
        success, self.frame = self.cap.read()
        if not success:
            return
        while success and not self.isDone:
            success, self.frame = self.cap.read()

    def saveAs(self, filename, frame):
        cv2.imwrite(filename, frame)

    def run(self):
        timestamp = time.time()
        now = time.strftime("%Y%m%d%H%M%S", time.localtime())
        filename = str(self.sectionId) + now + '.png'
        self.saveAs(filename, self.frame)
        self.callback(filename, timestamp)
        self.elapse += self.interval
        if (self.isOpened and self.elapse < self.duration):
            Timer(self.interval, self.run).start()
        

    def done(self):
        self.isDone = True

    def schedule(self):
        Timer(self.duration, self.done).start()
        ret, self.frame = self.cap.read()
        Timer(0, self.record).start()
        Timer(0, self.run).start()

    def __del__(self):
        self.cap.release()

