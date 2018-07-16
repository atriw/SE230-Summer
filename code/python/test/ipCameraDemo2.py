import ipCamera


def onTaken(filename):
    print filename


aIpCamera = ipCamera.IpCamera('http://admin:admin@192.168.1.59:8081/', 2, 20, onTaken)
print 'start'
aIpCamera.connect()
print 'connected'
aIpCamera.schedule()
print 'scheduled'

print 'done'

