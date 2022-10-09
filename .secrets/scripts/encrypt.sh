#!/bin/bash
mkdir -p .secrets
tar cvf .secrets/secrets.tar app/google-services.json secrets.properties app/keystore/cpawordproblem.jks fastlane
gpg -c .secrets/secrets.tar