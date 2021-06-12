tail.exe 复制到 c:\windows\system32\ 然后命令行执行 tail -f xxx.log即可。

jar -xvf log-viewer-2.2.5.RELEASE.jar BOOT-INF/classes/application.yml
  
  
  

jar -xvf log-viewer-2.2.5.RELEASE.jar BOOT-INF/classes/logback.xml
  

jar -uvf log-viewer-2.2.5.RELEASE.jar BOOT-INF/classes/application.yml


jar -uvf log-viewer-2.2.5.RELEASE.jar BOOT-INF/classes/logback.xml



nohup java -jar -Dreamrk=log-viewer  log-viewer-2.2.5.RELEASE.jar >/dev/null 2>&1 &







