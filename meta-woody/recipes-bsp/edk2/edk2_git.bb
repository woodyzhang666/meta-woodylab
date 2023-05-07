SUMMARY = "UEFI EDK2 Firmware"
DESCRIPTION = "UEFI EDK2 Firmware for Arm reference platforms"
HOMEPAGE = "https://github.com/tianocore/edk2"
LICENSE = "BSD-2-Clause-Patent"

EDK2_BUILD_RELEASE   ?= "0"
EDK2_PLATFORM        ?= "invalid"
EDK2_PLATFORM_DSC    ?= ""
EDK2_BIN_NAME        ?= ""
# Get EDK2_ARCH from TARGET_ARCH
EDK2_ARCH            ?= ""

EDK2_BUILD_MODE = "${@bb.utils.contains('EDK2_BUILD_RELEASE', '1', 'RELEASE', 'DEBUG', d)}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

SRC_URI = "gitsm://github.com/tianocore/edk2.git;protocol=https;name=edk2;destsuffix=${S}/edk2;nobranch=1 \
    gitsm://github.com/tianocore/edk2-platforms.git;protocol=https;name=edk2-platforms;destsuffix=${S}/edk2-platforms;nobranch=1 \
"
SRCREV_edk2           ?= "56e9828380b7425678a080bd3a08e7c741af67ba"
SRCREV_edk2-platforms ?= "64b06a4d19ad29f3307ef687d9b1c72cd6ad16bb"
SRCREV_FORMAT         = "edk2_edk2-platforms"

#PV = "1.0+git${SRCPV}"

# EDK2
LIC_FILES_CHKSUM = "file://edk2/License.txt;md5=2b415520383f7964e96700ae12b4570a"
# EDK2 Platforms
LIC_FILES_CHKSUM += "file://edk2-platforms/License.txt;md5=2b415520383f7964e96700ae12b4570a"

inherit python3native
inherit deploy

PROVIDES += "virtual/uefi-firmware"

DEPENDS += "nasm-native edk2-native util-linux-native iasl-native"

EDK_TOOLS_DIR="edk2_basetools"

#COMPATIBLE_HOST:class-target='(i.86|x86_64).*'
COMPATIBLE_MACHINE:class-target ?= "(licheerv_dock|qemuriscv64)"

#export PYTHON_COMMAND = "${HOSTTOOLS_DIR}/python3"
export PYTHON_COMMAND="${PYTHON}"
# Set variables as per envsetup

LDFLAGS[unexport] = "1"

# No configure
do_configure[noexec] = "1"

python __anonymous() {
    # If GCC Version is greater than 4 then pass GCC5
    # set GCC5 by default
    d.setVar('GCC_VER', 'GCC5')

    # Otherwise pass the corresponding version
    G = d.getVar('GCCVERSION',True).split(".")
    gcc_vlist = ['1', '2', '3', '4']
    if G[0] in gcc_vlist:
        d.setVar('GCC_VER', 'GCC'+G[0])
}

do_compile:class-native() {
    oe_runmake -C ${S}/edk2/BaseTools
}

do_compile:class-target() {
    export GCC5_${EDK2_ARCH}_PREFIX="${STAGING_BINDIR_TOOLCHAIN}/${TARGET_PREFIX}"
    export PACKAGES_PATH="${S}/edk2:${S}/edk2-platforms"
    export WORKSPACE="${S}"
    export EDK_TOOLS_PATH="${STAGING_BINDIR_NATIVE}/${EDK_TOOLS_DIR}"
    export CONF_PATH="${WORKSPACE}/edk2/Conf"
    export BTOOLS_PATH="${EDK_TOOLS_PATH}/BinWrappers/PosixLike"
    export IASL_PREFIX="${STAGING_BINDIR_NATIVE}/"

    # Copy the templates as we don't run envsetup
    cp ${EDK_TOOLS_PATH}/Conf/build_rule.template ${CONF_PATH}/build_rule.txt
    cp ${EDK_TOOLS_PATH}/Conf/tools_def.template ${CONF_PATH}/tools_def.txt
    cp ${EDK_TOOLS_PATH}/Conf/target.template ${CONF_PATH}/target.txt

    PATH="${WORKSPACE}:${BTOOLS_PATH}:$PATH" \
    "build" \
       -a "${EDK2_ARCH}" \
       -b ${EDK2_BUILD_MODE} \
       -t ${GCC_VER} \
       -p "${S}/${EDK2_PLATFORM_DSC}"
}

do_install:class-native() {
    install -d ${D}/${bindir}/edk2_basetools
    cp -r ${S}/edk2/BaseTools/* ${D}/${bindir}/${EDK_TOOLS_DIR}/
}

do_install:class-target() {
    install -d ${D}/firmware
    install "${S}/Build/${EDK2_PLATFORM}/${EDK2_BUILD_MODE}_${GCC_VER}/FV/${EDK2_BIN_NAME}" "${D}/firmware/${EDK2_BIN_NAME}"
}

FILES:${PN} = "/firmware"
SYSROOT_DIRS += "/firmware"
# Skip QA check for relocations in .text of elf binaries
INSANE_SKIP_${PN} = "textrel"

do_deploy() {
}
do_deploy:class-target() {
    # Copy the images to deploy directory
    cp -rf ${D}/firmware/* ${DEPLOYDIR}/
}
addtask do_deploy after do_install before do_build

BBCLASSEXTEND = "native"
