# Copyright (C) 2022 Woody Zhang <woodyzhang666@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "A portable foreign function interface library"
HOMEPAGE = "http://sourceware.org/libffi/"
DESCRIPTION = "The `libffi' library provides a portable, high level programming interface to various calling \
conventions.  This allows a programmer to call any function specified by a call interface description at run \
time. FFI stands for Foreign Function Interface.  A foreign function interface is the popular name for the \
interface that allows code written in one language to call code written in another language.  The `libffi' \
library really only provides the lowest, machine dependent layer of a fully featured foreign function interface.  \
A layer must exist above `libffi' that handles type conversions for values passed between the two languages."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=32c0d09a0641daf4903e5d61cc8f23a8"

SRC_URI = "git://github.com/libffi/libffi;protocol=https;branch=master \
           "

SRCREV = "c50c16d0bcb58952840184aa83e62c6d912bf779"
PV = "3.4.3"

S = "${WORKDIR}/git"

EXTRA_OECONF += "--disable-builddir --disable-exec-static-tramp"
EXTRA_OECONF:class-native += "--with-gcc-arch=generic"
EXTRA_OEMAKE:class-target = "LIBTOOLFLAGS='--tag=CC'"
inherit autotools texinfo multilib_header github-releases

do_install:append() {
	oe_multilib_header ffi.h ffitarget.h
}

FILES:${PN}-dev += "${libdir}/libffi-${PV}"

# Doesn't compile in MIPS16e mode due to use of hand-written
# assembly
MIPS_INSTRUCTION_SET = "mips"

BBCLASSEXTEND = "native nativesdk"
