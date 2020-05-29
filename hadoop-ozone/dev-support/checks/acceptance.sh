#!/usr/bin/env bash
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "$DIR/../../.." || exit 1

REPORT_DIR=${OUTPUT_DIR:-"$DIR/../../../target/acceptance"}
mkdir -p "$REPORT_DIR"

OZONE_VERSION=$(grep "<ozone.version>" "pom.xml" | sed 's/<[^>]*>//g'|  sed 's/^[ \t]*//')
DIST_DIR="$DIR/../../dist/target/ozone-$OZONE_VERSION"

if [ ! -d "$DIST_DIR" ]; then
    echo "Distribution dir is missing. Doing a full build"
    "$DIR/build.sh" -Pjacoco
fi

export HADOOP_OPTS='-javaagent:/opt/hadoop/share/jacoco/jacoco-agent.jar=destfile=/tmp/jacoco.exec,includes=org.apache.hadoop.ozone.*:org.apache.hadoop.hdds'

cd "$DIST_DIR/compose" || exit 1
./test-all.sh
RES=$?
cp result/* "$REPORT_DIR/"
cp "$REPORT_DIR/log.html" "$REPORT_DIR/summary.html"
set -x
java -jar "$DIST_DIR/share/jacoco/jacoco-cli.jar" merge $(find "$REPORT_DIR" -name "*.jacoco.exec") --destdir "$REPORT_DIR/jacoco-combined.exec"

exit $RES
