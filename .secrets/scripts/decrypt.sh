#!/bin/bash
gpg --quiet --batch --yes --always-trust --decrypt --passphrase="$1" --output .secrets/secrets.tar .secrets/secrets.tar.gpg
tar xvf .secrets/secrets.tar