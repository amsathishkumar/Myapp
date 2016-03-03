'''
Created on Feb 24, 2016

@author: smuniapp
'''
import json

import requests


def getPSD(url):
    r = requests.get(url);
    return r.json()

def putPSD(url, datas):
    headers = {'Content-Type': 'application/json'}
    r=requests.put(url, data=datas,headers=headers)
    return r.status_code

def _parseJSON(self, obj):
    if isinstance(obj, dict):
        newobj = {}
        for key, value in obj.iteritems():
            key = str(key)
            newobj[key] = self._parseJSON(value)
    elif isinstance(obj, list):
        newobj = []
        for value in obj:
            newobj.append(self._parseJSON(value))
    elif isinstance(obj, unicode):
        newobj = str(obj)
    else:
        newobj = obj
    return newobj
                          
if __name__ == '__main__':    
#     datas= getPSD("http://192.168.125.116:6660/cp/pds/v1/policies/geoLocation") 
#     encoded_str = json.dumps( datas )
#     print (putPSD("http://192.168.125.116:6660/cp/pds/v1/policies/geoLocation", datas));  
#     print getPSD("http://192.168.125.116:6660/cp/pds/v1/policies/geoLocation")
    #session guard
    url='http://10.78.220.26:8081/cmdc/content?filter=source~vod&count=255&catalogueId=16897'
    header = {'x-cisco-vcs-identity': '{"deviceFeatures": ["COMPANION", "ABR"], "sessionId": "12345678", "devId": "0a4dd0b0","cmdcRegion": "16384~16385", "region": "1", "hhId": "main_hub_test_vod_off_net_rest2016_02_29T19_28_00_257000","upId": "main_hub_test_vod_off_net_rest2016_02_29T19_28_00_257000_0", "cmdcDeviceType": "ANDROID", "tenant": "kd"}'}
    dicreply = requests.get(url, headers=header).json()
    print  dicreply
    contents = dicreply['contents']
    print " contents: "+str(contents)
    for i in range(0,255):
        print "Sno:"+str (i)+ " Name: "+contents[i]["id"]
    bsm='http://10.78.220.26:8081/bsm/OfferDetails'
    
    
  
    