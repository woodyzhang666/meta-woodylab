SUMMARY = "A Cython interface to HIDAPI library"
HOMEPAGE = "https://github.com/trezor/cython-hidapi"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE-gpl3.txt;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "gitsm://github.com/woodyzhang666/cython-hidapi;protocol=https;branch=woody-0.12.0"
SRCREV = "1ee07b5148d7a1c15cba03a97092df0b4e981bb6"

inherit setuptools3

SETUPTOOLS_BUILD_ARGS += "--with-system-hidapi"

S = "${WORKDIR}/git"

DEPENDS += "hidapi python3-pip-native python3-cython-native"

RDEPENDS:${PN} += "hidapi"
