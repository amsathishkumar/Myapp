import os
import unittest
from appium import webdriver
from time import sleep

"http://qxf2.com/blog/appium-mobile-automation/"
class ChessAndroidTests(unittest.TestCase):
    "Class to run tests against the Chess Free app"
    def setUp(self):
        "Setup for the test"
        ds = {}
        ds['platformName'] = 'Android'
        ds['platformVersion'] = '4.4'
        ds['deviceName'] = '0a4dd0b0'
        ds['app'] = os.path.abspath(os.path.join(os.path.dirname(__file__),'test.apk'))
        ds['appPackage'] = 'com.example.test'
        ds['appActivity'] = '.MainActivity'
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', ds)

 
    def tearDown(self):
        "Tear down the test"
        print "quit"
        self.driver.quit()
 
    def test_start(self):
        "Test the Single Player mode launches correctly"
        print "player"
        self.driver.launch_app();
        element = self.driver.find_element_by_xpath("//android.widget.TextView[@text='Hello world!']")
        print element.get_attribute("text")
        
 
#---START OF SCRIPT
if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(ChessAndroidTests)
    unittest.TextTestRunner(verbosity=2).run(suite)