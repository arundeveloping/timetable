@ECHO OFF
SET DIRNAME=%~dp0
SET APP_BASE_NAME=%~n0
SET CLASSPATH=%DIRNAME%gradle\wrapper\gradle-wrapper.jar
IF NOT "%JAVA_HOME%"=="" (
  SET JAVACMD=%JAVA_HOME%\bin\java.exe
) ELSE (
  SET JAVACMD=java.exe
)
"%JAVACMD%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*