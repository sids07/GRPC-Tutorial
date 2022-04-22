#!/usr/bin/env bash
# Author: Ashok Kumar Pant

PROTO_REPO=/home/dell/Desktop/treeleaf/anydone/anydone_java/protos_learn/src/main/protos
GEN_REPO=/home/dell/Desktop/treeleaf/anydone/anydone_java/protos_learn/src/main/pb

PROTO_EXE=/home/dell/Desktop/treeleaf/anydone/anydone_java/protoc-gen-grpc-java-1.28.1-linux-x86_64.exe

echo "Proto repo: "${PROTO_REPO}
echo "Gen repo: "${GEN_REPO}
echo "Generating proto..."
# cd ${PROTO_REPO} || exit
mkdir -p build
rm -rf ./build/*
protoc --proto_path=${PROTO_REPO} --java_out=${PROTO_REPO}/build/*.proto

echo "Generating gRPCs..."
protoc --plugin=protoc-gen-grpc-java=${PROTO_EXE} --grpc-java_out=${GEN_REPO} --proto_path=/home/dell/Desktop/treeleaf/anydone/anydone_java/protos_learn/src/main/protos/*.proto
cd ..
echo "Installing..."
cp -r ${PROTO_REPO}/build/* ${GEN_REPO}/src/main/java/
cd ${GEN_REPO} || exit
./gradlew clean build install
cd ..