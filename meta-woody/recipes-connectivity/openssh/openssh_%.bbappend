FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://ssh.service"

do_install:append () {
	install -c -m 0644 ${WORKDIR}/ssh.service ${D}${systemd_system_unitdir}
}
