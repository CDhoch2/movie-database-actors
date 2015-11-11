#!/usr/bin/env bash
export ACTORS_VERSION="$(git log | head -1 | sed s/'commit '//)"
