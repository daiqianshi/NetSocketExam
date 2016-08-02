运行步骤：
1.进入文件目录Exam2下
2.mvn clean package
3.mvn exec:java -Dexec.mainClass="com.hand.App" -Dexec.args="arg0 arg1 arg2"
4.另开一个cmd窗口， mvn exec:java -Dexec.mainClass="com.hand.Client" -Dexec.args="arg0 arg1 arg2"
5.点击桌面弹出的窗口就可以发送完成