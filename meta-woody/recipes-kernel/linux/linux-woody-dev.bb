# Copyright (C) 2022 Woody Zhang <woodyzhang666@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

KBUILD_BUILD_USER = "woody"
KBUILD_BUILD_HOST = "woodylab"

KBRANCH = "woody"
#KBRANCH:licheerv_dock = "woody-d1-wip"
KMETA = "kernel-meta"
SRC_URI = "git://github.com/woodyzhang666/linux.git;protocol=https;branch=${KBRANCH};name=machine \
            git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=master;destsuffix=${KMETA} \
            file://0001-add-usb-configfs-functions.patch;patchdir=${KMETA} \
            "
SRCREV_machine = "dc03d2b53fb2c2ffc0c1c705448863eb5fa0ab5b"
#SRCREV_machine:licheerv_dock = "7ac91b69d7d7566f8b1fcb6970c625c41db16efb"
SRCREV_meta = "bd77e1f904f681d21732bb3ae77b6591f6ec3d81"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION ?= "6.3"
#LINUX_VERSION:licheerv_dock ?= "6.1"
LINUX_VERSION_EXTENSION:append = "-woody"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"
DEPENDS += "gmp-native libmpc-native"

S = "${WORKDIR}/git"

LOCALVERSION ?= ""
KCONFIG_MODE ?= "--alldefconfig"

KBUILD_DEFCONFIG ?= "defconfig"
KBUILD_DEFCONFIG:loongarch64 = "loongson3_defconfig"
# TODO only for arm32 sunxi SOC.
KBUILD_DEFCONFIG:sunxi = "sunxi_defconfig"
KBUILD_DEFCONFIG:x86-64 = "x86_64_defconfig"
KBUILD_DEFCONFIG:arm64 = "defconfig"
#KBUILD_DEFCONFIG:licheerv_dock = "nezha_defconfig"

KERNEL_VERSION_SANITY_SKIP="1"

PV = "${LINUX_VERSION}+git${SRCPV}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

KERNEL_DANGLING_FEATURES_WARN_ONLY = "1"
KERNEL_FEATURES:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'usbgadget', ' features/usb/usb-gadgets.scc', '', d)}"
KERNEL_FEATURES:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'wifi', ' features/hostapd/hostapd.scc features/mac80211/mac80211.scc', '', d)}"

COMPATIBLE_MACHINE = "(qemuarm|qemuarmv5|qemuarm64|qemux86|qemuppc|qemuppc64|\
    qemumips|qemumips64|qemux86-64|qemuriscv64|qemuriscv32|sun4i|sun5i|sun7i|\
    sun8i|sun50i|timcreate-l5a2|licheerv_dock)"

INSANE_SKIP:kernel-vmlinux:qemuppc64 = "textrel"
