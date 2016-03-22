'''
Created on Feb 24, 2016

@author: smuniapp
'''
import json
import os
import requests
from time import sleep

NO_CONTENT = 204

def getPSDresponse():
        url = 'http://10.78.220.26:8081/cp/pds/v1/policies/network'
        r = requests.get(url);
        return r.json()
    
def setPSDrequest(datas):
        url = 'http://10.78.220.26:8081/cp/pds/v1/policies/network'
        pdsheaders = {'Content-Type': 'application/json'}
        r=requests.put(url, data=datas,headers=pdsheaders)
        assert r.status_code == NO_CONTENT , "Failed to configure network in PDS"   
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

# def from_string(cls, json_string):
#         """
#         Returns a Response-object or a list with Response-objects
#         """
# 
#         if not json_string:
#             return
# 
#         data = rpcjson.loads(json_string)
# 
#         if isinstance(data, list):
#             retlist = []
#             for response in data:
#                 retlist.append(cls.from_dict(response))
#             return retlist
#         else:
#             return cls.from_dict(data)
        
                              
if __name__ == '__main__':    
#     datas= getPSD("http://192.168.125.116:6660/cp/pds/v1/policies/geoLocation") 
#     encoded_str = json.dumps( datas )
#     print (putPSD("http://192.168.125.116:6660/cp/pds/v1/policies/geoLocation", datas));  
#     print getPSD("http://192.168.125.116:6660/cp/pds/v1/policies/geoLocation")
    #session guard
#     url='http://10.78.220.26:8081/cmdc/content?filter=source~vod&count=255&catalogueId=16897'
#     header = {'x-cisco-vcs-identity': '{"deviceFeatures": ["COMPANION", "ABR"], "sessionId": "12345678", "devId": "0a4dd0b0","cmdcRegion": "16384~16385", "region": "1", "hhId": "main_hub_test_vod_off_net_rest2016_02_29T19_28_00_257000","upId": "main_hub_test_vod_off_net_rest2016_02_29T19_28_00_257000_0", "cmdcDeviceType": "ANDROID", "tenant": "kd"}'}
#     dicreply = requests.get(url, headers=header).json()
#     print  dicreply
#     contents = dicreply['contents']
#     print " contents: "+str(contents)
#     for i in range(0,255):
#         print "Sno:"+str (i)+ " Name: "+contents[i]["id"]
#     bsm='http://10.78.220.26:8081/bsm/OfferDetails'
      asset = {"brId":"sat","provider":"sat"}

      pds_response= getPSDresponse()
      ss='[{"contentType":"'+asset['brId']+'", "serviceProvider": ["'+asset['provider']+'"],"deviceType":"COMPANION","definition": {"AND": {"EQ-1": ["networkType","wifi" ],"EQ-2": ["onSPNet","true" ],"NE-1": ["blacklist","true" ] }}}]'
      
#       print ss
#       print json.dumps(pds_response)
      cmd ='adb -s 33003a90a6e5226d shell input keyevent 61'
      pipe=os.popen(cmd + ' 2>&1', 'r')
      text = pipe.read()
      print text
      sleep(1)
      pipe.close()



    
    
  
    