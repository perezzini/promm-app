.PHONY:clean all def_target
SHELL=/bin/bash

GRADLE_FLAGS :=

def_target: all

QSAY := @echo

ifeq (${V},1)
	Q :=
	GRADLE_FLAGS += --info
else
	Q := @
endif

all:
	$(QSAY) " GRADLE"
	$(Q)./gradlewbin/gradlew $(GRADLE_FLAGS) build
	$(Q)cp ./Pr/build/outputs/apk/Pr-debug.apk Pr.apk

clean:
	$(QSAY) " CLEAN"
	$(Q)./gradlewbin/gradlew clean
