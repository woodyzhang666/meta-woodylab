# Copyright (C) 2022 Woody Zhang <woodyzhang666@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit kernel
require recipes-kernel/linux/linux-yocto.inc


KBRANCH = "woody"
KBRANCH:loongarch64 = "loongson-dev"

SRC_URI = "git://github.com/woodyzhang666/linux.git;protocol=https;nocheckout=1;name=machine;branch=${KBRANCH}"
#SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=git;nocheckout=1;name=machine"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION ?= "6.1"
LINUX_VERSION_EXTENSION:append = "-woody"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"
DEPENDS += "gmp-native libmpc-native"

S = "${WORKDIR}/git"

LOCALVERSION ?= ""
KCONFIG_MODE ?= "alldefconfig"
KMACHINE ?= "${MACHINE}"
KBUILD_DEFCONFIG:loongarch64 = "loongson3_defconfig"
KBUILD_DEFCONFIG:sunxi = "sunxi_defconfig"
KBUILD_DEFCONFIG:qemux86-64 = "x86_64_defconfig"

KERNEL_DANGLING_FEATURES_WARN_ONLY = "1"
KERNEL_VERSION_SANITY_SKIP="1"

SRCREV_machine = "3f41e17788cb9472dfe1a73ab554a2d83f0326c2"
SRCREV_machine:loongarch64 = "c7e4669914823c58bcd0029f2106144d2ef4dbb0"

PV = "${LINUX_VERSION}+git${SRCPV}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

# Override COMPATIBLE_MACHINE to include your machine in a copy of this recipe
# file. Leaving it empty here ensures an early explicit build failure.
#COMPATIBLE_MACHINE = "(^$)"
COMPATIBLE_MACHINE = "(qemuarm|qemuarmv5|qemuarm64|qemux86|qemuppc|qemuppc64|qemumips|qemumips64|qemux86-64|qemuriscv64|qemuriscv32|sun4i|sun5i|sun7i|sun8i|sun50i|timcreate-l5a2)"

INSANE_SKIP:kernel-vmlinux:qemuppc64 = "textrel"
