CHCP 65001
@echo off
echo "author godcheese [godcheese@outlook.com]"
set CURRENT_DIR=%cd%
echo %CURRENT_DIR%
set SCRIPTS_DIR=%~dp0
echo %SCRIPTS_DIR%
cd /d %SCRIPTS_DIR%
cd ..
set PROJECT_DIR=%cd%
rd /q /s maven-repository
cd /d %PROJECT_DIR%
git clone https://github.com/godcheese/maven-repository.git
call mvn clean deploy -Dmaven.test.skip -DaltDeploymentRepository="godcheese-github-maven-repository::default::file:%PROJECT_DIR%/maven-repository/repository"
cd maven-repository
echo "Add file..."
git add -A
git commit -m "deploy release package."
echo "Submit code..."
git push origin master
echo "Submit complete,close..."
cd /d %PROJECT_DIR%
rd /s /q maven-repository
cd /d %CURRENT_DIR%
@echo on