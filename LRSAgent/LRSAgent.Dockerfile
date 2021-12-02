FROM centos
WORKDIR /home/LRSAgent
COPY . /home/LRSAgent
RUN yum -y update
RUN yum -y remove java
RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel
	   
CMD ["java","-jar","/home/LRSAgent/LRSAgent.jar"]
