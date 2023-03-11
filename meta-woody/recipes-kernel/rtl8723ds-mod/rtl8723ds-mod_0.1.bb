SUMMARY = "Realtek RTL8723DS SDIO Wifi Bluetooth Adapter"
DESCRIPTION = "${SUMMARY}"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=1f6f1c0be32491a0c8d2915607a28f36"

inherit module siteinfo

SRC_URI = "git://github.com/lwfinger/rtl8723ds.git;protocol=https;branch=master"
SRCREV = "ec85dc6b9f72bfe413bff464ed01a272e29c8dbe"

S = "${WORKDIR}/git"

# Undefine the hardcoded CONFIG_LITTLE_ENDIAN
RTL8723DS_USER_EXTRA_CLAGS = "-UCONFIG_LITTLE_ENDIAN"
# Set endianness
RTL8723DS_USER_EXTRA_CLAGS += "${@oe.utils.conditional('SITEINFO_ENDIANNESS', 'le', '-DCONFIG_LITTLE_ENDIAN', '-DCONFIG_BIG_ENDIAN', d)}"

EXTRA_OEMAKE += " \
    CONFIG_RTL8723DS=m \
    KVER=${KERNEL_VERSION} \
    KSRC=${STAGING_KERNEL_DIR} \
    "

#    USER_EXTRA_CFLAGS=\"${RTL8723DS_USER_EXTRA_CLAGS}\" 

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-rtl8723ds"
