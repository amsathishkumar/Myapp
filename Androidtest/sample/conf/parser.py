'''
Created on Feb 9, 2016

@author: smuniapp
'''
from configparser import ConfigParser

    
def test():
    print "sathish"
    c=ConfigParser();
    c.optionxform = str
    c.read('appium.ini')
    configurations = {}
    for section in c.sections():
        fixed_name = section
        if section == "CLIENT_VARS": fixed_name = "device"
        if section == "APPIUM_VARS": fixed_name = "appium"
        if section == "HE_VARS": fixed_name = "he"
        configurations[fixed_name] = {}
        for key in c.options(section):
            configurations[fixed_name][key] = c.get(section, key)
    for key in configurations:
        print key, 'corresponds to', configurations[key]
    #"appium", "platformName"
    if "appium" in configurations:
            config_group = configurations["appium"]
            if "platformName" in config_group:
             print config_group["platformName"]
    
if __name__ == '__main__':
    test();  