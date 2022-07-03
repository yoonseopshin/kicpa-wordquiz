#!/bin/bash
mkdir -p .secrets
tar cvf .secrets/secrets.tar app/google-services.json secrets.properties app/keystore/cpawordproblem.jks
gpg -c .secrets/secrets.tar