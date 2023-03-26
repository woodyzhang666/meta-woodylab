SUMMARY = "This is a pure python implementation of IPMI protocol."
HOMEPAGE = "https://opendev.org/x/pyghmi"
LICENSE = "Apache"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI[sha256sum] = "f684837c25633220294c11b0b1be5925295704460ceaa964cb4dc156c3b5ca7f"

PYPI_SRC_URI = "https://files.pythonhosted.org/packages/4d/c4/f9b950987b30c1ceb1bf7fa16132017f0b792e965c6a702dde604ead2773/pyghmi-1.5.59.tar.gz"

inherit pypi python_hatchling

DEPENDS += "python3-pbr-native"
