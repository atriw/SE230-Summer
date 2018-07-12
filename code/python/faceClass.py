from PIL import Image, ImageDraw
from pprint import pformat
from facepp import API, File
import sys
API_KEY = "UNshC3btUGcZak1k8WpYOPSECdEjHp17"
API_SECRET = "0xpTu84uprFU1Br8ypODKhYcwwfFUs5r"

class Face:
    def __init__(self, imgurl, timestamp, callback):
        self.imgurl = imgurl
        self.timestamp = timestamp
        self.callback = callback
        self.api = API(API_KEY, API_SECRET)
        self.im = Image.open(self.imgurl)
        self.draw = ImageDraw.Draw(self.im)
        self.newimgurl = sys.path[0] + "\\new_" + self.imgurl

    def encode(self, obj):
        if type(obj) is unicode:
            return obj.encode('utf-8')
        if type(obj) is dict:
            return {self.encode(v): self.encode(k) for (v, k) in obj.iteritems()}
        if type(obj) is list:
            return [self.encode(i) for i in obj]
        return obj

    def getPicture(self):
        return self.newimgurl

    def getData(self):
        return self.res
        
    def processPicture(self):
        for i in self.res["faces"]:
            x0 = i["face_rectangle"]["left"]
            y0 = i["face_rectangle"]["top"]
            x1 = i["face_rectangle"]["width"] + x0
            y1 = i["face_rectangle"]["height"] + y0
            for j in range(4):
                self.draw.rectangle([x0-j,y0-j,x1+j,y1+j],outline="yellow")
        self.im.save(self.newimgurl,"JPEG")
   
    def run(self):
        self.res = self.api.detect(image_file = File(self.imgurl),
                              return_attributes = "emotion")
        self.res = self.encode(self.res)
        self.processPicture()
        self.callback(self)
        
