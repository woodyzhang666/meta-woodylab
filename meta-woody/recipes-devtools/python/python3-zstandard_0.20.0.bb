SUMMARY = "Python bindings for interfacing with the Zstandard compression library."
HOMEPAGE = "https://github.com/indygreg/python-zstandard"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3ae87c50fd64b6f0942823686871e758"

SRC_URI[sha256sum] = "613daadd72c71b1488742cafb2c3b381c39d0c9bb8c6cc157aa2d5ea45cc2efc"

PYPI_SRC_URI = "https://files.pythonhosted.org/packages/02/f8/9ee010452d7be18c699ddc598237b52215966220401289c66b7897c7ecfb/zstandard-0.20.0.tar.gz"

inherit setuptools3 pypi

DEPENDS += "libffi python3-cffi-native"
