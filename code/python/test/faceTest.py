from faceClass import Face

def onTaken(face):
    print face.getPicture()
    print face.getData()

face = Face("test.png", onTaken)
face.run()
