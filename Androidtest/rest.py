'''
Created on Feb 22, 2016

@author: smuniapp
'''
import requests
from xml.etree import ElementTree

def test():
     print "started"
     h={'x-cisco-vcs-identity': '{"deviceFeatures": ["COMPANION", "ABR"], "sessionId": "12345678", "devId": "0a4dd0b0","cmdcRegion": "16384~16385", "region": "1", "hhId": "main_hub_test_vod_geo_location2016_02_22T20_40_29_938000", "upId": "main_hub_test_vod_geo_location2016_02_22T20_40_29_938000_0", "cmdcDeviceType": "ANDROID", "tenant": "kd"}'}
     r=requests.get( "http://10.78.220.26:8081/cmdc/content?filter=source~vod&count=255&catalogueId=16897",h);
     print r.status_code
     cmdcCount=255
     contentsNum = cmdcCount
     r1=r.json();
     contents = r1['contents']
     print "contents" + str(contents)
     for i in range(0,contentsNum):
         print str(i)+" >>>"+contents[i]["id"]
       
if __name__ == '__main__':
    #test();
    i = {'Name': 'sathish', 'age':1}
    for key in i:
        if type(i[key]) == int:
            print "integer"
        else:
            print key + "  : "+ str(i[key])   
    root = ElementTree.Element("ZoneGroup")
    root.set("xmlns", "urn:com:cisco:videoscape:conductor:loc")
    root.set("name", "zoneGroupName")
    root.set("provider", "zoneGroupProvider")
    classifier = ElementTree.SubElement(root, "Classifier")
    classifier.set("type", "REGEX")
    classifier.text = "zoneGroupProvider" + ".*"
    classifiersub = ElementTree.SubElement(classifier, "Classifiersub")
    classifiersub.set("type", "REGEX")
    classifiersub.text = "zoneGroupProvider" + ".*"
    
    print  ElementTree.tostring(root)