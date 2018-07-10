# -*- coding: cp936 -*-
from PIL import Image, ImageDraw
from pprint import pformat
imgurl = 'D://image//test.png' #test image url
im = Image.open(imgurl)
#print(im.format, im.size, im.mode)
draw = ImageDraw.Draw(im)

API_KEY = "UNshC3btUGcZak1k8WpYOPSECdEjHp17"
API_SECRET = "0xpTu84uprFU1Br8ypODKhYcwwfFUs5r"

def print_result(hit, result):
    def encode(obj):
        if type(obj) is unicode:
            return obj.encode('utf-8')
        if type(obj) is dict:
            return {encode(v): encode(k) for (v, k) in obj.iteritems()}
        if type(obj) is list:
            return [encode(i) for i in obj]
        return obj
    print hit
    result = encode(result)
    print '\n'.join("  " + i for i in pformat(result, width=75).split('\n'))

def draw_rectangles(res):
    for i in res["faces"]:
        x0 = i["face_rectangle"]["left"]
        y0 = i["face_rectangle"]["top"]
        x1 = i["face_rectangle"]["width"] + x0
        y1 = i["face_rectangle"]["height"] + y0
        print([x0,y0,x1,y1])
        for j in range(4):
            draw.rectangle([x0-j,y0-j,x1+j,y1+j],outline="yellow")   
    im.show()   

from facepp import API, File
api = API(API_KEY, API_SECRET)
# detect image, set attributes
Face = {}
res = api.detect(image_file = File(imgurl),return_attributes = "emotion")
draw_rectangles(res)
print_result("test", res)

# 创建一个Faceset用来存储FaceToken
# create a Faceset to save FaceToken
# ret = api.faceset.create(outer_id = 'test', face_tokens = res["faces"][0]["face_token"])
# print_result("faceset create", ret)



#分析现有图片
#res = api.face.analyze(face_tokens = res["faces"][0]["face_token"])
#print_result("analyze", res)

# 删除无用的人脸库
# delect faceset because it is no longer needed
# api.faceset.delete(outer_id = 'test', check_empty=0)
