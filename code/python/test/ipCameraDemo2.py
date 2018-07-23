import ipCamera


def onTaken(filename, timestamp):
    print filename
    print timestamp


aIpCamera = ipCamera.IpCamera('http://admin:admin@192.168.1.81:8081/', 2, 20, 1, onTaken)
print 'start'
aIpCamera.connect()
print 'connected'
aIpCamera.schedule()
print 'scheduled'

print 'done'

