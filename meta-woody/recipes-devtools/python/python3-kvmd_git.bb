SUMMARY = "KVM Daemon for PIKVM"
HOMEPAGE = "https://pikvm.org/"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/woodyzhang666/kvmd;protocol=https;branch=woodylab-hdmiusb-d1"
SRCREV = "728dc5dbd1f99cd6d5c1a6f82a6ffacec847a88e"

inherit setuptools3

S = "${WORKDIR}/git"

RM_WORK_EXCLUDE += "${PN}"

KVMD_PLATFORM ?= "v2-hdmiusb"
KVMD_BOARD ?= "generic"

do_install:append() {
    local KVMD_PLATFORM=${@d.getVar('KVMD_PLATFORM')}
    local KVMD_BOARD=${@d.getVar('KVMD_BOARD')}
#    install -Dm755 -t ${D}${bindir} ${B}/scripts/"kvmd-{bootconfig,gencert,certbot}"
	install -d ${D}${bindir}
	install -m 755 ${B}/scripts/kvmd-bootconfig ${D}${bindir}
	install -m 755 ${B}/scripts/kvmd-gencert ${D}${bindir}
	install -m 755 ${B}/scripts/kvmd-certbot ${D}${bindir}
	
    install -Dm644 -t ${D}${libdir}/systemd/system ${B}/configs/os/services/*
#    install -d ${D}${libdir}/systemd/system
#	install -m 644 ${B}/configs/os/services/kvmd-bootconfig.service ${D}${libdir}/systemd/system
#	install -m 644 ${B}/configs/os/services/kvmd-otg.service ${D}${libdir}/systemd/system
#	install -m 644 ${B}/configs/os/services/kvmd-otgnet.service ${D}${libdir}/systemd/system
#	install -m 644 ${B}/configs/os/services/kvmd.service ${D}${libdir}/systemd/system
#	install -m 644 ${B}/configs/os/services/kvmd-vnc.service ${D}${libdir}/systemd/system

	install -DTm644 ${B}/configs/os/sysusers.conf ${D}${libdir}/sysusers.d/kvmd.conf
	install -DTm644 ${B}/configs/os/tmpfiles.conf ${D}${libdir}/tmpfiles.d/kvmd.conf
#	install -D -m 644 ${B}/configs/os/sysusers.conf ${D}${libdir}/sysusers.d/kvmd.conf
#	install -D -m 644 ${B}/configs/os/tmpfiles.conf ${D}${libdir}/tmpfiles.d/kvmd.conf
	
	install -d -m 755 ${D}${datadir}/kvmd
#	cp -r ${B}/{hid,web,extras,contrib/keymaps} ${D}${datadir}/kvmd
	cp -r ${B}/hid ${D}${datadir}/kvmd
	cp -r ${B}/web ${D}${datadir}/kvmd
	cp -r ${B}/extras ${D}${datadir}/kvmd
	cp -r ${B}/contrib/keymaps ${D}${datadir}/kvmd
	#install -D -m 644  ${D}${datadir}/kvmd/web ${B}/web/**/*
	#install -D -m 644  ${D}${datadir}/kvmd/extras ${B}/extras/*
	#install -D -m 644  ${D}${datadir}/kvmd/contrib/keymaps ${B}/contrib/keymaps/*
	find ${D}${datadir}/kvmd/web -name '*.pug' -exec rm -f '{}' \;
	
	local _cfg_default="${D}${datadir}/kvmd/configs.default"
	install -d -m 755 $_cfg_default
	cp -r ${B}/configs/* $_cfg_default
	
	find ${D}${datadir}/kvmd -name ".gitignore" -delete
	find $_cfg_default -type f -exec chmod 444 '{}' \;
	chmod 400 "$_cfg_default/kvmd"/*passwd
	chmod 400 "$_cfg_default/kvmd"/*.secret
	chmod 750 "$_cfg_default/os/sudoers"
	chmod 400 "$_cfg_default/os/sudoers"/*
	
	install -d -m 755 ${D}${sysconfdir}/kvmd/nginx/ssl
	install -d -m 755 ${D}${sysconfdir}/kvmd/vnc/ssl
	install -m 644 ${D}${datadir}/kvmd/configs.default/nginx/*.conf ${D}${sysconfdir}/kvmd/nginx
	
    install -d -m 755 ${D}${sysconfdir}/kvmd/janus
	install -m 444 ${D}${datadir}/kvmd/configs.default/janus/*.jcfg ${D}${sysconfdir}/kvmd/janus
	
	install -D -m 644 ${D}${datadir}/kvmd/configs.default/kvmd/*.yaml ${D}${sysconfdir}/kvmd
	install -D -m 600 ${D}${datadir}/kvmd/configs.default/kvmd/*passwd ${D}${sysconfdir}/kvmd
	install -D -m 600 ${D}${datadir}/kvmd/configs.default/kvmd/*.secret ${D}${sysconfdir}/kvmd
	install -D -m 644 ${D}${datadir}/kvmd/configs.default/kvmd/web.css ${D}${sysconfdir}/kvmd
	install -d -m 755 ${D}${sysconfdir}/kvmd/override.d
	
	install -d -m 755 ${D}/var/lib/kvmd/msd
	install -d -m 755 ${D}/var/lib/kvmd/pst
	
	# Avoid dhcp problems
	#install -D -T -m 755 configs/os/netctl-dhcp "$pkgdir${sysconfdir}/netctl/hooks/pikvm-dhcp"
	
    # Following are Platforom & Board Specific
	if [[ "$KVMD_PLATFORM" =~ "^.*-hdmiusb$" ]]; then
	    install -m 755 ${B}/scripts/kvmd-udev-hdmiusb-check ${D}${bindir}
	fi
	install -D -T -m 644 ${B}/configs/os/sysctl.conf ${D}${sysconfdir}/sysctl.d/99-kvmd.conf
	install -D -T -m 644 ${B}/configs/os/udev/${KVMD_PLATFORM}-${KVMD_BOARD}.rules ${D}${sysconfdir}/udev/rules.d/99-kvmd.rules
	install -D -T -m 444 ${B}/configs/kvmd/main/${KVMD_PLATFORM}-${KVMD_BOARD}.yaml ${D}${sysconfdir}/kvmd/main.yaml
	
    # kvmd-fan depends on raspi, shit
	if [ -f ${B}/configs/kvmd/fan/$KVMD_PLATFORM.ini ]; then
		install -DTm444 ${B}/configs/kvmd/fan/$KVMD_PLATFORM.ini ${D}${sysconfdir}/kvmd/fan.ini
	fi

	if [ -f ${B}/configs/os/modules-load/$KVMD_PLATFORM.conf ]; then
		install -DTm644 ${B}/configs/os/modules-load/$KVMD_PLATFORM.conf ${D}${sysconfdir}/modules-load.d/kvmd.conf
	fi

	if [ -f ${B}/configs/os/sudoers/$KVMD_PLATFORM ]; then
		install -DTm440 ${B}/configs/os/sudoers/$KVMD_PLATFORM ${D}${sysconfdir}/sudoers.d/99_kvmd
	fi

    # HDMI to MIPI-CSI2, tc358743
	if [[ "$KVMD_PLATFORM" =~ "^.*-hdmi$" ]]; then
		install -DTm444 ${B}/configs/kvmd/edid/$KVMD_PLATFORM.hex ${D}${sysconfdir}/kvmd/tc358743-edid.hex
	fi

	install -D -T -m 755 ${B}/testenv/fakes/vcgencmd ${D}/opt/vc/bin/vcgencmd
}

FILES:${PN} += "${datadir}/kvmd /opt/vc/bin/vcgencmd"

RDEPENDS:${PN} += " \
    bash \
    dnsmasq \
    dos2unix \
    fontconfig \
    freetype \
    iproute2 \
    iptables \
    libgpiod \
    libgpiod-python \
    libpython3 \
    libssl \
    libv4l \
    nginx \
    openssl \
    python3-aiohttp \
    python3-aiofiles \
    python3-certbot \
    python3-dbus \
    python3-dbus-next \
    python3-hidapi \
    python3-mako \
    python3-misc \
    python3-multiprocessing \
    python3-netifaces \
    python3-pam \
    python3-passlib \
    python3-periphery \
    python3-pillow \
    python3-profile \
    python3-psutil \
    python3-pyyaml \
    python3-pyotp \
    python3-pyserial-asyncio \
    python3-setproctitle \
    python3-spidev \
    python3-systemd \
    python3-xlib \
    python3-zstandard \
    tesseract \
    ustreamer \
    zstd \
"
