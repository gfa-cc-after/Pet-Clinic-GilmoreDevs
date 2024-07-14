#!/bin/sh

# This script is used to install a git pre-commit hook that will run the

if [ -f .git/hooks/pre-commit ]; then
  echo "Pre-commit hook already exists, good job!"
  echo "If you want to re-install the hook, please remove the .git/hooks/pre-commit file first"
  exit 1
fi

# Copy the pre-commit script to the .git/hooks directory
cp ./utils/pre-commit .git/hooks/pre-commit
# Make the script executable
chmod +x .git/hooks/pre-commit

echo "Pre-commit hook installed, you should be good to go"

# Currently it is for backend, but we will add frontend as well.