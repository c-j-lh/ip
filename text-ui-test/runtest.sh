#!/usr/bin/env bash
set -e

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]; then
    mkdir ../bin
fi

# delete output from previous run
rm -f data/duke.txt ACTUAL.TXT ACTUAL-SETUP.TXT EXPECTED-UNIX.TXT

# compile all sources
if ! javac -cp ../src/main/java -Xlint:none -d ../bin $(find ../src/main/java -name "*.java"); then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

#./gradlew -q clean classes 

# -------- Run 1: setup (creates data/duke.txt) ----------
#java -classpath ../bin Duke < input_setup.txt > ACTUAL-SETUP.TXT
java -classpath ../build/classes/java/main duke.Duke < input_setup.txt > actual_setup.txt

# -------- Run 2: assertion (verifies load + ops) ----------
java -classpath ../build/classes/java/main duke.Duke < input.txt > actual.txt
#java -classpath ../bin Duke < input.txt > actual.txt

# convert to UNIX format (no-op if tool missing)
cp expected.txt expected-UNIX.txt
dos2unix actual.txt expected-UNIX.txt >/dev/null 2>&1 || true

# compare
diff actual.txt expected-UNIX.txt && echo "Test result: PASSED"
