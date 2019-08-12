import cv2
import time
from threading import Timer


class IpCamera:
    def __init__(self, camera, interval, duration, sectionId, callback, errorHandler):
        self.camera = camera
        self.interval = interval
        self.duration = duration
        self.sectionId = sectionId
        self.callback = callback
        self.errorHandler = errorHandler
        self.cap = cv2.VideoCapture()
        self.frame = None
        self.error = False
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
            self.error = True
            self.errorHandler("Failed to connect camera " + self.camera)
            return
        while success and not self.isDone:
            success, self.frame = self.cap.read()
        self.error = not self.isDone
        self.errorHandler("Failed to connect camera " + self.camera)

    def saveAs(self, filename, frame):
        cv2.imwrite(filename, frame)

    def run(self):
        if not self.error:
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
        ret, self.frame = self.cap.read()
        if not ret:
            self.errorHandler("Failed to connect camera " + self.camera)
            self.error = True
            return
        Timer(self.duration, self.done).start()
        Timer(0, self.record).start()
        Timer(0, self.run).start()

    def __del__(self):
        self.cap.release()

