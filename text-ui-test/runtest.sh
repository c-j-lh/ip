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

# -------- Run 1: setup (creates data/duke.txt) ----------
java -classpath ../bin Duke < input_setup.txt > ACTUAL-SETUP.TXT

# -------- Run 2: assertion (verifies load + ops) ----------
java -classpath ../bin Duke < input.txt > ACTUAL.TXT

# convert to UNIX format (no-op if tool missing)
cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT >/dev/null 2>&1 || true

# compare
diff ACTUAL.TXT EXPECTED-UNIX.TXT && echo "Test result: PASSED"
