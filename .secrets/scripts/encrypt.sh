#!/bin/bash
mkdir -p .secrets
tar cvf .secrets/secrets.tar app/google-services.json secrets.properties
gpg -c .secrets/secrets.tar