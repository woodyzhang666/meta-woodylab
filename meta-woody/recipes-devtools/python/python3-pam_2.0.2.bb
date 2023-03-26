SUMMARY = "Pure Python interface to the Pluggable Authentication Modules system on Linux"
HOMEPAGE = "https://github.com/FirefighterBlu3/python-pam"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b65882aaede54437e617c55cc8b4fa17"

SRC_URI[sha256sum] = "97235235ba9b82dbae8068d1099508455949b275f77273ca22fdbd8b1fb5d950"

PYPI_PACKAGE = "python-pam"
PYPI_SRC_URI = "https://files.pythonhosted.org/packages/6a/da/879f1c849e886b783239b8a4710daac73535ba2cfcf672ee4548543e3a74/python-pam-2.0.2.tar.gz"

inherit pypi python_hatchling
