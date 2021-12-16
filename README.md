# LRSAgent

LRSAgent is a part of LRSBackup Solution, responsible to working in pair with LRSManager and verifing all files inside local disk that needs be uploaded to Public Cloud (just directories defined as 'Protected'), as Amazon AWS, Azure or Oracle Cloud. For it, the LRSAgent works like an 'background application' find files in all Protected Defined Directories by user.

## Getting Started (for developers)
All code about LRSAgent are available in **/src** directory and all libraries dependency are available in pom.xml file.
You can find a main class to run this program in **/src/br/com/lrsbackup/LRSAgent/LRSAgent.java**

Important Notice: This project is under development, almost done. So, there are some bugs yet and some values/parameters "hardcoded".  I'm working over it, so please, be patience!
All codes were write using Eclipse IDE using a "Maven Project"


## Getting Started (for end-users)
To run LRSAgent, look at the binary file (executable file) about LRSAgent. 
After download it, plese run the following commands in your command line application (Terminal to MacOs/Linux users or CMD to Windows Users)
```
java -jar LRSAgent.jar
```
We strongly recommend that you use a Docker container to run LRSManager. In this case, all docker containers related to LRSBackup environment needs have the same file mount point as a "Protected Files Source"
LRSAgent is not an API, so there's not any port being listening.

## Authors
- [Cesar Bianchi](https://www.linkedin.com/in/cesar-bianchi-9b90571b/), since 2021.

## License
 This software is licensed under [GPL-3.0 License](https://www.gnu.org/licenses/gpl-3.0.pt-br.html)   

## More Info
 Please visit http://www.lrsbackup.com (under construction) or write to me: cesar_bianchi@hotmail.com
 
 
