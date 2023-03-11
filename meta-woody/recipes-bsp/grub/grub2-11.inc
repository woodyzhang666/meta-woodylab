SUMMARY = "GRUB2 is the next-generation GRand Unified Bootloader"

DESCRIPTION = "GRUB2 is the next generaion of a GPLed bootloader \
intended to unify bootloading across x86 operating systems. In \
addition to loading the Linux kernel, it implements the Multiboot \
standard, which allows for flexible loading of multiple boot images."

HOMEPAGE = "http://www.gnu.org/software/grub/"
SECTION = "bootloaders"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

CVE_PRODUCT = "grub2"

SRC_URI = "file:///home/woody/workspace/loongson/grub-2.11.tar.xz"

SRC_URI[sha256sum] = "14a22dfea500f73f826fb733d561aedeacedd90a41972572200563380166fc64"

# Applies only to RHEL
#CVE_CHECK_IGNORE += "CVE-2019-14865"
# Applies only to SUSE
#CVE_CHECK_IGNORE += "CVE-2021-46705"

DEPENDS = "flex-native bison-native gettext-native"

GRUB_COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*|riscv.*|loongarch.*)-(linux.*|freebsd.*)'
COMPATIBLE_HOST = "${GRUB_COMPATIBLE_HOST}"
# Grub doesn't support hard float toolchain and won't be able to forcefully
# disable it on some of the target CPUs. See 'configure.ac' for
# supported/unsupported CPUs in hardfp.
COMPATIBLE_HOST:armv7a = "${@'null' if bb.utils.contains('TUNE_CCARGS_MFLOAT', 'hard', True, False, d) else d.getVar('GRUB_COMPATIBLE_HOST')}"
COMPATIBLE_HOST:armv7ve = "${@'null' if bb.utils.contains('TUNE_CCARGS_MFLOAT', 'hard', True, False, d) else d.getVar('GRUB_COMPATIBLE_HOST')}"

# configure.ac has code to set this automagically from the target tuple
# but the OE freeform one (core2-foo-bar-linux) don't work with that.

GRUBPLATFORM:arm = "efi"
GRUBPLATFORM:aarch64 = "efi"
GRUBPLATFORM:riscv32 = "efi"
GRUBPLATFORM:riscv64 = "efi"
GRUBPLATFORM:loongarch32 = "efi"
GRUBPLATFORM:loongarch64 = "efi"
GRUBPLATFORM ??= "pc"

inherit autotools gettext texinfo pkgconfig

CFLAGS:remove = "-O2"

EXTRA_OECONF = "--with-platform=${GRUBPLATFORM} \
                --disable-grub-mkfont \
                --program-prefix="" \
                --enable-liblzma=no \
                --enable-libzfs=no \
                --enable-largefile \
                --disable-werror \
"

PACKAGECONFIG ??= ""
PACKAGECONFIG[grub-mount] = "--enable-grub-mount,--disable-grub-mount,fuse"
PACKAGECONFIG[device-mapper] = "--enable-device-mapper,--disable-device-mapper,libdevmapper"

# grub2 creates its own set of -nostdinc / -isystem / -ffreestanding CFLAGS and
# OE's default BUILD_CFLAGS (assigned to CFLAGS for native builds) etc, conflict
# with that. Note that since BUILD_CFLAGS etc are not used by grub2 target
# builds, it's safe to clear them unconditionally for both target and native.
BUILD_CPPFLAGS = ""
BUILD_CFLAGS = ""
BUILD_CXXFLAGS = ""
BUILD_LDFLAGS = ""

export PYTHON = "python3"

do_configure:prepend() {
	cd ${S}
	FROM_BOOTSTRAP=1 ${S}/autogen.sh
	cd ${B}
}
