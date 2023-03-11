require conf/machine/include/loongarch/arch-loongarch.inc

TUNEVALID[loongarch64] = "Enable 64-bit LoongArch optimizations"
TUNEVALID[loongarch32] = "Enable 32-bit LoongArch optimizations"

AVAILTUNES += "loongarch64 loongarch32"

# Default
TUNE_FEATURES:tune-loongarch64= "loongarch64"
TUNE_ARCH:tune-loongarch64= "loongarch64"
TUNE_PKGARCH:tune-loongarch64= "loongarch64"
PACKAGE_EXTRA_ARCHS:tune-loongarch64= "loongarch64"

TUNE_FEATURES:tune-loongarch32 = "loongarch32"
TUNE_ARCH:tune-loongarch32 = "loongarch32"
TUNE_PKGARCH:tune-loongarch32 = "loongarch32"
PACKAGE_EXTRA_ARCHS:tune-loongarch32 = "loongarch32"


