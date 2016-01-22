#setup
1. Install java 8 as follows:
	$ sudo add-apt-repository ppa:webupd8team/java
	$ sudo apt-get update
	$ sudo apt-get install oracle-java8-installer

2. Download eclipse from here "http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/marsr"

3. Add testng plug in eclipse 
   goto help>install new software
   enter url " http://beust.com/eclipse"
4. Install eclipse maven plugin m2e
	goto help>install new software
    enter url "http://download.eclipse.org/technology/m2e/releases"
5. Clone repository
	$git clone ssh://vcs-user@phabricator.plancess.com/diffusion/QA/quality-assurance.git
6. Import maven project in eclipse
7. Install xvfb and firefox
   $ sudo apt-get install Xvfb firefox


#development

1. Update maven project
   right click on project > maven> update project
2. Add Page class in package "com.plancess.selenium.pages"
3. Add TestNG tests in  package "com.plancess.selenium.tests"

#Running tests

1. Start Selenium grid hub 
 	$java -jar selenium-server-standalone-2.49.0.jar -role hub -timeout 300000&
2. Register nodes to hub 
	$xvfb-run -s "-screen 0 1920x1020x24" java -jar selenium-server-standalone-2.49.0.jar -role node -nodeConfig node.json -Dwebdriver.ie.driver=.\IEDriverServer.exe -Dwebdriver.chrome.driver=./chromedriver & 
3. Run project as maven test
	$mvn clean install



