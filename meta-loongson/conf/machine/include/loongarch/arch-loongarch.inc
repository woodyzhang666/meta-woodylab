# LoongArch Architecture definition

DEFAULTTUNE ?= "loongarch64"

TUNE_ARCH = "${TUNE_ARCH:tune-${DEFAULTTUNE}}"
TUNE_PKGARCH = "${TUNE_PKGARCH:tune-${DEFAULTTUNE}}"
TUNE_CCARGS:append = "${@bb.utils.contains('TUNE_FEATURES', 'loongarch64', ' -mabi=lp64d', ' ', d)}"
TUNE_CCARGS:append = "${@bb.utils.contains('TUNE_FEATURES', 'loongarch32', ' -mabi=ilp32', ' ', d)}"

# Fix: ld: unrecognized option '--hash-style=sysv'
#LINKER_HASH_STYLE:libc-newlib = ""
# Fix: ld: unrecognized option '--hash-style=gnu'
#LINKER_HASH_STYLE:libc-baremetal = ""
