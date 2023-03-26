SUMMARY = "A small fan controller daemon for PiKVM "
HOMEPAGE = "https://github.com/pikvm/kvmd-fan"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/pikvm/kvmd-fan.git;protocol=https;branch=master"
SRCREV = "b343d97037c08f5ce58729e04ebb0d74da4bfa74"

DEPENDS += ""

S = "${WORKDIR}/git"

#FILES:${PN} += "${bindir}/*"

do_compile() {
    ${MAKE} PREFIX=/usr DESTDIR="${D}"
}

do_install() {
    ${MAKE} PREFIX=/usr DESTDIR="${D}" install
}
