SUMMARY = "µStreamer - Lightweight and fast MJPEG-HTTP streamer"
DESCRIPTION = "µStreamer - Lightweight and fast MJPEG-HTTP streamer"
HOMEPAGE = "https://github.com/pikvm/ustreamer"
SECTION = "multimedia"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/pikvm/ustreamer.git;protocol=https;branch=master"
SRCREV = "28c85991672b43b5a0e1c17329c000308293f5ba"

DEPENDS += "libevent libjpeg-turbo libbsd python3 python3-setuptools-native"

S = "${WORKDIR}/git"

inherit python3native python3-dir

RDEPENDS:${PN}:append:class-target = " ${PYTHON_PN}-core"

FILES:${PN} += "${libdir}/* ${libdir}/${PYTHON_DIR}/*"

FILES:${PN}-staticdev += "\
  ${PYTHON_SITEPACKAGES_DIR}/*.a \
"
FILES:${PN}-dev += "\
  ${datadir}/pkgconfig \
  ${libdir}/pkgconfig \
  ${PYTHON_SITEPACKAGES_DIR}/*.la \
"

do_compile() {
    export PY=${PYTHON}
    ${MAKE} WITH_PYTHON=1 PREFIX=/usr DESTDIR="${D}"
}

do_install() {
    export PY=${PYTHON}
    ${MAKE} WITH_PYTHON=1 PREFIX=/usr DESTDIR="${D}" install
}
