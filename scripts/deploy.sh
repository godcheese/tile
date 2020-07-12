#!/usr/bin/env bash

echo "author godcheese [godcheese@outlook.com]"
CURRENT_DIR=$(pwd)
SCRIPTS_DIR=$(cd "$(dirname $0)" || exit; pwd)
cd "${SCRIPTS_DIR}" || exit
cd ..
PROJECT_DIR=$(pwd)
rm -rf maven-repository
git clone https://github.com/godcheese/maven-repository.git
mvn clean deploy -Dmaven.test.skip -DaltDeploymentRepository="godcheese-github-maven-repository::default::file:${PROJECT_DIR}/maven-repository/repository"
cd maven-repository
echo "Add file..."
git add -A
git commit -m "deploy release package."
echo "Submit code..."
git push origin master
echo "Submit complete,close..."
cd "${PROJECT_DIR}"
rm -rf maven-repository
cd "${CURRENT_DIR}"