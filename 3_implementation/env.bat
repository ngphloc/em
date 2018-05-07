@echo off

echo "You should set environmental variable JDK_HOME or JAVA_HOME"

set ANT_HOME=.\tools\ant
echo ANT_HOME = %ANT_HOME%

if "%HUDUP_OLD_PATH%" == "" set HUDUP_OLD_PATH=%PATH%

set PATH=.;%JDK_HOME%\bin;%JAVA_HOME%\bin;%ANT_HOME%\bin;%HUDUP_OLD_PATH%

echo PATH=%PATH%

set CLASSPATH=./bin;./lib/hudup-v11-evaluator.jar;./lib/hudup-v11-evaluator.jar

echo CLASSPATH=%CLASSPATH%

set JAVA_CMD=java -cp %CLASSPATH%

@echo on
