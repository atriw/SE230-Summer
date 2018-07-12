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
        self.frame = None
        self.isDone = False

    def connect(self):
        self.cap = cv2.VideoCapture(self.ip)
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
        filename = now + '.jpg'
        self.saveAs(filename, self.frame)
        self.callback(filename, timestamp)

    def done(self):
        self.isDone = True

    def schedule(self):
        Timer(self.duration, self.done).start()
        ret, self.frame = self.cap.read()
        Timer(0, self.record).start()
        for i in range(0, self.duration, self.interval):
            Timer(i, self.run).start()

    def __del__(self):
        self.cap.release()

