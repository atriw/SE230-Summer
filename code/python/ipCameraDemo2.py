import ipCamera


def onTaken(filename):
    print filename


aIpCamera = ipCamera.IpCamera('http://admin:admin@192.168.1.59:8081/', 1, 10, onTaken)
aIpCamera.connect()
aIpCamera.schedule()
print 'test'

