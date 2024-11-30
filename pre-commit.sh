#!/usr/bin/bash

YOUR_CODE_SUCKS=0

test() {
  DIR=$1

  ######### GRADLE TEST HOOK START #######
  echo "Running Gradle clean test for errors"

  if [ -f $DIR/compose.yaml ]; then
    echo "Docker Compose file exists. Starting containers for tests."
    DOCKER_RUNNING=1
    docker compose -f $DIR/compose.yaml up --detach --force-recreate
  fi

  OUTPUT="/tmp/test-$(date +%s)"
  ./gradlew -p $DIR clean test -i > $OUTPUT

  EXIT_CODE=$?
  if [ $EXIT_CODE -ne 0 ]; then
    cat $OUTPUT
    rm $OUTPUT
    echo "***********************************************"
    echo "                 test failed                   "
    echo " Please fix the above issues before committing "
    echo "***********************************************"
    YOUR_CODE_SUCKS=$EXIT_CODE
  fi
  ######## GRADLE TEST HOOK END #######

  if [ "$DOCKER_RUNNING" == "1" ]; then
    echo "Removing containers used for tests."
    docker compose -f $DIR/compose.yaml down --remove-orphans
  fi
}

lint() {
  DIR=$1
  ######## KTLINT-GRADLE HOOK START ########

  CHANGED_FILES="$(git --no-pager diff --name-status --no-color --cached -- $DIR/ | awk '$1 != "D" && $NF ~ /\.kts?$/ { print $NF }')"

  if [ -z "$CHANGED_FILES" ]; then
      echo "No Kotlin staged files."
      exit 0
  fi;

  echo "Running ktlint over these files:"
  echo "$CHANGED_FILES"

  diff=.git/unstaged-ktlint-git-hook.diff
  git diff --color=never > $diff
  if [ -s $diff ]; then
    git apply -R $diff
  fi

  echo $(pwd)
  echo $DIR
  ./gradlew --warning-mode all ktlintCheck -PinternalKtlintGitFilter="$CHANGED_FILES"
  YOUR_CODE_SUCKS=$?

  echo "Completed ktlint run."

  if [ -s $diff ]; then
    git apply --ignore-whitespace $diff
  fi
  rm $diff
  unset diff

  echo "Completed ktlint hook."
  ######## KTLINT-GRADLE HOOK END ########
}

detekt() {
  DIR=$1

  ######## DETEKT-GRADLE HOOK START ########
  echo "Running detekt check..."
  OUTPUT="/tmp/detekt-$(date +%s)"

  ./gradlew -p $DIR detekt > $OUTPUT
  EXIT_CODE=$?

  if [ $EXIT_CODE -ne 0 ]; then
    cat $OUTPUT
    rm $OUTPUT
    echo "***********************************************"
    echo "                 detekt failed                 "
    echo " Please fix the above issues before committing "
    echo "***********************************************"
    YOUR_CODE_SUCKS=$EXIT_CODE
  fi
  rm $OUTPUT
  ######## DETEKT-GRADLE HOOK END ########
}

SERVER_DIR=server

for DIR in "$SERVER_DIR"/*/; do
  DIR_NAME=$(basename "$DIR")
  echo "Processing directory: $DIR_NAME"

  test $DIR

  YOUR_CODE_SUCKS=0

  if [ $YOUR_CODE_SUCKS -ne 0 ]; then
    break
  fi

  lint $DIR

  if [ $YOUR_CODE_SUCKS -ne 0 ]; then
    break
  fi

  detekt $DIR

  if [ $YOUR_CODE_SUCKS -ne 0 ]; then
    break
  fi

  done

exit $YOUR_CODE_SUCKS
