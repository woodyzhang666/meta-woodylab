require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

RM_WORK_EXCLUDE += "${PN}"

# Override values in u-boot-common.inc
SRC_URI = "git://github.com/woodyzhang666/u-boot.git;protocol=https;branch=woody-d1-wip"
SRCREV = "1ec560da7bdaaefcf3471e1c70bccd5e2f41c71e"

DEPENDS += "bc-native dtc-native u-boot-tools-native python3-setuptools-native"

do_compile[depends] += "${@bb.utils.contains('EXTRA_IMAGEDEPENDS', 'opensbi', 'opensbi:do_deploy', '',d)}"
do_compile:prepend:licheerv_dock() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

COMPATIBLE_MACHINE = "(nezha-allwinner-d1|licheerv_dock)"
