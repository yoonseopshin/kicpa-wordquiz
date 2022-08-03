#!/bin/bash
find . -name "BaselineProfileGenerator*.txt" -type f -maxdepth 1 -exec mv -f {} ./app/src/main/baseline-prof.txt \;