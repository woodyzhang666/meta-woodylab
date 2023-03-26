SUMMARY = "The Python X Library is intended to be a fully functional X client library for Python programs"
HOMEPAGE = "https://github.com/python-xlib/python-xlib"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8975de00e0aab10867abf36434958a28"

SRC_URI[sha256sum] = "55af7906a2c75ce6cb280a584776080602444f75815a7aff4d287bb2d7018b32"

PYPI_SRC_URI = "https://files.pythonhosted.org/packages/86/f5/8c0653e5bb54e0cbdfe27bf32d41f27bc4e12faa8742778c17f2a71be2c0/python-xlib-0.33.tar.gz"
PYPI_PACKAGE = "python-xlib"

inherit setuptools3 pypi

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"
